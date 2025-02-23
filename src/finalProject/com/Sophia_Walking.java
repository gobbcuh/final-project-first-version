package finalProject.com;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Sophia_Walking extends JPanel implements KeyListener {
    private Image walkRight1, walkRight2, walkLeft1, walkLeft2, currentImage;
    private int x = 0; // Initial X position
    private boolean toggleImage = false; // To alternate images

    public Sophia_Walking() {
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
            System.err.println("Error: One or more images failed to load!");
        }

        currentImage = walkRight1; // Set initial image

        setFocusable(true); // Allow the panel to receive key events
        addKeyListener(this); // Add key listener

        // Set background color
        setBackground(Color.WHITE);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        // Draw the current image at position (x, y) near the bottom of the screen
        int y = getHeight() - 120; // Adjust Y-coordinate to position the image near the bottom
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
        JFrame frame = new JFrame("Sophia Walking");
        Sophia_Walking panel = new Sophia_Walking();

        // Add the panel to the frame
        frame.add(panel);

        // Set frame properties
        frame.setSize(800, 400); // Set window size
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

        // Ensure the panel is focusable to receive key events
        panel.requestFocusInWindow();
    }
}