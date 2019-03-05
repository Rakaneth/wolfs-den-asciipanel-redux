package com.rakaneth.wolfsden.gamestates;

import java.awt.event.KeyEvent;

import asciiPanel.AsciiPanel;

public class TitleState implements GameState {

    @Override
    public String stateName() {
        return "title";
    }

    @Override
    public void render(AsciiPanel screen) {
        screen.writeCenter("Welcome to Wolf's Den 1.5", 20);
    }

    @Override
    public void handleInput(KeyEvent e) {
        System.out.printf("Key %d pressed\n", e.getKeyCode());
    }
}