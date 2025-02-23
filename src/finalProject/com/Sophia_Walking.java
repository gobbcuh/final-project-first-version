package finalProject.com;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Sophia_Walking extends JPanel implements KeyListener {
    private Image walkRight1, walkRight2, currentImage;
    private int x = 0; // Initial X position
    private boolean toggleImage = false; // To alternate images

    public Sophia_Walking() {
        // Load images
        walkRight1 = new ImageIcon("C:/Users/User/IdeaProjects/java Programs/out/production/java Programs/finalProject/com/sophiaWalk/right_walk1.png").getImage();
        walkRight2 = new ImageIcon("C:/Users/User/IdeaProjects/java Programs/out/production/java Programs/finalProject/com/sophiaWalk/right_walk2.png").getImage();

        // Check if images are loaded
        if (walkRight1 == null || walkRight2 == null) {
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
        // Draw the current image at position (x, 200)
        g.drawImage(currentImage, x, 200, null);
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            if (x < getWidth() - 50) { // Ensure character does not move past the screen width
                x += 10; // Move right
                toggleImage = !toggleImage; // Switch image
                currentImage = toggleImage ? walkRight2 : walkRight1;
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