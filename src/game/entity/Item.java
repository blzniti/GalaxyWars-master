package game.entity;

import static game.util.Constant.*;

import java.awt.Graphics2D;
import java.awt.Point;

import game.GamePlay;
import game.interfaces.ItemType;

public class Item extends Entity {
    private ItemType item;
    private Point target;
    private int speed = ITEM_SPEED;

    GamePlay gp;

    public Item(GamePlay gp, ItemType item, int x, int y, Point target) {
        super(x, y);
        this.gp = gp;
        this.target = target;
        this.item = item;
        image = item.getImage();
    }

    @Override
    public void update() {
        // Calculate Vector
        double dx = target.x - x;
        double dy = target.y - y;
        double distance = Math.max(1, (int) Math.sqrt(dx * dx + dy * dy));
        x += (int) (speed * dx / distance);
        y += (int) (speed * dy / distance);
    }

    @Override
    public void draw(Graphics2D g2d) {
        // new g2d
        Graphics2D g2d2 = (Graphics2D) g2d.create();
        g2d2.drawImage(image, x, y, null);
        g2d2.dispose();
    }

    public ItemType getItem() {
        return item;
    }
}
