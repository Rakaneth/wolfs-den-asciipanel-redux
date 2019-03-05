package com.rakaneth.wolfsden.gamestates;

import java.awt.event.KeyEvent;
import java.util.List;

import asciiPanel.AsciiPanel;

public class TestMenu implements GameState {
    private int m_width;
    private int m_height;

    public TestMenu(List<String> items) {
        m_height = items.size() + 2;
        m_width = items.stream().mapToInt(el -> el.length()).max().orElse(0);
    }

    @Override
    public String stateName() {
        return "test menu";
    }

    @Override
    public void render(AsciiPanel screen) {
        // screen.
    }

    @Override
    public void handleInput(KeyEvent e) {

    }

}