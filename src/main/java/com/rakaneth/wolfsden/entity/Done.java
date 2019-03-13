package com.rakaneth.wolfsden.entity;

public class Done implements CommandResult {
    private int timeTaken;

    public Done(int timeTaken) {
        this.timeTaken = timeTaken;
    }

    public int getTimeTaken() {
        return timeTaken;
    }
}
