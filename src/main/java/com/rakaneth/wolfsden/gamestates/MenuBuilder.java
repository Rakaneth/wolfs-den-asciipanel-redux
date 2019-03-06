package com.rakaneth.wolfsden.gamestates;

import java.util.ArrayList;
import java.util.List;

public class MenuBuilder {
    private List<MenuEntry> menu;

    public MenuBuilder() {
        menu = new ArrayList<>();
    }

    public MenuBuilder add(MenuEntry entry) {
        menu.add(entry);
        return this;
    }

    public MenuBuilder add(String display, String value) {
        menu.add(new MenuEntry(display, value));
        return this;
    }

    public List<MenuEntry> build() {
        return menu;
    }
}