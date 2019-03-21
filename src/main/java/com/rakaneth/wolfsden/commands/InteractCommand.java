package com.rakaneth.wolfsden.commands;

import com.rakaneth.wolfsden.GameContext;
import com.rakaneth.wolfsden.entity.GameObject;

public class InteractCommand implements Command {
    private GameObject blocker;

    public InteractCommand(GameObject blocker) {
        this.blocker = blocker;
    }

    @Override public CommandResult execute(GameObject entity) {
        //TODO: real interaction mechanics
        GameContext.getInstance()
                   .addMessage(entity.getName() + " interacted with " +
                               blocker.getName() + ".");
        return new Done(10);
    }
}
