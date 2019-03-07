package com.rakaneth.wolfsden.entity;

import com.rakaneth.wolfsden.GameConfig;
import squidpony.squidmath.Coord;

import java.awt.*;

public class GameObjectFactory {
    private String id;
    private String desc;
    private String name;
    private Coord startPos;
    private char glyph;
    private Color fg;
    private Color bg;
    private boolean blockMove;
    private boolean blockSight;
    private int layer;
    private String mapID;

    public GameObjectFactory() {
        desc = "No desc";
        name = "No name";
        startPos = Coord.get(0, 0);
        glyph = '@';
        fg = null;
        bg = null;
        blockMove = false;
        blockSight = false;
        layer = 0;
        mapID = "No map id";
    }

    public GameObjectFactory setID(String id) {
        this.id = id;
        return this;
    }

    public GameObjectFactory setDesc(String desc) {
        this.desc = desc;
        return this;
    }

    public GameObjectFactory setName(String name) {
        this.name = name;
        return this;
    }

    public GameObjectFactory setGlyph(char c) {
        glyph = c;
        return this;
    }

    public GameObjectFactory setFG(Color fg) {
        this.fg = fg;
        return this;
    }

    public GameObjectFactory setBG(Color bg) {
        this.bg = bg;
        return this;
    }

    public GameObjectFactory setBlockSight() {
        this.blockSight = true;
        return this;
    }

    public GameObjectFactory setBlockMove() {
        this.blockMove = true;
        return this;
    }

    public GameObjectFactory setStartPos(Coord p) {
        this.startPos = p;
        return this;
    }

    public GameObjectFactory setLayer(int layer) {
        this.layer = layer;
        return this;
    }

    public GameObjectFactory setStartMap(String mapID) {
        this.mapID = mapID;
        return this;
    }

    public GameObject build() {
        GameObject foetus = new GameObject(id);
        foetus.name = name;
        foetus.desc = desc;
        foetus.glyph = glyph;
        foetus.fg = fg;
        foetus.bg = bg;
        foetus.pos = startPos;
        foetus.blockSight = blockSight;
        foetus.blockMove = blockMove;
        foetus.layer = layer;
        return foetus;
    }
}