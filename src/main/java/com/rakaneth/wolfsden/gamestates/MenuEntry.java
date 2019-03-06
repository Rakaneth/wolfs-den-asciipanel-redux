package com.rakaneth.wolfsden.gamestates;

public class MenuEntry {
    private String display;
    private String value;

    public MenuEntry(String display, String value) {
        this.display = display;
        this.value = value;
    }

    public String getDisplay() {
        return display;
    }

    public String getValue() {
        return value;
    }
}