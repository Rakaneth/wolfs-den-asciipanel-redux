package com.rakaneth.wolfsden.commands;

public class Done implements CommandResult {
    private int timeTaken;

    public Done(int timeTaken) {
        this.timeTaken = timeTaken;
    }

    public int getTimeTaken() {
        return timeTaken;
    }

    @Override public boolean done() {
        return true;
    }

    @Override public Command alternate() {
        return null;
    }
}
