package game.util;

import java.io.File;
import java.io.IOException;
import java.awt.image.BufferedImage;
import java.awt.*;
import javax.sound.sampled.Clip;

public class Constant {
    // WINDOW GAME SETTINGS
    public static final int SCREEN_WIDTH = 1920;
    public static final int SCREEN_HEIGHT = 1080;
    public static final int FRAME_RATE = 60;
    public static final long ENEMY_SPAWN_DELAY = 2500;
    public static boolean sound_state = true;
    public static int MAX_LEVEL = 0;
    public static String DifficultyStr = "normal";

    public static String[] planets = FileManager.getFiles("res/images/planets/");

    // ========== PLANET ========== \\
    public static String PLANET_IMG = "res/images/planets/planet00.png";
    public static int PLANET_SIZE = SCREEN_WIDTH / 12; // 250

    // ========== BULLET ========== \\
    public static String BULLET_IMG = "res/images/bullet.png";
    public static int BULLET_SPEED = 2;
    public static int BULLET_HITBOX = 30;
    public static int BULLET_WIDTH = 30;
    public static int BULLET_HEIGHT = 100;

    // ========== METEOR ========== \\
    public static int METEOR_MAX_SIZE = 200;
    public static int METEOR_SPEED = 1;
    public static final int MAX_COUNT_METEOR = 99;
    public static int METEOR_PER_LEVEL = 1;

    // ========== PLAYER ========== \\
    public static String PLAYER_IMG = "res/images/player2.png";
    public static String SHIELD_IMG = "res/images/shield2.png";
    public static final int PLAYER_WIDTH = 70;
    public static final int SHOOT_RATE = 10; // per sec
    public static final int PLAYER_HEIGHT = 79;
    public static int MAX_HEALTH = 5;

    // ========== ITEM ========== \\
    public static int ITEM_SIZE = 50;
    public static int ITEM_SPEED = 10;
    public static int DROP_RATE = 3;
    public static final int FREEZE_TIME = 3_000;
    public static final int AUTOMATIC_TIME = 3_000;
    public static final int PROTECT_TIME = 3_000;

    // ========== ASSETS ========== \\
    // GAME
    public static BufferedImage GAME_LOGO;
    public static BufferedImage reloadImg;
    public static BufferedImage reloadImg2;

    // Images
    public static BufferedImage SOUND_ON;
    public static BufferedImage SOUND_OFF;
    public static BufferedImage BG1;
    public static BufferedImage BG2;

    // Items
    public static BufferedImage ITEM_HEART;
    public static BufferedImage ITEM_GRAY_HEART;
    public static BufferedImage ITEM_SHIELD;
    public static BufferedImage ITEM_FREEZE;
    public static BufferedImage ITEM_AUTOMATIC;

    public static BufferedImage spriteExplosion;

    // Fonts
    public static Font fRegular;
    public static Font fSemiBold;
    public static Font fBold;

    // Sounds
    public static Clip bgm1;
    public static Clip bgm2;
    public static Clip SHOOT_SOUND;
    public static Clip BOOM_SOUND;
    public static Clip COUNT_SOUND;
    public static Clip ITEM_COLLECT;
    public static Clip DEAD_SOUND;
    public static Clip LEVEL_UP;
    public static Clip WINNER_SOUND;
    public static Clip FREEZE_SOUND;

    public static Clip Endcredit;

    // Cursor
    private final static String CURSOR_PATH = "res/images/crosshair.png";
    public static Cursor CUSTOM_CURSOR;

    // load assets
    static {
        try {
            Toolkit toolkit = Toolkit.getDefaultToolkit();
            // Change the size of the image to 50x50
            Image image = toolkit.getImage(CURSOR_PATH).getScaledInstance(50, 50, Image.SCALE_SMOOTH);
            Point hotspot = new Point(25, 25);
            CUSTOM_CURSOR = toolkit.createCustomCursor(image, hotspot, "Crosshair");

            GAME_LOGO = ImageManager.resizeImage("res/images/icon.jpg", 64, 64);
            reloadImg = ImageManager.resizeImage("res/images/reload.png", 350, 350);
            reloadImg2 = ImageManager.resizeImage("res/images/reload2.png", 350, 350);

            BG1 = ImageManager.resizeImage("res/images/bg/bg1.jpg", SCREEN_WIDTH, SCREEN_HEIGHT);
            BG2 = ImageManager.resizeImage("res/images/bg/bg2.jpg", SCREEN_WIDTH, SCREEN_HEIGHT);

            bgm1 = SoundManager.getClip("res/sounds/bgm1.wav");
            bgm2 = SoundManager.getClip("res/sounds/bgm2.wav");

            BOOM_SOUND = SoundManager.getClip("res/sounds/bomb.wav");
            SHOOT_SOUND = SoundManager.getClip("res/sounds/shoot.wav");
            COUNT_SOUND = SoundManager.getClip("res/sounds/count.wav");
            ITEM_COLLECT = SoundManager.getClip("res/sounds/pickup2.wav");
            FREEZE_SOUND = SoundManager.getClip("res/sounds/freeze.wav");

            DEAD_SOUND = SoundManager.getClip("res/sounds/GameOver2.wav");
            LEVEL_UP = SoundManager.getClip("res/sounds/Next_level.wav");
            WINNER_SOUND = SoundManager.getClip("res/sounds/winner.wav");
            Endcredit = SoundManager.getClip("res/sounds/Endcredit.wav");

            SOUND_ON = ImageManager.resizeImage("res/images/sound_on.png", 50, 50);
            SOUND_OFF = ImageManager.resizeImage("res/images/sound_off.png", 50, 50);

            spriteExplosion = ImageManager.load("res/images/effects/explosion.png");

            ITEM_HEART = ImageManager.resizeImage("res/images/items/heart.png", 50, 50);
            ITEM_GRAY_HEART = ImageManager.resizeImage("res/images/items/heart_gray.png", 50, 50);
            ITEM_SHIELD = ImageManager.resizeImage("res/images/items/shield.png", 50, 50);
            ITEM_FREEZE = ImageManager.resizeImage("res/images/items/freeze.png", 50, 50);
            ITEM_AUTOMATIC = ImageManager.resizeImage("res/images/items/damage.png", 50, 50);

            fRegular = Font.createFont(Font.TRUETYPE_FONT, new File("res/fonts/Orbitron-Regular.ttf"));
            fSemiBold = Font.createFont(Font.TRUETYPE_FONT, new File("res/fonts/Orbitron-SemiBold.ttf"));
            fBold = Font.createFont(Font.TRUETYPE_FONT, new File("res/fonts/Orbitron-Bold.ttf"));
        } catch (FontFormatException | IOException e) {
            e.printStackTrace();
        }
    }
}
