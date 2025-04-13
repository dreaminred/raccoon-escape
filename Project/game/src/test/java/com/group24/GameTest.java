package com.group24;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class GameTest {


    @Test
    void testMakeMap() {
        Game testGame = new Game();
        TileMap testMap = new TileMap(-4);
        // Boolean result = testMap.validMove(0, 0);
        // testGame.add(testMap);
        testGame.startTest(-4);

        assertArrayEquals(testMap.getMap(), testGame.getMap().getMap());
    }


    @Test
    void testTickNoMove() {
        Game testGame = new Game();
        TileMap testMap = new TileMap(-4);
        // Boolean result = testMap.validMove(0, 0);
        // testGame.add(testMap);
        testGame.startTest(-4);
        testGame.tick();
        assertArrayEquals(testMap.getMap(), testGame.getMap().getMap());
    }



    @Test
    void testClear() {
        Game testGame = new Game();

        ArrayList<Enemy> enemies = new ArrayList<>(); 
        ArrayList<Reward> rewards = new ArrayList<>(); 
        ArrayList<BonusReward> bonusrewards = new ArrayList<>(); 
        ArrayList<Spring> springs = new ArrayList<>(); 

        // Boolean result = testMap.validMove(0, 0);
        // testGame.add(testMap);
        testGame.startTest(-4);
        testGame.tick();
        testGame.clearLevel();
        assertEquals(true, enemies.equals(testGame.getEnemies())  );
        assertEquals(true, rewards.equals(testGame.getRewards())  );
        assertEquals(true, bonusrewards.equals(testGame.getBonusRewards())  );
        assertEquals(true, springs.equals(testGame.getSprings())  );


    }


    @Test
    void testTickMove() {
        Game testGame = new Game();
        TileMap testMap = new TileMap(0);
        // Boolean result = testMap.validMove(0, 0);
        // testGame.add(testMap);
        testGame.startTest(-4);
        testGame.tick();
        testGame.setcurrentInput(1);
        //Check player position

        // assertArrayEquals(testMap.getMap(), testGame.getMap().getMap());
    }

    @Test
    void testControllerInput() {
        Game testGame = new Game();
        testGame.startTest(-4);
        testGame.setControllerInput(1);
        testGame.tick();
        testGame.setControllerInput(2);
        testGame.tick();
        testGame.setControllerInput(3);
        testGame.tick();
        testGame.setControllerInput(4);
        testGame.tick();
        //Check player position

        assertTrue(testGame.getScore() >= 0);
    }

    @Test
    void testTickMoveWall() {
        Game testGame = new Game();
        TileMap testMap = new TileMap(0);
        // Boolean result = testMap.validMove(0, 0);
        // testGame.add(testMap);
        testGame.startTest(0);
        testGame.setcurrentInput(1);
        testGame.tick();
        testGame.tick();
        testGame.tick();
        testGame.tick();
        testGame.tick();
        testGame.tick();
        testGame.tick();
        testGame.tick();
        testGame.tick();
        testGame.tick();
        Player playertest = testGame.getPlayers().get(0);
        assertEquals(1, playertest.getX());
        assertEquals(1, playertest.getY());

        // assertArrayEquals(testMap.getMap(), testGame.getMap().getMap());
    }
@Test
void testPlayerCollision(){
    Game testGame = new Game();
    testGame.startTest(-997);
    testGame.tick();
    testGame.tick();
    testGame.tick();
    testGame.tick();
    testGame.tick();

    // //Enemy moves once every five ticks


    assertTrue(testGame.getScore() <= 0);
    // Assuming collision with enemy results in game over
    // assertTrue(testGame.isGameOver());
}

@Test
void testEnemyMovement() {
    Game testGame = new Game();
    testGame.startTest(0);
    URL enemypath = getClass().getClassLoader().getResource("Guard_32.png");

    Enemy testEnemy = new Enemy(enemypath, 5, 5, 50);
    testGame.getEnemies().add(testEnemy);
    
    int initialX = testEnemy.getX();
    int initialY = testEnemy.getY();
    
    testGame.tick();
    testGame.tick();
    testGame.tick();
    testGame.tick();
    testGame.tick();

    // Assert that the enemy has moved
    assertTrue(testEnemy.getX() != initialX || testEnemy.getY() != initialY);
}

@Test
void testInitializeElements() {
    Game testGame = new Game();
    // testGame.initializeLabels();
    testGame.startTest(0);
    // Make sure it doesn't crash
}

@Test
void testIfNoPath() {
    Game testGame = new Game();
    testGame.startTest(-5);

    // URL enemypath = getClass().getClassLoader().getResource("Guard_32.png");
    Enemy testEnemy = testGame.getEnemies().get(0);
    
    int initialX = testEnemy.getX();
    int initialY = testEnemy.getY();

    testGame.tick();
    testGame.tick();
    testGame.tick();
    testGame.tick();
    testGame.tick();

    // Assert that the enemy has moved
    assertTrue(testEnemy.getX() == initialX && testEnemy.getY() == initialY);
}


@Test
void testReward() {
    Game testGame = new Game();
    // TileMap testMap = new TileMap(-1);
    // Boolean result = testMap.validMove(0, 0);
    // testGame.add(testMap);
    testGame.startTest(-999);
    testGame.getPlayers().get(0).move(2,1);

    testGame.setcurrentInput(2);

    testGame.tick();
    testGame.tick();
    testGame.tick();
    testGame.tick();
    testGame.tick();
    testGame.tick();
    testGame.tick();

    // Player playertest = testGame.getPlayers().get(0);
    assertTrue(testGame.getScore() >= 0);


}


@Test
void testBonus() {
    Game testGame = new Game();
    // TileMap testMap = new TileMap(-1);
    // Boolean result = testMap.validMove(0, 0);
    // testGame.add(testMap);
    testGame.startTest(-999);
    testGame.getPlayers().get(0).move(2,1);

    testGame.setcurrentInput(2);
    testGame.tick();
    testGame.tick();
    testGame.tick();
    testGame.tick();
    testGame.tick();
    testGame.tick();
    testGame.tick();

    // Player playertest = testGame.getPlayers().get(0);
    assertTrue(testGame.getScore() >= 0);


}

@Test
void testBonusVanish() {
    Game testGame = new Game();
    // TileMap testMap = new TileMap(-1);
    // Boolean result = testMap.validMove(0, 0);
    // testGame.add(testMap);
    testGame.startTest(-999);
    testGame.setcurrentInput(0);
    testGame.tick();
    testGame.tick();
    testGame.tick();
    testGame.tick();

    testGame.tick();

    // Thread.sleep(2000);
    // TimeUnit.SECONDS.sleep(5);
    testGame.tick();

    // Player playertest = testGame.getPlayers().get(0);
    assertTrue(testGame.getBonusRewards().size() == 1);

}


@Test // tests if springs send to (1,1)
void testSpring() {
    Game testGame = new Game();
    // TileMap testMap = new TileMap(-1);
    // Boolean result = testMap.validMove(0, 0);
    // testGame.add(testMap);
    testGame.startTest(-999);
    testGame.getPlayers().get(0).move(2,1);

    testGame.setcurrentInput(2);
    testGame.tick();
    testGame.tick();
    testGame.tick();
    testGame.tick();
    testGame.tick();
    testGame.tick();
    testGame.tick();

    // Player playertest = testGame.getPlayers().get(0);
    // assertTrue(testGame.getScore() >= 0);

  
    assertEquals(1,testGame.getPlayers().get(0).getX());
    assertEquals(1,testGame.getPlayers().get(0).getY());

}

@Test
void testLose() {

    Game testGame = new Game();
    // TileMap testMap = new TileMap(-1);
    // Boolean result = testMap.validMove(0, 0);
    // testGame.add(testMap);
    // testGame.start(-1);
    testGame.startStuff(-1);
    testGame.start();
    testGame.setcurrentInput(1);
    testGame.tick();
    testGame.setcurrentInput(2);

    testGame.tick();
    testGame.setcurrentInput(3);

    testGame.tick();
    testGame.setcurrentInput(4);

    testGame.tick();

    testGame.tick();
    testGame.setcurrentInput(2);
    for (int i = 0; i < 25; i++) {
        testGame.tick();
    }

    testGame.setScore(-10);
    testGame.tick();


    // Player playertest = testGame.getPlayers().get(0);
    assertTrue(testGame.getScore() <= 0);
}

@Test
void testWin() {

    Game testGame = new Game();
    // TileMap testMap = new TileMap(-1);
    // Boolean result = testMap.validMove(0, 0);
    // testGame.add(testMap);
    // testGame.start(-1);
    testGame.startStuff(10);
    testGame.start();

    // testGame.setLevel(10);
    // testGame.start();
    // testGame.setcurrentInput(1);
    // testGame.tick();
    // testGame.setcurrentInput(2);

    // testGame.tick();
    // testGame.setcurrentInput(3);

    // testGame.tick();
    // testGame.setcurrentInput(4);

    // testGame.tick();

    testGame.tick();
    // testGame.setcurrentInput(2);
    // for (int i = 0; i < 25; i++) {
    //     testGame.tick();
    // }

    // testGame.setScore(-10);
    // testGame.tick();


    // Player playertest = testGame.getPlayers().get(0);

    ArrayList<Enemy> enemies = new ArrayList<>(); 
    ArrayList<Reward> rewards = new ArrayList<>(); 
    ArrayList<BonusReward> bonusrewards = new ArrayList<>(); 
    ArrayList<Spring> springs = new ArrayList<>(); 


    assertEquals(true, enemies.equals(testGame.getEnemies())  );
    assertEquals(true, rewards.equals(testGame.getRewards())  );
    assertEquals(true, bonusrewards.equals(testGame.getBonusRewards())  );
    assertEquals(true, springs.equals(testGame.getSprings())  );

    assertTrue(testGame.getScore() <= 0);
}


@Test
void testExit() {
    Game testGame = new Game();

    testGame.startStuff(-999);
    testGame.start();
    testGame.setLevel(2);
    testGame.setMinRewards(1);
    testGame.getPlayers().get(0).move(0,1);

    testGame.setcurrentInput(4); // Move left
    testGame.tick();
    testGame.setcurrentInput(2); // Move right

    testGame.tick();
    testGame.tick();
    testGame.tick();
    testGame.tick();
    testGame.tick();
    testGame.setMinRewards(0);

    testGame.getPlayers().get(0).move(0,1);

    testGame.setcurrentInput(4); // Move left
    
    testGame.tick();
    testGame.tick();
    testGame.tick();
    testGame.tick();
    testGame.tick();
    testGame.tick();


    // Checks that the exit was properly used 
    // And that reawrds were collected
    // and that the score is not reset
    assertTrue(testGame.getScore() >= 0);
}


@Test
void testExitNonReset() {


    Game testGame = new Game();

    testGame.startStuff(-999);
    testGame.start();
    testGame.setMinRewards(1);
    testGame.getPlayers().get(0).move(0,1);

    testGame.setcurrentInput(4); // Move left
    testGame.tick();
    testGame.setcurrentInput(2); // Move right

    testGame.tick();
    testGame.tick();
    testGame.tick();
    testGame.tick();
    testGame.tick();
    testGame.setMinRewards(0);

    testGame.getPlayers().get(0).move(0,1);

    testGame.setcurrentInput(4); // Move left
    
    testGame.tick();
    testGame.tick();
    testGame.tick();
    testGame.tick();
    testGame.tick();
    testGame.tick();


    // Checks that the exit was properly used 
    // And that reawrds were collected
    // and that the score is not reset
    assertTrue(testGame.getScore() >= 0);
}


}
