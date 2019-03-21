package com.rakaneth.wolfsden;

import asciiPanel.AsciiPanel;
import com.rakaneth.wolfsden.commands.Command;
import com.rakaneth.wolfsden.commands.CommandResult;
import com.rakaneth.wolfsden.gamestates.GSM;
import com.rakaneth.wolfsden.gamestates.GameState;
import com.rakaneth.wolfsden.gamestates.TitleState;

import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Program extends JFrame implements KeyListener {
    private static final long serialVersionUID = 0xDEADBEEFL;

    private AsciiPanel screen;

    private Program() {
        super();
        screen = new AsciiPanel(100, 40);
        add(screen);
        pack();
        GSM.get()
           .push(new TitleState());
        repaint();
        addKeyListener(this);
    }

    public void repaint() {
        screen.clear();
        var it = GSM.get()
                    .iterator();
        while (it.hasNext()) {
            var toDraw = it.next();
            toDraw.render(screen);
        }
        super.repaint();
    }

    public static void main(String[] args) {
        System.out.println("Test");
        Program app = new Program();
        app.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        app.setVisible(true);
    }

    @Override public void keyTyped(KeyEvent e) {

    }

    @Override public void keyPressed(KeyEvent e) {
        GameState curState = GSM.get()
                                .curState();

        Command cmd = curState.handleInput(e);
        //TODO: energy system
        var player = GameContext.getInstance()
                                .player();

        CommandResult result = cmd.execute(player);
        while (!result.done()) {
            var newCmd = result.alternate();
            result = newCmd.execute(player);
        }

        if (GSM.get()
               .stackEmpty()) {
            System.exit(0);
        } else {
            repaint();
        }
    }

    @Override public void keyReleased(KeyEvent e) {

    }

}