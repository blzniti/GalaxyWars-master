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

public class GameOverPanel extends JPanel {

    GamePlay gp;

    public GameOverPanel(GamePlay gp) {
        super();
        this.gp = gp;
        int width = SCREEN_WIDTH, height = SCREEN_HEIGHT;

        setLayout(null);
        setBounds(0, 0, width, height);
        setOpaque(false); // transparent
        setBackground(new Color(0, 0, 0, 125));

        OutlineLabel lblGameOver = new OutlineLabel("Game Over", 2);
        lblGameOver.setOutlineColor(Color.GRAY);
        lblGameOver.setFont(fSemiBold.deriveFont(Font.BOLD, 60f));
        lblGameOver.setForeground(Color.white);
        lblGameOver.setBounds(width / 2 - 300, height / 2 - 250, 600, 70);
        lblGameOver.setHorizontalAlignment(JLabel.CENTER);
        lblGameOver.setVerticalAlignment(JLabel.CENTER);
        add(lblGameOver);

        // reloadIcon
        JLabel reloadIcon = new JLabel();
        reloadIcon.setIcon(new ImageIcon(reloadImg));
        reloadIcon.setBounds(width / 2 - 200, height / 2 - 200, 400, 400);
        reloadIcon.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent evt) {
                gp.restart();
            }

            @Override
            public void mouseEntered(MouseEvent evt) {
                reloadIcon.setIcon(new ImageIcon(reloadImg2));
            }

            @Override
            public void mouseExited(MouseEvent evt) {
                reloadIcon.setIcon(new ImageIcon(reloadImg));
            }
        });
        add(reloadIcon);

        // back to menu
        OutlineLabel backToMenuBtn = new OutlineLabel("Back To Menu", 3);
        backToMenuBtn.setOutlineColor(Color.GRAY);
        backToMenuBtn.setFont(fRegular.deriveFont(Font.BOLD, 30f));
        backToMenuBtn.setForeground(Color.white);
        backToMenuBtn.setBounds(width / 2 - 200, height / 2 + 180, 400, 70);
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
                Frame.getInstance().changePanel(new MainPane()); // กลับไปหน้า main
            }

            public void mouseEntered(MouseEvent evt) {
                backToMenuBtn.setForeground(Color.yellow); // เมาส์อยู่บน
            }

            public void mouseExited(MouseEvent evt) {
                backToMenuBtn.setForeground(Color.white); // เมาส์อยู่นอก
            }
        });
        add(backToMenuBtn);
    }

    protected void paintComponent(Graphics g) {
        // พื้นหลังทึบ
        g.setColor(getBackground());
        g.fillRect(0, 0, getWidth(), getHeight());
        super.paintComponent(g);
    }
}
