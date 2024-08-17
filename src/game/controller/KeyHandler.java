package game.controller;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import game.GamePlay;

public class KeyHandler implements KeyListener {

    GamePlay gp;

    public KeyHandler(GamePlay gp) {
        this.gp = gp;
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            gp.togglePause();
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }
}