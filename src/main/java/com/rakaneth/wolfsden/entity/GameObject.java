package com.rakaneth.wolfsden.entity;

import squidpony.squidmath.Coord;
import squidpony.squidmath.SquidID;

import java.awt.*;

public class GameObject {
    Coord pos;
    char glyph;
    Color fg;
    Color bg;
    String name;
    String desc;
    private String id;
    int layer;
    boolean blockMove;
    boolean blockSight;
    String mapID;

    GameObject(String id) {
        pos = Coord.get(0, 0);
        glyph = '@';
        fg = null;
        bg = null;
        name = "No name";
        desc = "No desc";
        layer = 0;
        mapID = "None";
        this.id = id == null ?
            SquidID.randomUUID()
                   .toString() :
            id;
        this.blockMove = false;
        this.blockSight = false;
    }

    GameObject() {
        this(null);
    }

    public Coord getPos() {
        return pos;
    }

    public void setPos(Coord p) {
        pos = p;
    }

    public String getID() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public String getDesc() {
        return this.desc;
    }
}