package finalProject.com; // game intro to main menu draft

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

public class GameIntroScreen extends JPanel {
    private Image[] backgrounds; // loading background images
    private Timer starTimer;
    private Timer backgroundTimer; // cycling through background images
    private Timer loadingTextTimer; // Timer for loading text animation
    private Timer dotAnimationTimer; // Timer for dot animation
    private int starAlpha = 255;
    private boolean fadeOut = true;
    private Clip backgroundMusic;
    private JFrame parentFrame;
    private int currentBackgroundIndex = 0; // Index to track the current background image
    private String[] loadingTexts = {"Welcome to the Game", "Please wait for a moment", "Starting the Game"};
    private int currentLoadingTextIndex = 0;
    private boolean isLastTextDisplayed = false;
    private int dotCount = 0; // Tracks the number of dots displayed
    private final int maxDots = 3; // Maximum number of dots to display

    public GameIntroScreen(JFrame frame) {
        this.parentFrame = frame;

        // loading the background images
        backgrounds = new Image[4];
        for (int i = 0; i < 4; i++) {
            backgrounds[i] = new ImageIcon("C:/Users/User/IdeaProjects/java Programs/out/production/java Programs/finalProject/com/loading-bg" + (i + 1) + ".png").getImage();
        }

        playBackgroundMusic();

        // Blinking stars
        starTimer = new Timer(150, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (fadeOut) {
                    starAlpha -= 25;
                    if (starAlpha <= 50) fadeOut = false;
                } else {
                    starAlpha += 25;
                    if (starAlpha >= 255) fadeOut = true;
                }
                repaint();
            }
        });
        starTimer.start();

        // Timer for background animation (cycling through images)
        backgroundTimer = new Timer(200, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                currentBackgroundIndex = (currentBackgroundIndex + 1) % 4; // Cycle through 0-3
                repaint();
            }
        });
        backgroundTimer.start();

        // Timer for loading text animation
        loadingTextTimer = new Timer(3000, new ActionListener() { // 3 seconds per text
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!isLastTextDisplayed) {
                    currentLoadingTextIndex = (currentLoadingTextIndex + 1) % loadingTexts.length;
                    if (currentLoadingTextIndex == loadingTexts.length - 1) {
                        isLastTextDisplayed = true;
                        loadingTextTimer.stop(); // Stop the loading text timer
                        dotAnimationTimer.stop(); // Stop the dot animation timer
                        // Start a new timer to pause for 5 seconds on the last text
                        Timer pauseTimer = new Timer(5000, new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                showButtons(); // Show buttons after the pause
                            }
                        });
                        pauseTimer.setRepeats(false); // Ensure the timer only runs once
                        pauseTimer.start();
                    }
                    dotCount = 0; // Reset dot count for the new text
                    dotAnimationTimer.restart(); // Restart the dot animation timer
                    repaint();
                }
            }
        });
        loadingTextTimer.start();

        // Timer for dot animation
        dotAnimationTimer = new Timer(800, new ActionListener() { // 0.8 seconds per dot
            @Override
            public void actionPerformed(ActionEvent e) {
                if (dotCount < maxDots) {
                    dotCount++; // Increment dot count
                    repaint();
                } else {
                    dotAnimationTimer.stop(); // Stop the timer when all dots are displayed
                }
            }
        });
    }

    private void showButtons() {
        // Load the button images
        ImageIcon startButtonIcon = new ImageIcon("C:/Users/User/IdeaProjects/java Programs/out/production/java Programs/finalProject/com/start-button.png");
        ImageIcon exitButtonIcon = new ImageIcon("C:/Users/User/IdeaProjects/java Programs/out/production/java Programs/finalProject/com/quit-button.png");

        // Create the buttons
        JButton startButton = new JButton(startButtonIcon);
        JButton exitButton = new JButton(exitButtonIcon);

        // Remove borders and backgrounds from the buttons
        startButton.setBorderPainted(false);
        startButton.setContentAreaFilled(false);
        exitButton.setBorderPainted(false);
        exitButton.setContentAreaFilled(false);

        // Set button positions
        int buttonWidth = startButtonIcon.getIconWidth();
        int buttonHeight = startButtonIcon.getIconHeight();
        int x = (getWidth() - buttonWidth) / 2; // Center horizontally
        int startY = (getHeight() - (2 * buttonHeight + 10)) / 2; // Center vertically with spacing
        int exitY = startY + buttonHeight + 5; // 10 pixels spacing between buttons

        startButton.setBounds(x, startY, buttonWidth, buttonHeight);
        exitButton.setBounds(x, exitY, buttonWidth, buttonHeight);

        // Add action listeners to the buttons
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Handle start button click
                transitionToNextScreen();
            }
        });

        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Handle exit button click
                System.exit(0);
            }
        });

        // Add buttons to the panel
        setLayout(null); // Use absolute positioning
        add(startButton);
        add(exitButton);

        // Repaint the panel to show the buttons
        repaint();
    }

    private void transitionToNextScreen() {
        parentFrame.getContentPane().removeAll();
        parentFrame.getContentPane().add(new GameMainScreen(parentFrame, backgrounds));
        parentFrame.revalidate();
        parentFrame.repaint();
    }

    private void playBackgroundMusic() {
        try {
            File musicFile = new File("C:/Users/User/IdeaProjects/java Programs/out/production/java Programs/finalProject/com/theme-music.wav");
            AudioInputStream audioIn = AudioSystem.getAudioInputStream(musicFile);
            backgroundMusic = AudioSystem.getClip();
            backgroundMusic.open(audioIn);
            backgroundMusic.loop(Clip.LOOP_CONTINUOUSLY);
            backgroundMusic.start();
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        // first background image (UI)
        g2d.drawImage(backgrounds[currentBackgroundIndex], 0, 0, getWidth(), getHeight(), this);

        // drawing blinking stars
        g2d.setColor(new Color(255, 255, 255, starAlpha));
        for (int i = 0; i < 20; i++) {
            int x = (int) (Math.random() * getWidth());
            int y = (int) (Math.random() * getHeight());
            g2d.fillOval(x, y, 3, 3);
        }

        // Draw loading text with black border
        String loadingText = loadingTexts[currentLoadingTextIndex];
        String dots = ""; // Build the dots string
        for (int i = 0; i < dotCount; i++) {
            dots += " .";
        }
        String fullText = loadingText + dots; // Combine text and dots
        g2d.setFont(new Font("Lucida Console", Font.BOLD, 16)); // Smaller font size
        int textWidth = g2d.getFontMetrics().stringWidth(fullText);
        int x = (getWidth() - textWidth) / 2;
        int y = getHeight() - 40; // Adjusted position for better visibility

        // Draw black border by drawing the text multiple times with slight offsets
        g2d.setColor(Color.BLACK);
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                if (i != 0 || j != 0) { // Skip the center position
                    g2d.drawString(fullText, x + i, y + j);
                }
            }
        }

        // Draw the white text on top
        g2d.setColor(Color.WHITE);
        g2d.drawString(fullText, x, y);
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("The Amazing Adventures");
        GameIntroScreen panel = new GameIntroScreen(frame);
        frame.add(panel);
        frame.setSize(384, 265);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}

class GameMainScreen extends JPanel {
    private Image[] backgrounds;
    private Timer backgroundTimer; // Timer for cycling through background images
    private int currentBackgroundIndex = 0; // Index to track the current background image
    private JFrame parentFrame;

    public GameMainScreen(JFrame parentFrame, Image[] backgrounds) {
        this.parentFrame = parentFrame;
        this.backgrounds = backgrounds;

        // Timer for background animation (cycling through images)
        backgroundTimer = new Timer(200, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                currentBackgroundIndex = (currentBackgroundIndex + 1) % 4; // Cycle through 0-3
                repaint();
            }
        });
        backgroundTimer.start();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(backgrounds[currentBackgroundIndex], 0, 0, getWidth(), getHeight(), this);
    }
}