package com.rakaneth.wolfsden.entity;

import com.rakaneth.wolfsden.Swatch;
import squidpony.squidmath.Coord;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

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
    private GameObject.GameObjectType type;
    final private Map<String, Integer> statBlock;

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
        type = GameObject.GameObjectType.CREATURE;
        statBlock = new HashMap<>();
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

    public GameObjectFactory setObjectType(GameObject.GameObjectType type) {
        this.type = type;
        return this;
    }

    public GameObjectFactory setStat(String statID, int baseVal) {
        statBlock.put(statID, baseVal);
        return this;
    }

    public GameObjectFactory setBaseCreatureStats(int str, int stam, int spd,
                                                  int skl, int sag, int smt) {
        return setStat("str", str).setStat("stam", stam)
                                  .setStat("spd", spd)
                                  .setStat("skl", skl)
                                  .setStat("sag", sag)
                                  .setStat("smt", smt);

    }

    public GameObject build() {
        GameObject foetus = new GameObject(type, id);
        foetus.name = name;
        foetus.desc = desc;
        foetus.glyph = glyph;
        foetus.fg = fg;
        foetus.bg = bg;
        foetus.pos = startPos;
        foetus.blockSight = blockSight;
        foetus.blockMove = blockMove;
        foetus.layer = layer;
        foetus.mapID = mapID;
        statBlock.forEach((id, val) -> {
            foetus.addStat(id, new Stat(val));
        });
        return foetus;
    }

    static public GameObject samplePlayer(String mapID) {
        return new GameObjectFactory().setBlockMove()
                                      .setName("Braw")
                                      .setDesc("Wolfborn of Fang Wood")
                                      .setStartMap(mapID)
                                      .setFG(Swatch.WOLF)
                                      .setID("player")
                                      .setLayer(4)
                                      .setObjectType(
                                          GameObject.GameObjectType.CREATURE)
                                      .setBaseCreatureStats(15, 15, 10, 5, 10,
                                                            5)
                                      .build();
    }
}