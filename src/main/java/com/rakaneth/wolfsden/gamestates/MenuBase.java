package com.rakaneth.wolfsden.gamestates;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.Comparator;
import java.util.List;

import asciiPanel.AsciiPanel;
import com.rakaneth.wolfsden.GameConfig;

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
        String longest = items.stream().map(el -> el.getDisplay()).max(Comparator.comparing(String::length)).orElse("");
        m_width = longest.length() + 3;
        mx = (GameConfig.SCREEN_W - longest.length()) / 2 - 1;
        my = (GameConfig.SCREEN_H - m_height) / 2 - 1;
        mx2 = mx + m_width - 1;
        my2 = my + m_height - 1;
    }

    @Override
    public void render(AsciiPanel screen) {
        screen.clear(' ', mx, my, m_width, m_height, Color.BLACK, Color.BLACK);
        char ul = 0xC9;
        char ur = 0xBB;
        char ll = 0xC8;
        char lr = 0xBC;
        char hz = 0xCD;
        char vt = 0xBA;

        for (int x = mx + 1; x < mx2; x++) {
            screen.write(hz, x, my);
            screen.write(hz, x, my2);
        }

        for (int y = my + 1; y < my2; y++) {
            screen.write(vt, mx, y);
            screen.write(vt, mx2, y);
        }

        screen.write(ul, mx, my);
        screen.write(ur, mx2, my);
        screen.write(ll, mx, my2);
        screen.write(lr, mx2, my2);

        for (int row = 0; row < this.items.size(); row++) {
            screen.write(items.get(row).getDisplay(), mx + 2, my + row + 1);
            if (selected == row) {
                screen.write('>', mx + 1, my + row + 1);
            }
        }
    }

    @Override
    public void handleInput(KeyEvent e) {
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
            GSM.get().pop();
            break;
        case KeyEvent.VK_ENTER:
            select(items.get(selected));
            break;
        }
    }

    abstract protected void select(MenuEntry e);
}