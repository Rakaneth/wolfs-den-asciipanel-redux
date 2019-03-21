package com.rakaneth.wolfsden.commands;

import com.rakaneth.wolfsden.entity.GameObject;

public class WaitCommand implements Command {

    @Override public CommandResult execute(GameObject entity) {
        return new Done(10);
    }
}
