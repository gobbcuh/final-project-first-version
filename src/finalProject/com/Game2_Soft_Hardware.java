package finalProject.com;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Game2_Soft_Hardware extends JPanel implements KeyListener {
    private Image backgroundImage; // Background image
    private Image walkRight1, walkRight2, walkLeft1, walkLeft2, currentImage; // Character images
    private int x = 0; // Initial X position of the character
    private boolean toggleImage = false; // To alternate images

    public Game2_Soft_Hardware() {
        // Load background image
        try {
            backgroundImage = new ImageIcon("C:/Users/User/IdeaProjects/java Programs/out/production/java Programs/finalProject/com/gameTwo/game2-bg.jpg").getImage();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error loading background image.");
        }

        // Load right-walking images
        walkRight1 = new ImageIcon("C:/Users/User/IdeaProjects/java Programs/out/production/java Programs/finalProject/com/sophiaWalk/right_walk1.png").getImage();
        walkRight2 = new ImageIcon("C:/Users/User/IdeaProjects/java Programs/out/production/java Programs/finalProject/com/sophiaWalk/right_walk2.png").getImage();

        // Load left-walking images
        walkLeft1 = new ImageIcon("C:/Users/User/IdeaProjects/java Programs/out/production/java Programs/finalProject/com/sophiaWalk/left_walk1.png").getImage();
        walkLeft2 = new ImageIcon("C:/Users/User/IdeaProjects/java Programs/out/production/java Programs/finalProject/com/sophiaWalk/left_walk2.png").getImage();

        // Scale images to a larger size (e.g., 100x100)
        int width = 100; // Desired width
        int height = 100; // Desired height
        walkRight1 = walkRight1.getScaledInstance(width, height, Image.SCALE_SMOOTH);
        walkRight2 = walkRight2.getScaledInstance(width, height, Image.SCALE_SMOOTH);
        walkLeft1 = walkLeft1.getScaledInstance(width, height, Image.SCALE_SMOOTH);
        walkLeft2 = walkLeft2.getScaledInstance(width, height, Image.SCALE_SMOOTH);

        // Check if images are loaded
        if (walkRight1 == null || walkRight2 == null || walkLeft1 == null || walkLeft2 == null) {
            System.err.println("Error: One or more character images failed to load!");
        }

        currentImage = walkRight1; // Set initial image

        setFocusable(true); // Allow the panel to receive key events
        addKeyListener(this); // Add key listener
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Draw the background image
        if (backgroundImage != null) {
            g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
        }

        // Draw the current character image at position (x, y) near the bottom of the screen
        int y = getHeight() - 150; // Adjust Y-coordinate to position the character near the bottom
        g.drawImage(currentImage, x, y, null);
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            // Move right
            if (x < getWidth() - 100) { // Ensure character does not move past the screen width
                x += 10; // Move right
                toggleImage = !toggleImage; // Switch image
                currentImage = toggleImage ? walkRight2 : walkRight1;
                repaint(); // Update screen
            }
        } else if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            // Move left
            if (x > 0) { // Ensure character does not move past the screen's left edge
                x -= 10; // Move left
                toggleImage = !toggleImage; // Switch image
                currentImage = toggleImage ? walkLeft2 : walkLeft1;
                repaint(); // Update screen
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {}

    @Override
    public void keyTyped(KeyEvent e) {}

    public static void main(String[] args) {
        // Create the JFrame
        JFrame frame = new JFrame("Game with Walking Character");
        Game2_Soft_Hardware panel = new Game2_Soft_Hardware();

        // Add the panel to the frame
        frame.add(panel);

        // Set frame properties
        frame.setSize(800, 600); // Set window size
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

        // Ensure the panel is focusable to receive key events
        panel.requestFocusInWindow();
    }
}