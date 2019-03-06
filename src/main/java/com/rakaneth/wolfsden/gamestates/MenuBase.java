package com.rakaneth.wolfsden.gamestates;

import java.awt.*;
import java.util.Comparator;
import java.util.Map;

import asciiPanel.AsciiPanel;
import com.rakaneth.wolfsden.GameConfig;

abstract public class MenuBase implements GameState {
    protected int m_width;
    protected int m_height;
    protected int mx;
    protected int my;

    public MenuBase(Map<String, String> items) {
        m_height = items.size() + 2;
        String longest = items.keySet().stream().max(Comparator.comparing(String::length)).orElse("");
        m_width = longest.length() + 2;
        mx = (GameConfig.SCREEN_W - longest.length())/2 - 1;
        my = (GameConfig.SCREEN_H - m_height)/2 - 1;
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

        for (int x=mx+1; x<m_width+mx-1; x++) {
            screen.write(hz, x, my);
            screen.write(hz, x, my+m_height);
        }

        for (int y=my+1; y<m_height+my-1; y++) {
            screen.write(vt, mx, y);
            screen.write(vt, mx+m_width, y);
        }

        screen.write(ul, mx, my);
        screen.write(ur, mx+m_width, my);
        screen.write(ll, mx, my+m_height);
        screen.write(lr, mx+m_width, my+m_height);
    }

}