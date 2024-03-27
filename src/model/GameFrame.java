package model;

import javax.swing.JFrame;

/**
 * The GameFrame class represents the main frame of the Snake game.
 * It extends JFrame and is responsible for initializing and displaying the game panel.
 */
public class GameFrame extends JFrame {
    /**
     * Constructor for the GameFrame class.
     * Initializes the frame, sets up the game panel, and configures the frame settings.
     */
    public GameFrame() {
        this.add(new GamePanel());
        this.setTitle("Snake Game");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.pack();
        this.setVisible(true);
        this.setLocationRelativeTo(null);
    }
}
