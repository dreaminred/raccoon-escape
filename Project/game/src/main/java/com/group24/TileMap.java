package com.group24;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.net.URL;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import com.google.gson.JsonArray;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;

/**
 * 
 * This is the TileMap class that makes the different levels. 
 * @author Group 24
 */
public class TileMap extends JPanel{
// each tile is 64x64 (can change later)
private int TILE_RES = Constants.TILE_SIZE;
// rows and cols
private int M = Constants.MAP_ROWS;
private int N = Constants.MAP_COLUMNS;

private Image groundImg;
private Image wallImg;
private Image enemyImg;
private Image exitImg;
private Image trapImg;
private Image bonusImg;
// private Image tutorial1;
private Image springImg; // the spring sets the raccon back to the start of the maze
private Image powerUpImg; // powerup adds to score

private ArrayList<Player> players = new ArrayList<>(); 
private ArrayList<Enemy> enemies = new ArrayList<>(); 
private ArrayList<Reward> rewards = new ArrayList<>(); 
private ArrayList<BonusReward> bonusrewards = new ArrayList<>(); 
private ArrayList<Spring> springs = new ArrayList<>(); 
//
private GameData gamedata = new GameData();
//
LoadLevel loader = new LoadLevel();

public int[][] map;

    /**
     * Constructor generates the according level
     * @param level The level to create
     * 
     */
public TileMap(int level) {

    // Uses files that are packaged into the executable JAR by maven
    URL playerpath = getClass().getClassLoader().getResource("Raccoon_32.png");

    URL groundpath = getClass().getClassLoader().getResource("ground.png");
    URL wallpath = getClass().getClassLoader().getResource("wall.png");
    URL enemypath = getClass().getClassLoader().getResource("Guard_32.png");
    URL exitpath = getClass().getClassLoader().getResource("exit.png");
    URL trappath = getClass().getClassLoader().getResource("trap_32.png");
    URL springpath = getClass().getClassLoader().getResource("Punishment.png");
    URL poweruppath = getClass().getClassLoader().getResource("PowerUP.png");
    URL bonusrewardpath = getClass().getClassLoader().getResource("PowerUPBonus2.png");

    URL tutorial1path = getClass().getClassLoader().getResource("tutorial1.png");
    URL tutorial2path = getClass().getClassLoader().getResource("tutorial2.png");
    URL tutorial3path = getClass().getClassLoader().getResource("tutorial3.png");
    URL tutorial4path = getClass().getClassLoader().getResource("tutorial4.png");
    URL tutorial5path = getClass().getClassLoader().getResource("tutorial5.png");

    loadImages();

    //
    // These things are done every time so they might as well be done outside the ifs
    if(level < 1) map = loader.loadLevel(level);
    Player player = new Player(playerpath, 1, 1);
    players.add(player);

    if (level == -999) {
        // Secret level for testing

        Reward a = new Reward(poweruppath, 2, 1, 10);
        Reward c = new Reward(trappath, 3, 1, -10);
        Spring df = new Spring(springpath, 2, 1);
        BonusReward f = new BonusReward(bonusrewardpath, 2, 1, 10, 10000);
        Enemy g = new Enemy(enemypath, 2, 13, 100);

        rewards.add(c);
        rewards.add(a);
        springs.add(df);
        bonusrewards.add(f);
        enemies.add(g);

    } 
    else if (level == -997)  {
        Enemy g = new Enemy(enemypath, 2, 1, -100000000);
        enemies.add(g);
    }
    else if (level == -5) {
        // Tutorial level
        Enemy m = new Enemy(enemypath, 3, 3, 1000000);
        enemies.add(m);

    }
    else if (level == -4) {
        // Tutorial level

        Reward b = new Reward(tutorial1path, 0, 1, 10);
        rewards.add(b);

    } 
    else if (level == -3) {
        // Tutorial level
        Reward b = new Reward(tutorial2path, 0, 1, 10);
        Reward a = new Reward(poweruppath, 25, 4, 10);
        Spring c = new Spring(springpath, 20, 13);

        rewards.add(b);
        rewards.add(a);
        springs.add(c);

    } 
    else if (level == -2) {
        // Tutorial level
        Reward b = new Reward(tutorial3path, 0, 1, 10);
        Reward a = new Reward(poweruppath, 25, 4, 10);
        Reward c = new Reward(trappath, 8, 5, -10);
        Reward d = new Reward(trappath, 20, 10, -10);
        Spring df = new Spring(springpath, 20, 13);

        rewards.add(b);
        rewards.add(a);
        rewards.add(c);
        rewards.add(d);
        springs.add(df);


    } 
    else if (level == -1) {
        // Tutorial level
        BonusReward b = new BonusReward(tutorial4path, 0, 1, 0, 1000000000);
        Reward      a = new Reward(poweruppath, 25, 4, 10);
        BonusReward d = new BonusReward(bonusrewardpath, 23, 5, 10, 7000);
        BonusReward e = new BonusReward(bonusrewardpath, 24, 5, 10, 6000);
        BonusReward f = new BonusReward(bonusrewardpath, 25, 5, 10, 5000);
        Enemy g = new Enemy(enemypath, 1, 16, 100);
        bonusrewards.add(b);
        rewards.add(a);
        bonusrewards.add(d);
        bonusrewards.add(e);
        bonusrewards.add(f);
        enemies.add(g);

    } 
    else if (level == 0) {
        // Tutorial level
        BonusReward a = new BonusReward(tutorial5path, 0, 1, 10,1000000000);
        
        Reward b = new Reward(poweruppath, 10, 11, 10);
        Reward c = new Reward(poweruppath, 22, 3, 10);
        Reward d = new Reward(poweruppath, 28, 12, 10);
        Reward e = new Reward(poweruppath, 13, 15, 10);
        Reward f = new Reward(poweruppath, 1, 9, 10);

        bonusrewards.add(a);
        rewards.add(b);
        rewards.add(c);
        rewards.add(d);
        rewards.add(e);
        rewards.add(f);

    } 
    else if (level >= 1) {
        MazeGenerator mg = new MazeGenerator(17, 30);
        mg.generateMaze();
        map = mg.getMaze();
        rewards = mg.getRewards();
        bonusrewards = mg.getBonusRewards();
        enemies = mg.getEnemies();
        springs = mg.getSprings();
    }
    //
    // update gamedata
    gamedata = new GameData(players, enemies, rewards, bonusrewards, springs);
}


    //
    /**
     * Draws all of the tiles in this tilemap's array.
     * Paints acording to the enum in TileTypes.java.
     */
    protected void paintComponent(Graphics g) { 
        super.paintComponent(g);
        for (int m = 0; m<M; m++){
            for(int n = 0; n < N; n++){
                int tileType = map[m][n];
                int mapWidth =  n*TILE_RES;
                int mapHeight =  m*TILE_RES;
                g.drawImage(groundImg, mapWidth,mapHeight, TILE_RES, TILE_RES, this);
                switch (tileType) {
                    case 0:
                        g.drawImage(groundImg,mapWidth, mapHeight, TILE_RES, TILE_RES, this);
                        break;
                    case 1:
                        g.drawImage(wallImg,mapWidth,mapHeight, TILE_RES, TILE_RES, this);
                        break;
                    case 2: 
                        g.drawImage(enemyImg,mapWidth,mapHeight, TILE_RES, TILE_RES, this);
                        break;
                    case 3: 
                        g.drawImage(exitImg,mapWidth, mapHeight, TILE_RES, TILE_RES, this);
                        break;
                    case 4: 
                        g.drawImage(trapImg, mapWidth, mapHeight, TILE_RES, TILE_RES, this);
                        break;
                    case 5: 
                        g.drawImage(springImg, mapWidth,mapHeight, TILE_RES, TILE_RES, this);
                        break;
                    case 6: 
                        g.drawImage(powerUpImg, mapWidth,mapHeight, TILE_RES, TILE_RES, this);
                        break;
                }
            }
        }
    }

    /**
     * Getter for the 2d represtnation of the map
     * @return 2d integer array representation of the map
     */
    public int[][] getMap(){
        return map;
    }

    /**
     * Getter for the arrayulist of players
     * @return Arraylist of enemies
     */
    public ArrayList<Player> getPlayers(){
        return players;
    }

    /**
     * Getter for the arrayulist of enemies
     * @return Arraylist of enemies
     */
    public ArrayList<Enemy> getEnemies(){
        return enemies;
    }


    /**
     * Getter for array list of rewards
     * @return Arraylist of rewards
     */
    public ArrayList<Reward> getRewards(){
        return rewards;
    }
    /**
     * Getter for the list bonus rewards associated with this level
     * @return Arraylist of bonus rewards
     */
    public ArrayList<BonusReward> getBonusRewards(){
        return bonusrewards;
    }
     /**
     * Getter for the list springs associated with this level
     * @return Arraylist of bonus rewards
     */
    public ArrayList<Spring> getSprings(){
        return springs;
    }
     /**
     * Getter for the gamedataassociated with this level
     * @return gamedata
     */
    public GameData getGameData(){
        return gamedata;
    }
    // public ArrayList<Entity> getExits(){
    //     return exits;
    // }


    /**
     * Checks if the coordinates passed in are a valid move for the player
     * @param x The x position to check
     * @param y The y position to check
     * @return True if no wall and in bounds, false otherwise
     */
    public boolean validMove(int x, int y) {
        if (x < 0 || x > N-1 || y < 0 || y > M-1 || map[y][x] == 1){
            return false;
        }
        return true;
    }

    /**
     * 
     * Checks if there is an exit at the coordinates passed in
     * @param x The x coord to check
     * @param y The y coord to check
     * @return A bool if it's an exit or not on the map
     */
    public boolean isExit(int x, int y) {
        if (map[y][x] == 3) {
            return true;
        }
        else {
            return false;
        }
    }

    private void loadImages() {
        groundImg = loadImage("ground.png");
        wallImg = loadImage("wall.png");
        enemyImg = loadImage("Guard_32.png");
        exitImg = loadImage("exit.png");
        trapImg = loadImage("trap_32.png");
        springImg = loadImage("Punishment.png");
        powerUpImg = loadImage("PowerUP.png");
        bonusImg = loadImage("PowerUPBonus2.png");
    }

    private Image loadImage(String fileName) {
        URL path = getClass().getClassLoader().getResource(fileName);
        assert path != null;
        return new ImageIcon(path).getImage();
    }
}
