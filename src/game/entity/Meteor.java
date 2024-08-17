package game.entity;

import static game.util.Constant.*;

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.util.Timer;
import java.util.TimerTask;

import game.GamePlay;
import game.util.ImageManager;
import game.util.SoundManager;

public class Meteor extends Entity {
    double size;
    double deg;
    public boolean destroyed = false;
    Point target;
    double speed;
    private Timer timer;

    // Explosion animation variables
    private BufferedImage spriteExplosion;
    private int explosionState = 0;
    private int explosionStateX = 0;
    private int explosionStateY = 0;
    public boolean exploding = false;

    GamePlay gp;

    public Meteor(GamePlay gp) {
        super(0, 0, null);
        int type = random(1, 4);
        double coordsX = 0;
        double coordsY = 0;
        size = random(1, 3);
        switch (type) {
            case 1:
                coordsX = random(0, SCREEN_WIDTH);
                coordsY = 0 - (METEOR_MAX_SIZE / size);
                break;
            case 2:
                coordsX = SCREEN_WIDTH + (METEOR_MAX_SIZE / size);
                coordsY = random(0, SCREEN_HEIGHT);
                break;
            case 3:
                coordsX = random(0, SCREEN_WIDTH);
                coordsY = SCREEN_HEIGHT + (METEOR_MAX_SIZE / size);
                break;
            case 4:
                coordsX = 0 - (METEOR_MAX_SIZE / size);
                coordsY = random(0, SCREEN_HEIGHT);
                break;
        }

        x = (int) coordsX;
        y = (int) coordsY;
        deg = Math.atan2(coordsX - (SCREEN_WIDTH / 2), -(coordsY - (SCREEN_HEIGHT / 2)));

        // calc meteor Size
        int width = (int) (METEOR_MAX_SIZE / size);
        int height = (int) (METEOR_MAX_SIZE / size);

        // random Meteor != PLANET_IMG
        String imageStr = planets[random(0, planets.length - 1)];
        while (imageStr.equals(PLANET_IMG)) {
            imageStr = planets[random(0, planets.length - 1)];
        }

        image = ImageManager.resizeImage(imageStr, width, height);

        // speed
        speed = random(5, 20) / 10f;

        target = new Point();
        target.x = (int) (SCREEN_WIDTH / 2);
        target.y = (int) (SCREEN_HEIGHT / 2);

        // Load explosion sprite sheet
        spriteExplosion = ImageManager.load("res/images/effects/explosion.png");

        this.gp = gp;
    }

    private int random(int from, int to) {
        return (int) (Math.floor(Math.random() * (to - from + 1)) + from);
    }

    public void explosion() {
        if (!destroyed && !exploding) {
            exploding = true;
            SoundManager.play(BOOM_SOUND);
            timer = new Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    if (gp.isPause()) {
                        return;
                    }
                    explosionStateX = (explosionState % 4) * 256;
                    explosionStateY = (explosionState / 4) * 256;
                    explosionState++;
                    if (explosionState == 16) {
                        destroyed = true;
                        exploding = false;
                        timer.cancel();
                    }
                }
            }, 0, 50);
        }
    }

    @Override
    public void update() {
        if (gp.isPause()) {
            return;
        }

        if (destroyed) {
            return;
        }
        if (exploding) {
            return;
        }

        if (inScreen() && gp.isFreeze()) {
            return;
        }

        // Calculate Vector
        double dx = target.x - x; // Calculate the difference in x-coordinates
        double dy = target.y - y; // Calculate the difference in y-coordinates
        double sqr = Math.sqrt(dx * dx + dy * dy); // Calculate the Euclidean distance
        // Round the distance up to the nearest whole number
        double distance = Math.signum(sqr) * Math.ceil(Math.abs(sqr));

        int oldX = x, oldY = y; // Store the old position

        // Move the x-coordinate towards the target
        x += Math.signum(dx) * Math.ceil(Math.abs(speed * dx / distance * size));
        // Move the y-coordinate towards the target
        y += Math.signum(dy) * Math.ceil(Math.abs(speed * dy / distance * size));

        // check freeze
        if (oldX == x && oldY == y) {
            System.err.println(this + " FREEZE" + " : " + distance + " : " + dx + " : " + dy);
            explosion();
        }
    }

    @Override
    public void draw(Graphics2D g2d) {
        if (exploding) {
            g2d.drawImage(spriteExplosion, x - 128, y - 128, x + 128, y + 128, explosionStateX, explosionStateY,
                    explosionStateX + 256, explosionStateY + 256, null);

            if (explosionState == 16) {
                destroyed = true;
                exploding = false;
                timer.cancel();
            }
        } else if (!destroyed) {
            g2d.drawImage(image, x, y, null);
        }
    }

    public boolean inScreen() {
        if (x < 0 || x > SCREEN_WIDTH - image.getWidth() || y < 0 || y > SCREEN_HEIGHT - image.getHeight()) {
            return false;
        }
        return true;
    }
}