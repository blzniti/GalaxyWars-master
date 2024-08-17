package game.interfaces;

import static game.util.Constant.DROP_RATE;
import static game.util.Constant.MAX_HEALTH;
import static game.util.Constant.MAX_LEVEL;
import static game.util.Constant.METEOR_PER_LEVEL;

public enum Difficulty {

    NOOB(10, 5, 6, 2),
    EASY(8, 10, 4, 5),
    NORMAL(6, 15, 3, 8),
    HARD(4, 20, 2, 10),
    HELL(2, 25, 1, 10);

    private int maxHealth;
    private int meteorPerLevel;
    private int rateDrop;
    private int maxLevel;

    Difficulty(int maxHealth, int meteorPerLevel, int rateDrop, int maxLevel) {
        this.maxHealth = maxHealth;
        this.meteorPerLevel = meteorPerLevel;
        this.rateDrop = rateDrop;
        this.maxLevel = maxLevel;
    }

    public void setDifficulty() {
        MAX_HEALTH = this.maxHealth;
        METEOR_PER_LEVEL = this.meteorPerLevel;
        DROP_RATE = this.rateDrop;
        MAX_LEVEL = this.maxLevel;
    }

    public static void setDifficulty(String str) {
        for (Difficulty difficulty : Difficulty.values()) {
            if (difficulty.toString().toLowerCase().equals(str.toLowerCase())) {
                difficulty.setDifficulty();
            }
        }
    }
}
