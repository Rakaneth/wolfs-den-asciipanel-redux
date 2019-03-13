package com.rakaneth.wolfsden;

import com.rakaneth.wolfsden.entity.GameObject;
import com.rakaneth.wolfsden.map.GameMap;
import squidpony.squidmath.Coord;
import squidpony.squidmath.RNG;

import java.util.*;
import java.util.stream.Collectors;

public class GameContext {
    private static GameContext instance;
    public final RNG RNG;
    private final Map<String, GameObject> things;
    private final Map<String, GameMap> maps;
    private String curMapID;
    private List<String> messages;
    private boolean running = false;

    GameContext() {
        RNG = GameConfig.RNG;
        things = new HashMap<>();
        maps = new HashMap<>();
        messages = new ArrayList<>();
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

    public Optional<GameObject> getBlockerAt(Coord c) {
        return things.values()
                     .stream()
                     .filter(el -> el.getPos() == c && isHere(el) &&
                                   el.isBlockMove())
                     .findFirst();
    }

    public void changeMap(GameMap.MapConnection conn) {
        curMapID = conn.toMapID;
        player().move(conn.toPt);
    }

    public void changeMap(String mapID) {
        curMapID = mapID;
    }

    public void addMessage(String msg) {
        messages.add(msg);
    }

    public List<String> getMessages() {
        return messages;
    }

    public void pause() {
        running = false;
    }

    public void unpause() {
        running = true;
    }

    public boolean isRunning() {
        return running;
    }

}
