package main;

import model.GameFrame;

/**
 * The SnakeGame class serves as the entry point for the Snake game application.
 * It contains the main method that creates an instance of the GameFrame class to start the game.
 */
public class SnakeGame {
    /**
     * The main method serves as the entry point for the Snake game application.
     * It creates an instance of the GameFrame class to initialize and start the game.
     *
     * @param args The command-line arguments (not used in this application).
     */
    public static void main(String[] args) {
        new GameFrame();
    }
}
