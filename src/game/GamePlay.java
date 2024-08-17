package game;

import javax.swing.*;

import game.controller.KeyHandler;
import game.controller.MouseHandler;
import game.entity.Bullet;
import game.entity.Item;
import game.entity.Meteor;
import game.entity.Planet;
import game.entity.Player;
import game.gui.EndGame;
import game.gui.GameOverPanel;
import game.gui.NextLevel;
import game.gui.PausePanel;
import game.interfaces.Difficulty;
import game.interfaces.ItemType;
import game.util.SoundManager;

import java.awt.*;
import static game.util.Constant.*;
import java.lang.Runnable;
import java.util.ArrayList;
import java.util.TimerTask;
import java.util.Timer;

public class GamePlay extends JPanel implements Runnable {
    // GAME
    private boolean gameOver;
    private boolean isPause;
    private Thread thread;
    private int seconds;
    private int score;
    private int level;
    private int spawnedMeteor; //
    private int amountMeteorEnough;
    private int maxMeteorLevel;
    private long lastTimeMeteorSpawn;
    private long nextDelaySpawn;
    private Timer timer;
    private boolean inTimer = false;
    private boolean hold = false;
    private int killed;
    private boolean inEndgame;

    // ITEM
    private int automaticTime;
    private int freezeTime;
    private int protectTime;

    // threadItem counter time
    Timer automaticTimer;
    Timer freezeTimer;
    Timer protectTimer;

    // Object
    private int health;
    private Planet planet;
    private Player player;

    // List
    private ArrayList<Bullet> bullets;
    private ArrayList<Meteor> meteors;
    private ArrayList<Item> items;

    // Controller
    private KeyHandler keyHandler;
    private MouseHandler mouseHandler;

    // GUI
    private PausePanel pausePanel;
    private GameOverPanel gameOverPanel;

    public GamePlay() {
        setSize(getPreferredSize());
        setLayout(null);

        inTimer = false;

        if (sound_state) {
            // remove sound frame
            Frame.getInstance().sound.stop();

            // new sound
            Frame.getInstance().sound = new SoundManager(bgm2);
            Frame.getInstance().sound.play(true);
        }

    }

    private void init() {
        setLayout(null);
        removeAll();
        repaint();

        Difficulty.setDifficulty(DifficultyStr);

        gameOver = false;
        isPause = false;
        inEndgame = false;

        freezeTime = 0;
        protectTime = 0;
        automaticTime = 0;

        automaticTimer = null;
        freezeTimer = null;
        protectTimer = null;

        seconds = -3;

        score = 0;
        killed = 0;

        level = 0;
        health = MAX_HEALTH;
        lastTimeMeteorSpawn = System.currentTimeMillis();

        spawnedMeteor = 0;
        maxMeteorLevel = METEOR_PER_LEVEL * level;
        amountMeteorEnough = maxMeteorLevel;
        nextDelaySpawn = 0;

        planet = new Planet(this);
        player = new Player(this);

        bullets = new ArrayList<Bullet>();
        meteors = new ArrayList<Meteor>();
        items = new ArrayList<Item>();

        keyHandler = new KeyHandler(this);
        Frame.getInstance().addKeyListener(keyHandler);

        mouseHandler = new MouseHandler(this);
        Frame.getInstance().addMouseListener(mouseHandler);
        Frame.getInstance().addMouseMotionListener(mouseHandler);
    }

    public void start() {
        init();
        thread = new Thread(this);

        SoundManager.play(COUNT_SOUND);

        if (inTimer)
            return;

        inTimer = true;

        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                level++;
                maxMeteorLevel = METEOR_PER_LEVEL * level;
                amountMeteorEnough = maxMeteorLevel;
                spawnedMeteor = 0;
                nextDelaySpawn = random(100, ENEMY_SPAWN_DELAY);
                METEOR_SPEED = Math.min(level, 5);
                timer.cancel();
                inTimer = false;
                seconds = 0;
            }
        }, 3500);

        thread.start();
    }

    public void stop() {
        if (thread != null) {
            thread = null;
        }
        // remove other components
        removeAll();
        Frame.getInstance().removeKeyListener(keyHandler);
        Frame.getInstance().removeMouseListener(mouseHandler);
        Frame.getInstance().removeMouseMotionListener(mouseHandler);
    }

    public void gameOver() {
        repaint();
        SoundManager.play(DEAD_SOUND);
        gameOverPanel = new GameOverPanel(this);
        gameOverPanel.setBounds(0, 0, SCREEN_WIDTH, SCREEN_HEIGHT);
        add(gameOverPanel);

        Frame.getInstance().removeKeyListener(keyHandler);
        Frame.getInstance().removeMouseListener(mouseHandler);
        Frame.getInstance().removeMouseMotionListener(mouseHandler);
    }

    public void restart() {
        stop();
        start();
    }

    public void togglePause() {
        isPause = !isPause;
    }

    public boolean isPause() {
        return isPause;
    }

    private void update() {
        planet.update();

        // bullet for i
        for (int i = 0; i < bullets.size(); i++) {
            Bullet bullet = bullets.get(i);
            if (bullet == null) {
                bullets.remove(i);
                i--;
                continue; // add this line to skip the rest of the loop
            }

            bullet.update();

            if (bullet.isOutOfScreen()) {
                bullets.remove(i);
                i--;
            }
        }

        // meteor for i
        for (int i = 0; i < meteors.size(); i++) {
            Meteor meteor = meteors.get(i);
            meteor.update();

            if (meteor.exploding) {
                continue;
            } else if (meteor.destroyed) {
                meteors.remove(i);
                i--;
            } else if (meteor.intersect(planet)) {
                if (!isProtected()) {
                    health--;
                }
                meteor.explosion();
                amountMeteorEnough--;
            }
        }

        // meteor and bullet
        for (int i = 0; i < meteors.size(); i++) {
            Meteor meteor = meteors.get(i);
            if (meteor.exploding) {
                continue;
            }
            for (int j = 0; j < bullets.size(); j++) {
                Bullet bullet = bullets.get(j);
                if (bullet.intersect(meteor)) {
                    killed++;
                    int spawnX = meteor.getX();
                    int spawnY = meteor.getY();
                    spawnItem(spawnX, spawnY);
                    meteor.explosion();
                    bullets.remove(j);
                    score += 10;
                    amountMeteorEnough--;
                    break;
                }
            }
        }

        // item update
        for (int i = 0; i < items.size(); i++) {
            Item item = items.get(i);
            item.update();
            // if touch planet planet
            if (item.intersect(planet)) {
                applyEffect(item.getItem());
                items.remove(i);
                i--;
            }
        }

        if (spawnedMeteor == maxMeteorLevel && meteors.size() == 0) {
            amountMeteorEnough = 0;
        }

        // spawn ENEMY_SPAWN_DELAY
        if (System.currentTimeMillis() - lastTimeMeteorSpawn > nextDelaySpawn && spawnedMeteor < maxMeteorLevel
                && spawnedMeteor < MAX_COUNT_METEOR) {
            lastTimeMeteorSpawn = System.currentTimeMillis();
            meteors.add(new Meteor(this));
            spawnedMeteor++;
            nextDelaySpawn = random(100, ENEMY_SPAWN_DELAY - level * 10);
        }

        // level up
        if (level != 0 && spawnedMeteor == maxMeteorLevel && amountMeteorEnough <= 0) {
            if (!inTimer) {
                inTimer = true;
                timer = new Timer();
                timer.schedule(new TimerTask() {
                    @Override
                    public void run() {

                        if (isPause || gameOver) {
                            return;
                        }

                        if (isFinished()) {
                            inEndgame = true;
                            removeAll();
                            Frame.getInstance().removeKeyListener(keyHandler);
                            Frame.getInstance().removeMouseListener(mouseHandler);
                            Frame.getInstance().removeMouseMotionListener(mouseHandler);
                            Frame.getInstance().changePanel(new EndGame(GamePlay.this));
                            timer.cancel();
                            return;
                        }

                        NextLevel nextLevel = new NextLevel(GamePlay.this);
                        nextLevel.setBounds(0, 0, SCREEN_WIDTH, SCREEN_HEIGHT);
                        add(nextLevel);

                        // play Sound
                        SoundManager.play(LEVEL_UP);

                        while (nextLevel.isRunning()) {
                            try {
                                Thread.sleep(100);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }

                        remove(nextLevel);

                        // health = MAX_HEALTH;

                        // delay 1 sec
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }

                        level++;
                        maxMeteorLevel = METEOR_PER_LEVEL * level;
                        amountMeteorEnough = maxMeteorLevel;
                        spawnedMeteor = 0;
                        nextDelaySpawn = random(100, ENEMY_SPAWN_DELAY);
                        METEOR_SPEED = Math.min(level, 5);
                        timer.cancel();
                        inTimer = false;
                    }
                }, 2000);
            }
        }

        if (health <= 0) {
            gameOver = true;
        }
    }

    private void spawnItem(int spawnX, int spawnY) {
        // calc rate
        int rate = random(1, 100);
        if (rate <= DROP_RATE * level) {
            // index Item
            int index = random(0, ItemType.values().length - 1);
            ItemType itemType = ItemType.values()[index];

            Item item = new Item(this, itemType, spawnX, spawnY, planet.getPoint());
            items.add(item);
        }
    }

    public Player getPlayer() {
        return player;
    }

    public void addBullet(Bullet bullet) {
        bullets.add(bullet);
    }

    private int random(int from, long enemySpawnDelay) {
        return (int) (Math.floor(Math.random() * (enemySpawnDelay - from + 1)) + from);
    }

    public void applyEffect(ItemType item) {
        switch (item) {
            case FREEZE:
                freezeTime = item.getEffectTime();

                // del old timer
                if (freezeTimer != null) {
                    freezeTimer.cancel();
                }

                freezeTimer = new Timer();
                freezeTimer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        if (inTimer || isPause) {
                            return;
                        }

                        freezeTime--;
                        if (freezeTime <= 0) {
                            freezeTimer.cancel();
                            freezeTimer = null;
                        }
                    }
                }, 0, 1);

                // play sound
                SoundManager.play(FREEZE_SOUND);

                break;

            case SHIELD:
                protectTime = item.getEffectTime();

                // del old timer
                if (protectTimer != null) {
                    protectTimer.cancel();
                }

                protectTimer = new Timer();
                protectTimer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        if (inTimer || isPause) {
                            return;
                        }

                        protectTime--;
                        if (protectTime <= 0) {
                            protectTimer.cancel();
                            protectTimer = null;
                        }
                    }
                }, 0, 1);

                break;

            case AUTOMATIC:
                automaticTime = item.getEffectTime();

                // del old timer
                if (automaticTimer != null) {
                    automaticTimer.cancel();
                }

                automaticTimer = new Timer();
                automaticTimer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        if (inTimer || isPause) {
                            return;
                        }

                        automaticTime--;
                        if (automaticTime <= 0) {
                            automaticTimer.cancel();
                            automaticTimer = null;
                        }
                    }
                }, 0, 1);

                break;

            case HEART:
                if (health + item.getEffectTime() > MAX_HEALTH) {
                    health = MAX_HEALTH;
                } else {
                    health += item.getEffectTime();
                }
                break;
            default:
                break;
        }

        // play Sound
        SoundManager.play(ITEM_COLLECT);
    }

    public boolean isFreeze() {
        return freezeTime > 0;
    }

    public boolean isAutomatic() {
        return automaticTime > 0;
    }

    public boolean isProtected() {
        return protectTime > 0;
    }

    public boolean isFinished() {
        if (isPause || gameOver) {
            return false;
        }
        return level >= MAX_LEVEL;
    }

    public String getTime() {
        return String.format("%02d:%02d:%02d", seconds / 3600, (seconds % 3600) / 60, seconds % 60);
    }

    public String getScore() {
        return String.format("%,d", score);

    }

    public String getKilled() {
        return String.format("%,d", killed);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(BG2, 0, 0, null);
        Graphics2D graphics2d = (Graphics2D) g;

        // Time Counter Center Top "00:00:00"
        graphics2d.setColor(Color.white);
        graphics2d.setFont(fSemiBold.deriveFont(Font.BOLD, 30f));

        String timeString = String.format("%02d:%02d:%02d", seconds / 3600, (seconds % 3600) / 60, seconds % 60);
        int width = graphics2d.getFontMetrics().stringWidth(timeString);
        graphics2d.drawString(timeString, SCREEN_WIDTH / 2 - width / 2, 50);

        // Heart and GrayHeart
        for (int i = 0; i < MAX_HEALTH; i++) {
            if (i < health) {
                g.drawImage(ITEM_HEART, 10 + i * 35, 10, 40, 40, null);
            } else {
                g.drawImage(ITEM_GRAY_HEART, 10 + i * 35, 10, 40, 40, null);
            }
        }

        // Score
        graphics2d.setColor(Color.white);
        graphics2d.setFont(fSemiBold.deriveFont(Font.BOLD, 30f));
        String scoreString = String.format("%06d", score);
        int scoreWidth = graphics2d.getFontMetrics().stringWidth(scoreString);
        graphics2d.drawString(scoreString, SCREEN_WIDTH - scoreWidth - 50, 50);

        // effectTime
        graphics2d.setColor(Color.white);

        if (isFreeze()) {
            // drawTime
            String freezeString = String.format("%02d.%02d", (freezeTime / 1000), (freezeTime % 1000) / 10);
            int freezeWidth = graphics2d.getFontMetrics().stringWidth(freezeString);
            graphics2d.drawString(freezeString, SCREEN_WIDTH - freezeWidth - 50, 150);

            // drawIcon Center Left text
            g.drawImage(ITEM_FREEZE, SCREEN_WIDTH - freezeWidth - 100, 150 - 35, 50, 50, null);
        }

        if (isAutomatic()) {
            String autoString = String.format("%02d.%02d", (automaticTime / 1000), (automaticTime % 1000) / 10);
            int autoWidth = graphics2d.getFontMetrics().stringWidth(autoString);
            graphics2d.drawString(autoString, SCREEN_WIDTH - autoWidth - 50, 200);

            // drawIcon
            g.drawImage(ITEM_AUTOMATIC, SCREEN_WIDTH - autoWidth - 100, 200 - 35, 50, 50, null);
        }

        if (isProtected()) {
            String protectString = String.format("%02d.%02d", (protectTime / 1000), (protectTime % 1000) / 10);
            int protectWidth = graphics2d.getFontMetrics().stringWidth(protectString);
            graphics2d.drawString(protectString, SCREEN_WIDTH - protectWidth - 50, 250);

            // drawIcon
            g.drawImage(ITEM_SHIELD, SCREEN_WIDTH - protectWidth - 100, 250 - 35, 50, 50, null);
        }

        // Level
        graphics2d.setColor(Color.white);
        graphics2d.setFont(fSemiBold.deriveFont(Font.BOLD, 30f));
        String levelString = String.format("Level : %02d", level);
        graphics2d.drawString(levelString, 15, 100);

        // Enemy Count size / max
        graphics2d.setColor(Color.white);
        graphics2d.setFont(fSemiBold.deriveFont(Font.BOLD, 30f));
        String enemyString = String.format("%02d / %02d", amountMeteorEnough,
                maxMeteorLevel);
        int enemyWidth = graphics2d.getFontMetrics().stringWidth(enemyString);
        graphics2d.drawString(enemyString, SCREEN_WIDTH - enemyWidth - 50, 100);

        // Planet
        planet.draw(graphics2d);

        // Player
        player.draw(graphics2d);

        // Bullet
        ArrayList<Bullet> bulletsCopy = new ArrayList<>(bullets);
        for (Bullet bullet : bulletsCopy) {
            if (bullet != null) {
                bullet.draw(graphics2d);
            }
        }

        // Meteor
        ArrayList<Meteor> meteorsCopy = new ArrayList<>(meteors);
        for (Meteor meteor : meteorsCopy) {
            if (meteor != null) {
                meteor.draw(graphics2d);
            }
        }

        // Item
        ArrayList<Item> itemsCopy = new ArrayList<>(items);
        for (Item item : itemsCopy) {
            if (item != null) {
                item.draw(graphics2d);
            }
        }

        // Freeze blue overlay screen
        if (isFreeze()) {
            g.setColor(new Color(0, 0, 255, 30));
            g.fillRect(0, 0, SCREEN_WIDTH, SCREEN_HEIGHT);
        }

        // Automatic red over screen
        if (isAutomatic()) {
            g.setColor(new Color(255, 0, 0, 30));
            g.fillRect(0, 0, SCREEN_WIDTH, SCREEN_HEIGHT);
        }
    }

    public void run() {
        // Initialize time variables
        long oldTime = System.nanoTime();
        long currentTime = System.nanoTime();
        long oldSecondTime = System.nanoTime();
        long currentTimeSecond = System.nanoTime();

        // Initialize delta and frame rate variables
        double delta = 0.0f;
        double ns = 1_000_000_000 / FRAME_RATE;

        // Game loop
        while (!gameOver && !inEndgame) {
            // Repaint the game
            repaint();

            // Update time variables
            oldTime = currentTime;
            currentTime = System.nanoTime();

            // Update time counter variables
            currentTimeSecond = System.nanoTime();

            // Calculate delta
            delta += (currentTime - oldTime) / ns;

            // Update game state while delta is greater than or equal to 1
            while (delta >= 1) {
                // Update game if not paused
                if (!isPause) {
                    update();
                    // remove pause panel if has
                    if (pausePanel != null) {
                        remove(pausePanel);
                        pausePanel = null;
                    }

                    if (currentTimeSecond - oldSecondTime >= 1_000_000_000f) {
                        seconds++;
                        oldSecondTime = currentTimeSecond;
                    }
                } else {
                    // add pause panel
                    pausePanel = new PausePanel(this);
                    add(pausePanel);
                    // pause until unpause
                    while (isPause) {
                        repaint();
                        update();
                        lastTimeMeteorSpawn = System.currentTimeMillis();

                        // Update time variables
                        oldTime = currentTime;
                        currentTime = System.nanoTime();
                    }
                }
                // Decrement delta
                delta--;
            }
        }

        // gameOverPanel
        if (gameOver) {
            gameOver();
        }
    }

    public void setHold(boolean b) {
        this.hold = b;
    }

    public boolean isHold() {
        return hold;
    }

}
