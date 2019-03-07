package com.rakaneth.wolfsden.gamestates;

import asciiPanel.AsciiPanel;
import com.rakaneth.wolfsden.GameConfig;
import com.rakaneth.wolfsden.GameUtils;

import java.awt.event.KeyEvent;

public class PlayState implements GameState {

    @Override public String stateName() {
        return "play";
    }

    @Override public void render(AsciiPanel screen) {
        screen.clear();
        drawMap(screen);
        drawMsgs(screen);
        drawSkills(screen);
        drawInfo(screen);
        drawStats(screen);
    }

    @Override public void handleInput(KeyEvent e) {
        System.out.printf("Key %d pressed", e.getKeyCode());
    }

    private void drawMap(AsciiPanel screen) {
        //TODO: draw map after basic player
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
