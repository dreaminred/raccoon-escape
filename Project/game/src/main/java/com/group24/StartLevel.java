package com.group24;

import javax.swing.JFrame;
/**
 * Class to start level from the menu buttons
 */
public class StartLevel {
    /**
 * level method to set the JFrame and Game to start the level
 * @param jf JFrame for the game
 * @param g the Game object
 * @param level number to start
 */
    public static void level(JFrame jf, Game g, int level){
        jf.getContentPane().removeAll();
        jf.add(g);
        g.startStuff(level);
        g.start();
        jf.revalidate();
        jf.repaint();
        g.requestFocusInWindow();
    }  
}
