package finalProject.com;

import javax.swing.*;
import java.awt.*;

public class Game2_Soft_Hardware extends JPanel {

    private Image backgroundImage;

    public Game2_Soft_Hardware() {
        try {
            backgroundImage = new ImageIcon("C:/Users/User/IdeaProjects/java Programs/out/production/java Programs/finalProject/com/gameTwo/game2-bg.jpg").getImage();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error loading background image.");
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        // Draw the background image
        if (backgroundImage != null) {
            g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
        }
    }

    public static void main(String[] args) {
        // Create the JFrame
        JFrame frame = new JFrame("Game 2 - Soft & Hardware");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600); // Set the size of the window

        // Create an instance of your Game2_Soft_Hardware panel
        Game2_Soft_Hardware gamePanel = new Game2_Soft_Hardware();
        frame.add(gamePanel);

        // Center the frame on the screen
        frame.setLocationRelativeTo(null);

        // Make the frame visible
        frame.setVisible(true);
    }
}