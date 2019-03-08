package com.rakaneth.wolfsden;

import asciiPanel.AsciiPanel;

import java.awt.*;

public final class GameUtils {
    public static <T extends Comparable<T>> T clamp(T val, T low, T high) {
        if (val.compareTo(low) < 0) {
            return low;
        } else if (val.compareTo(high) > 0) {
            return high;
        } else {
            return val;
        }
    }

    public static <T extends Comparable<T>> boolean between(T val, T low,
                                                            T high) {
        return clamp(val, low, high) == val;
    }

    public static void borderArea(AsciiPanel screen, int x, int y, int w, int h,
                                  String caption) {
        int x2 = x + w - 1;
        int y2 = y + h - 1;
        screen.clear(' ', x, y, w, h, Color.BLACK, Color.BLACK);
        char ul = 0xC9;
        char ur = 0xBB;
        char ll = 0xC8;
        char lr = 0xBC;
        char hz = 0xCD;
        char vt = 0xBA;

        for (int lx = x + 1; lx < x2; lx++) {
            screen.write(hz, lx, y);
            screen.write(hz, lx, y2);
        }

        for (int ly = y + 1; ly < y2; ly++) {
            screen.write(vt, x, ly);
            screen.write(vt, x2, ly);
        }

        screen.write(ul, x, y);
        screen.write(ur, x2, y);
        screen.write(ll, x, y2);
        screen.write(lr, x2, y2);

        if (caption != null) {
            screen.write(caption, x + 1, y);
        }
    }
}
