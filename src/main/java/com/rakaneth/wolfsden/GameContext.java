package com.rakaneth.wolfsden;

import com.rakaneth.wolfsden.entity.GameObject;
import com.rakaneth.wolfsden.map.GameMap;
import squidpony.squidmath.Coord;
import squidpony.squidmath.RNG;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
        maps.put(m.getID(), m);
    }

    public void addEntity(GameObject o) {
        things.put(o.getID(), o);
    }

    public void removeEntity(GameObject o) {
        things.remove(o.getID());
    }

    public GameObject getEntity(String eID) {
        return things.get(eID);
    }

    public GameMap curMap() {
        return getMap(curMapID);
    }

    public GameObject player() {
        return getEntity("player");
    }

    public boolean isHere(GameObject o) {
        return o.getMapID()
                .equals(curMapID);
    }

    public List<GameObject> thingsAt(Coord c) {
        return things.values()
                     .stream()
                     .filter(el -> el.getPos() == c && isHere(el))
                     .collect(Collectors.toList());
    }

    public void changeMap(GameMap.MapConnection conn) {
        curMapID = conn.toMapID;
        player().move(conn.toPt);
    }

    public void changeMap(String mapID) {
        curMapID = mapID;
    }

}
