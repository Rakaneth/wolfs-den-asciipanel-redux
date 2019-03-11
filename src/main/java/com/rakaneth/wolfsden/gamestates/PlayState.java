package com.rakaneth.wolfsden.gamestates;

import asciiPanel.AsciiPanel;
import com.rakaneth.wolfsden.GameConfig;
import com.rakaneth.wolfsden.GameContext;
import com.rakaneth.wolfsden.GameUtils;
import com.rakaneth.wolfsden.Swatch;
import com.rakaneth.wolfsden.entity.GameObject;
import com.rakaneth.wolfsden.entity.GameObjectFactory;
import com.rakaneth.wolfsden.map.GameMap;
import com.rakaneth.wolfsden.map.MapBuilder;
import squidpony.squidmath.Coord;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

public class PlayState implements GameState {

    @Override public String stateName() {
        return "play";
    }

    @Override public void enter() {
        GameContext ctx = GameContext.getInstance();
        MapBuilder.sampleMap();
        ctx.changeMap("mines");
        GameObject player = GameObjectFactory.samplePlayer("mines");
        ctx.addEntity(player);
        GameState.super.enter();
    }

    @Override public void render(AsciiPanel screen) {
        drawMap(screen);
        drawMsgs(screen);
        drawSkills(screen);
        drawInfo(screen);
        drawStats(screen);
    }

    @Override public void handleInput(KeyEvent e) {
        GameObject player = GameContext.getInstance()
                                       .player();
        switch (e.getKeyCode()) {
        case KeyEvent.VK_NUMPAD8:
        case KeyEvent.VK_UP:
            player.moveBy(0, -1);
            break;
        case KeyEvent.VK_NUMPAD9:
            player.moveBy(1, -1);
            break;
        case KeyEvent.VK_NUMPAD6:
        case KeyEvent.VK_RIGHT:
            player.moveBy(1, 0);
            break;
        case KeyEvent.VK_NUMPAD3:
            player.moveBy(1, 1);
            break;
        case KeyEvent.VK_NUMPAD2:
        case KeyEvent.VK_DOWN:
            player.moveBy(0, 1);
            break;
        case KeyEvent.VK_NUMPAD1:
            player.moveBy(-1, 1);
            break;
        case KeyEvent.VK_NUMPAD4:
        case KeyEvent.VK_LEFT:
            player.moveBy(-1, 0);
            break;
        case KeyEvent.VK_NUMPAD7:
            player.moveBy(-1, -1);
            break;
        default:
            System.out.printf("Key %d pressed", e.getKeyCode());
        }
    }

    private boolean inScreenBounds(int x, int y) {
        return GameUtils.between(x, 0, GameConfig.MAP_W - 1) &&
               GameUtils.between(y, 0, GameConfig.MAP_H - 1);
    }

    private boolean inScreenBounds(Coord c) {
        return inScreenBounds(c.x, c.y);
    }

    private void drawMap(AsciiPanel screen) {
        screen.clear(' ', 0, 0, GameConfig.MAP_W, GameConfig.MAP_H);
        GameContext ctx = GameContext.getInstance();
        GameObject player = ctx.player();
        GameMap curMap = ctx.curMap();
        Iterator it = curMap.dirtyTiles()
                            .iterator();
        while (it.hasNext()) {
            Coord toUpdate = (Coord) it.next();
            GameMap.Tile t = curMap.getTile(toUpdate);
            if (t == GameMap.Tile.NULL_TILE) {
                it.remove();
            } else {
                Coord screenPos = curMap.toScreenCoord(player.getPos(),
                                                       toUpdate);
                boolean shouldDraw = curMap.isLit();
                if (inScreenBounds(screenPos)) {
                    Color fg = t.fg;
                    Color bg = t.bg;
                    if (shouldDraw) {
                        if (bg == null) {
                            if (t == GameMap.Tile.WALL) {
                                bg = curMap.getWallColor();
                            } else {
                                bg = curMap.getFloorColor();
                            }
                        }
                        screen.write(t.glyph, screenPos.x, screenPos.y, fg, bg);
                    } else if (curMap.isExplored(toUpdate)) {
                        if (bg == null) {
                            if (t == GameMap.Tile.WALL) {
                                bg = Swatch.EXPLORE_WALL;
                            } else {
                                bg = Swatch.EXPLORE_FLOOR;
                            }
                        }
                        screen.write(t.glyph, screenPos.x, screenPos.y, fg, bg);
                    }
                    List<GameObject> stuff = ctx.thingsAt(toUpdate);
                    if (!stuff.isEmpty()) {
                        stuff.sort(
                            Comparator.comparingInt(GameObject::getLayer));
                        stuff.forEach(thing -> {
                            Color cbg = thing.getBG();
                            if (cbg == null) {
                                cbg = curMap.getFloorColor();
                            }
                            screen.write(thing.getGlyph(), screenPos.x,
                                         screenPos.y, thing.getFG(), cbg);
                        });
                    }
                }
                it.remove();
            }
        }
    }

    private void drawMsgs(AsciiPanel screen) {
        int x = GameConfig.MSG_X;
        int y = GameConfig.MSG_Y;
        int w = GameConfig.MSG_W;
        int h = GameConfig.MSG_H;
        GameUtils.borderArea(screen, x, y, w, h, "Messages");
    }

    private void drawSkills(AsciiPanel screen) {
        int x = GameConfig.SKILL_X;
        int y = GameConfig.SKILL_Y;
        int w = GameConfig.SKILL_W;
        int h = GameConfig.SKILL_H;
        GameUtils.borderArea(screen, x, y, w, h, "Skills");
    }

    private void drawInfo(AsciiPanel screen) {
        int x = GameConfig.INFO_X;
        int y = GameConfig.INFO_Y;
        int w = GameConfig.INFO_W;
        int h = GameConfig.INFO_H;
        GameUtils.borderArea(screen, x, y, w, h, "Info");
    }

    private void drawStats(AsciiPanel screen) {
        int x = GameConfig.STAT_X;
        int y = GameConfig.STAT_Y;
        int w = GameConfig.STAT_W;
        int h = GameConfig.STAT_H;
        GameUtils.borderArea(screen, x, y, w, h, "Stats");
    }
}
