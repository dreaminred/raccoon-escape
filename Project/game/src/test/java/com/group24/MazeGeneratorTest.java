package com.group24;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MazeGeneratorTest {

    /**
     * Generates 1000 mazes and checks the percentage of walls and paths
     * to ensure that the maze is not too sparse or too dense.
     * The maze should have a wall percentage between 30% and 70%.
     */
    @Test
    void test1() {
        int numWalls = 0;
        int numPaths = 0;
        for (int i = 0; i < 1000; i++) {
            MazeGenerator mg = new MazeGenerator(17, 30);
            mg.generateMaze();
            int[][] maze = mg.getMaze();
            for (int j = 1; j < maze.length - 1; j++) {
                for (int k = 1; k < maze[j].length - 1; k++) {
                    if (maze[j][k] == 1) {
                        numWalls++;
                    } else {
                        numPaths++;
                    }
                }
            }
        }
        double wallPercentage = (double) numWalls / (numWalls + numPaths) * 100;
        double pathPercentage = (double) numPaths / (numWalls + numPaths) * 100;
        System.out.println("Wall percentage: " + wallPercentage + "%");
        System.out.println("Path percentage: " + pathPercentage + "%");
        assertTrue(wallPercentage > 30 && wallPercentage < 70);
        assertTrue(pathPercentage > 30 && pathPercentage < 70);
    }

    /**
     * Generates a 0x0 maze
     */
    @Test
    void test2() {
        assertThrows(IllegalArgumentException.class, () -> new MazeGenerator(0,0));
    }

    /**
     * Tests the print maze to console feature
     */
    @Test
    public void testPrintMaze(){
        MazeGenerator mg = new MazeGenerator(17, 30);
        mg.printMaze();
        assertEquals(true,true); // There's nothing to check
    }

    /**
     * Generates a 2x2 square maze
     */
    @Test
    void test3() {
        MazeGenerator mg = new MazeGenerator(2,2);

        int[][] maze = mg.getMaze();
        for (int i = 0; i < maze.length; i++) {
            for (int j = 0; j < maze[i].length; j++) {
                assertTrue(maze[i][j] == 1);
            }
        }
    }

    /**
     * Generates a 10x1 rectangular maze
     */
    @Test
    void test4() {
        MazeGenerator mg = new MazeGenerator(1,10);
        int[][] maze = mg.getMaze();
        for (int i = 0; i < maze.length; i++) {
            for (int j = 0; j < maze[i].length; j++) {
                assertTrue(maze[i][j] == 1);
            }
        }
    }


}