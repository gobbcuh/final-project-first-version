package finalProject.com; // draft 741

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

public class GameIntroScreen extends JPanel {
    private Image[] backgrounds;
    private Timer starTimer;
    private Timer backgroundTimer;
    private Timer loadingTextTimer;
    private Timer dotAnimationTimer;
    private int starAlpha = 255;
    private boolean fadeOut = true;
    private Clip backgroundMusic;
    private JFrame parentFrame;
    private int currentBackgroundIndex = 0;
    private String[] loadingTexts = {"Welcome to the Game", "Please wait for a moment", "Starting the Game"};
    private int currentLoadingTextIndex = 0;
    private boolean isLastTextDisplayed = false;
    private int dotCount = 0;
    private final int maxDots = 3;
    private boolean buttonsVisible = false;
    private float buttonScale = 0.1f;
    private float fadeAlpha = 1.0f;

    public GameIntroScreen(JFrame frame) {
        this.parentFrame = frame;

        backgrounds = new Image[4];
        for (int i = 0; i < 4; i++) {
            backgrounds[i] = new ImageIcon("C:/Users/User/IdeaProjects/java Programs/out/production/java Programs/finalProject/com/loading-bg" + (i + 1) + ".png").getImage();
        }

        playBackgroundMusic();

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

        backgroundTimer = new Timer(200, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                currentBackgroundIndex = (currentBackgroundIndex + 1) % 4;
                repaint();
            }
        });
        backgroundTimer.start();

        loadingTextTimer = new Timer(3000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!isLastTextDisplayed) {
                    currentLoadingTextIndex = (currentLoadingTextIndex + 1) % loadingTexts.length;
                    if (currentLoadingTextIndex == loadingTexts.length - 1) {
                        isLastTextDisplayed = true;
                        loadingTextTimer.stop();
                        dotAnimationTimer.stop();
                        Timer pauseTimer = new Timer(5000, new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                showButtons();
                            }
                        });
                        pauseTimer.setRepeats(false);
                        pauseTimer.start();
                    }
                    dotCount = 0;
                    dotAnimationTimer.restart();
                    repaint();
                }
            }
        });
        loadingTextTimer.start();

        dotAnimationTimer = new Timer(800, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (dotCount < maxDots) {
                    dotCount++;
                    repaint();
                } else {
                    dotAnimationTimer.stop();
                }
            }
        });
    }

    private void showButtons() {
        buttonsVisible = true;
        playSoundEffect("C:/Users/User/IdeaProjects/java Programs/out/production/java Programs/finalProject/com/sound-effect1.wav");

        ImageIcon startButtonIcon = new ImageIcon("C:/Users/User/IdeaProjects/java Programs/out/production/java Programs/finalProject/com/start-button.png");
        ImageIcon exitButtonIcon = new ImageIcon("C:/Users/User/IdeaProjects/java Programs/out/production/java Programs/finalProject/com/quit-button.png");
        ImageIcon startButtonHoverIcon = new ImageIcon("C:/Users/User/IdeaProjects/java Programs/out/production/java Programs/finalProject/com/start-button2.png");
        ImageIcon exitButtonHoverIcon = new ImageIcon("C:/Users/User/IdeaProjects/java Programs/out/production/java Programs/finalProject/com/quit-button2.png");

        Image startButtonImage = startButtonIcon.getImage().getScaledInstance(
                startButtonIcon.getIconWidth() + 100,
                startButtonIcon.getIconHeight() + 40,
                Image.SCALE_SMOOTH
        );
        Image exitButtonImage = exitButtonIcon.getImage().getScaledInstance(
                exitButtonIcon.getIconWidth() + 100,
                exitButtonIcon.getIconHeight() + 40,
                Image.SCALE_SMOOTH
        );
        Image startButtonHoverImage = startButtonHoverIcon.getImage().getScaledInstance(
                startButtonHoverIcon.getIconWidth() + 100,
                startButtonHoverIcon.getIconHeight() + 40,
                Image.SCALE_SMOOTH
        );
        Image exitButtonHoverImage = exitButtonHoverIcon.getImage().getScaledInstance(
                exitButtonHoverIcon.getIconWidth() + 100,
                exitButtonHoverIcon.getIconHeight() + 40,
                Image.SCALE_SMOOTH
        );

        JButton startButton = new JButton(new ImageIcon(startButtonImage));
        JButton exitButton = new JButton(new ImageIcon(exitButtonImage));
        JButton startButtonHover = new JButton(new ImageIcon(startButtonHoverImage));
        JButton exitButtonHover = new JButton(new ImageIcon(exitButtonHoverImage));

        startButton.setBorderPainted(false);
        startButton.setContentAreaFilled(false);
        exitButton.setBorderPainted(false);
        exitButton.setContentAreaFilled(false);
        startButtonHover.setBorderPainted(false);
        startButtonHover.setContentAreaFilled(false);
        exitButtonHover.setBorderPainted(false);
        exitButtonHover.setContentAreaFilled(false);

        int buttonWidth = startButton.getIcon().getIconWidth();
        int buttonHeight = startButton.getIcon().getIconHeight();
        int x = (getWidth() - buttonWidth) / 2;
        int startY = (getHeight() - (2 * buttonHeight + 10)) / 2 + 70;
        int exitY = startY + buttonHeight + 10;

        buttonScale = 0.1f;
        startButton.setBounds(
                (int) (x + (buttonWidth * (1 - buttonScale)) / 2),
                (int) (startY + (buttonHeight * (1 - buttonScale)) / 2),
                (int) (buttonWidth * buttonScale),
                (int) (buttonHeight * buttonScale)
        );
        exitButton.setBounds(
                (int) (x + (buttonWidth * (1 - buttonScale)) / 2),
                (int) (exitY + (buttonHeight * (1 - buttonScale)) / 2),
                (int) (buttonWidth * buttonScale),
                (int) (buttonHeight * buttonScale)
        );
        startButtonHover.setBounds(
                (int) (x + (buttonWidth * (1 - buttonScale)) / 2),
                (int) (startY + (buttonHeight * (1 - buttonScale)) / 2),
                (int) (buttonWidth * buttonScale),
                (int) (buttonHeight * buttonScale)
        );
        exitButtonHover.setBounds(
                (int) (x + (buttonWidth * (1 - buttonScale)) / 2),
                (int) (exitY + (buttonHeight * (1 - buttonScale)) / 2),
                (int) (buttonWidth * buttonScale),
                (int) (buttonHeight * buttonScale)
        );

        setLayout(null);
        add(startButton);
        add(exitButton);
        add(startButtonHover);
        add(exitButtonHover);

        startButtonHover.setVisible(false);
        exitButtonHover.setVisible(false);

        Timer zoomTimer = new Timer(16, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (buttonScale < 1.0f) {
                    buttonScale += 0.05f;
                    startButton.setBounds(
                            (int) (x + (buttonWidth * (1 - buttonScale)) / 2),
                            (int) (startY + (buttonHeight * (1 - buttonScale)) / 2),
                            (int) (buttonWidth * buttonScale),
                            (int) (buttonHeight * buttonScale)
                    );
                    exitButton.setBounds(
                            (int) (x + (buttonWidth * (1 - buttonScale)) / 2),
                            (int) (exitY + (buttonHeight * (1 - buttonScale)) / 2),
                            (int) (buttonWidth * buttonScale),
                            (int) (buttonHeight * buttonScale)
                    );
                    startButtonHover.setBounds(
                            (int) (x + (buttonWidth * (1 - buttonScale)) / 2),
                            (int) (startY + (buttonHeight * (1 - buttonScale)) / 2),
                            (int) (buttonWidth * buttonScale),
                            (int) (buttonHeight * buttonScale)
                    );
                    exitButtonHover.setBounds(
                            (int) (x + (buttonWidth * (1 - buttonScale)) / 2),
                            (int) (exitY + (buttonHeight * (1 - buttonScale)) / 2),
                            (int) (buttonWidth * buttonScale),
                            (int) (buttonHeight * buttonScale)
                    );
                    repaint();
                } else {
                    ((Timer) e.getSource()).stop();
                }
            }
        });
        zoomTimer.start();

        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                playSoundEffect("C:/Users/User/IdeaProjects/java Programs/out/production/java Programs/finalProject/com/click-intro-button.wav");
                startButton.setVisible(false);
                startButtonHover.setVisible(true);
                Timer hoverTimer = new Timer(200, new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        transitionToNextScreen();
                    }
                });
                hoverTimer.setRepeats(false);
                hoverTimer.start();
            }
        });

        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                playSoundEffect("C:/Users/User/IdeaProjects/java Programs/out/production/java Programs/finalProject/com/click-intro-button.wav");
                exitButton.setVisible(false);
                exitButtonHover.setVisible(true);
                Timer hoverTimer = new Timer(200, new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        System.exit(0);
                    }
                });
                hoverTimer.setRepeats(false);
                hoverTimer.start();
            }
        });

        repaint();
    }

    private void transitionToNextScreen() {
        GameMainScreen gameMainScreen = new GameMainScreen(parentFrame);
        parentFrame.getContentPane().removeAll();
        parentFrame.getContentPane().add(gameMainScreen);
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

    private void playSoundEffect(String filePath) {
        try {
            File soundFile = new File(filePath);
            AudioInputStream audioIn = AudioSystem.getAudioInputStream(soundFile);
            Clip clip = AudioSystem.getClip();
            clip.open(audioIn);
            clip.start();
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, fadeAlpha));
        g2d.drawImage(backgrounds[currentBackgroundIndex], 0, 0, getWidth(), getHeight(), this);
        g2d.setColor(new Color(255, 255, 255, starAlpha));
        for (int i = 0; i < 20; i++) {
            int x = (int) (Math.random() * getWidth());
            int y = (int) (Math.random() * getHeight());
            g2d.fillOval(x, y, 3, 3);
        }
        if (!buttonsVisible) {
            String loadingText = loadingTexts[currentLoadingTextIndex];
            String dots = "";
            for (int i = 0; i < dotCount; i++) {
                dots += " .";
            }
            String fullText = loadingText + dots;
            g2d.setFont(new Font("Lucida Console", Font.BOLD, 16));
            int textWidth = g2d.getFontMetrics().stringWidth(fullText);
            int x = (getWidth() - textWidth) / 2;
            int y = getHeight() - 40;
            g2d.setColor(Color.BLACK);
            for (int i = -1; i <= 1; i++) {
                for (int j = -1; j <= 1; j++) {
                    if (i != 0 || j != 0) {
                        g2d.drawString(fullText, x + i, y + j);
                    }
                }
            }
            g2d.setColor(Color.WHITE);
            g2d.drawString(fullText, x, y);
        }
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
    private Timer backgroundTimer;
    private int currentBackgroundIndex = 0;
    private JFrame parentFrame;
    private Image queenBinaryImage;
    private float queenScale = 0.1f; // Initial scale for the queen's image
    private Timer queenAnimationTimer; // Timer for the queen's animation
    private int sparkleAlpha = 255; // Alpha value for sparkles
    private boolean sparkleFadeOut = true; // Sparkle fade direction
    private int queenX; // X position of the queen
    private int queenY; // Y position of the queen
    private int newQueenWidth; // Width of the queen's image
    private int newQueenHeight; // Height of the queen's image
    private Timer queenMoveTimer; // Timer for moving the queen to the left
    private boolean queenMoveStarted = false; // Flag to check if the queen has started moving

    private Image introTextBoxImage; // New image for the intro text box
    private int introTextBoxX; // X position of the intro text box
    private int introTextBoxY; // Y position of the intro text box
    private int introTextBoxWidth; // Width of the intro text box
    private int introTextBoxHeight; // Height of the intro text box
    private Timer introTextBoxMoveTimer; // Timer for moving the intro text box to the right
    private boolean introTextBoxVisible = false; // Flag to control visibility of the intro text box
    private boolean introTextBoxStopped = false; // Flag to check if the intro text box has stopped moving

    private Image subTextBoxImage; // New image for the sub-text box

    private String queenName = "QUEEN BINARY";
    private int queenNameIndex = 0; // For appearance animation
    private int queenNameDisappearIndex = queenName.length(); // For disappearance animation
    private Timer queenNameTimer;
    private Timer queenNameDisappearTimer;
    private boolean queenNameVisible = false;
    private boolean queenNameAnimationComplete = false;

    private String[] textWords; // Array to hold individual words of the text
    private int currentWordIndex = 0; // Index of the current word being displayed
    private Timer wordDisplayTimer; // Timer for word-by-word display

    public GameMainScreen(JFrame parentFrame) {
        this.parentFrame = parentFrame;

        // Load the new background images
        this.backgrounds = new Image[3];
        this.backgrounds[0] = new ImageIcon("C:/Users/User/IdeaProjects/java Programs/out/production/java Programs/finalProject/com/bg-first.png").getImage();
        this.backgrounds[1] = new ImageIcon("C:/Users/User/IdeaProjects/java Programs/out/production/java Programs/finalProject/com/bg-first2.png").getImage();
        this.backgrounds[2] = new ImageIcon("C:/Users/User/IdeaProjects/java Programs/out/production/java Programs/finalProject/com/bg-first3.png").getImage();

        queenBinaryImage = new ImageIcon("C:/Users/User/IdeaProjects/java Programs/out/production/java Programs/finalProject/com/queen-binary.png").getImage();
        introTextBoxImage = new ImageIcon("C:/Users/User/IdeaProjects/java Programs/out/production/java Programs/finalProject/com/intro-text-box.png").getImage(); // Load the intro text box image
        subTextBoxImage = new ImageIcon("C:/Users/User/IdeaProjects/java Programs/out/production/java Programs/finalProject/com/sub-text-box.png").getImage(); // Load the sub-text box image

        // Initialize the text words array
        String text = "Brave Sophia, something terrible has happened! A bad bug called Glitch has stolen all the knowledge from our kingdom and broken it into pieces! These pieces are scattered across four lands: Binary River, Hardware Mountains, Software Sanctum, and Internet Island. You’re the only one who can help! You must travel to these lands, solve the challenges, and find the lost knowledge. Only then can we defeat Glitch and fix our world!";
        textWords = text.split(" ");

        // Timer for word-by-word display
        wordDisplayTimer = new Timer(500, new ActionListener() { // Increased delay to 500ms
            @Override
            public void actionPerformed(ActionEvent e) {
                if (currentWordIndex < textWords.length) {
                    currentWordIndex++;
                    repaint();
                } else {
                    ((Timer) e.getSource()).stop();
                }
            }
        });

        // Set up the background timer to cycle through the new images
        backgroundTimer = new Timer(200, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                currentBackgroundIndex = (currentBackgroundIndex + 1) % backgrounds.length;
                repaint();
            }
        });
        backgroundTimer.start();

        // Timer for the queen's animation
        queenAnimationTimer = new Timer(50, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (queenScale < 1.0f) {
                    queenScale += 0.02f; // Gradually increase the scale
                }

                // Sparkle fade animation
                if (sparkleFadeOut) {
                    sparkleAlpha -= 10;
                    if (sparkleAlpha <= 200) sparkleFadeOut = false; // Adjusted minimum alpha to 100
                } else {
                    sparkleAlpha += 10;
                    if (sparkleAlpha >= 255) sparkleFadeOut = true;
                }
                repaint();
            }
        });
        queenAnimationTimer.start();

        // Play the queen's entrance sound effect
        playSoundEffect("C:/Users/User/IdeaProjects/java Programs/out/production/java Programs/finalProject/com/queen-appear-sound.wav");

        // Timer to delay the queen's movement (removed the 3-second delay)
        Timer delayTimer = new Timer(0, new ActionListener() { // Changed from 3000 to 0
            @Override
            public void actionPerformed(ActionEvent e) {
                queenNameVisible = true;
                queenNameTimer.start(); // Start the queen's name animation immediately
            }
        });
        delayTimer.setRepeats(false);
        delayTimer.start();

        // Timer for the queen's name appearance animation
        queenNameTimer = new Timer(150, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (queenNameIndex < queenName.length()) {
                    queenNameIndex++;
                    // Play the sound effect when the first letter starts to animate
                    if (queenNameIndex == 1) {
                        // Add a 2-second delay before playing the sound
                        Timer soundDelayTimer = new Timer(4000, new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                playQueenTextIntroSound(); // Play the sound after the delay
                            }
                        });
                        soundDelayTimer.setRepeats(false); // Ensure the timer only runs once
                        soundDelayTimer.start();
                    }
                    repaint();
                } else {
                    queenNameTimer.stop();
                    Timer pauseTimer = new Timer(3000, new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            queenNameDisappearTimer.start(); // Start the disappearance animation
                        }
                    });
                    pauseTimer.setRepeats(false);
                    pauseTimer.start();
                }
            }
        });

        // Timer for the queen's name disappearance animation
        queenNameDisappearTimer = new Timer(150, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (queenNameDisappearIndex > 0) {
                    queenNameDisappearIndex--;
                    repaint();
                } else {
                    queenNameDisappearTimer.stop();
                    queenNameVisible = false;
                    queenMoveStarted = true;
                    queenMoveTimer.start();

                    // Set the initial position of the text box to be slightly closer to the middle of the screen
                    introTextBoxX = queenX - introTextBoxWidth - 150; // Start the text box to the left of the queen
                    introTextBoxVisible = true; // Make the intro text box visible

                    // Play the queen-box-slide-sound effect when the text box starts moving
                    playSoundEffect("C:/Users/User/IdeaProjects/java Programs/out/production/java Programs/finalProject/com/queen-box-slide-sound.wav");

                    introTextBoxMoveTimer.start(); // Start the intro text box movement timer
                }
            }
        });

        // Timer for moving the queen to the left
        queenMoveTimer = new Timer(17, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (queenX > 265) { // Move the queen to the left until she reaches x = 150
                    queenX -= 7;
                    repaint();
                } else {
                    ((Timer) e.getSource()).stop();
                }
            }
        });

        // Timer for moving the intro text box to the right
        introTextBoxMoveTimer = new Timer(16, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Stop the intro text box before it reaches the right edge (leave some space)
                if (introTextBoxX < getWidth() - introTextBoxWidth - 320) { // Adjusted to stop 200 pixels before the right edge
                    introTextBoxX += 5; // Move the text box to the right
                    repaint();
                } else {
                    ((Timer) e.getSource()).stop();
                    introTextBoxStopped = true; // Set the flag to true when the text box stops moving
                    repaint(); // Trigger repaint to draw the text

                    // Start the word-by-word display timer
                    wordDisplayTimer.start();
                }
            }
        });
    }

    private void playSoundEffect(String filePath) {
        try {
            File soundFile = new File(filePath);
            AudioInputStream audioIn = AudioSystem.getAudioInputStream(soundFile);
            Clip clip = AudioSystem.getClip();
            clip.open(audioIn);
            clip.start();
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }

    private void playQueenTextIntroSound() {
        try {
            File soundFile = new File("C:/Users/User/IdeaProjects/java Programs/out/production/java Programs/finalProject/com/queen-text-intro-sound.wav");
            AudioInputStream audioIn = AudioSystem.getAudioInputStream(soundFile);
            Clip clip = AudioSystem.getClip();
            clip.open(audioIn);
            clip.start();
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(backgrounds[currentBackgroundIndex], 0, 0, getWidth(), getHeight(), this);

        // Draw the intro text box first (behind the queen and sparkles)
        if (introTextBoxVisible && introTextBoxImage != null) {
            introTextBoxWidth = introTextBoxImage.getWidth(this);
            introTextBoxHeight = introTextBoxImage.getHeight(this);
            introTextBoxY = (getHeight() - introTextBoxHeight) / 2; // Center vertically
            g.drawImage(introTextBoxImage, introTextBoxX, introTextBoxY, introTextBoxWidth, introTextBoxHeight, this);

            // Draw the sub-text-box on top of the intro text box
            if (subTextBoxImage != null) {
                int subTextBoxWidth = subTextBoxImage.getWidth(this);
                int subTextBoxHeight = subTextBoxImage.getHeight(this);
                int subTextBoxX = introTextBoxX + (introTextBoxWidth - subTextBoxWidth) / 2; // Center horizontally
                int subTextBoxY = introTextBoxY - subTextBoxHeight / 2; // Position at the center top of the intro text box
                g.drawImage(subTextBoxImage, subTextBoxX, subTextBoxY, subTextBoxWidth, subTextBoxHeight, this);
            }

            // Draw the text within the intro text box after it has stopped moving
            if (introTextBoxStopped && queenMoveTimer.isRunning() == false) {
                Graphics2D g2d = (Graphics2D) g;
                g2d.setFont(new Font("Century Gothic", Font.BOLD, 14));
                FontMetrics fm = g2d.getFontMetrics();

                // Calculate the margins
                int marginX = (int) (0.7 * 72); // 0.5 inch margin in pixels (72 pixels per inch)
                int marginY = (int) (0.3 * 72); // 0.3 inch margin in pixels

                // Calculate the text area
                int textAreaX = introTextBoxX + marginX;
                int textAreaY = introTextBoxY + marginY + 30; // Adjusted downward by 20 pixels
                int textAreaWidth = introTextBoxWidth - 2 * marginX;
                int textAreaHeight = introTextBoxHeight - 2 * marginY;

                // Build the text up to the current word
                StringBuilder displayedText = new StringBuilder();
                for (int i = 0; i < currentWordIndex; i++) {
                    displayedText.append(textWords[i]).append(" ");
                }

                // Split the text into lines that fit within the text area
                String[] words = displayedText.toString().split(" ");
                StringBuilder line = new StringBuilder();
                int y = textAreaY + fm.getAscent();
                for (String word : words) {
                    String testLine = line.length() == 0 ? word : line + " " + word;
                    int testWidth = fm.stringWidth(testLine);
                    if (testWidth > textAreaWidth) {
                        drawLine(g2d, line.toString(), textAreaX, y, fm);
                        line = new StringBuilder(word);
                        y += fm.getHeight();
                    } else {
                        line.append(line.length() == 0 ? word : " " + word);
                    }
                }
                if (line.length() > 0) {
                    drawLine(g2d, line.toString(), textAreaX, y, fm);
                }
            }
        }

        // Draw the queen's image
        int queenWidth = queenBinaryImage.getWidth(this);
        int queenHeight = queenBinaryImage.getHeight(this);
        int screenHeight = getHeight();
        int screenWidth = getWidth();

        newQueenHeight = (int) (screenHeight / 3 * queenScale);
        newQueenWidth = (int) ((double) queenWidth / queenHeight * newQueenHeight);

        if (!queenMoveStarted) {
            queenX = (screenWidth - newQueenWidth) / 2;
            queenY = (screenHeight - newQueenHeight) / 2 + 30;
        }

        g.drawImage(queenBinaryImage, queenX, queenY, newQueenWidth, newQueenHeight, this);

        // Draw the queen's name with karaoke-like animation
        if (queenNameVisible) {
            Graphics2D g2d = (Graphics2D) g;
            g2d.setFont(new Font("Lucida Console", Font.BOLD, 24));
            FontMetrics fm = g2d.getFontMetrics();
            int textWidth = fm.stringWidth(queenName);
            int textHeight = fm.getHeight();

            // Calculate the position of the text above the queen's head
            int textX = queenX + (newQueenWidth - textWidth) / 2;
            int textY = queenY - textHeight;

            // Draw the full text in a lighter color
            g2d.setColor(new Color(255, 255, 255, 100));
            g2d.drawString(queenName, textX, textY);

            // Draw the animated part of the text
            if (queenNameDisappearIndex == queenName.length()) {
                // Appearance animation: Draw the text in yellow
                String animatedText = queenName.substring(0, queenNameIndex);
                g2d.setColor(Color.BLACK);
                for (int i = -1; i <= 1; i++) {
                    for (int j = -1; j <= 1; j++) {
                        if (i != 0 || j != 0) {
                            g2d.drawString(animatedText, textX + i, textY + j);
                        }
                    }
                }
                g2d.setColor(Color.YELLOW); // Yellow during appearance animation
                g2d.drawString(animatedText, textX, textY);
            } else {
                // Disappearance animation: Turn letters white one by one
                String remainingText = queenName.substring(0, queenNameDisappearIndex);
                String whiteText = queenName.substring(queenNameDisappearIndex);

                // Draw the remaining text in yellow
                g2d.setColor(Color.BLACK);
                for (int i = -1; i <= 1; i++) {
                    for (int j = -1; j <= 1; j++) {
                        if (i != 0 || j != 0) {
                            g2d.drawString(remainingText, textX + i, textY + j);
                        }
                    }
                }
                g2d.setColor(Color.YELLOW); // Yellow for the remaining text
                g2d.drawString(remainingText, textX, textY);

                // Draw the disappearing text in white, one letter at a time
                g2d.setColor(Color.BLACK);
                for (int i = -1; i <= 1; i++) {
                    for (int j = -1; j <= 1; j++) {
                        if (i != 0 || j != 0) {
                            g2d.drawString(whiteText, textX + fm.stringWidth(remainingText) + i, textY + j);
                        }
                    }
                }
                g2d.setColor(Color.WHITE); // White for the disappearing text
                g2d.drawString(whiteText, textX + fm.stringWidth(remainingText), textY);
            }
        }

        // Draw sparkles around the queen
        Graphics2D g2d = (Graphics2D) g;
        for (int i = 0; i < 20; i++) { // Increase the number of sparkles
            // Randomly position sparkles around the queen (but not inside her body)
            int sparkleX, sparkleY;
            do {
                sparkleX = queenX + (int) (Math.random() * (newQueenWidth + 100)) - 50; // Wider scatter area
                sparkleY = queenY + (int) (Math.random() * (newQueenHeight + 100)) - 50; // Wider scatter area
            } while (sparkleX >= queenX && sparkleX <= queenX + newQueenWidth && sparkleY >= queenY && sparkleY <= queenY + newQueenHeight);

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

    private void drawLine(Graphics2D g2d, String line, int x, int y, FontMetrics fm) {
        String[] words = line.split(" ");
        int currentX = x;
        for (String word : words) {
            // Check if the word is "Glitch"
            if (word.equalsIgnoreCase("Glitch")) {
                g2d.setColor(new Color(165, 42, 42)); // Brown color for "Glitch"
            }
            // Check if the word is part of the special names
            else if (word.equalsIgnoreCase("Binary") || word.equalsIgnoreCase("River") ||
                    word.equalsIgnoreCase("Hardware") || word.equalsIgnoreCase("Mountains") ||
                    word.equalsIgnoreCase("Software") || word.equalsIgnoreCase("Sanctum") ||
                    word.equalsIgnoreCase("Internet") || word.equalsIgnoreCase("Island")) {
                g2d.setColor(new Color(184, 134, 11)); // Dark yellow color for special names
            } else {
                g2d.setColor(Color.BLACK); // Default color for other words
            }

            // Draw the word
            g2d.drawString(word, currentX, y);
            currentX += fm.stringWidth(word + " ");
        }
    }
}