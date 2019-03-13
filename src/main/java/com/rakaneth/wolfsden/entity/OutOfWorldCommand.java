package com.rakaneth.wolfsden.entity;

public class OutOfWorldCommand implements Command {
    @Override public CommandResult execute(GameObject entity) {
        return new Done(0);
    }
}
