package com.group24;

/**
 * Interface class to pass information between game and main screen
 * @author Group 24
 */
public interface GameListener {
    void onGameLost();
    void menuPressed();
    void onGameWon(int finalScore);
}