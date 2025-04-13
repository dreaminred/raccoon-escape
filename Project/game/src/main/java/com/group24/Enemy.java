package com.group24;
import java.net.URL;

/**
 * Enemy class that extends the reward, because enemies substract 100000000
 * @author Group 24
 */
public class Enemy extends Reward{
    public Enemy(URL imagePath, int x, int y, int amount) {
        super(imagePath, x, y, amount);
        
    }
}
