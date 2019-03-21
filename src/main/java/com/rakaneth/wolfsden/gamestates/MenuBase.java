package com.rakaneth.wolfsden.gamestates;

import asciiPanel.AsciiPanel;
import com.rakaneth.wolfsden.GameConfig;
import com.rakaneth.wolfsden.GameUtils;
import com.rakaneth.wolfsden.commands.Command;
import com.rakaneth.wolfsden.commands.OutOfWorldCommand;

import java.awt.event.KeyEvent;
import java.util.Comparator;
import java.util.List;

abstract public class MenuBase implements GameState {
    protected int m_width;
    protected int m_height;
    protected int mx;
    protected int my;
    protected int mx2;
    protected int my2;
    protected int selected = 0;
    List<MenuEntry> items;

    public MenuBase(List<MenuEntry> items) {
        this.items = items;
        m_height = items.size() + 2;
        String longest = items.stream()
                              .map(el -> el.getDisplay())
                              .max(Comparator.comparing(String::length))
                              .orElse("");
        m_width = longest.length() + 2;
        mx = (GameConfig.SCREEN_W - longest.length()) / 2 - 1;
        my = (GameConfig.SCREEN_H - m_height) / 2 - 1;
        mx2 = mx + m_width - 1;
        my2 = my + m_height - 1;
    }

    @Override public void render(AsciiPanel screen) {
        GameUtils.borderArea(screen, mx, my, m_width, m_height, null);
    }

    @Override public Command handleInput(KeyEvent e) {
        switch (e.getKeyCode()) {
        case KeyEvent.VK_UP:
        case KeyEvent.VK_NUMPAD8:
            if (--selected < 0) {
                selected = items.size() - 1;
            }
            break;
        case KeyEvent.VK_DOWN:
        case KeyEvent.VK_2:
            if (++selected >= items.size()) {
                selected = 0;
            }
            break;
        case KeyEvent.VK_ESCAPE:
            GSM.get()
               .pop();
            break;
        case KeyEvent.VK_ENTER:
            select(items.get(selected));
            break;
        }
        return new OutOfWorldCommand();
    }

    abstract protected void select(MenuEntry e);
}