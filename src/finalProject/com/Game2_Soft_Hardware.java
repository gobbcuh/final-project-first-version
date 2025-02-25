package finalProject.com;

import javax.sound.sampled.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.IOException;

public class Game2_Soft_Hardware extends JPanel implements KeyListener {
    private Image backgroundImage; // Background image
    private Image walkRight1, walkRight2, walkLeft1, walkLeft2, currentImage; // Character images
    private Image gameTitleImage; // Game title image
    private int x = 0; // Initial X position of the character
    private boolean toggleImage = false; // To alternate images
    private float alpha = 0f; // Transparency for teleportation effect
    private boolean isTeleporting = true; // Teleportation state
    private boolean showGameTitle = false; // Flag to show game title
    private int gameTitleY = -100; // Initial Y position of the game title (off-screen)
    private Clip walkSoundClip; // Clip for walking sound effect

    // Sparkle animation variables
    private int sparkleAlpha = 255; // Alpha value for sparkles
    private boolean sparkleFadeOut = true; // Sparkle fade direction
    private Timer sparkleAnimationTimer;

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

        // Load game title image
        gameTitleImage = new ImageIcon("C:/Users/User/IdeaProjects/java Programs/out/production/java Programs/finalProject/com/gameTwo/game-title.png").getImage();

        // Scale character images to a larger size (e.g., 100x100)
        int width = 100; // Desired width
        int height = 100; // Desired height
        walkRight1 = walkRight1.getScaledInstance(width, height, Image.SCALE_SMOOTH);
        walkRight2 = walkRight2.getScaledInstance(width, height, Image.SCALE_SMOOTH);
        walkLeft1 = walkLeft1.getScaledInstance(width, height, Image.SCALE_SMOOTH);
        walkLeft2 = walkLeft2.getScaledInstance(width, height, Image.SCALE_SMOOTH);

        // Scale game title image to a smaller size (e.g., 600x400)
        int titleWidth = 600; // Desired width
        int titleHeight = 400; // Desired height
        gameTitleImage = gameTitleImage.getScaledInstance(titleWidth, titleHeight, Image.SCALE_SMOOTH);

        // Check if images are loaded
        if (walkRight1 == null || walkRight2 == null || walkLeft1 == null || walkLeft2 == null || gameTitleImage == null) {
            System.err.println("Error: One or more images failed to load!");
        }

        currentImage = walkRight1; // Set initial image

        setFocusable(true); // Allow the panel to receive key events
        addKeyListener(this); // Add key listener

        // Load walking sound effect
        try {
            File walkSoundFile = new File("C:/Users/User/IdeaProjects/java Programs/out/production/java Programs/finalProject/com/gameTwo/sophia-walk-sound-effect.wav");
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(walkSoundFile);
            walkSoundClip = AudioSystem.getClip();
            walkSoundClip.open(audioInputStream);
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
            System.err.println("Error loading walking sound effect: " + e.getMessage());
        }

        // Initialize the sparkles animation timer
        sparkleAnimationTimer = new Timer(50, e -> {
            // Sparkle fade animation
            if (sparkleFadeOut) {
                sparkleAlpha -= 10;
                if (sparkleAlpha <= 200) sparkleFadeOut = false; // Adjusted minimum alpha to 200
            } else {
                sparkleAlpha += 10;
                if (sparkleAlpha >= 255) sparkleFadeOut = true;
            }
            repaint();
        });
        sparkleAnimationTimer.start();

        // Start teleportation animation
        startTeleportation();
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

        // Apply transparency for teleportation effect
        Graphics2D g2d = (Graphics2D) g;
        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha));
        g2d.drawImage(currentImage, x, y, null);

        // Draw the game title image if teleportation is complete
        if (showGameTitle) {
            int titleX = (getWidth() - gameTitleImage.getWidth(null)) / 2; // Center horizontally
            g2d.drawImage(gameTitleImage, titleX, gameTitleY, null);

            // Draw sparkles around the game title
            drawSparkles(g2d, titleX, gameTitleY, gameTitleImage.getWidth(null), gameTitleImage.getHeight(null));
        }
    }

    private void drawSparkles(Graphics2D g2d, int x, int y, int width, int height) {
        for (int i = 0; i < 20; i++) { // Increase the number of sparkles
            // Randomly position sparkles closer to the title
            int sparkleX, sparkleY;
            do {
                sparkleX = x + (int) (Math.random() * (width + 40)) - 20; // Reduced scatter area (closer to the title)
                sparkleY = y + (int) (Math.random() * (height + 40)) - 20; // Reduced scatter area (closer to the title)
            } while (sparkleX >= x && sparkleX <= x + width && sparkleY >= y && sparkleY <= y + height);

            // Randomize sparkle size
            int sparkleSize = 5 + (int) (Math.random() * 5); // Vary size between 5 and 10

            // Use a solid white color with the current alpha value
            g2d.setColor(new Color(255, 255, 255, sparkleAlpha));

            // Draw the sparkle as a star shape
            int[] xPoints = {
                    sparkleX, sparkleX + sparkleSize / 4, sparkleX + sparkleSize / 2, sparkleX + sparkleSize / 4, sparkleX,
                    sparkleX - sparkleSize / 4, sparkleX - sparkleSize / 2, sparkleX - sparkleSize / 4
            };
            int[] yPoints = {
                    sparkleY - sparkleSize / 2, sparkleY - sparkleSize / 4, sparkleY, sparkleY + sparkleSize / 4, sparkleY + sparkleSize / 2,
                    sparkleY + sparkleSize / 4, sparkleY, sparkleY - sparkleSize / 4
            };
            g2d.fillPolygon(xPoints, yPoints, 8);
        }
    }

    private void startTeleportation() {
        // Play teleport sound effect immediately
        playSound("C:/Users/User/IdeaProjects/java Programs/out/production/java Programs/finalProject/com/gameTwo/teleport-sophia.wav");

        // Use a Timer to create the teleportation effect
        Timer teleportTimer = new Timer(80, e -> {
            alpha += 0.05f; // Gradually increase transparency
            if (alpha >= 1f) { // Stop when fully visible
                alpha = 1f;
                isTeleporting = false;
                ((Timer) e.getSource()).stop();
                showGameTitle = true; // Set flag to show game title
                startGameTitleAnimation(); // Start game title animation
            }
            repaint();
        });
        teleportTimer.setInitialDelay(0); // Start the timer immediately
        teleportTimer.start();
    }

    private void startGameTitleAnimation() {
        // Use a Timer to create the sliding animation for the game title
        Timer titleTimer = new Timer(10, e -> {
            if (gameTitleY < 50) { // Adjust the final Y position as needed (moved upwards)
                gameTitleY += 2; // Slide down
                repaint();
            } else {
                ((Timer) e.getSource()).stop();
            }
        });
        titleTimer.setInitialDelay(0); // Start the timer immediately
        titleTimer.start();
    }

    private void playSound(String soundFilePath) {
        try {
            // Load the sound file
            File soundFile = new File(soundFilePath);
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(soundFile);
            Clip clip = AudioSystem.getClip();
            clip.open(audioInputStream);

            // Play the sound
            clip.start();
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
            System.err.println("Error playing sound: " + e.getMessage());
        }
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

                // Play walking sound effect
                if (walkSoundClip != null) {
                    if (!walkSoundClip.isRunning()) { // Avoid overlapping sounds
                        walkSoundClip.setFramePosition(0); // Rewind to the beginning
                        walkSoundClip.start();
                    }
                }
            }
        } else if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            // Move left
            if (x > 0) { // Ensure character does not move past the screen's left edge
                x -= 10; // Move left
                toggleImage = !toggleImage; // Switch image
                currentImage = toggleImage ? walkLeft2 : walkLeft1;
                repaint(); // Update screen

                // Play walking sound effect
                if (walkSoundClip != null) {
                    if (!walkSoundClip.isRunning()) { // Avoid overlapping sounds
                        walkSoundClip.setFramePosition(0); // Rewind to the beginning
                        walkSoundClip.start();
                    }
                }
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        // Stop the walking sound effect when the key is released
        if (e.getKeyCode() == KeyEvent.VK_RIGHT || e.getKeyCode() == KeyEvent.VK_LEFT) {
            if (walkSoundClip != null && walkSoundClip.isRunning()) {
                walkSoundClip.stop(); // Stop the sound effect
            }
        }
    }

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