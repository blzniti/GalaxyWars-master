package game.controller;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import game.GamePlay;
import game.entity.Player;

public class MouseHandler implements MouseMotionListener, MouseListener {

    GamePlay gp;
    Player player;

    public MouseHandler(GamePlay gp) {
        this.gp = gp;
        player = gp.getPlayer();
    }

    @Override
    public void mouseDragged(MouseEvent e) {

        if (gp.isPause()) {
            return;
        }
        player.update(e);
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        player.update(e);
    }

    @Override
    public void mouseClicked(MouseEvent e) {

        if (gp.isPause()) {
            return;
        }

        player.update(e);
    }

    @Override
    public void mousePressed(MouseEvent e) {
        gp.setHold(true);

        if (gp.isPause()) {
            return;
        }

        player.update(e);

        player.shoot(e);
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        gp.setHold(false);

        if (gp.isPause()) {
            return;
        }

        player.update(e);
        player.stopThread();
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }

}
