package finalProject.com; // game intro to main menu draft

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

public class GameIntroScreen extends JPanel {
    private Image background;
    private Image characterLeft;
    private Image characterRight;
    private Image currentCharacter;
    private int loadingProgress = 0;
    private Timer loadingTimer;
    private Timer starTimer;
    private Timer characterTimer;
    private int starAlpha = 255;
    private boolean fadeOut = true;
    private boolean facingLeft = true;
    private Clip backgroundMusic;
    private JFrame parentFrame;
    private int fadeAlpha = 0; // For fade transition
    private boolean transitioning = false; // To control the transition state

    public GameIntroScreen(JFrame frame) {
        this.parentFrame = frame;
        background = new ImageIcon("C:/Users/User/IdeaProjects/java Programs/out/production/java Programs/finalProject/com/ui inspo.png").getImage();
        characterLeft = new ImageIcon("C:/Users/User/IdeaProjects/java Programs/out/production/java Programs/finalProject/com/sophia.png").getImage();
        characterRight = createMirroredImage(characterLeft);
        currentCharacter = characterLeft;

        playBackgroundMusic();

        // timer for loading bar
        loadingTimer = new Timer(200, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (loadingProgress < 100) {
                    loadingProgress += 2;
                    repaint();
                } else {
                    ((Timer) e.getSource()).stop();
                    startTransition();
                }
            }
        });
        loadingTimer.start();

        // timer for blinking stars
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

        // timer for character animation (flipping left and right)
        characterTimer = new Timer(500, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                facingLeft = !facingLeft;
                currentCharacter = facingLeft ? characterLeft : characterRight;
                repaint();
            }
        });
        characterTimer.start();
    }

    private void startTransition() {
        transitioning = true;
        Timer fadeTimer = new Timer(10, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (fadeAlpha < 255) {
                    fadeAlpha += 5; // fade opacity
                    repaint();
                } else {
                    ((Timer) e.getSource()).stop();
                    playTransitionSound(); // transition sound effect
                    transitionToNextScreen();
                }
            }
        });
        fadeTimer.start();
    }

    private void playTransitionSound() {
        try {
            File soundFile = new File("C:/Users/User/IdeaProjects/java Programs/out/production/java Programs/finalProject/com/sound-effect1.wav");
            AudioInputStream audioIn = AudioSystem.getAudioInputStream(soundFile);
            Clip transitionSound = AudioSystem.getClip();
            transitionSound.open(audioIn);
            transitionSound.start(); // sound effect 1
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }

    private void transitionToNextScreen() {
        parentFrame.getContentPane().removeAll();
        parentFrame.getContentPane().add(new GameMainScreen(parentFrame));
        parentFrame.revalidate();
        parentFrame.repaint();
    }

    private Image createMirroredImage(Image original) {
        int width = original.getWidth(null);
        int height = original.getHeight(null);
        BufferedImage mirrored = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = mirrored.createGraphics();
        g2d.drawImage(original, width, 0, -width, height, null);
        g2d.dispose();
        return mirrored;
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
        g2d.drawImage(background, 0, 0, getWidth(), getHeight(), this);
        g2d.setColor(new Color(255, 255, 255, starAlpha));
        for (int i = 0; i < 20; i++) {
            int x = (int) (Math.random() * getWidth());
            int y = (int) (Math.random() * getHeight());
            g2d.fillOval(x, y, 3, 3);
        }
        int barWidth = 288;
        int barHeight = 23;
        int barX = getWidth() / 2 - barWidth / 2;
        int barY = getHeight() - 100;
        g2d.setColor(Color.BLACK);
        g2d.fillRect(barX, barY, barWidth, barHeight);
        g2d.setColor(Color.GREEN);
        g2d.fillRect(barX, barY, (loadingProgress * barWidth) / 100, barHeight);
        g2d.setColor(Color.WHITE);
        g2d.setFont(new Font("Lucida Console", Font.BOLD, 16));
        String loadingText = "  Loading...";
        int textWidth = g2d.getFontMetrics().stringWidth(loadingText);
        g2d.drawString(loadingText, getWidth() / 2 - textWidth / 2, barY + barHeight + 20);
        int charWidth = currentCharacter.getWidth(null) / 2;
        int charHeight = currentCharacter.getHeight(null) / 2;
        int charX = getWidth() / 2 - charWidth / 2 + 2;
        int charY = barY - charHeight - 20;
        g2d.drawImage(currentCharacter, charX, charY, charWidth, charHeight, this);

        // fade overlay when transitioning
        if (transitioning) {
            g2d.setColor(new Color(0, 0, 0, fadeAlpha));
            g2d.fillRect(0, 0, getWidth(), getHeight());
        }
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("The Amazing Adventures of Sophia");
        GameIntroScreen panel = new GameIntroScreen(frame);
        frame.add(panel);
        frame.setSize(800, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}

class GameMainScreen extends JPanel {
    private Image mainBackground;
    private JButton startButton;
    private JButton quitButton;
    private JFrame parentFrame;

    public GameMainScreen(JFrame parentFrame) {
        this.parentFrame = parentFrame;
        mainBackground = new ImageIcon("C:/Users/User/IdeaProjects/java Programs/out/production/java Programs/finalProject/com/ui inspo two.png").getImage();

        // Load button images
        ImageIcon startIcon = new ImageIcon("C:/Users/User/IdeaProjects/java Programs/out/production/java Programs/finalProject/com/start-button.png");
        ImageIcon quitIcon = new ImageIcon("C:/Users/User/IdeaProjects/java Programs/out/production/java Programs/finalProject/com/quit-button.png");

        // Create buttons with images
        startButton = new JButton(startIcon);
        quitButton = new JButton(quitIcon);

        // Remove borders and background
        startButton.setBorderPainted(false);
        startButton.setContentAreaFilled(false);
        quitButton.setBorderPainted(false);
        quitButton.setContentAreaFilled(false);

        // button positions
        setLayout(null); // Use absolute positioning
        int buttonWidth = startIcon.getIconWidth();
        int buttonHeight = startIcon.getIconHeight();
        int startX = (708 - buttonWidth) / 1; // Center horizontally
        int startY = (600 - buttonHeight * 2 - 10) / 2 + 150; // Position a bit lower
        int quitX = startX;
        int quitY = startY + buttonHeight + 10; // Space between buttons

        startButton.setBounds(startX, startY, buttonWidth, buttonHeight);
        quitButton.setBounds(quitX, quitY, buttonWidth, buttonHeight);

        // adding action listeners
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                playClickSound(); // Play click sound effect
                // Start game logic here
                JOptionPane.showMessageDialog(parentFrame, "Game Started!");
            }
        });

        quitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                playClickSound(); // Play click sound effect
                System.exit(0); // Exit the game
            }
        });

        // adding buttons to the panel
        add(startButton);
        add(quitButton);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(mainBackground, 0, 0, getWidth(), getHeight(), this);
    }

    private void playClickSound() {
        try {
            File soundFile = new File("C:/Users/User/IdeaProjects/java Programs/out/production/java Programs/finalProject/com/click-sound-effect.wav");
            AudioInputStream audioIn = AudioSystem.getAudioInputStream(soundFile);
            Clip clickSound = AudioSystem.getClip();
            clickSound.open(audioIn);
            clickSound.start(); // Play the click sound effect
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }
}