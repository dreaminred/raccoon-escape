package com.group24;
import java.awt.*;
import java.awt.event.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.*;
// import javax.swing.Timer;

// import com.group24.Tile;
// We don't need all of these imports, but I don't know which ones we do need. 

/**
 * 
 * The class that contains the both the logic for the game and the panel that gets added to the main panel
 * @author Group 24
 */
public class Game extends JPanel implements ActionListener {

    // I have no idea what I'm doing ignore this
    //Needs a list entities
    // ArrayList<Tile> tiles = new ArrayList<>(); 
    private ArrayList<Player> players = new ArrayList<>(); 
    private ArrayList<Enemy> enemies = new ArrayList<>(); 
    private ArrayList<Reward> rewards = new ArrayList<>(); 
    private ArrayList<BonusReward> bonusrewards = new ArrayList<>(); 
    private ArrayList<Spring> springs = new ArrayList<>(); 
    // ArrayList<Enemy> list = new ArrayList<>(); 
    private ArrayList<Entity> exits = new ArrayList<>();


    private GameData gamedata;

    private GameListener gameListener;
    JLabel levellabel;
    JLabel scorelabel;
    JLabel timerlabel;
    // The current input tracks what
    private Controller controller = new Controller();
    private int currentInput = 0;
    private int xsize = Constants.TILE_SIZE;
    private int ysize = Constants.TILE_SIZE;
    private Timer timer;
    private Player raccoon;
    private TileMap tileMap;
    private int level = 1;
    private int score = 0;
    private int startTime = 0;
    private int currentTime = 0;
    private MazePathFinder finder;
    private int tempcounterthing = 0;
    private int rewardsToProgress = 0;
    private int finalScore = 0;


    private GameUI gameUI;

    /**
     * Constructor for the game, just basic initilization stuff
     */
    public Game() {
        addKeyListener(this.controller);
        setFocusable(true);
        requestFocus();
        
    }
    /**
     * Initalizes the game with the parameters given
     * @param level The level that the player is on now
     */
    public void startStuff(int level) {

        // initializeLabels();
        // gameUI.updateUI(level, score, (currentTime - startTime) / 1000);
        // gameUI.initi
        this.gameUI = new GameUI(this, gameListener);
        gameUI.attachToPanel(this);
        this.level = level;
        repaint();

    }

    /** 
     * Sets the tilemap to the approriate level and starts the timer
     * 
     */
    public void start() {
        startTime = (int)System.currentTimeMillis();

        tileMap = new TileMap(level); 
        this.add(tileMap);
        initializeStuff(tileMap);
        startTimer();

    }
    /** 
     * Sets the tilemap to the approriate level 
     * For testing purposes
     */
    public void startTest(int testlevel) {
        startTime = (int)System.currentTimeMillis();

        tileMap = new TileMap(testlevel); 
        this.add(tileMap);
        initializeStuff(tileMap);

    }


    /** 
     * Start the internal in game timer loop
     */
    public void startTimer() {
        timer = new Timer(); // Create a Timer object
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                tick();
            }
        }, 0, 100); // Initial delay of 0 milliseconds, repeat every 1000 milliseconds
    }

    /** 
     * Stop the timer loop
     */
    public void stop() {
        timer.cancel();
    }


    /**
     * 
     * Takes a map and initilizes the game to have the parameters of the map
     * @param map Tile map of the level
     * 
     */
    public void initializeStuff(TileMap map) {

        URL playerIcon = getClass().getClassLoader().getResource("Raccoon_32.png");

        currentInput = 0;

        gamedata = map.getGameData();


        players = gamedata.getPlayers();
        raccoon = gamedata.getPlayers().get(0);
        enemies = gamedata.getEnemies();
        rewards = gamedata.getRewards();
        bonusrewards = gamedata.getBonusRewards();
        springs = gamedata.getSprings();
        finder = new MazePathFinder(tileMap.getMap());
        finder.setMaze(tileMap.getMap());


        for (Entity a : players) {
            a.move(a.getX(), a.getY());
            repaint();
        }
        for (Entity a : enemies) {
            a.move(a.getX(), a.getY());
            repaint();
        }
        for (Entity a : rewards) {
            a.move(a.getX(), a.getY());
            repaint();
        }
        for (Entity a : bonusrewards) {
            a.move(a.getX(), a.getY());
            repaint();
        }
        for (Entity a : springs) {
            a.move(a.getX(), a.getY());
            repaint();
        }
        //
        // set rewards needed to progress
        rewardsToProgress=0;
        if(level >= 0){
            for(Reward a: rewards){
                if(a.amount > 0) rewardsToProgress++;
            }
        }

    }

    /**
     * One iteration of a tick that advances the game state forwards
     * Is public for testing purposes
     */
    public void tick() {

        // Testing code
        // System.out.println("Task executed at: " + System.currentTimeMillis());
        currentTime = (int)System.currentTimeMillis();
        // System.out.println(getWidth());

        //
        // Perform move based on current user input
        int startX = 0;
        int startY = 0;
        Player a = players.get(0);
        startX = a.getX();
        startY = a.getY();
        int nextx = 0;
        int nexty = 0;
        switch(controller.input){
            case 1:
                nextx = a.getX();
                nexty = a.getY()-1;
                break;
            case 2:
                nextx = a.getX()+1;
                nexty = a.getY();
                break;
            case 3:
                nextx = a.getX();
                nexty = a.getY()+1;
                break;
            case 4:
                nextx = a.getX()-1;
                nexty = a.getY();
                break;
            default:
                // Backup for initialization
                nextx = a.getX();
                nexty = a.getY();
                a.move(1, 1);
        }

        repaint();
        //
        // Check for collision with other entity types
        playerToEnemyCollision(nextx, nexty, a);
        enemyToPlayerCollision();
        exitCollision(startX, startY);
        rewardCollision();
        bonusCollision();
        bonusRemoval();
        springCollision();    
        //
        // Update the levels the scores and the timers

        // Check for testing purposes
        if (gameUI != null) {
            gameUI.updateUI(level, score, (currentTime - startTime) / 1000);
        }
        // updateLabels();

        //
        // Check for win or loss conditions
        checkWinOrLoss();
        repaint();
    }
    private void playerToEnemyCollision(int nextx, int nexty, Player a){
        if (tileMap.validMove(nextx, nexty)) {
                int tempamount = 0;
                int tempvalid = 1;
                for (Enemy b : enemies) {
                    if (nextx == b.getX() && nexty == b.getY()){
                        tempvalid = 0;
                        tempamount = tempamount + b.getAmount();
                    }
                }
                if (tempvalid == 1) {
                    a.move(nextx, nexty);
                }
                else {
                    // You lose if you touch enemey
                    score -= 1000000000;
                }
        }
    }
    private void checkWinOrLoss(){
        // Check if loss
        if (score < 0) {
            // player loses
            clearLevel();
            lose();
        }
        // check if won
        if(level > 5){
            // player wins
            clearLevel();
            win();
        }
    }
    private void enemyToPlayerCollision(){
        tempcounterthing = tempcounterthing + 1;
        for (Enemy a : enemies) {
            // Slows down the enemies by a factor of like, 5
            if ((tempcounterthing % 5 ) == 0) {
                int[] temp = finder.getNextMove(a.getX(), a.getY(), raccoon.getX(), raccoon.getY());
                int tempx = temp[0];
                int tempy = temp[1];
                // Check if valid
                int tempinvalid = 0;
                for (Enemy b : enemies) { 
                    if (b!=a && tempx == b.getX() && tempy == b.getY()) {
                        tempinvalid = 1;
                    }
                }
                // If you move onto enemy
                if (tempx == raccoon.getX() && tempy == raccoon.getY()) {      
                    tempinvalid = 1;
                    // Do score stuff
                    // You lose
                    score = score - 1000000;
                }
                if (tempinvalid == 0) {
                    a.move(tempx,tempy);
                }
            }
            repaint();
        }
    }
    private void exitCollision(int startX, int startY){
        // Check if player at exit and all regular trash collected
        if (tileMap.isExit(raccoon.getX(), raccoon.getY())){
            if(rewardsToProgress <= 0){
                stop();
                level = level + 1;
                if(level==1){
                    score = 0;
                } // resets score and time when you finish tutorial
                tileMap = new TileMap(level); 
                initializeStuff(tileMap);
                controller.reset();
                start();
            }
            else{
                raccoon.move(startX,startY); // reverse movement since can't leave yet
            }
        }
    }
    private void rewardCollision(){
        // Check if player is on reward
        Entity temp = null;
        for (Reward a : rewards) {
            if (a.getX() == raccoon.getX() && a.getY() == raccoon.getY()){
                temp = a;
                score = score + a.getAmount(); // do points
                rewardsToProgress--;
            }
        }
        if (temp != null) {
            rewards.remove(temp);
        }
    }
    private void bonusCollision(){
        // Check if player is on reward
        BonusReward temp = null;
        for (BonusReward a : bonusrewards) {
            if (a.getX() == raccoon.getX() && a.getY() == raccoon.getY()){
                temp = a;
                score = score + a.getAmount();
            }
        }
        if (temp != null) {
            bonusrewards.remove(temp);
        }
    }
    private void springCollision(){
         // Check if player is on spring
         Spring temp = null;
         for (Spring a : springs) {
             if (a.getX() == raccoon.getX() && a.getY() == raccoon.getY()){
                 temp = a;
                 // Do points
                raccoon.move(1,1);
                 
             }
         }
         if (temp != null) {
             springs.remove(temp);
         }
    }
    private void bonusRemoval(){
        // Check if bonus rewards should disappear
        BonusReward temp = null;
        for (BonusReward a : bonusrewards) {
            if ((startTime + a.getTime()) < currentTime){
                temp = a;
            }
        }
        if (temp != null) {
            bonusrewards.remove(temp);
        }
    }
    
    /**
     * Draws the images at their x and y coordinates*/ 
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        tileMap.paintComponent(g);
        for (Entity a : bonusrewards) {
            g.drawImage(a.getImage(), a.getX() * xsize, a.getY() * ysize, this);
        }
        for (Entity a : rewards) {
            g.drawImage(a.getImage(), a.getX() * xsize, a.getY() * ysize, this);
        }
        for (Entity a : enemies) {
            g.drawImage(a.getImage(), a.getX() * xsize, a.getY() * ysize, this);
        }
        for (Entity a : springs) {
            g.drawImage(a.getImage(), a.getX() * xsize, a.getY() * ysize, this);
        }
        for (Entity a : players) {
            g.drawImage(a.getImage(), a.getX() * xsize, a.getY() * ysize, this);
        }
    }


    /**
     * Resets all the variables and clears the frame
     */
    public void clearLevel() {
        // Clears all the arrays
         level = 1;
         finalScore = score;
         score = 0;
         startTime = 0;
         currentTime = 0;

        players = new ArrayList<>(); 
        enemies = new ArrayList<>(); 
        rewards = new ArrayList<>(); 
        bonusrewards = new ArrayList<>(); 
        springs = new ArrayList<>(); 
        exits = new ArrayList<>();
        gamedata.clear();
        this.removeAll();
    }

    /**
     * Setter for the listener to pass information up to the big frame
     * @param listener The listener
     */
    public void setGameListener(GameListener listener) {
        this.gameListener = listener;
    }


    /**
     * If the player is in a lose state
     * Stop the game
     * And sent the trigger to the listener
     */
    public void lose() {
        if (timer != null) {
            stop();
        }
        if (gameListener != null) {
            gameListener.onGameLost();
        }
    }
    public void win() {
        if (timer != null) {
            stop();
        }
        if (gameListener != null) {
            gameListener.onGameWon(finalScore);
        }
    }

    //
    // Getters for testing purposes

    /**
     * Getter for testing purposes
     * @return player list
     */
    public ArrayList<Player> getPlayers() {
        return players;
    }
    /**
     * Getter for testing purposes
     * @return enemy list
     */
    public ArrayList<Enemy> getEnemies() {
        return enemies;
    }
    /**
     * Getter for testing purposes
     * @return reward list
     */
    public ArrayList<Reward> getRewards() {
        return rewards;
    }
    /**
     * Getter for testing purposes
     * @return bonus list
     */
    public ArrayList<BonusReward> getBonusRewards() {
        return bonusrewards;
    }
    /**
     * Getter for testing purposes
     * @return spring list
     */
    public ArrayList<Spring> getSprings() {
        return springs;
    }
    /**
     * Getter for testing purposes
     * @return map
     */
    public TileMap getMap() {
        return tileMap;
    }
    /**
     * Getter for testing purposes
     * @return score 
     */
    public int getScore() {
        return score;
    }

    /** 
    * Getter for testing purposes
    * @return level 
    */
   public int getLevel() {
       return level;
   }
     /**
     * Setter for testing purposes
     */
    public void setcurrentInput(int input) {
     currentInput = input;

    }
    /**
     * for debut purposes
     * @param input new int between (inclusive) 1 and 4 
     */
    public void setControllerInput(int input){
        controller.setInput(input);
    }

    /**
     * Setter for testing purposes
     */
    public void setScore(int input) {
        score = input;
   
    }
        /**
     * Setter for testing purposes
     */
    public void setLevel(int input) {
        level = input;

    }
    /**
     * Setter for testing purposes
     */
    public void setMinRewards(int input) {
        rewardsToProgress = input;
   
    }
   

//    // I don't think we need these but I don't think it works without them
//    @Override
//    public void keyTyped(KeyEvent e) {}
    @Override
    public void actionPerformed(ActionEvent e) {    }
//    @Override
//    public void keyReleased(KeyEvent e) {     }
}
