package com.rakaneth.wolfsden.commands;

import com.rakaneth.wolfsden.entity.GameObject;

public interface Command {
    CommandResult execute(GameObject entity);
}
