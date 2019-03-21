package com.rakaneth.wolfsden.commands;

public interface CommandResult {
    default boolean done() {
        return false;
    }

    Command alternate();
}
