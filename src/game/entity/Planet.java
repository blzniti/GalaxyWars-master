package game.entity;

import static game.util.Constant.*;

import game.GamePlay;
import game.util.ImageManager;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.image.BufferedImage;

public class Planet extends Entity {

    private double angle = 0;
    private BufferedImage imageShield;

    GamePlay gp;

    public Planet(GamePlay gp) {
        super(SCREEN_WIDTH / 2 - PLANET_SIZE / 2, SCREEN_HEIGHT / 2 - PLANET_SIZE / 2,
                ImageManager.resizeImage(PLANET_IMG, PLANET_SIZE, PLANET_SIZE));

        this.gp = gp;

        imageShield = ImageManager.resizeImage(SHIELD_IMG, PLANET_SIZE + 100, PLANET_SIZE + 100);
    }

    @Override
    public void update() {
        // check game pause
        if (gp.isPause()) {
            return;
        }
        angle += 0.01;
    }

    @Override
    public void draw(Graphics2D g2d) {
        // new g2d
        Graphics2D g2d2 = (Graphics2D) g2d.create();
        g2d2.rotate(angle, x + PLANET_SIZE / 2, y + PLANET_SIZE / 2);
        g2d2.drawImage(image, x, y, null);
        g2d2.dispose();

        if (gp.isProtected()) {
            g2d.drawImage(imageShield, x - 50, y - 45, null);
        }

        // draw hit box
        // g2d.drawRect(x, y, image.getWidth(), image.getHeight());
    }

    public Point getPoint() {
        return new Point(x + PLANET_SIZE / 2, y + PLANET_SIZE / 2);
    }
}
