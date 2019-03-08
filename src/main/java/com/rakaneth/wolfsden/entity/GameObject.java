package com.rakaneth.wolfsden.entity;

import com.rakaneth.wolfsden.GameContext;
import com.rakaneth.wolfsden.map.GameMap;
import squidpony.squidmath.Coord;
import squidpony.squidmath.SquidID;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class GameObject {
    Coord pos;
    char glyph;
    Color fg;
    Color bg;
    String name;
    String desc;
    String mapID;
    private String id;
    int layer;
    boolean blockMove;
    boolean blockSight;
    final Map<String, Stat> statBlock;
    final GameObjectType type;

    public enum GameObjectType {
        CREATURE,
        ITEM,
        EQUIPMENT
    }

    GameObject(GameObjectType type, String id) {
        this.type = type;
        pos = Coord.get(0, 0);
        glyph = '@';
        fg = null;
        bg = null;
        name = "No name";
        desc = "No desc";
        layer = 0;
        mapID = "None";
        if (id == null) {
            this.id = SquidID.randomUUID()
                             .toString();
        } else {
            this.id = id;
        }
        this.blockMove = false;
        this.blockSight = false;
        statBlock = new HashMap<>();
    }

    GameObject(GameObjectType type) {
        this(type, null);
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

    public GameMap getMap() {
        return GameContext.getInstance()
                          .getMap(mapID);
    }

    public Stat getStat(String statID) {
        return statBlock.getOrDefault(statID, Stat.ZERO_STAT);
    }

    public int getStatValue(String statID) {
        return statBlock.getOrDefault(statID, Stat.ZERO_STAT)
                        .getValue();
    }

    public void addStat(String statID, Stat stat) {
        statBlock.put(statID, stat);
    }

    public void removeStat(String statID) {
        statBlock.remove(statID);
    }

    public String getMapID() {
        return mapID;
    }

    public int getLayer() {
        return layer;
    }

    public Color getFG() {
        return fg;
    }

    public Color getBG() {
        return bg;
    }

    public char getGlyph() {
        return glyph;
    }

    public void move(Coord c) {
        getMap().dirtyTile(pos);
        pos = c;
        getMap().dirtyTile(c);
    }

    public void moveBy(int dx, int dy) {
        int x = Math.max(0, dx + pos.x);
        int y = Math.max(0, dx + pos.y);
        move(Coord.get(x, y));
    }
}