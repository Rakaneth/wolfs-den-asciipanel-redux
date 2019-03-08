package com.rakaneth.wolfsden.entity;

public class TempBonus extends BaseStat implements Upkeep {
    private static final int FOREVER = -1;
    private int duration;

    public TempBonus(int baseVal, double mult, int duration) {
        super(baseVal, mult);
        this.duration = duration;
    }

    public TempBonus(int baseVal, double mult) {
        this(baseVal, mult, FOREVER);
    }

    public int getDuration() {
        return duration;
    }

    public boolean isExpired() {
        return duration == 0;
    }

    public void tick(int ticks) {
        if (duration > 0) {
            duration = Math.max(duration - ticks, 0);
        }
    }
}
