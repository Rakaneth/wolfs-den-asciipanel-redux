package com.rakaneth.wolfsden.entity;

public class WaitCommand implements Command {

    @Override public CommandResult execute(GameObject entity) {
        return new Done(10);
    }
}
