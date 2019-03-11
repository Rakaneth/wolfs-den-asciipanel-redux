package com.rakaneth.wolfsden.map;

import com.rakaneth.wolfsden.GameConfig;
import com.rakaneth.wolfsden.GameContext;
import com.rakaneth.wolfsden.GameUtils;
import com.rakaneth.wolfsden.Swatch;
import squidpony.ArrayTools;
import squidpony.squidgrid.mapping.DungeonUtility;
import squidpony.squidmath.Coord;
import squidpony.squidmath.GreasedRegion;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class GameMap {
    private final int width;
    private final int height;
    Color wallColor;
    Color floorColor;
    boolean lit;
    private char[][] tiles;
    private double[][] resistances;
    private boolean[][] explored;
    private GreasedRegion floors;
    String name;
    String id;
    private final ArrayList<Coord> dirty;

    public enum Tile {
        FLOOR(' ', null, null),
        WALL(' ', Color.WHITE, null, true, true),
        DOOR_CLOSED('+', Color.WHITE, Swatch.WOOD, true, true),
        DOOR_OPEN('/', Color.WHITE, Swatch.WOOD),
        STAIRS_UP('<', Swatch.STAIRS, null),
        STAIRS_DOWN('>', Swatch.STAIRS, null),
        BRIDGE((char) 0xB2, Swatch.WOOD, null),
        SHALLOW(' ', null, Swatch.SHALLOW),
        DEEP(' ', null, Swatch.DEEP, false, true),
        NULL_TILE((char) 0, null, null, true, true),
        PORTAL_IN((char) 0xEA, Swatch.PORTAL, null);

        public final char glyph;
        public final Color fg;
        public final Color bg;
        public final boolean blockSight;
        public final boolean blockMove;

        Tile(char glyph, Color fg, Color bg, boolean blockSight,
             boolean blockMove) {
            this.glyph = glyph;
            this.fg = fg;
            this.bg = bg;
            this.blockMove = blockMove;
            this.blockSight = blockSight;
        }

        Tile(char glyph, Color fg, Color bg) {
            this(glyph, fg, bg, false, false);
        }
    }

    public class MapConnection {
        public final Coord toPt;
        public final String toMapID;

        public MapConnection(Coord toPt, String toMapID) {
            this.toPt = toPt;
            this.toMapID = toMapID;
        }
    }

    public enum ConnDirection {
        DOWN,
        OUT,
        UP;

        public ConnDirection opp() {
            switch (this) {
            case DOWN:
                return UP;
            case UP:
                return DOWN;
            default:
                return this;
            }
        }

    }

    private static final Map<Character, Tile> TileInfo = new HashMap<>() {
        {
            put('#', Tile.WALL);
            put('.', Tile.FLOOR);
            put('+', Tile.DOOR_CLOSED);
            put('/', Tile.DOOR_OPEN);
            put('>', Tile.STAIRS_DOWN);
            put('<', Tile.STAIRS_UP);
            put(':', Tile.BRIDGE);
            put(',', Tile.SHALLOW);
            put('~', Tile.DEEP);
            put((char) 0xEA, Tile.PORTAL_IN);
        }
    };

    private final Map<Coord, MapConnection> connections;

    public GameMap(char[][] baseMap) {
        width = baseMap.length;
        height = baseMap[0].length;
        tiles = baseMap;
        this.wallColor = null;
        this.floorColor = null;
        this.lit = false;
        floors = new GreasedRegion(tiles, '.');
        explored = ArrayTools.fill(false, width, height);
        resistances = DungeonUtility.generateResistances(tiles);
        id = "No map id";
        name = "No map name";
        connections = new HashMap<>();
        dirty = new ArrayList<>();
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                dirtyTile(x, y);
            }
        }
        lit = true;
    }

    public boolean isLit() {
        return lit;
    }

    public Color getWallColor() {
        return wallColor;
    }

    public Color getFloorColor() {
        return floorColor;
    }

    public void dirtyTile(int x, int y) {
        dirty.add(Coord.get(x, y));
    }

    public void dirtyTile(Coord c) {
        dirty.add(c);
    }

    public void cleanTile(int x, int y) {
        dirty.remove(Coord.get(x, y));
    }

    public void cleanTile(Coord c) {
        dirty.remove(c);
    }

    public ArrayList<Coord> dirtyTiles() {
        return dirty;
    }

    public void setTile(int x, int y, char c) {
        tiles[x][y] = c;
        floors.refill(tiles, '.');
        resistances = DungeonUtility.generateResistances(tiles);
    }

    public void setTile(Coord pt, char c) {
        setTile(pt.x, pt.y, c);
    }

    public boolean inBounds(int x, int y) {
        return GameUtils.between(x, 0, width - 1) && GameUtils.between(y, 0,
                                                                       height -
                                                                       1);
    }

    public boolean inBounds(Coord c) {
        return inBounds(c.x, c.y);
    }

    public Tile getTile(int x, int y) {
        if (inBounds(x, y)) {
            return TileInfo.getOrDefault(tiles[x][y], Tile.NULL_TILE);
        } else {
            return Tile.NULL_TILE;
        }
    }

    public Tile getTile(Coord c) {
        return getTile(c.x, c.y);
    }

    public String getID() {
        return id;
    }

    public String getName() {
        return name;
    }

    public boolean blockSight(Coord c) {
        return getTile(c).blockSight;
    }

    public boolean blookMove(Coord c) {
        return getTile(c).blockMove;
    }

    public void openDoor(Coord c) {
        if (getTile(c) == Tile.DOOR_CLOSED) {
            setTile(c, '/');
        }
    }

    public void closeDoor(Coord c) {
        if (getTile(c) == Tile.DOOR_OPEN) {
            setTile(c, '+');
        }
    }

    public Coord randomFloor() {
        return floors.singleRandom(GameContext.getInstance().RNG);
    }

    public Coord randomFloorNear(Coord c, int radius) {
        GreasedRegion temp = new GreasedRegion(width, height, c);
        return temp.expand8way(radius)
                   .and(floors)
                   .singleRandom(GameContext.getInstance().RNG);
    }

    public void connect(String toMapID, ConnDirection dir, boolean twoWay,
                        Coord fromPt, Coord toPt) {
        GameContext ctx = GameContext.getInstance();
        GameMap toMap = ctx.getMap(toMapID);
        if (fromPt == null) {
            fromPt = randomFloor();
        }
        if (toPt == null) {
            toPt = toMap.randomFloor();
        }
        connections.put(fromPt, new MapConnection(toPt, toMapID));
        char newTile = (char) 0;
        switch (dir) {
        case UP:
            newTile = '<';
            break;
        case DOWN:
            newTile = '>';
            break;
        case OUT:
            newTile = (char) 0xEA;
            break;
        }
        setTile(fromPt, newTile);
        if (twoWay) {
            toMap.connect(id, dir.opp(), false, toPt, fromPt);
        }
    }

    public MapConnection getConnection(Coord c) {
        return connections.get(c);
    }

    public boolean isExplored(Coord c) {
        return explored[c.x][c.y];
    }

    public void explore(Coord c) {
        explored[c.x][c.y] = true;
    }

    private int camCalc(int p, int md, int s) {
        return GameUtils.clamp(p - s / 2, 0, Math.max(0, md - s));
    }

    public Coord cam(Coord c) {
        int left = camCalc(c.x, width, GameConfig.MAP_W);
        int top = camCalc(c.y, height, GameConfig.MAP_H);
        return Coord.get(left, top);
    }

    public Coord toScreenCoord(Coord pt, Coord fromMap) {
        Coord c = cam(pt);
        return Coord.get(fromMap.x - c.x, fromMap.y - c.y);
    }

    public Coord toMapCoord(Coord pt, Coord fromScreen) {
        Coord c = cam(pt);
        return Coord.get(fromScreen.x + c.x, fromScreen.y + c.y);
    }

}