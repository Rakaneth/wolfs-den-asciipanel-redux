package com.rakaneth.wolfsden;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JFrame;

import com.rakaneth.wolfsden.gamestates.GSM;
import com.rakaneth.wolfsden.gamestates.GameState;
import com.rakaneth.wolfsden.gamestates.TitleState;

import asciiPanel.AsciiPanel;

public class Program extends JFrame implements KeyListener {
    private static final long serialVersionUID = 0xDEADBEEFL;

    private AsciiPanel screen;

    public Program() {
        super();
        screen = new AsciiPanel(100, 40);
        add(screen);
        pack();
        newGame();
        repaint();
        addKeyListener(this);
    }

    public void repaint() {
        GSM.get().curState().render(screen);
        super.repaint();
    }

    private void newGame() {
        GSM.get().push(new TitleState());
    }

    public static void main(String[] args) {
        System.out.println("Test");
        Program app = new Program();
        app.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        app.setVisible(true);
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        GameState curState = GSM.get().curState();
        curState.handleInput(e);
        repaint();
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

}