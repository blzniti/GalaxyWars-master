package game.gui;

import game.Frame;
import game.GamePlay;
import game.components.OutlineLabel;
import game.util.SoundManager;

import java.awt.*;
import javax.swing.*;
import static game.util.Constant.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class PausePanel extends JPanel {

    GamePlay gp;

    public PausePanel(GamePlay gp) {
        super();
        this.gp = gp;
        int width = SCREEN_WIDTH, height = SCREEN_HEIGHT;
        setLayout(null);
        setBounds(0, 0, width, height);
        setOpaque(false);
        setBackground(new Color(0, 0, 0, 125));

        OutlineLabel lblPaused = new OutlineLabel("PAUSED", 2);
        lblPaused.setOutlineColor(Color.GRAY);
        lblPaused.setFont(fSemiBold.deriveFont(Font.BOLD, 40f));
        lblPaused.setForeground(Color.white);
        lblPaused.setBounds(width / 2 - 150, height / 2 - 120, 300, 70);
        lblPaused.setHorizontalAlignment(JLabel.CENTER);
        lblPaused.setVerticalAlignment(JLabel.CENTER);
        add(lblPaused);

        // Menu "Continue", "Back to Menu" center x
        OutlineLabel continueBtn = new OutlineLabel("Continue", 3);
        continueBtn.setOutlineColor(Color.GRAY);
        continueBtn.setFont(fRegular.deriveFont(Font.BOLD, 30f));
        continueBtn.setForeground(Color.white);
        continueBtn.setBounds(width / 2 - 100, height / 2 - 50, 200, 70);
        continueBtn.setHorizontalAlignment(JLabel.CENTER);
        continueBtn.setVerticalAlignment(JLabel.CENTER);
        continueBtn.setOpaque(false);
        continueBtn.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent evt) {
                gp.togglePause(); // เล่นเกมต่อ
            }

            public void mouseEntered(MouseEvent evt) {
                continueBtn.setForeground(Color.yellow);
            }

            public void mouseExited(MouseEvent evt) {
                continueBtn.setForeground(Color.white);
            }
        });
        add(continueBtn);

        OutlineLabel backToMenuBtn = new OutlineLabel("Back To Menu", 3);
        backToMenuBtn.setOutlineColor(Color.GRAY);
        backToMenuBtn.setFont(fRegular.deriveFont(Font.BOLD, 30f));
        backToMenuBtn.setForeground(Color.white);
        backToMenuBtn.setBounds(width / 2 - 200, height / 2 + 20, 400, 70);
        backToMenuBtn.setHorizontalAlignment(JLabel.CENTER);
        backToMenuBtn.setVerticalAlignment(JLabel.CENTER);
        backToMenuBtn.setOpaque(false);
        backToMenuBtn.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent evt) {
                gp.stop();
                if (sound_state) {
                    Frame.getInstance().sound.stop();
                    Frame.getInstance().sound = new SoundManager(bgm1);
                    Frame.getInstance().sound.play(true);
                }
                Frame.getInstance().changePanel(new MainPane());
            }

            public void mouseEntered(MouseEvent evt) {
                backToMenuBtn.setForeground(Color.yellow);
            }

            public void mouseExited(MouseEvent evt) {
                backToMenuBtn.setForeground(Color.white);
            }
        });
        add(backToMenuBtn);

        // Right Sound On/Off
        JLabel soundButton = new JLabel();
        soundButton.setIcon(new ImageIcon(sound_state ? SOUND_ON : SOUND_OFF));
        soundButton.setBounds(SCREEN_WIDTH - 100, 25, 50, 50);
        soundButton.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent evt) {
                if (sound_state) {
                    soundButton.setIcon(new ImageIcon(SOUND_OFF));
                    sound_state = false;
                    Frame.getInstance().sound.stop();
                } else {
                    soundButton.setIcon(new ImageIcon(SOUND_ON));
                    sound_state = true;
                    if (sound_state) {
                        // remove sound frame
                        Frame.getInstance().sound.stop();

                        // new sound
                        Frame.getInstance().sound = new SoundManager(bgm2);
                        Frame.getInstance().sound.play(true);
                    }
                }
            }
        });
        soundButton.setHorizontalAlignment(JLabel.CENTER);
        soundButton.setVerticalAlignment(JLabel.CENTER);
        add(soundButton);
    }

    protected void paintComponent(Graphics g) {
        // bg ทึบ
        g.setColor(getBackground());
        g.fillRect(0, 0, getWidth(), getHeight());
        super.paintComponent(g);
    }
}
