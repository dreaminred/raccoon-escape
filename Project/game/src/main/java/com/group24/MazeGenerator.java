package com.group24;

import java.net.URL;
import java.util.*;
import java.time.Instant;

public class MazeGenerator {

    private static final int[][] DIRECTIONS = {{0, 1}, {1, 0}, {0, -1}, {-1, 0}}; // East, South, West, North

    int mazeWidth;
    int mazeHeight;
    int [][] maze;

    Random rand = new Random(Instant.now().getEpochSecond());

    private ArrayList<Enemy> enemies = new ArrayList<>();
    private ArrayList<Reward> rewards = new ArrayList<>();
    private ArrayList<BonusReward> bonusrewards = new ArrayList<>();
    private ArrayList<Spring> springs = new ArrayList<>();

    URL enemypath = getClass().getClassLoader().getResource("Guard_32.png");
    URL poweruppath = getClass().getClassLoader().getResource("PowerUP.png");
    URL bonusPath = getClass().getClassLoader().getResource("PowerUPBonus2.png");
    URL trappath = getClass().getClassLoader().getResource("trap_32.png");
    URL springpath = getClass().getClassLoader().getResource("Punishment.png");


    MazeGenerator(int width, int height) {
        
        // Throw exception if maze dimensions are invalid
        if (width <= 0 || height <= 0) {
            throw new IllegalArgumentException("Invalid maze dimensions");
        }
        this.mazeWidth = width;
        this.mazeHeight = height;
        this.maze = new int [width][height];
        // Assign all cells to be a wall
        for (int i = 0; i < this.mazeWidth; i++){
            Arrays.fill(this.maze[i], 1);
        }
    }

    /**
     * Generates a maze using the Recursive Backtracker algorithm
     * @return 2D int array representation of the maze
     */
    public int[][] generateMaze(){
        int x_initial = mazeWidth/2;
        int y_initial = mazeHeight/2;
        createPassage(x_initial, y_initial);
        carveBorder();
        patchBorder();
        connectCentre();
        generateEntities();
        generateExit();
        return this.maze;
    }

    /** Carves passages in a maze using the recursive backtracker algorithm. Set the middle of the maze as the starting point to reduce direction bias
     *
     * @param x_pos Initial x coordinate
     * @param y_pos Initial y coordinate
     */
    private void createPassage(int x_pos, int y_pos){
        this.maze[x_pos][y_pos] = 0;

        // Randomize traversal order, uses .clone() since DIRECTIONS is final
        List<int[]> directions = new ArrayList<>(Arrays.asList(DIRECTIONS.clone()));
        Collections.shuffle(directions, rand);

        for(int[] direction : directions){
            int x_new = x_pos + direction[0] * 2; // Multiply by two to look ahead (due to walls being represented as a cell)
            int y_new = y_pos + direction[1] * 2;

            // Validate if new cell is within maze borders and is a wall
            if (x_new >= 0 && x_new < this.mazeWidth && y_new >= 0 && y_new < this.mazeHeight && this.maze[x_new][y_new] == 1){
                this.maze[x_pos + direction[0]][y_pos + direction[1]] = 0;
                createPassage(x_new, y_new);
            }
        }
    }

    /** Patches the outer border
     *
     */
    private void patchBorder() {

        for (int row = 0; row < this.mazeWidth; row++) {
            this.maze[row][0] = 1;
            this.maze[row][this.mazeHeight-1] = 1;
        }

        for (int col = 0; col < this.mazeHeight; col++) {
            this.maze[0][col] = 1;
            this.maze[this.mazeWidth-1][col] = 1;
        }

    }

    /** Carves out the inner layer of the border to connect mazes
     *
     */
    private void carveBorder() {

        for(int row = 1; row < (this.mazeWidth-1); row++) {
            this.maze[row][1] = 0;
            this.maze[row][(this.mazeHeight-2)] = 0;
        }

        for (int col = 1; col < (this.mazeHeight-1); col++) {
            this.maze[1][col] = 0;
            this.maze[this.mazeWidth-2][col] = 0;
        }
    }

    /** Connects disconnected mazes together
     *
     */
    public void connectCentre() {

        List<int[]> randomPassages = getRandomPassages(5, this.mazeWidth/5, this.mazeWidth*4/5,
                this.mazeHeight/5, this.mazeHeight*4/5);

        for (int[] randomPassage : randomPassages) {
            for (int[] direction : DIRECTIONS) {
                int x = randomPassage[0] + direction[0]*2;
                int y = randomPassage[1] + direction[1]*2;
                if (this.maze[x][y] == 0) {
                    int x_temp = randomPassage[0] + direction[0];
                    int y_temp = randomPassage[1] + direction[1];
                    this.maze[x_temp][y_temp] = 0;
                }
            }
        }

    }

    /** Randomly selects n number of tiles inside the determined grid area of a maze
     *
     * @param count number of tiles to return
     * @param row_start grid row start
     * @param col_start grid col end
     * @param row_end grid row end
     * @param col_end
     * @return
     */
    public List<int[]> getRandomPassages(int count, int row_start, int col_start, int row_end, int col_end) {
        List<int[]> randomPassages = new ArrayList<>();

        // Scanning over the input area and checking for passage cells
        for (int row = row_start; row <= row_end; row++) {
            for (int col = col_start; col <= col_end; col++) {
                if (this.maze[row][col] == 0) {
                    randomPassages.add(new int[]{row, col});
                }
            }
        }

        Collections.shuffle(randomPassages, rand);

        if (randomPassages.isEmpty()) {
            return null;
        } else if (randomPassages.size() <= count) {
            return randomPassages;
        }

        return randomPassages.subList(0, count);
    }

    /** Gets n number of random passages in the specified quadrant
     *
     * @param quadrant quadrant to find random passages
     * @param count number of passages
     */
    private List<int[]> getRandomPassageInQuadrant(int quadrant, int count) {
        int row_midpoint = this.mazeWidth/2;
        int col_midpoint = this.mazeHeight/2;
        List<int[]> location;
        if (quadrant == 0) {
            location = getRandomPassages(count,1,col_midpoint+1,row_midpoint,this.mazeHeight-1);
        }
        else if (quadrant == 1) {
            location = getRandomPassages(count,1,1,row_midpoint, col_midpoint);
        }
        else if (quadrant == 2) {
            location = getRandomPassages(count,row_midpoint+1,1,this.mazeWidth-1,col_midpoint);
        }
        else if (quadrant == 3) {
            location = getRandomPassages(count,row_midpoint+1,col_midpoint+1,this.mazeWidth-1,this.mazeHeight-1);
        }
        else {
            location = getRandomPassages(count,1,1,this.mazeWidth-1,this.mazeHeight-1);
        }

        return location;
    }

    private void generateEntities() {
        int[] QUADRANTS = {0, 2, 3};
        Random random = new Random();
        int maybeDoBonus = random.nextInt(6);
        for (int QUAD: QUADRANTS) {
            List<int[]> locations = getRandomPassageInQuadrant(QUAD, 4);
            int x_reward = locations.getFirst()[0];
            int y_reward = locations.getFirst()[1];
            int x_enemy = locations.get(1)[0];
            int y_enemy = locations.get(1)[1];
            int x_trap = locations.get(2)[0];
            int y_trap = locations.get(2)[1];
            int x_spring = locations.get(3)[0];
            int y_spring = locations.get(3)[1];

            if(QUAD == maybeDoBonus){
                this.bonusrewards.add(new BonusReward(bonusPath, y_reward, x_reward, 10,5000));
            }
            else{
                this.rewards.add(new Reward(poweruppath, y_reward, x_reward, 10));
            }
            this.enemies.add(new Enemy(enemypath, y_enemy, x_enemy, 100));
            this.rewards.add(new Reward(trappath, y_trap, x_trap, -10));
            this.springs.add(new Spring(springpath, y_spring, x_spring));
        }
    }

    private void generateExit() {
        // Select a random side
        int randSide = rand.nextInt((3-0) + 1) + 0;
        int min = 1;
        int max;
        switch (randSide) {
            case 0:
                max = this.mazeWidth-2;
                int randRow = rand.nextInt((max - min) + 1) + min;
                this.maze[randRow][this.mazeHeight-1] = 3;
            case 1:
                max = this.mazeHeight-2;
                int randCol = rand.nextInt((max - min) + 1) + min;
                this.maze[0][randCol] = 3;
            case 2:
                max = this.mazeWidth-2;
                int randRow1 = rand.nextInt((max - min) + 1) + min;
                this.maze[randRow1][0] = 3;
            case 3:
                max = this.mazeHeight-2;
                int randCol1 = rand.nextInt((max - min) + 1) + min;
                this.maze[this.mazeWidth-1][randCol1] = 3;
        }
    }



    /** Prints maze to console for debugging purposes
     *
     */
    public void printMaze() {
        for (int y = 0; y < mazeHeight; y++) {
            for (int x = 0; x < mazeWidth; x++) {
                System.out.print(maze[x][y] == 1 ? " " : "|"); // ' ' for path, '|' for walls
            }
            System.out.println();
        }
    }

    /** Returns generated maze.
     *
     * @return a 2d int array where 0 is a passage and 1 is a wall
     */
    public int[][] getMaze() {
        return this.maze;
    }

    public ArrayList<Enemy> getEnemies() {
        return enemies;
    }

    public ArrayList<Reward> getRewards() {
        return rewards;
    }

    public ArrayList<Spring> getSprings() {
        return springs;
    }

    public ArrayList<BonusReward> getBonusRewards() {
        return bonusrewards;
    }
}
