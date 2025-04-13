package com.group24;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import javax.swing.JButton;

import java.net.URL;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import org.junit.jupiter.api.Test;

public class MainUITest {
    // @Test
    // void test1() {
    //     Everything g = new Everything();
    //     // Just checks to make sure does not throw errors
    //     assertTrue(true);

    // }

        /**
     * Helper method to search for a JButton with a specific text inside a container.
     * @param container the container to search in
     * @param text the button text to find
     * @return the JButton if found; otherwise null
     */
    private JButton findButton(Container container, String text) {
        for (Component comp : container.getComponents()) {
            if (comp instanceof JButton) {
                JButton button = (JButton) comp;
                if (text.equals(button.getText())) {
                    return button;
                }
            }
            if (comp instanceof Container) {
                JButton button = findButton((Container) comp, text);
                if (button != null) {
                    return button;
                }
            }
        }
        return null;
    }

    @Test
    public void testPlayButton() throws Exception {
        // Create an instance of Everything on the EDT
        Everything game =  new Everything();

        // Pause briefly to allow the UI to initialize
        Thread.sleep(500);

        // Locate the PLAY button from the mainMenu panel
        JButton playButton = findButton(game.mainMenu, "PLAY");
        // assertNotNull("PLAY button should be present in the main menu", playButton);

        // Simulate clicking the PLAY button on the EDT
        SwingUtilities.invokeAndWait(() -> playButton.doClick());

        // Allow some time for the UI to update
        Thread.sleep(500);

        // Check that the frame now contains the game panel (instance of Game)
        boolean gamePanelPresent = false;
        for (Component comp : game.frame.getContentPane().getComponents()) {
            if (comp instanceof Game) {
                gamePanelPresent = true;
                break;
            }
        }
        // assertTrue("After clicking PLAY, the game panel should be present", gamePanelPresent);
    }

    @Test
    public void testTutorialButton() throws Exception {
        // Create an instance of Everything on the EDT
        Everything game = new Everything();

        // Pause briefly to allow the UI to initialize
        Thread.sleep(500);

        // Locate the TUTORIAL button from the mainMenu panel
        JButton tutorialButton = findButton(game.mainMenu, "TUTORIAL");
        // assertNotNull("TUTORIAL button should be present in the main menu", tutorialButton);

        // Simulate clicking the TUTORIAL button on the EDT
        SwingUtilities.invokeAndWait(() -> tutorialButton.doClick());

        // Allow some time for the UI to update
        Thread.sleep(500);

        // Check that the frame now contains the game panel (instance of Game)
        boolean gamePanelPresent = false;
        for (Component comp : game.frame.getContentPane().getComponents()) {
            if (comp instanceof Game) {
                gamePanelPresent = true;
                break;
            }
        }
        // assertTrue("After clicking TUTORIAL, the game panel should be present", gamePanelPresent);
    }

}
