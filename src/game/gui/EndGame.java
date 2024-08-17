package game.gui;

import static game.util.Constant.BG2;
import static game.util.Constant.fBold;
import static game.util.Constant.sound_state;

import javax.sound.sampled.Clip;
import javax.swing.*;
import java.awt.event.MouseEvent;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.image.BufferedImage;
import java.util.TimerTask;
import java.util.Timer;

import game.GamePlay;
import game.Frame;
import game.util.ImageManager;
import game.util.SoundManager;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class EndGame extends JPanel {

        private static final int PANEL_WIDTH = 1960;
        private static final int PANEL_HEIGHT = 1080;
        private BufferedImage Time;
        private BufferedImage Score;
        private BufferedImage Kill;
        private BufferedImage image1;
        private BufferedImage Iconwin;

        private int imageX; // ตำแหน่งในแกน X ของรูปภาพ
        private int imageY; // ตำแหน่งในแกน Y ของรูปภาพ
        private int textY; // ตำแหน่งในแกน Y ของข้อความ

        private Timer timer;

        GamePlay gp;

        public EndGame(GamePlay gp) {
                this.gp = gp;
                setPreferredSize(new Dimension(PANEL_WIDTH, PANEL_HEIGHT));
                setLayout(null);
                setBounds(0, 0, PANEL_WIDTH, PANEL_HEIGHT);
                setOpaque(false);
                setBackground(new Color(0, 0, 0, 125));

                // เล่นเพลง
                if (sound_state) {
                        Frame.getInstance().sound.stop();
                }
                Clip endGameSong = SoundManager.getClip("res/sounds/Endcredit.wav");
                Frame.getInstance().sound.setSound(endGameSong);
                Frame.getInstance().sound.play(true);

                image1 = ImageManager.load("res/images/nextlevelxx.Wpng.png");
                Iconwin = ImageManager.resizeImage("res/images/iconwin.png", 450, 450);
                Score = ImageManager.resizeImage("res/images/Score2.png", 300, 300);
                Time = ImageManager.resizeImage("res/images/Time.png", 300, 300);
                Kill = ImageManager.resizeImage("res/images/Kill.png", 300, 300);

                // ตำแหน่งเริ่มต้นของรูปภาพและข้อความ
                imageX = PANEL_WIDTH / 2 - image1.getWidth() / 2;
                imageY = PANEL_HEIGHT;
                textY = PANEL_HEIGHT + 1500;

                timer = new Timer();
                timer.schedule(new TimerTask() {
                        @Override
                        public void run() {
                                // เลื่อนรูปภาพและข้อความขึ้นไป
                                if (imageY > PANEL_HEIGHT / 2 - 420) {
                                        imageY -= 1;
                                }
                                if (textY > PANEL_HEIGHT / 2 - 120) {
                                        textY -= 1;
                                }

                                if (textY <= 420) {
                                        JLabel backButton = new JLabel("Back to menu");
                                        backButton.setFont(fBold.deriveFont(Font.BOLD, 32f));
                                        backButton.setForeground(Color.WHITE);
                                        backButton.setIcon(new ImageIcon(
                                                        ImageManager.resizeImage("res/images/arrow_back.png", 50, 50,
                                                                        180)));
                                        backButton.setBounds(5, textY + 280, 300, 300);
                                        backButton.addMouseListener(new MouseAdapter() {

                                                public void mouseClicked(MouseEvent evt) {
                                                        Frame.getInstance().sound.stop();
                                                        Frame.getInstance().clearEvent();
                                                        Frame.getInstance().changePanel(new MainPane());
                                                }
                                        });
                                        backButton.setVerticalAlignment(JLabel.CENTER);
                                        backButton.setHorizontalAlignment(JLabel.CENTER);
                                        add(backButton);

                                        // Add key listening esc to goto main
                                        Frame.getInstance().addKeyListener(new KeyAdapter() {
                                                @Override
                                                public void keyPressed(KeyEvent e) {
                                                        // If the escape key is pressed, clear the event and change the
                                                        // panel to the
                                                        // main panel
                                                        if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                                                                Frame.getInstance().sound.stop();
                                                                Frame.getInstance().clearEvent();
                                                                Frame.getInstance().changePanel(new MainPane());
                                                        }
                                                }
                                        });

                                        timer.cancel();
                                }
                                repaint(); // อัปเดตหน้าจอ

                        }
                }, 10, 10);
        }

        @Override
        protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(BG2, 0, 0, getWidth(), getHeight(), null);
                g.setColor(getBackground());
                g.fillRect(0, 0, getWidth(), getHeight());

                // วาดรูปภาพและข้อความในตำแหน่งที่กำหนด
                g.drawImage(Iconwin, imageX - 100, textY - 1330 - Iconwin.getHeight() / 2, null);
                g.drawImage(Score, imageX - 500, textY - 960 - image1.getHeight() / 2, null);
                g.drawImage(Time, imageX - 30, textY - 960 - image1.getHeight() / 2, null);
                g.drawImage(Kill, imageX + 450, textY - 960 - image1.getHeight() / 2, null);
                g.drawImage(image1, imageX, textY - 320 - image1.getHeight() / 2, null);

                Font textFont = new Font("SansSerif", Font.BOLD, 98);
                g.setFont(textFont);
                g.setColor(Color.WHITE);
                g.setFont(fBold.deriveFont(Font.BOLD, 98f)); // ปรับให้เป็นตามขนาดจอด้วย
                g.drawString("Victory Galaxy Wars",
                                PANEL_WIDTH / 2 - g.getFontMetrics().stringWidth("Victory Galaxy Wars") / 2,
                                textY - 1080);
                g.setFont(fBold.deriveFont(Font.BOLD, 42f));

                int stringWidth = g.getFontMetrics().stringWidth("Score : " + gp.getScore());
                g.drawString("    Score : " + gp.getScore(), PANEL_WIDTH / 2 - stringWidth / 2 - 500, textY - 730);

                stringWidth = g.getFontMetrics().stringWidth("Time : " + gp.getTime());
                g.drawString("    Time : " + gp.getTime(), PANEL_WIDTH / 2 - stringWidth / 2 - 30, textY - 730);

                stringWidth = g.getFontMetrics().stringWidth("Kill : " + gp.getKilled());
                g.drawString("  Kill : " + gp.getKilled(), PANEL_WIDTH / 2 - stringWidth / 2 + 450, textY - 730);

                g.setFont(fBold.deriveFont(Font.BOLD, 98f)); // ปรับให้เป็นตามขนาดจอด้วย
                g.drawString("Galaxy Wars", PANEL_WIDTH / 2 - g.getFontMetrics().stringWidth("Galaxy Wars") / 2,
                                textY - 160);

                g.setFont(fBold.deriveFont(Font.BOLD, 48f)); // ปรับให้เป็นตามขนาดจอด้วย
                g.drawString("----------------- Developer -----------------",
                                PANEL_WIDTH / 2 - g.getFontMetrics()
                                                .stringWidth("----------------- Developer -----------------") / 2,
                                textY - 70);
                Font DeveloperFont = fBold.deriveFont(Font.BOLD, 32f); // 48 เป็นขนาดตัวหนังสือ
                g.setFont(DeveloperFont);
                if (textY <= 420) {
                        // g.drawString("Back to menu", 62,
                        // textY + 447 );
                        g.drawString("Score : " + gp.getScore(), 20,
                                        textY + 500);
                        g.drawString("Time : " + gp.getTime(), 20,
                                        textY + 550);
                        g.drawString("Kill : " + gp.getKilled(), 20,
                                        textY + 600);
                }
                g.drawString("65011212122 Phothiphong Meethonglang : Code and Desgin",
                                PANEL_WIDTH / 2 - 30
                                                - g.getFontMetrics().stringWidth(
                                                                "65011212178 Nitipong Boonprasert : Code and Desgin")
                                                                / 2,
                                textY);
                g.drawString("65011212178 Nitipong Boonprasert : Code and Desgin",
                                PANEL_WIDTH / 2 - 30
                                                - g.getFontMetrics().stringWidth(
                                                                "65011212178 Nitipong Boonprasert : Code and Desgin")
                                                                / 2,
                                textY + 70);
                g.drawString("65011212148 Apidsada Laochai : Desgin and Assets",
                                PANEL_WIDTH / 2 - 30
                                                - g.getFontMetrics().stringWidth(
                                                                "65011212178 Nitipong Boonprasert : Code and Desgin")
                                                                / 2,
                                textY + 140);
                g.drawString("65011212151 Atsadawut Trakanjun : Desgin and Assets",
                                PANEL_WIDTH / 2 - 30
                                                - g.getFontMetrics().stringWidth(
                                                                "65011212178 Nitipong Boonprasert : Code and Desgin")
                                                                / 2,
                                textY + 210);
                g.drawString("65011212132 Wiritphon DuangDusan : Sound and Assets",
                                PANEL_WIDTH / 2 - 30
                                                - g.getFontMetrics().stringWidth(
                                                                "65011212178 Nitipong Boonprasert : Code and Desgin")
                                                                / 2,
                                textY + 280);

                g.setFont(fBold.deriveFont(Font.BOLD, 48f)); // ปรับให้เป็นตามขนาดจอด้วย
                g.drawString("----------------- Teacher -----------------",
                                PANEL_WIDTH / 2 - g.getFontMetrics()
                                                .stringWidth("----------------- Teacher -----------------") / 2,
                                textY + 350);
                g.setFont(fBold.deriveFont(Font.BOLD, 36f)); // ปรับให้เป็นตามขนาดจอด้วย
                g.drawString("Natthariya Laopracha",
                                PANEL_WIDTH / 2 - 30
                                                - g.getFontMetrics().stringWidth("Natthariya Laopracha") / 2,
                                textY + 420);
                g.drawString("Object Oriented Programming 1204203",
                                PANEL_WIDTH / 2 - 30
                                                - g.getFontMetrics().stringWidth("Object Oriented Programming 1204203")
                                                                / 2,
                                textY + 480);
                g.drawString("Final Project OOP : 35 Score",
                                PANEL_WIDTH / 2 - 30
                                                - g.getFontMetrics().stringWidth("Final Project OOP : 35 Score") / 2,
                                textY + 540);
        }

        // public static void main(String[] args) {
        // SwingUtilities.invokeLater(() -> {
        // JFrame frame = new JFrame("Victory");
        // frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // frame.add(new EndGame());
        // frame.pack();
        // frame.setVisible(true);
        // });
        // }
}
