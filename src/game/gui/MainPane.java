package game.gui;

import javax.swing.*;

import game.components.OutlineLabel;
import game.components.ItemShows;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseAdapter;
import static game.util.Constant.*;
import game.Frame;
import game.GamePlay;

public class MainPane extends JPanel {
    public MainPane() {
        setLayout(null);
        // set Background Image
        JLabel background = new JLabel();
        Image img = new ImageIcon(BG1).getImage().getScaledInstance(SCREEN_WIDTH, SCREEN_HEIGHT, Image.SCALE_SMOOTH);
        background.setIcon(new ImageIcon(img));
        background.setBounds(0, 0, SCREEN_WIDTH, SCREEN_HEIGHT);
        background.setHorizontalAlignment(JLabel.CENTER);
        background.setVerticalAlignment(JLabel.CENTER);
        add(background);

        // Right Sound On/Off
        JLabel soundButton = new JLabel();
        soundButton.setIcon(new ImageIcon(sound_state ? SOUND_ON : SOUND_OFF));
        soundButton.setBounds(SCREEN_WIDTH - 100, 25, 50, 50);
        soundButton.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent evt) {
                if (sound_state) {
                    soundButton.setIcon(new ImageIcon(SOUND_OFF));
                    sound_state = false;
                    Frame.getInstance().sound.stop(); // หยุดเพลง
                } else {
                    soundButton.setIcon(new ImageIcon(SOUND_ON));
                    sound_state = true;
                    Frame.getInstance().sound.play(true); // เล่นเพลง
                }
            }
        });
        soundButton.setHorizontalAlignment(JLabel.CENTER);
        soundButton.setVerticalAlignment(JLabel.CENTER);
        background.add(soundButton);

        // Title OutlineLabel
        OutlineLabel title = new OutlineLabel("Galaxy Wars", 5);
        title.setFont(fSemiBold.deriveFont(Font.BOLD, 100));
        title.setForeground(Color.WHITE);
        title.setBounds(0, 50, SCREEN_WIDTH, 120);
        title.setHorizontalAlignment(JLabel.CENTER);
        title.setVerticalAlignment(JLabel.CENTER);
        title.setOutlineColor(new Color(0, 0, 0));
        background.add(title);

        // ItemShow
        ItemShows itemShow = new ItemShows(50);
        itemShow.setBounds(20, SCREEN_HEIGHT - 350, 250, 350);
        background.add(itemShow);

        // MainMenu
        OutlineLabel startBtn = new OutlineLabel("START", 2);
        startBtn.setFont(fRegular.deriveFont(Font.BOLD, 30));
        startBtn.setForeground(Color.WHITE);
        startBtn.setBounds(SCREEN_WIDTH / 2 - 100, SCREEN_HEIGHT / 2 - 60, 200, 70);
        startBtn.setHorizontalAlignment(JLabel.CENTER);
        startBtn.setVerticalAlignment(JLabel.CENTER);
        startBtn.setOutlineColor(Color.BLACK);
        startBtn.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent evt) {
                startBtn.setFont(fRegular.deriveFont(Font.BOLD, 35));
            }

            public void mouseExited(MouseEvent evt) {
                startBtn.setFont(fRegular.deriveFont(Font.BOLD, 30));
            }

            public void mouseClicked(MouseEvent evt) {
                GamePlay gp = new GamePlay();
                Frame.getInstance().changePanel(gp);
                gp.start();
            }
        });
        background.add(startBtn);

        // setting
        OutlineLabel settingBtn = new OutlineLabel("Setting", 2);
        settingBtn.setFont(fRegular.deriveFont(Font.BOLD, 30));
        settingBtn.setForeground(Color.WHITE);
        settingBtn.setBounds(SCREEN_WIDTH / 2 - 100, SCREEN_HEIGHT / 2, 200, 70);
        settingBtn.setHorizontalAlignment(JLabel.CENTER);
        settingBtn.setVerticalAlignment(JLabel.CENTER);
        settingBtn.setOutlineColor(Color.BLACK);
        settingBtn.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent evt) {
                settingBtn.setFont(fRegular.deriveFont(Font.BOLD, 35));
            }

            public void mouseExited(MouseEvent evt) {
                settingBtn.setFont(fRegular.deriveFont(Font.BOLD, 30));
            }

            public void mouseClicked(MouseEvent evt) {
                Frame.getInstance().changePanel(new Setting());
            }
        });
        background.add(settingBtn);

        // Quit Button
        OutlineLabel quitBtn = new OutlineLabel("QUIT", 2);
        quitBtn.setFont(fRegular.deriveFont(Font.BOLD, 30));
        quitBtn.setForeground(Color.WHITE);
        quitBtn.setBounds(SCREEN_WIDTH / 2 - 100, SCREEN_HEIGHT / 2 + 60, 200, 70);
        quitBtn.setHorizontalAlignment(JLabel.CENTER);
        quitBtn.setVerticalAlignment(JLabel.CENTER);
        quitBtn.setOutlineColor(Color.BLACK);
        quitBtn.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent evt) {
                quitBtn.setFont(fRegular.deriveFont(Font.BOLD, 35));
            }

            public void mouseExited(MouseEvent evt) {
                quitBtn.setFont(fRegular.deriveFont(Font.BOLD, 30));
            }

            public void mouseClicked(MouseEvent evt) {
                System.exit(0);
            }
        });
        background.add(quitBtn);
    }
}
