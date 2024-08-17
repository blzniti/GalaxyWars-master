package game.gui;

import static game.util.Constant.BG1;
import static game.util.Constant.DifficultyStr;
import static game.util.Constant.PLANET_IMG;
import static game.util.Constant.SCREEN_HEIGHT;
import static game.util.Constant.SCREEN_WIDTH;
import static game.util.Constant.fSemiBold;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;

import game.Frame;
import game.interfaces.Difficulty;
import game.util.FileManager;
import game.util.ImageManager;
import game.util.SoundManager;

public class Setting extends JPanel {
    public Setting() {
        // Set the layout to null and set the bounds and opacity of the panel
        setLayout(null);
        setBounds(0, 0, SCREEN_WIDTH, SCREEN_HEIGHT);
        setOpaque(false);
        setBackground(new Color(0, 0, 0, 125));

        // Add key listening esc to goto main
        Frame.getInstance().addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                // If the escape key is pressed, clear the event and change the panel to the
                // main panel
                if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                    Frame.getInstance().clearEvent();
                    Frame.getInstance().changePanel(new MainPane());
                }
            }
        });

        // Create a background label and add it to the panel
        JLabel bg = new JLabel();
        bg.setBounds(0, 0, getWidth(), getHeight());
        bg.setIcon(new ImageIcon(BG1));
        add(bg);

        // Create a label for the title and add it to the background label
        JLabel st = new JLabel("Setting");
        st.setBounds(1920 / 2 - 100, 20, 300, 70);
        st.setForeground(Color.white);
        st.setFont(fSemiBold.deriveFont(40f));
        bg.add(st);

        // Create a label for the planet text and add it to the background label
        JLabel text1 = new JLabel("Planet");
        text1.setBounds(100, 200, 300, 70);
        text1.setForeground(Color.white);
        text1.setFont(fSemiBold.deriveFont(36f));
        bg.add(text1);

        // Get the planet image sources and calculate the number of planets that can fit
        // on the screen
        String[] planetSources = FileManager.getFiles("res/images/planets");
        int countPlanet = (SCREEN_WIDTH - 350) / 250; // หน้าจอจะมีดาวแสดงกี่ดวง

        // Create an array of planet labels and add them to the background label
        JLabel planetsLabel[] = new JLabel[countPlanet];
        for (int i = 0; i < countPlanet; i++) {
            // Create a label for the planet and set its properties
            planetsLabel[i] = new JLabel();
            planetsLabel[i].setIcon(new ImageIcon(ImageManager.resizeImage(planetSources[i], 200, 200, 0,
                    planetSources[i].equals(PLANET_IMG) ? 255 : 125)));
            planetsLabel[i].setBounds(350 + i * 250, 200 - 80, 200, 200);
            planetsLabel[i].setOpaque(false);
            planetsLabel[i].setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
            final int index = i;

            // Add mouse listeners to the planet label to change its appearance and set the
            // selected planet
            planetsLabel[i].addMouseListener(new MouseAdapter() {
                @Override
                public void mouseEntered(MouseEvent evt) {
                    // If the planet is disabled, return
                    if (planetSources[index].equals(PLANET_IMG)) {
                        return;
                    }
                    planetsLabel[index]
                            .setIcon(new ImageIcon(ImageManager.resizeImage(planetSources[index], 200, 200, 0, 200)));
                    repaint();
                }

                @Override
                public void mouseExited(MouseEvent evt) {
                    // If the planet is disabled, return
                    if (planetSources[index].equals(PLANET_IMG)) {
                        return;
                    }
                    planetsLabel[index]
                            .setIcon(new ImageIcon(ImageManager.resizeImage(planetSources[index], 200, 200, 0, 125)));
                    repaint();
                }

                @Override
                public void mousePressed(MouseEvent evt) {
                    // If the planet is disabled, return
                    if (planetSources[index].equals(PLANET_IMG)) {
                        return;
                    }

                    // Reset the appearance of other planets and set the selected planet
                    for (int j = 0; j < planetsLabel.length; j++) {
                        planetsLabel[j]
                                .setIcon(new ImageIcon(ImageManager.resizeImage(planetSources[j], 200, 200, 0, 125)));
                    }

                    PLANET_IMG = planetSources[index]; // set
                    planetsLabel[index]
                            .setIcon(new ImageIcon(ImageManager.resizeImage(planetSources[index], 200, 200, 0, 255)));
                }
            });
            bg.add(planetsLabel[i]);
        }

        // Create a label for the game rules text and add it to the background label
        JLabel text2 = new JLabel("Game Rules");
        text2.setBounds(100, 500, 300, 70);
        text2.setForeground(Color.white);
        text2.setFont(fSemiBold.deriveFont(36f));
        bg.add(text2);

        // Create a label for the volume text and add it to the background label
        JLabel text3 = new JLabel("Volume");
        text3.setBounds(100, 800, 300, 70);
        text3.setForeground(Color.white);
        text3.setFont(fSemiBold.deriveFont(36f));
        bg.add(text3);

        // Create a slider for the volume and add it to the background label
        JSlider slider = new JSlider(JSlider.HORIZONTAL, 0, 100, (int) SoundManager.getVolumeNumber());
        slider.setBounds(550, 800, 1100, 70);
        slider.setOpaque(false);
        slider.setMinorTickSpacing(2);
        slider.setMajorTickSpacing(10);
        slider.setPaintTicks(true);
        slider.setPaintLabels(true);
        slider.setForeground(Color.white);
        slider.setFont(fSemiBold.deriveFont(20f));
        slider.setFocusable(false); // can esc
        slider.addChangeListener(e -> {
            SoundManager.setVolumeNumber(slider.getValue());
        });
        bg.add(slider);

        // Create an array of buttons for the game difficulty and add them to the
        // background label
        JButton[] buttons = new JButton[5];
        buttons[0] = new JButton("Noob (2)");
        buttons[1] = new JButton("Easy (5)");
        buttons[2] = new JButton("Normal (8)");
        buttons[3] = new JButton("Hard (10)");
        buttons[4] = new JButton("Hell (10)");

        for (int i = 0; i < buttons.length; i++) {
            buttons[i].setBounds(550 + i * 250, 500, 200, 90);
            buttons[i].setFont(fSemiBold.deriveFont(28f));
            buttons[i].setFocusable(false);

            // Initialize the DifficultyStr
            if (buttons[i].getText().split(" ")[0].toLowerCase().equals(DifficultyStr)) {
                buttons[i].setEnabled(false);
                buttons[i].setForeground(Color.white);
                buttons[i].setBackground(new Color(0, 255, 0, 255));
            } else {
                buttons[i].setEnabled(true);
                buttons[i].setForeground(Color.black);
                buttons[i].setBackground(new Color(238, 238, 238, 255));
            }

            // event
            final int index = i;
            buttons[i].addMouseListener(new MouseAdapter() {
                // hover color change
                @Override
                public void mouseEntered(MouseEvent evt) {
                    // if disable return
                    if (!buttons[index].isEnabled()) {
                        return;
                    }
                    buttons[index].setForeground(Color.white);
                    buttons[index].setBackground(new Color(0, 0, 0, 50));
                    repaint();
                }

                @Override
                public void mouseExited(MouseEvent evt) {
                    // if disable return
                    if (!buttons[index].isEnabled()) {
                        return;
                    }
                    buttons[index].setForeground(Color.black);
                    buttons[index].setBackground(new Color(238, 238, 238, 255));
                    repaint();
                }

                // selected color
                @Override
                public void mousePressed(MouseEvent evt) {
                    // set another true reset
                    for (int j = 0; j < buttons.length; j++) {
                        if (j != index) {
                            buttons[j].setEnabled(true);
                            buttons[j].setForeground(Color.black);
                            buttons[j].setBackground(new Color(238, 238, 238, 255));
                        }
                    }

                    buttons[index].setEnabled(false);
                    buttons[index].setForeground(Color.white);
                    buttons[index].setBackground(new Color(0, 255, 0, 255));

                    // get text
                    DifficultyStr = buttons[index].getText().toLowerCase().split(" ")[0]; // save

                    // apply Difficulty
                    for (Difficulty difficulty : Difficulty.values()) {
                        if (difficulty.toString().toLowerCase().equals(DifficultyStr)) {
                            difficulty.setDifficulty();
                        }
                    }
                    repaint();
                }
            });
            bg.add(buttons[i]);
        }

        JLabel back = new JLabel();
        back.setBounds(100, 30, 60, 50);
        back.setIcon(new ImageIcon(ImageManager.resizeImage("res/images/arrow_back.png", 60, 50, 180)));
        bg.add(back);

        back.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseClicked(MouseEvent evt) {
                // กลับไปหน้า main
                Frame.getInstance().clearEvent();
                Frame.getInstance().changePanel(new MainPane());
            }

        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        // พื้นหลัง ทึบ
        g.setColor(getBackground());
        g.fillRect(0, 0, getWidth(), getHeight());
        super.paintComponent(g);
    }
}
