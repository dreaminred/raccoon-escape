package com.group24;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Controller implements KeyListener {

    public int input;

    @Override
    public void keyTyped(KeyEvent e) {

    }

    public void keyPressed(KeyEvent e) {
        // If wasd
        if(e.getKeyCode() == KeyEvent.VK_W)
        {
            input = 1;
        }
        if(e.getKeyCode() == KeyEvent.VK_D)
        {
            input = 2;
        }
        if(e.getKeyCode() == KeyEvent.VK_S)
        {
            input = 3;
        }
        if(e.getKeyCode() == KeyEvent.VK_A)
        {
            input = 4;
        }
        // System.out.println(currentInput);

    }

    /**
     * for debug purposes 
     */
    public void setInput(int dir){
        if(dir > 0 && dir < 5){
            input = dir;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    public void reset() {
        input = -1;
    }

}
