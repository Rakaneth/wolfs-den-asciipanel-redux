package com.rakaneth.wolfsden.gamestates;

import java.util.List;

public class TestMenu extends MenuBase {

    public TestMenu(final List<MenuEntry> items) {
        super(items);
    }

    @Override
    public String stateName() {
        return "test menu";
    }

    @Override
    protected void select(MenuEntry e) {
        System.out.printf("Selected entry %s with value %s\n", e.getDisplay(), e.getValue());
    }
}
