package com.rakaneth.wolfsden.gamestates;

import asciiPanel.AsciiPanel;
import com.rakaneth.wolfsden.commands.Command;

import java.awt.event.KeyEvent;

public interface GameState {
    String stateName();

    default void exit() {
        System.out.printf("Exited %s state\n", stateName());
    }

    default void enter() {
        System.out.printf("Entered %s state\n", stateName());
    }

    void render(AsciiPanel screen);

    Command handleInput(KeyEvent e);
}