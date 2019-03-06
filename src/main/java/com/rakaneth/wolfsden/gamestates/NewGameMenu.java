package com.rakaneth.wolfsden.gamestates;

import java.awt.Color;

import asciiPanel.AsciiPanel;

public class NewGameMenu extends MenuBase {

    public NewGameMenu() {
        super(new MenuBuilder().add("New Game", "new").add("Continue", "continue").build());
    }

    @Override
    public String stateName() {
        return "new game";
    }

    @Override
    protected void select(MenuEntry e) {
        GSM.get().pop();
        switch (e.getValue()) {
        case "new":
            // TODO: push chargen state
            break;
        case "continue":
            // TODO: push saved game state
            break;
        }
    }

    @Override
    public void render(AsciiPanel screen) {
        super.render(screen);
        for (int row = 0; row < this.items.size(); row++) {
            Color fg = (selected == row) ? Color.CYAN : null;
            screen.write(items.get(row).getDisplay(), mx + 1, my + row + 1, fg);
        }
    }
}