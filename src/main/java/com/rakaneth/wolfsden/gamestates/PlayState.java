package com.rakaneth.wolfsden.gamestates;

import asciiPanel.AsciiPanel;
import com.rakaneth.wolfsden.GameConfig;
import com.rakaneth.wolfsden.GameContext;
import com.rakaneth.wolfsden.GameUtils;
import com.rakaneth.wolfsden.Swatch;
import com.rakaneth.wolfsden.commands.Command;
import com.rakaneth.wolfsden.commands.MoveByCommand;
import com.rakaneth.wolfsden.commands.OutOfWorldCommand;
import com.rakaneth.wolfsden.entity.GameObject;
import com.rakaneth.wolfsden.entity.GameObjectFactory;
import com.rakaneth.wolfsden.map.GameMap;
import com.rakaneth.wolfsden.map.MapBuilder;
import squidpony.squidmath.Coord;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.Comparator;
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
        var startPos = ctx.curMap()
                          .randomFloor();
        player.move(startPos);
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

    @Override public Command handleInput(KeyEvent e) {
        GameObject player = GameContext.getInstance()
                                       .player();
        Command cmd = new OutOfWorldCommand();
        switch (e.getKeyCode()) {
        case KeyEvent.VK_NUMPAD8:
        case KeyEvent.VK_UP:
            cmd = new MoveByCommand(0, -1);
            break;
        case KeyEvent.VK_NUMPAD9:
            cmd = new MoveByCommand(1, -1);
            break;
        case KeyEvent.VK_NUMPAD6:
        case KeyEvent.VK_RIGHT:
            cmd = new MoveByCommand(1, 0);
            break;
        case KeyEvent.VK_NUMPAD3:
            cmd = new MoveByCommand(1, 1);
            break;
        case KeyEvent.VK_NUMPAD2:
        case KeyEvent.VK_DOWN:
            cmd = new MoveByCommand(0, 1);
            break;
        case KeyEvent.VK_NUMPAD1:
            cmd = new MoveByCommand(-1, 1);
            break;
        case KeyEvent.VK_NUMPAD4:
        case KeyEvent.VK_LEFT:
            cmd = new MoveByCommand(-1, 0);
            break;
        case KeyEvent.VK_NUMPAD7:
            cmd = new MoveByCommand(-1, -1);
            break;
        default:
            System.out.printf("Key %d pressed", e.getKeyCode());
        }
        return cmd;
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
        for (int x = 0; x < GameConfig.MAP_W; x++) {
            for (int y = 0; y < GameConfig.MAP_H; y++) {
                Coord screenPos = Coord.get(x, y);
                Coord mapPos = curMap.toMapCoord(player.getPos(), screenPos);
                boolean shouldDraw = curMap.isLit();
                // TODO: || player.canSee(mapPos)
                GameMap.Tile t = curMap.getTile(mapPos);
                if (t != GameMap.Tile.NULL_TILE) {
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
                    } else if (curMap.isExplored(mapPos)) {
                        if (bg == null) {
                            if (t == GameMap.Tile.WALL) {
                                bg = Swatch.EXPLORE_WALL;
                            } else {
                                bg = Swatch.EXPLORE_FLOOR;
                            }
                        }
                        screen.write(t.glyph, screenPos.x, screenPos.y, fg, bg);
                    }
                    List<GameObject> stuff = ctx.thingsAt(mapPos);
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
