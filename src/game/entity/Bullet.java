
package game.entity;

import static game.util.Constant.*;

import java.awt.Graphics2D;
import java.awt.event.MouseEvent;

import game.GamePlay;
import game.util.ImageManager;

public class Bullet extends Entity {

    private double deg;
    private int realX, realY;
    private int speed;

    GamePlay gp;
    // private int oldX1, oldY1, oldX2, oldY2;

    public Bullet(GamePlay gp, MouseEvent e) {
        super(-PLAYER_WIDTH / 2 + BULLET_WIDTH / 2 - BULLET_WIDTH / 4, -PLAYER_HEIGHT * 2 - BULLET_HEIGHT,
                ImageManager.resizeImage(BULLET_IMG, BULLET_WIDTH, BULLET_HEIGHT));

        deg = Math.atan2(e.getX() - (SCREEN_WIDTH / 2) - Math.ceil(PLAYER_HEIGHT / 4),
                -(e.getY() - (SCREEN_HEIGHT / 2)) + PLAYER_WIDTH / 2);

        // real Pos
        speed = BULLET_SPEED * 10;
        this.gp = gp;
    }

    public boolean isOutOfScreen() {
        return realX < -BULLET_WIDTH / 2 || realX > SCREEN_WIDTH + BULLET_WIDTH / 2 || realY < -BULLET_HEIGHT / 2
                || realY > SCREEN_HEIGHT + BULLET_HEIGHT / 2;
    }

    @Override
    public boolean intersect(Entity entity) {
        if (entity instanceof Meteor) {
            Meteor meteor = (Meteor) entity;
            return this.realX < meteor.x + meteor.image.getWidth() &&
                    this.realX + BULLET_HITBOX > meteor.x &&
                    this.realY < meteor.y + meteor.image.getHeight() &&
                    this.realY + BULLET_HITBOX > meteor.y;
        }
        return false;
    }

    @Override
    public void update() {
        if (gp.isPause()) {
            return;
        }

        y -= speed;

        realX = (int) (-(y + BULLET_HITBOX / 2 + speed / 2) * Math.sin(deg)) - BULLET_HITBOX / 2;
        realY = (int) (+(y + speed / 2) * Math.cos(deg)) - BULLET_HITBOX / 2;

        realX += SCREEN_WIDTH / 2;
        realY += SCREEN_HEIGHT / 2;

        // if (!GamePlay.getInstance().isPause()) {
        // oldX1 = x;
        // oldY1 = y;
        // oldX2 = realX;
        // oldY2 = realY;
        // }
    }

    // // flashBack
    // public void flashBack() {
    // x = oldX1;
    // y = oldY1;
    // realX = oldX2;
    // realY = oldY2;
    // }

    @Override
    public void draw(Graphics2D g2d) {
        // new g2d
        Graphics2D g2d2 = (Graphics2D) g2d.create();
        g2d2.translate(SCREEN_WIDTH / 2, SCREEN_HEIGHT / 2);
        g2d2.rotate(deg);
        g2d2.drawImage(image, x + BULLET_HITBOX / 4, y - BULLET_HITBOX / 4, BULLET_WIDTH, BULLET_HEIGHT, null);
        g2d2.dispose();

        // g2d2 = (Graphics2D) g2d.create();
        // g2d2.setColor(Color.red);
        // g2d2.drawRect(realX, realY, BULLET_HITBOX, BULLET_HITBOX);
        // g2d2.dispose();
    }
}