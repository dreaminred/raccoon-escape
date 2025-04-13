package com.group24;
import java.net.URL;

/** 
 * Class for any sort of entity that changes the player's score
 * @author Group 24
 */
public class Reward extends Entity{
    int amount;
    /**
     * Constructor for the reward entity
     * @param imagePath URL to the file
     * @param x X location of the entity
     * @param y Y location of the entity
     * @param amount Amount that the score will change by if the player touches
     */
    public Reward(URL imagePath, int x, int y, int amount) {
        super(imagePath, x, y);
        this.amount = amount;
    }
    /**
     * Getter for the amount to change the player's score by
     * @return int amount 
     */
    public int getAmount() {
        return amount;
    }
}
