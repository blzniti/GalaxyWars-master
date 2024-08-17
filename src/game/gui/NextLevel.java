package game.gui;

import javax.swing.*;

import game.GamePlay;
import game.util.ImageManager;

import static game.util.Constant.PLANET_SIZE;
import static game.util.Constant.PLAYER_HEIGHT;
import static game.util.Constant.PLAYER_WIDTH;
import static game.util.Constant.fSemiBold;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

public class NextLevel extends JPanel {
    private BufferedImage LOGO;
    private BufferedImage SPACE;
    private int spaceX;
    private Timer timer;
    private long startTime;
    private boolean isRunning;

    GamePlay gp;

    public NextLevel(GamePlay gp) {
        this.gp = gp;

        isRunning = true;

        setLayout(null);
        setBounds(0, 0, gp.getWidth(), gp.getHeight());
        setOpaque(false);
        setBackground(new Color(0, 0, 0, 125));

        LOGO = ImageManager.resizeImage("res/images/nextlevelxx.Wpng.png", PLANET_SIZE * 2, PLANET_SIZE * 2);
        SPACE = ImageManager.resizeImage("res/images/player-next.png", PLAYER_WIDTH * 2, PLAYER_HEIGHT);

        spaceX = -SPACE.getWidth();

        Timer delayTimer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                startTime = System.currentTimeMillis();
                timer.start();
            }
        });
        delayTimer.setRepeats(false); // Make the timer only fire once
        delayTimer.start();

        // timer animation of space
        timer = new Timer(1000 / 60, new ActionListener() {
            // Inside the actionPerformed method of the 'timer':
            @Override
            public void actionPerformed(ActionEvent e) {
                long elapsedTime = System.currentTimeMillis() - startTime;
                double progress = (double) elapsedTime / 6000.0;

                Graphics g = getGraphics();

                if (g == null) {
                    return;
                }

                g.setFont(fSemiBold.deriveFont(Font.BOLD, 40f));
                FontMetrics fm = g.getFontMetrics();
                int stringWidth = fm.stringWidth("Next Level");
                int nPos = getWidth() / 2 - stringWidth / 2 - SPACE.getWidth() / 2;
                int lPos = getWidth() / 2 + stringWidth / 2 + SPACE.getWidth() / 2;

                if (progress < 0.125) {
                    // First phase: spaceX move to 'N' in 1.5 seconds
                    // Calculate the intermediate position for 'N' based on progress
                    spaceX = (int) (nPos * (8 * progress));
                } else if (progress < 0.75) {
                    // Second phase: spaceX move slowly to 'L' in 3 seconds with easing
                    double phaseProgress = (progress - 0.125) / 0.5; // Normalize the progress to [0, 1]
                    double easedProgress = 0.5 * (1.0 - Math.cos(phaseProgress * Math.PI)); // Apply ease-in-out easing
                    // Calculate the intermediate position for 'L' based on eased progress
                    spaceX = (int) (nPos + (lPos - nPos) * easedProgress);
                } else {
                    // Third phase: spaceX move to end in 3 seconds
                    // Calculate the intermediate position for the end based on progress
                    spaceX = (int) (lPos + (getWidth() - lPos) * (8 * (progress - 0.75)));
                }

                if (progress >= 1.0) {
                    timer.stop();
                    isRunning = false;
                }
                repaint();
            }

        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(getBackground());
        g.fillRect(0, 0, getWidth(), getHeight());

        // Draw image center
        g.drawImage(LOGO, getWidth() / 2 - LOGO.getWidth() / 2, getHeight() / 2 - (LOGO.getHeight() / 2) * 2,
                null);

        // Draw String "Next Level" center
        g.setColor(Color.white);
        g.setFont(fSemiBold.deriveFont(Font.BOLD, 40f));
        // calc String Width
        int width = g.getFontMetrics().stringWidth("Next Level");
        g.drawString("Next Level", getWidth() / 2 - width / 2, getHeight() / 2);

        // Draw image
        g.drawImage(SPACE, spaceX - SPACE.getWidth() / 2, getHeight() / 2 + (SPACE.getHeight() / 2) * 2,
                null);

        // Draw Line trail width of SPACE
        g.setColor(Color.white);
        g.fillRect(0, getHeight() / 2 + (SPACE.getHeight() / 2) * 3, spaceX - SPACE.getWidth() / 2, 5);

    }

    public boolean isRunning() {
        return isRunning;
    }
}
