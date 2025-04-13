package com.group24;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

class MazePathFinderTest {

    private final int [][]maze1 = new int[][]  {{1,1,1,1,1,1},
                                                {1,0,0,0,4,1},
                                                {1,0,1,1,0,1},
                                                {1,0,1,1,0,1},
                                                {1,2,0,0,0,1},
                                                {1,1,1,1,1,1}};

    private final int [][]maze2 = new int[][]  {{1,1,1,1,1,1,1},
                                                {1,0,0,0,0,4,1},
                                                {1,0,1,1,1,0,1},
                                                {1,0,1,1,1,0,1},
                                                {1,2,0,0,0,0,1},
                                                {1,1,1,1,1,1,1}};

    private final int [][]maze3 = new int[][]  {{1,1,1,1,1,1,1},
                                                {1,2,0,1,0,4,1},
                                                {1,1,1,1,1,1,1}};

    
    /**
     * Test for the findPathBFS method in the MazePathFinder class.
     * Checks if the path from the start position to the end position is correct.
     * Square maze, two similar paths
     */
    @Test
    void findPathBFS1() {

        int []pos_init = {4,1};
        int []pos_end = {1,4};
        MazePathFinder mazePathFinder = new MazePathFinder(maze1);

        List<int[]> path_actual = mazePathFinder.findPathBFS(pos_init, pos_end);

        List<int[]> path_expected = new ArrayList<int[]>();
        path_expected.add(new int[] {4,2});
        path_expected.add(new int[] {4,3});
        path_expected.add(new int[] {4,4});
        path_expected.add(new int[] {3,4});
        path_expected.add(new int[] {2,4});
        path_expected.add(new int[] {1,4});

        // Assert whether the two lists are equal
        for (int i = 0; i < path_expected.size(); i++) {
            assertArrayEquals(path_expected.get(i), path_actual.get(i));
        }
    }

    /**
     * Test for the findPathBFS method in the MazePathFinder class.
     * Checks if the path from the start position to the end position is correct.
     * Rectangular maze, two similar paths
     */
    @Test
    void findPathBFS2() {

        int []pos_init = {4,1};
        int []pos_end = {1,5};
        MazePathFinder mazePathFinder = new MazePathFinder(maze2);

        List<int[]> path_actual = mazePathFinder.findPathBFS(pos_init, pos_end);

        List<int[]> path_expected = new ArrayList<int[]>();
        path_expected.add(new int[] {4,2});
        path_expected.add(new int[] {4,3});
        path_expected.add(new int[] {4,4});
        path_expected.add(new int[] {4,5});
        path_expected.add(new int[] {3,5});
        path_expected.add(new int[] {2,5});
        path_expected.add(new int[] {1,5});

        // Assert whether the two lists are equal
        for (int i = 0; i < path_expected.size(); i++) {
            assertArrayEquals(path_expected.get(i), path_actual.get(i));
        }
    }

    /**
     * Test for the findPathBFS method in the MazePathFinder class, when
     * the start and end positions are the same.
     * 
     */
    @Test
    void findPathBFS3() {

        int []pos_init = {4,1};
        int []pos_end = {4,1};

        MazePathFinder mazePathFinder = new MazePathFinder(maze1);

        List<int[]> path_actual = mazePathFinder.findPathBFS(pos_init, pos_end);

        List<int[]> path_target = new ArrayList<>();

        System.out.println(path_actual);

        Assertions.assertEquals(path_actual, path_target);
    }

    /**
     * Test for the findPathBFS method in the MazePathFinder class.
     * Checks for branch when there is no path between the start and end positions.
     */
    @Test
    void findPathBFS4() {

        int []pos_init = {1,1};
        int []pos_end = {1,5};
        MazePathFinder mazePathFinder = new MazePathFinder(maze3);

        List<int[]> path_actual = mazePathFinder.findPathBFS(pos_init, pos_end);

        List<int[]> path_target = new ArrayList<>();

        Assertions.assertEquals(path_actual, path_target);
    }
}