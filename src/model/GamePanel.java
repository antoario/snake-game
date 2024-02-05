package src.model;

import java.awt.*;
import java.awt.event.*;
import java.util.Random;
import javax.swing.*;
import javax.swing.JPanel;

/**
 * The GamePanel class represents the game panel where the Snake game is played.
 * It extends JPanel and implements the ActionListener interface.
 */
public class GamePanel extends JPanel implements ActionListener {
    private static final long serialVersionUID = 1L;
    private static final int SCREEN_WIDTH = 600;
    private static final int SCREEN_HEIGHT = 600;
    private static final int UNIT_SIZE = 25;
    private static final int GAME_UNITS = (SCREEN_WIDTH * SCREEN_HEIGHT) / UNIT_SIZE;
    private static final int DELAY = 75;
    private final int x[] = new int[GAME_UNITS];
    private final int y[] = new int[GAME_UNITS];
    private int bodyParts = 5;
    private int appleEaten, appleX, appleY;
    private int bufferX = 3, bufferY = 2;
    private char direction = 'R';
    private boolean running = false;
    private Timer timer;
    private Random random;

    /**
     * Constructor for the GamePanel class.
     * Initializes the panel and starts the game.
     */
    public GamePanel() {
        random = new Random();
        this.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
        this.setBackground(Color.black);
        this.setFocusable(true);
        this.addKeyListener(new MyKeyAdapter());
        startGame();
    }

    /**
     * Starts the game by initializing necessary components and starting the timer.
     */
    public void startGame() {
        newApple();
        running = true;
        timer = new Timer(DELAY, this);
        timer.start();
    }

    /**
     * Overrides the paintComponent method to draw the game elements on the panel.
     *
     * @param g The Graphics object used for drawing.
     */
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        draw(g);
    }

    /**
     * Draws the game elements on the panel.
     *
     * @param g The Graphics object used for drawing.
     */
    public void draw(Graphics g) {
        if (running) {
            // Draw game grid
            for (int i = 0; i < SCREEN_HEIGHT / UNIT_SIZE; i++) {
                g.setColor(Color.white);
                g.drawLine(i * UNIT_SIZE, 0, i * UNIT_SIZE, SCREEN_HEIGHT);
                g.drawLine(0, i * UNIT_SIZE, SCREEN_WIDTH, i * UNIT_SIZE);
            }

            // Draw the apple
            g.setColor(Color.RED);
            g.fillOval(appleX, appleY, UNIT_SIZE - 5, UNIT_SIZE - 5);

            // Draw the snake body
            for (int i = 0; i < bodyParts; i++) {
                g.setColor(Color.green);
                g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
            }

            // Draw the score
            g.setColor(Color.RED);
            g.setFont(new Font("Arial", Font.BOLD, 40));
            FontMetrics metrics = getFontMetrics(g.getFont());
            g.drawString("Score: " + appleEaten, (SCREEN_WIDTH - metrics.stringWidth("Score: " + appleEaten)) / 2, g.getFont().getSize());
        } else {
            // Display game over screen
            gameOver(g);
        }
    }

    /**
     * Generates a new random position for the apple on the game board.
     */
    public void newApple() {
        appleX = random.nextInt((int) SCREEN_WIDTH / UNIT_SIZE) * UNIT_SIZE + bufferX;
        appleY = random.nextInt((int) SCREEN_WIDTH / UNIT_SIZE) * UNIT_SIZE + bufferY;
    }

    /**
     * Moves the Snake based on the current direction.
     */
    public void move() {
        for (int i = bodyParts; i > 0; i--) {
            x[i] = x[i - 1];
            y[i] = y[i - 1];
        }
        switch (direction) {
            case 'U':
                y[0] = y[0] - UNIT_SIZE;
                break;
            case 'D':
                y[0] = y[0] + UNIT_SIZE;
                break;
            case 'L':
                x[0] = x[0] - UNIT_SIZE;
                break;
            case 'R':
                x[0] = x[0] + UNIT_SIZE;
                break;
        }
    }

    /**
     * Checks if the Snake has eaten the apple and updates the game accordingly.
     */
    public void checkApple() {
        if (((x[0] + bufferX) == appleX) && ((y[0] + bufferY) == appleY)) {
            bodyParts++;
            appleEaten++;
            newApple();
        }
    }

    /**
     * Checks for collisions with the Snake's own body or game borders.
     */
    public void checkCollisions() {
        // Checks if head collides with body
        for (int i = bodyParts; i > 0; i--) {
            if ((x[0] == x[i]) && (y[0] == y[i])) running = false;
        }

        // Check if head touches left border
        if (x[0] < 0) running = false;
        // Check if head touches right border
        if (x[0] > SCREEN_WIDTH) running = false;
        // Check if head touches top border
        if (y[0] < 0) running = false;
        // Check if head touches bottom border
        if (y[0] > SCREEN_HEIGHT) running = false;

        if (!running) timer.stop();
    }

    /**
     * Displays the game over screen with the final score.
     *
     * @param g The Graphics object used for drawing.
     */
    public void gameOver(Graphics g) {
        // Display the score
        g.setColor(Color.red);
        g.setFont(new Font("Arial", Font.BOLD, 40));
        FontMetrics metrics1 = getFontMetrics(g.getFont());
        g.drawString("Score: " + appleEaten, (SCREEN_WIDTH - metrics1.stringWidth("Score: " + appleEaten)) / 2, g.getFont().getSize());

        // Display the game over text
        g.setColor(Color.red);
        g.setFont(new Font("Arial", Font.BOLD, 75));
        FontMetrics metrics2 = getFontMetrics(g.getFont());
        g.drawString("Game Over!", (SCREEN_WIDTH - metrics2.stringWidth("Game Over!")) / 2, SCREEN_HEIGHT / 2);
    }

    /**
     * Handles the actionPerformed event from the timer.
     *
     * @param e The ActionEvent object.
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if (running) {
            move();
            checkApple();
            checkCollisions();
        }
        repaint();
    }

    /**
     * Inner class representing a KeyAdapter for handling key presses.
     */
    public class MyKeyAdapter extends KeyAdapter {
        /**
         * Overrides the keyPressed method to update the Snake's direction based on key input.
         *
         * @param e The KeyEvent object representing the key press.
         */
        @Override
        public void keyPressed(KeyEvent e) {
            switch (e.getKeyCode()) {
                case KeyEvent.VK_LEFT:
                    if (direction != 'R') direction = 'L';
                    break;
                case KeyEvent.VK_RIGHT:
                    if (direction != 'L') direction = 'R';
                    break;
                case KeyEvent.VK_UP:
                    if (direction != 'D') direction = 'U';
                    break;
                case KeyEvent.VK_DOWN:
                    if (direction != 'U') direction = 'D';
                    break;
            }
        }
    }
}
