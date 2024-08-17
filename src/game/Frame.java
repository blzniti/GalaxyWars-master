package game;

import javax.swing.*;

import game.util.SoundManager;

import java.awt.Dimension;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import static game.util.Constant.*;

public class Frame extends JFrame {
    public static Frame instance;
    public SoundManager sound;

    public Frame() {
        super("Galaxy Wars");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
        setResizable(false);
        setExtendedState(JFrame.MAXIMIZED_BOTH); // fullScreen
        setIconImage(GAME_LOGO);
        setUndecorated(true); // no header

        // set cursor
        setCursor(CUSTOM_CURSOR);

        // ======= SINGLETON ======
        if (instance == null) {
            instance = this;
        }

    }

    public static Frame getInstance() {
        return instance;
    }

    public void changePanel(JPanel panel) {
        setContentPane(panel);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
        requestFocus();
    }

    public void clearEvent() {
        KeyListener[] key1 = getKeyListeners();
        for (KeyListener k : key1) {
            removeKeyListener(k);
        }

        MouseListener[] mouse1 = getMouseListeners();
        for (MouseListener m : mouse1) {
            removeMouseListener(m);
        }

        MouseMotionListener[] mouse2 = getMouseMotionListeners();
        for (MouseMotionListener m : mouse2) {
            removeMouseMotionListener(m);
        }

        // add focus MouseListening
        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                requestFocus();
            }
        });
    }
}