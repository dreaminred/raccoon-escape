package com.group24;
import java.net.URL;

/**
 * Class for the spring that sends player back to the corner
 */
public class Spring extends Entity{

    /**
     * Constructor for the Spring
     * @param imagePath The URL of the path to the image
     * @param x The y coord of the Spring
     * @param y the x coord of the Springr
     */
    public Spring(URL imagePath, int x, int y) {
        super(imagePath, x, y);
     
    }

}
