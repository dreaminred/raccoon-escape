package com.group24;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GameUI {
    private JLabel levellabel;
    private JLabel scorelabel;
    private JLabel timerlabel;
    private JButton menuButton;
    private Game game;
    private GameListener gameListener;

    int level = 0;
    int score = 0;

    public GameUI(Game game, GameListener gameListener) {
        this.game = game;
        this.gameListener = gameListener;
    }

    /**
     * Attaches UI elements to the given panel and initializes them.
     */
    public void attachToPanel(JPanel panel) {
        // Set the layout to null so we can manually set bounds
        panel.setLayout(null);

        // Create labels and button
        levellabel = new JLabel("", JLabel.LEFT);
        scorelabel = new JLabel("", JLabel.LEFT);
        timerlabel = new JLabel("", JLabel.LEFT);
        
        menuButton = new JButton("MENU");
        menuButton.setBounds(850, 10, 100, 20);
        menuButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                game.stop();
                game.clearLevel();
                if (gameListener != null) {
                    gameListener.menuPressed();
                }
            }
        });

        // Add components to the panel
        panel.add(levellabel);
        panel.add(scorelabel);
        panel.add(timerlabel);
        panel.add(menuButton);

        // Initialize labels with starting values
        updateLabel(levellabel, "LEVEL: " + game.getLevel(), 200, 0, 1000, 32);
        updateLabel(scorelabel, "SCORE: " + game.getScore(), 400, 0, 1000, 32);
        updateLabel(timerlabel, "TIME: 0", 600, 0, 1000, 32);
    }

    /**
     * Helper method to update a label's text, position, size, font, and color.
     */
    private void updateLabel(JLabel label, String text, int x, int y, int width, int height) {
        label.setText(text);
        label.setBounds(x, y, width, height);
        label.setFont(new Font("Arial", Font.BOLD, 30));
        label.setForeground(Color.decode("#FFFFFF"));
    }

    /**
     * Updates the UI with new values for level, score, and time.
     */
    public void updateUI(int level, int score, int time) {
        //
        // negative level numbres look ugly to this will fix that
        if(level <= 0){
            int tutLevel = 5+level;
            updateLabel(levellabel, "TUTORIAL: " + tutLevel, 100, 0, 1000, 32);
        }
        else{
            updateLabel(levellabel, "LEVEL: " + level, 200, 0, 1000, 32);
        }
        updateLabel(scorelabel, "SCORE: " + score, 400, 0, 1000, 32);
        updateLabel(timerlabel, "TIME: " + time, 600, 0, 1000, 32);
    }
}
