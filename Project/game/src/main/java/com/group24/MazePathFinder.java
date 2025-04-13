package com.group24;

import java.lang.reflect.Array;
import java.util.*;

/**
 * Class that contains the pathfinding algorithm for the enemies
 * @author Group 24
 */
public class MazePathFinder {
    private static final int[][] DIRECTIONS = {{0, 1}, {1, 0}, {0, -1}, {-1, 0}}; // East, South, West, North

    private int[][] MAZE;
    /**
     * Constructor for the class
     * @param maze The maze to be path found
     */
    MazePathFinder(int[][] maze) {
        this.MAZE = maze;

    }


    /**
     *
     * @param pos_init initial tile coordinates
     * @param pos_final target tile coordinates
     * @return List of the optimal tiles to go to
     */
    public List<int[]> findPathBFS(int[] pos_init, int[] pos_final){
        int rows = this.MAZE.length;
        int cols = this.MAZE[0].length;

        boolean[][] visited = new boolean[rows][cols]; // 1 if tile has been visited, 0 if not
        int[][] previous = new int[rows][cols]; // Stores the parent tiles
        Queue<int[]> queue = new LinkedList<>(); // Stores the neighbouring tiles which need to be processed

        visited[pos_init[0]][pos_init[1]] = true;
        previous[pos_init[0]][pos_init[1]] = -1; // -1 represents the starting position
        queue.add(pos_init);

        // BFS terminates when all reachable tiles within a certain neighbourhood have been processed OR
        // target tile has been reached
        while (!queue.isEmpty()) {
            int[] currentTile = queue.poll();
            int currentTileRow = currentTile[0];
            int currentTileCol = currentTile[1];

            // Check if target tile is reached
            if (currentTileRow == pos_final[0] && currentTileCol == pos_final[1]) {
                return processPathFromTargetToStart(previous, pos_init, pos_final);
            }

            // Check neighbouring tiles
            for (int[] direction : DIRECTIONS) {
                int nextTileRow = currentTileRow + direction[0];
                int nextTileCol = currentTileCol + direction[1];

                // Validate if the neighbouring tile is within bounds and hasn't been visited before
                if(validateTile(nextTileRow, nextTileCol, visited)){
                    queue.add(new int[]{nextTileRow, nextTileCol});
                    visited[nextTileRow][nextTileCol] = true;
                    // Might be better to store direction instead of tile number
                    previous[nextTileRow][nextTileCol] = currentTileRow * cols + currentTileCol;

                }
            }
        }
        return new ArrayList<>(); // Return empty list if unable to find a path
    }

    /**
     *  Reconstructs path from Target to Start Tile
     *
     * @param previous a matrix of previously visited tile numbers
     * @param pos_init Starting tile
     * @param pos_final Target Tile
     * @return Path of coords
     */
    private ArrayList<int[]> processPathFromTargetToStart(int[][] previous, int[] pos_init, int[] pos_final) {
        ArrayList<int[]> path = new ArrayList<>();
        int cols = this.MAZE[0].length; // Used to calculate the row and column from tile number

        // Starting from target position, walk backwards
        int row = pos_final[0]; // current position, row
        int col = pos_final[1]; // current position, col

        // While the current position does not equal the starting position
        while(!Arrays.equals(new int[]{row, col}, pos_init)){
            path.add(new int[]{row, col}); // Add current position to path
            int prev = previous[row][col]; // Load previous tile number
            row = prev / cols; // Row corresponding to previous tile
            col = prev % cols; // Column corresponding to previous tile
        }
        Collections.reverse(path); // Reversing to a forward walk
        return path;
    }

    /**
     * Checks whether the nextTile is not a wall, is not out of boundary, and hasn't been visited before.
     * @param nextTileRow row of nextTile
     * @param nextTileCol column of nextTile
     * @param visited a 2d boolean array storing whether a tile has been visited before.
     * @return true if conditions are met, else false
     */
    private boolean validateTile(int nextTileRow, int nextTileCol, boolean[][] visited) {
        return  isWithinBounds(nextTileRow, nextTileCol) && isPassageway(nextTileRow, nextTileCol) &&
                isNotVisited(nextTileRow, nextTileCol, visited);
    }

    /**
     * Checks whether the nextTile is a passageway (0) or not (1)
     * @param row
     * @param col
     * @return
     */
    private boolean isPassageway(int row, int col) { return MAZE[row][col] != 1;}

    /**
     * Checks whether the tile is within bounds of the maze
     * @param row row
     * @param col column
     * @return true if within bounds, false if not
     */
    private boolean isWithinBounds(int row, int col) {return row >= 0 && row < MAZE.length &&
            col >= 0 && col < MAZE[0].length;}

    /**
     * Checks whether the tile has been visited before
     * @param row
     * @param col
     * @param visited
     * @return
     */
    private boolean isNotVisited(int row, int col, boolean[][] visited) {return !visited[row][col];}


    /**
     * Runs breadth-first search and returns the next move to the target position
     * @param x initial x coordinate
     * @param y initial y coordinate
     * @param tx target x coordinate
     * @param ty target y coordinate
     * @return Returns a 1 dimensional integer array of the next move. If no possible path, returns current position.
     */
    public int[] getNextMove(int x, int y, int tx, int ty) {
        int initial[] = {y,x};
        int target[] = {ty,tx};
        ArrayList<int[]> temp = new ArrayList<>();
        temp = (ArrayList<int[]>)findPathBFS(initial, target);
        if (temp.size() == 0) {
            return new int[] {x, y};
        }else {
            return new int [] {temp.get(0)[1], temp.get(0)[0]};
        }
    }

    /**
     * Setter for the maze
     * @param maze The maze to set
     */
    public void setMaze(int[][] maze){this.MAZE = maze;}


}
