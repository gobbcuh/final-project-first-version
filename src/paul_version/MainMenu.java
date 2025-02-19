package paul_version;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class MainMenu extends JFrame {

    private BufferedImage[] menuImages;
    private int currentImageIndex = 0;
    private ImagePanel imagePanel;
    private JButton startButton;
    private JButton exitButton;

    public MainMenu() {
        this.setTitle("The Amazing Adventures of Sophia");
        this.setSize(800, 600);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null); // Center the window
        this.setResizable(false); // Make the window non-resizable

        // Load the images
        loadImages();

        // Set up the custom panel to display images
        imagePanel = new ImagePanel();

        // Create a layered pane
        JLayeredPane layeredPane = new JLayeredPane();
        layeredPane.setPreferredSize(new Dimension(800, 600));

        // Add the image panel to the layered pane
        imagePanel.setBounds(0, 0, 800, 600);
        layeredPane.add(imagePanel, Integer.valueOf(0)); // Background layer

        // Create the start button
        startButton = new JButton();
        startButton.setContentAreaFilled(false); // Makes the button transparent
        startButton.setBorderPainted(false); // Remove border
        startButton.setFocusPainted(false); // Remove focus outline

        // Set start button size and position (centered at the bottom)
        startButton.setBounds(320, 350, 100, 50); // Adjusted size and position
        setButtonIcon(startButton, "C:\\Users\\lored\\Downloads\\1Start.png");

        startButton.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                setButtonIcon(startButton, "C:\\Users\\lored\\Downloads\\2Start.png"); // Change icon on hover
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                setButtonIcon(startButton, "C:\\Users\\lored\\Downloads\\1Start.png"); // Revert icon when not hovering
            }
        });



        startButton.addActionListener(e -> {
            // Start button action (e.g., start the game)
            this.dispose();
            GameFrame gameFrame = new GameFrame();
        });

        // Add the start button to the layered pane
        layeredPane.add(startButton, Integer.valueOf(1)); // Button layer

        // Create the exit button
        exitButton = new JButton();
        exitButton.setContentAreaFilled(false); // Makes the button transparent
        exitButton.setBorderPainted(false); // Remove border
        exitButton.setFocusPainted(false); // Remove focus outline

        // Set exit button size and position (centered at the bottom)
        exitButton.setBounds(320, 420, 100, 50); // Adjusted size and position
        setButtonIcon(exitButton, "C:\\Users\\lored\\Downloads\\1Q.png"); // Set exit button icon

        exitButton.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                setButtonIcon(exitButton, "C:\\Users\\lored\\Downloads\\2Q.png"); // Change icon on hover
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                setButtonIcon(exitButton, "C:\\Users\\lored\\Downloads\\1Q.png"); // Revert icon when not hovering
            }
        });

        exitButton.addActionListener(e -> {
            System.exit(0);
        });

        // Add the exit button to the layered pane
        layeredPane.add(exitButton, Integer.valueOf(1)); // Button layer

        // Add the layered pane to the frame
        this.add(layeredPane);

        // Set up a timer for animation
        Timer timer = new Timer(200, e -> updateImage());
        timer.start();

        this.setVisible(true);
    }

    private void loadImages() {
        menuImages = new BufferedImage[4];
        try {
            menuImages[0] = ImageIO.read(new File("C:\\Users\\lored\\Downloads\\mainMenu1.png"));
            menuImages[1] = ImageIO.read(new File("C:\\Users\\lored\\Downloads\\mainMenu2.png"));
            menuImages[2] = ImageIO.read(new File("C:\\Users\\lored\\Downloads\\mainMenu3.png"));
            menuImages[3] = ImageIO.read(new File("C:\\Users\\lored\\Downloads\\mainMenu4.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void setButtonIcon(JButton button, String imagePath) {
        // Load the image
        ImageIcon originalIcon = new ImageIcon(imagePath);
        // Scale the image to fit the button size
        Image scaledImage = originalIcon.getImage().getScaledInstance(button.getWidth(), button.getHeight(), Image.SCALE_SMOOTH);
        button.setIcon(new ImageIcon(scaledImage));
    }

    private void updateImage() {
        currentImageIndex = ( currentImageIndex + 1) % menuImages.length;
        imagePanel.repaint(); // Repaint the panel to update the image
    }

    // Custom JPanel to draw the images
    private class ImagePanel extends JPanel {
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            if (menuImages[currentImageIndex] != null) {
                // Scale the image to fit the panel
                int width = getWidth();
                int height = getHeight();
                Image scaledImage = menuImages[currentImageIndex].getScaledInstance(width, height, Image.SCALE_SMOOTH);
                g.drawImage(scaledImage, 0, 0, this);
            }
        }
    }
}
