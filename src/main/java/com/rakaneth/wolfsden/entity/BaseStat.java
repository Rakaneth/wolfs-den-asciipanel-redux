package com.rakaneth.wolfsden.entity;

public class BaseStat {
    protected int baseValue;
    protected double mult;

    public BaseStat(int baseValue, double mult) {
        this.baseValue = baseValue;
        this.mult = mult;
    }

    public int getBaseValue() {
        return baseValue;
    }

    public double getMult() {
        return mult;
    }
}
