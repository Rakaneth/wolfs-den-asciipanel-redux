package com.rakaneth.wolfsden.commands;

public class AlternateCommand implements CommandResult {
    private Command alternate;

    public AlternateCommand(Command cmd) {
        alternate = cmd;
    }

    public Command alternate() {
        return alternate;
    }
}
