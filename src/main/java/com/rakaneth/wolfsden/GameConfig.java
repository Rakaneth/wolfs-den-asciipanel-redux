package com.rakaneth.wolfsden;

import squidpony.squidmath.RNG;
import squidpony.squidmath.StatefulRNG;

public class GameConfig {
    public static final int SCREEN_W = 100;
    public static final int SCREEN_H = 40;
    public static final int MAP_W = 60;
    public static final int MAP_H = 30;
    public static final int MAP_X = 0;
    public static final int MAP_Y = 0;
    public static final int STAT_W = SCREEN_W - MAP_W;
    public static final int STAT_H = MAP_H;
    public static final int STAT_X = MAP_W;
    public static final int STAT_Y = 0;
    public static final int MSG_W = MAP_W / 2;
    public static final int MSG_H = SCREEN_H - MAP_H;
    public static final int MSG_X = 0;
    public static final int MSG_Y = MAP_H;
    public static final int SKILL_W = MSG_W;
    public static final int SKILL_H = MSG_H;
    public static final int SKILL_X = MSG_W;
    public static final int SKILL_Y = MSG_Y;
    public static final int INFO_W = STAT_W;
    public static final int INFO_H = MSG_H;
    public static final int INFO_X = MAP_W;
    public static final int INFO_Y = MAP_H;
    public static final RNG RNG = new StatefulRNG(0xDEADBEEFL);
}
