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
        case KeyEvent.VK_SPACE:
            List<MenuEntry> mnu = new MenuBuilder().add("Inventory", "item").add("Status", "status")
                    .add("Magic", "magic").add("Skills", "skills").add("Party", "party")
                    .add("Long Entry to make sure width expands properly", "long").build();
            GSM.get().push(new TestMenu(mnu));
            break;
        case KeyEvent.VK_ESCAPE:
            GSM.get().pop();
            break;
        default:
            System.out.printf("Key %d pressed\n", e.getKeyCode());
        }
    }
}