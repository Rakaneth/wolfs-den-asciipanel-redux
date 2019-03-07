package com.rakaneth.wolfsden;

import com.rakaneth.wolfsden.entity.GameObject;
import com.rakaneth.wolfsden.map.GameMap;
import squidpony.squidmath.RNG;

import java.util.HashMap;
import java.util.Map;

public class GameContext {
    private static GameContext instance;
    public final RNG RNG;
    private final Map<String, GameObject> things;
    private final Map<String, GameMap> maps;
    private String curMapID;

    GameContext() {
        RNG = GameConfig.RNG;
        things = new HashMap<>();
        maps = new HashMap<>();
    }

    public static GameContext getInstance() {
        if (instance == null) {
            instance = new GameContext();
        }
        return instance;
    }

    public GameMap getMap(String mapID) {
        return maps.get(mapID);
    }

    public void addMap(GameMap m) {
        maps.put(m.getName(), m);
    }

    public void addEntity(GameObject o) {
        things.put(o.getID(), o);
    }

    public void removeEntity(GameObject o) {
        things.remove(o.getID());
    }

    public GameMap curMap() {
        return getMap(curMapID);
    }


}
