package com.rakaneth.wolfsden.gamestates;

import java.awt.event.KeyEvent;
import java.util.List;

import asciiPanel.AsciiPanel;

public class TitleState implements GameState {

    @Override
    public String stateName() {
        return "title";
    }

    @Override
    public void render(AsciiPanel screen) {
        screen.clear();
        screen.writeCenter("Welcome to Wolf's Den 1.5", 20);
    }

    @Override
    public void handleInput(KeyEvent e) {
        switch (e.getKeyCode()) {
        case KeyEvent.VK_ESCAPE:
            GSM.get().pop();
            break;
        default:
            GSM.get().push(new NewGameMenu());
        }
    }
}