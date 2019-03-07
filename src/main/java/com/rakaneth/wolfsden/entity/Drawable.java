package com.rakaneth.wolfsden.entity;

import java.awt.*;

public interface Drawable {
    char getGlyph();
    Color getFG();
    Color getBG();
    int getLayer();
}
