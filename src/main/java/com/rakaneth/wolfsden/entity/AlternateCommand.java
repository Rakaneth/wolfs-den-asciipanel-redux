package com.rakaneth.wolfsden.entity;

public class AlternateCommand implements CommandResult {
    private Command alternate;

    public AlternateCommand(Command cmd) {
        alternate = cmd;
    }

    public Command getAlternate() {
        return alternate;
    }
}
