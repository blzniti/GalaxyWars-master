package game.entity;

import java.awt.image.BufferedImage;
import java.awt.Graphics2D;

public abstract class Entity {
    protected int x;
    protected int y;
    protected BufferedImage image;

    public Entity() {
    }

    public Entity(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Entity(int x, int y, BufferedImage image) {
        this.x = x;
        this.y = y;
        this.image = image;
    }

    public abstract void update();

    public abstract void draw(Graphics2D g2d);

    public boolean intersect(Entity other) {
        return this.x < other.x + other.image.getWidth() &&
                this.x + this.image.getWidth() > other.x &&
                this.y < other.y + other.image.getHeight() &&
                this.y + this.image.getHeight() > other.y;
    }

    public int getX() {
        return this.x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return this.y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getWidth() {
        return image.getWidth();
    }

    public int getHeight() {
        return image.getHeight();
    }
}
