package com.group24;

import javax.swing.JPanel;
import java.awt.*;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import java.net.URL;

/**
 * Main Entity class that represents an object on the map
 * @author Group 24
 */
public class Entity extends JPanel{
    private Image image;
    private int x, y;
    // Fix this later, hardcode now
    private int xMul = Constants.TILE_SIZE;
    private int yMul = Constants.TILE_SIZE;
    
    /**
     * Constructor for a basic entity
     * @param imagePath A URL to the file of the image
     * @param x x coordinate of the entity
     * @param y y coordinate of the entity
     */
    public Entity(URL imagePath, int x, int y) {
            this.x = x;
            this.y = y;
            image = new ImageIcon(imagePath).getImage();
    }
    /**
     * Uses x and y coordinates to redraw the entity in a different location.
     * @param x an integer representing the new x position.
     * @param y an integer representing the new y position.
     */
    public void move(int x, int y) {
        this.x = x;
        this.y = y;
        // g.drawImage(image, x, y, this);
        repaint();
        // paintComponents(getGraphics());
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(image, x * xMul, y*yMul, this);
    }


    /**
     * Getter for the x coord
     * @return int x coordinate
     */
    public int getX() {
        return this.x;
    }


    /**
     * Getter for the y coordinates
     * @return int y coordinate
     */
    public int getY() {
        return this.y;
    }

    /**
     * Getter for the image
     * @return the image
     */
    public Image getImage() {
        return this.image;
    }

}
