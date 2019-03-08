package com.rakaneth.wolfsden.map;

import com.rakaneth.wolfsden.GameContext;
import squidpony.squidgrid.mapping.SectionDungeonGenerator;
import squidpony.squidgrid.mapping.SerpentMapGenerator;

public class MapBuilder {
    private int rooms;
    private int rounds;
    private int caves;
    private int doorPct;
    private int waterPct;
    private int width;
    private int height;
    private boolean lit;
    private String id;
    private String name;

    public MapBuilder() {
        rooms = 0;
        rounds = 0;
        caves = 1;
        doorPct = 0;
        waterPct = 0;
        width = 30;
        height = 30;
        lit = true;
        id = "No map id";
        name = "No map name";
    }

    public MapBuilder setDimenions(int w, int h) {
        width = w;
        height = h;
        return this;
    }

    public MapBuilder setDark() {
        lit = false;
        return this;
    }

    public MapBuilder setWaterPct(int val) {
        waterPct = val;
        return this;
    }

    public MapBuilder setDoorPct(int val) {
        doorPct = val;
        return this;
    }

    public MapBuilder setRooms(int val) {
        rooms = val;
        return this;
    }

    public MapBuilder setCaves(int val) {
        caves = val;
        return this;
    }

    public MapBuilder setRounds(int val) {
        rounds = val;
        return this;
    }

    public MapBuilder setID(String id) {
        this.id = id;
        return this;
    }

    public MapBuilder setName(String name) {
        this.name = name;
        return this;
    }

    public GameMap build() {
        SerpentMapGenerator smg = new SerpentMapGenerator(width, height,
                                                          GameContext.getInstance().RNG);
        SectionDungeonGenerator decorator = new SectionDungeonGenerator(width,
                                                                        height,
                                                                        GameContext.getInstance().RNG);
        smg.putBoxRoomCarvers(rooms);
        smg.putCaveCarvers(caves);
        smg.putRoundRoomCarvers(rounds);
        char[][] base = smg.generate();
        decorator.addLake(waterPct);
        decorator.addDoors(doorPct, true);
        char[][] finalBase = decorator.generate(base, smg.getEnvironment());
        GameMap newMap = new GameMap(finalBase);
        newMap.lit = lit;
        newMap.name = name;
        newMap.id = id;
        return newMap;
    }

    static public GameMap sampleMap() {
        GameMap newMap = new MapBuilder().setID("mines")
                                         .setName("Mines - Upper")
                                         .setRooms(2)
                                         .setWaterPct(20)
                                         .setDoorPct(55)
                                         .setDimenions(85, 85)
                                         .setCaves(0)
                                         .build();
        GameContext.getInstance()
                   .addMap(newMap);
        return newMap;
    }

}
