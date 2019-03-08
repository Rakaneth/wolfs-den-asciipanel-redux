package com.rakaneth.wolfsden.entity;

import java.util.ArrayList;
import java.util.List;

public class Stat extends BaseStat implements Upkeep {
    private List<TempBonus> preBonuses;
    private List<TempBonus> postBonuses;
    private boolean dirty;
    private int val;
    public static final Stat ZERO_STAT = new Stat(0);

    public Stat(int baseVal) {
        super(baseVal, 0);
        dirty = true;
        preBonuses = new ArrayList<>();
        postBonuses = new ArrayList<>();
    }

    private void setDirty() {
        if (this != ZERO_STAT) {
            dirty = true;
        }
    }

    public void addPreBonus(TempBonus bonus) {
        preBonuses.add(bonus);
        setDirty();
    }

    public void addPostBonus(TempBonus bonus) {
        postBonuses.add(bonus);
        setDirty();
    }

    public void removePostBonus(TempBonus bonus) {
        postBonuses.remove(bonus);
        setDirty();
    }

    public void removePreBonus(TempBonus bonus) {
        preBonuses.remove(bonus);
        setDirty();
    }

    public void setBaseValue(int val) {
        baseValue = val;
        setDirty();
    }

    public int getValue() {
        if (dirty) {
            double acc = baseValue;
            int preBase = preBonuses.stream()
                                    .mapToInt(TempBonus::getBaseValue)
                                    .sum();
            double preMult = preBonuses.stream()
                                       .mapToDouble(TempBonus::getMult)
                                       .sum();
            int postBase = postBonuses.stream()
                                      .mapToInt(TempBonus::getBaseValue)
                                      .sum();
            double postMult = postBonuses.stream()
                                         .mapToDouble(TempBonus::getMult)
                                         .sum();
            acc *= (1 + preMult);
            acc += preBase;
            acc *= (1 + postMult);
            acc += postBase;
            val = (int) acc;
            dirty = false;
        }
        return val;
    }

    public void tick(int ticks) {
        preBonuses.removeIf(b -> {
            b.tick(ticks);
            boolean ex = b.isExpired();
            if (ex) {
                setDirty();
            }
            return ex;
        });
        postBonuses.removeIf(b -> {
            b.tick(ticks);
            boolean ex = b.isExpired();
            if (ex) {
                setDirty();
            }
            return ex;
        });
    }

}
