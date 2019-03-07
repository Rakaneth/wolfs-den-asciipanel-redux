package com.rakaneth.wolfsden.map;

import com.rakaneth.wolfsden.entity.GameObject;
import com.rakaneth.wolfsden.entity.GameObjectFactory;
import squidpony.squidmath.Coord;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class GameMap {
    private final int width;
    private final int height;
    private final Color wallColor;
    private final Color floorColor;
    private boolean lit;
    private List<GameObject> objects;

    public GameMap(char[][] baseMap, Color wallColor, Color floorColor,
                   boolean lit, String wallName, String wallDesc,
                   String floorName, String floorDesc) {
        width = baseMap.length;
        height = baseMap[0].length;
        objects = new ArrayList<>();
        this.wallColor = wallColor;
        this.floorColor = floorColor;
        this.lit = lit;
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                Coord c = Coord.get(x, y);
                GameObject toAdd = null;
                switch (baseMap[x][y]) {
                case '#':
                    toAdd = GameObjectFactory.wall(c, wallColor,
                                                           wallName,
                                                           wallDesc);
                    break;
                case '.':
                    toAdd = GameObjectFactory.floor(c, floorColor, floorName, floorDesc);
                    break;
                case ',':
                    toAdd = GameObjectFactory.shallow(c);
                    break;
                case '~':
                    toAdd = GameObjectFactory.deep(c);
                    break;
                case ':':
                    toAdd = GameObjectFactory.bridge(c);
                    break;
                case '+':
                    toAdd = GameObjectFactory.closedDoor(c);
                    break;
                case '/':
                    toAdd = GameObjectFactory.openDoor(c);
                    break;
                case '>':
                    toAdd = GameObjectFactory.downStairs(c);
                    break;
                case '<':
                    toAdd = GameObjectFactory.upStairs(c);
                    break;
                default:
                    toAdd = GameObjectFactory.floor(c, floorColor, floorName, floorDesc);
                }
                objects.add(toAdd);
            }
        }
    }
}