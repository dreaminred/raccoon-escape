package com.group24;
import java.net.URL;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * The main class for the main frame. 
 * 
 * @author Group 24
 */
public class Everything extends JFrame implements GameListener {

    JFrame frame;
    JPanel mainMenu;
    Game gamePanel;
    Sound sound = new Sound();
    /**
     * Constructor for the main JFrame that holds everything
     * Initializes everything
     */
    public Everything() {
        frame = new JFrame("Raccoon Escape");
            
        frame.setSize(973, 581);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);


        ImageIcon tile = new ImageIcon(getClass().getClassLoader().getResource("Raccoon_640.png"));
        frame.setIconImage(tile.getImage());


        mainMenu = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                ImageIcon raccoonIcon = new ImageIcon(getClass().getClassLoader().getResource("Raccoon_640.png"));
                Image bgIMG = raccoonIcon.getImage();
                // Image bgIMG = new ImageIcon("media/Raccoon_640.png").getImage();
                g.drawImage(bgIMG, 250, 100, 500, 500, this);  
            }
        };
        
        mainMenu.setLayout(null);
        mainMenu.setBounds(0, 0, 800, 600); 

        JButton playButton = new JButton("PLAY");
        JButton tutorial = new JButton("TUTORIAL");
        JLabel gameTitle = new JLabel("RACCOON ESCAPE", JLabel.CENTER);
        gameTitle.setFont(new Font("Arial", Font.BOLD, 50));
        
        gameTitle.setBounds(250, 20, 500, 50);  
        playButton.setBounds(400, 375, 200, 50); 
        tutorial.setBounds(400, 430, 200, 50);

        mainMenu.add(gameTitle);
        mainMenu.add(playButton);
        mainMenu.add(tutorial);

        playBackgroundMusic();
        
        gamePanel = new Game();
        gamePanel.setGameListener(this);

        playButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                StartLevel.level(frame,gamePanel,1);
            }
        });

        tutorial.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                StartLevel.level(frame,gamePanel,-4);
            }
        });

        frame.add(mainMenu);
        frame.setVisible(true);
    }

    /**
     *  When lose game, tell the player and return to main menu once the popup is closed
     */
    @Override
    public void onGameLost() {
        JOptionPane.showMessageDialog(this, "You lost the game!", "Game Over", JOptionPane.INFORMATION_MESSAGE);
        mainscreen();
    }
    /**
     * when game is won, tell the player, report their final score and then return to menu once the popup is closed
     */
    public void onGameWon(int finalScore) {
        JOptionPane.showMessageDialog(this, "Congratulations! \nYour final score was: " +finalScore+"!", "You Won", JOptionPane.INFORMATION_MESSAGE);
        mainscreen();
    }

    /**
     * Returns to main menu
     */
    @Override
    public void menuPressed() {
        mainscreen();
    }

    /**
     * Function to change the screen to the title screen
     */
    private void mainscreen() {
        frame.getContentPane().removeAll();
        frame.add(mainMenu);
        frame.revalidate();
        frame.repaint();
    }
    // private void
    /**
     * The main function that runs it all
     * @param args None
     */
    public static void main(String[] args) {
        Everything g = new Everything();
    }

    public void playBackgroundMusic() {
        this.sound.setSoundClip(0);
        this.sound.play();
        this.sound.loop();
    }


}
