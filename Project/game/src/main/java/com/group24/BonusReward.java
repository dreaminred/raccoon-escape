package com.group24;
import java.net.URL;

/**
 * Class for the bonus rewards that disappears after x time
 */
public class BonusReward extends Reward{
    private int time;
    /**
     * Constructor for the bonus reward
     * @param imagePath The URL of the path to the image
     * @param x The y coord of the reward
     * @param y the x coord of the reward
     * @param amount The amount you get from the reward
     * @param time The amount of time it takes for the rewards to disappear
     */
    public BonusReward(URL imagePath, int x, int y, int amount, int time) {
        super(imagePath, x, y, amount);
        // time is in milliseconds 
        this.time = time;
    }

    /**
     * Getter for the total time that this bonus reward will last until disappearing.
     * @return int time in milliseconds
     */
    public int getTime() {
        return time;
    }
}
