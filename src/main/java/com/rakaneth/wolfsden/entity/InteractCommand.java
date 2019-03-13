package com.rakaneth.wolfsden.entity;

import com.rakaneth.wolfsden.GameContext;

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
