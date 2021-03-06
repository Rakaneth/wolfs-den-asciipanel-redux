package com.rakaneth.wolfsden.gamestates;

import java.util.Iterator;
import java.util.Stack;

public class GSM {
    private static GSM instance;

    private Stack<GameState> states;

    GSM() {
        states = new Stack<>();
    }

    static public GSM get() {
        if (instance == null) {
            instance = new GSM();
        }
        return instance;
    }

    public void push(GameState state) {
        states.push(state);
        state.enter();
    }

    public void pop() {
        GameState lastState = states.pop();
        lastState.exit();
    }

    public void set(GameState state) {
        for (int i = 0; i < states.size(); i++) {
            pop();
        }
        push(state);
    }

    public GameState curState() {
        return states.peek();
    }

    public boolean stackEmpty() {
        return states.empty();
    }

    public Iterator<GameState> iterator() {
        return states.iterator();
    }
}