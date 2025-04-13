package com.group24;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.group24.BonusReward;
import com.group24.Enemy;
import com.group24.GameData;
import com.group24.Player;
import com.group24.Reward;
import com.group24.Spring;

import java.beans.Transient;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.swing.SpinnerDateModel;

public class GameDataTest {
    @Test
    /**
     * Adds a new arraylist to the hashmap in GameData
     * then checks that the newly create object was correctly added
     */
    public void setArrayLists(){

        GameData d = new GameData();
        ArrayList<Player>      p = new ArrayList<>(); 
        ArrayList<Enemy>       e = new ArrayList<>();
        ArrayList<Reward>      r = new ArrayList<>();
        ArrayList<BonusReward> br = new ArrayList<>();
        ArrayList<Spring>      s = new ArrayList<>();

        d.setBonusRewards(br);
        d.setEnemies(e);
        d.setPlayers(p);
        d.setRewards(r);
        d.setSprings(s);
        
        //
        // Check if they were set correctly
        assertSame(p ,d.getPlayers());
        assertSame(e ,d.getEnemies());
        assertSame(r ,d.getRewards());
        assertSame(br,d.getBonusRewards());
        assertSame(s ,d.getSprings());
    }

    @Test
    /**
     * adds an object to each of the containers in GameData
     * and then makes sure they each contain something (they start empty)
     */
    public void addStuffToArraylists(){

        URL playerpath = getClass().getClassLoader().getResource("Raccoon_32.png");
        URL enemypath = getClass().getClassLoader().getResource("Guard_32.png");
        URL trappath = getClass().getClassLoader().getResource("trap_32.png");
        URL springpath = getClass().getClassLoader().getResource("Punishment.png");
        URL poweruppath = getClass().getClassLoader().getResource("PowerUP.png");
        URL bonusrewardpath = getClass().getClassLoader().getResource("PowerUPBonus2.png");

        GameData d = new GameData();
        
        d.add(new Player(playerpath, 1, 1));
        d.add(new Enemy(enemypath, 2, 2, -100));
        d.add(new Reward(poweruppath, 3, 3, 10));
        d.add(new Spring(springpath,4,4));
        d.add(new BonusReward(bonusrewardpath, 5, 5, 10, 70));

        //
        // Check if they were added correctly
        assertEquals(true,d.getPlayers().size()== 1
                        && d.getEnemies().size()==1
                        && d.getBonusRewards().size()==1
                        && d.getRewards().size()==1
                        &&d.getSprings().size()==1); // all should have 1 element now    
    }

    /** 
     * Creates an exception by trying to add an invalid object type
    */
    @Test
    public void addInvalidObjectType(){

        GameData d = new GameData();
        
        boolean caught = false;
        try{
            d.add(new GameData());
        }
        catch(IllegalArgumentException e){
            caught = true;
        }
        assertEquals(true,caught);
    }
}