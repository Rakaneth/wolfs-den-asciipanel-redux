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

    public void setLayer(int layer) {
        this.layer = layer;
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



    static public GameObject wall(Coord c, Color wallColor, String name,
                                  String desc) {
        return new GameObjectFactory().setFG(Color.WHITE)
                                      .setBG(wallColor)
                                      .setGlyph('#')
                                      .setName(name)
                                      .setDesc(desc)
                                      .setStartPos(c)
                                      .setBlockSight()
                                      .setBlockMove()
                                      .build();
    }

    static public GameObject floor(Coord c, Color floorColor, String name,
                                   String desc) {
        return new GameObjectFactory().setBG(floorColor)
                                      .setGlyph(' ')
                                      .setStartPos(c)
                                      .setName(name)
                                      .setDesc(desc)
                                      .build();
    }

    static public GameObject shallow(Coord c) {
        return new GameObjectFactory().setBG(Color.CYAN)
                                      .setGlyph(' ')
                                      .setStartPos(c)
                                      .setName("shallow water")
                                      .setDesc("A body of shallow water")
                                      .build();
    }

    static public GameObject deep(Coord c) {
        return new GameObjectFactory().setBG(Color.BLUE)
                                      .setGlyph(' ')
                                      .setStartPos(c)
                                      .setName("deep water")
                                      .setDesc("A body of deep water")
                                      .setBlockMove()
                                      .build();
    }

    static public GameObject bridge(Coord c) {
        return new GameObjectFactory().setBG(GameConfig.SEPIA)
                                      .setFG(Color.WHITE)
                                      .setGlyph(':')
                                      .setStartPos(c)
                                      .setName("bridge")
                                      .setDesc("A length of bridge")
                                      .build();
    }

    static public GameObject closedDoor(Coord c) {
        return new GameObjectFactory().setBG(GameConfig.SEPIA)
                                      .setFG(Color.WHITE)
                                      .setGlyph('+')
                                      .setStartPos(c)
                                      .setName("closed door")
                                      .setDesc("A closed door")
                                      .setBlockSight()
                                      .setBlockMove()
                                      .build();
    }

    static public GameObject openDoor(Coord c) {
        return new GameObjectFactory().setBG(GameConfig.SEPIA)
                                      .setFG(Color.WHITE)
                                      .setGlyph('/')
                                      .setStartPos(c)
                                      .setName("open door")
                                      .setDesc("An open door")
                                      .build();
    }

    static public GameObject downStairs(Coord c) {
        return new GameObjectFactory().setFG(Color.YELLOW)
                                      .setGlyph('>')
                                      .setStartPos(c)
                                      .setName("stairs")
                                      .setDesc("stairs downward")
                                      .build();
    }

    static public GameObject upStairs(Coord c) {
        return new GameObjectFactory().setFG(Color.YELLOW)
                                      .setGlyph('<')
                                      .setStartPos(c)
                                      .setName("stairs")
                                      .setDesc("stairs upward")
                                      .build();
    }
}