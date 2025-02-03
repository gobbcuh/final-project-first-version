package finalProject.com;

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

    public GameIntroScreen() {
        // Load images
        background = new ImageIcon("C:/Users/User/IdeaProjects/java Programs/out/production/java Programs/finalProject/com/ui inspo.png").getImage();
        characterLeft = new ImageIcon("C:/Users/User/IdeaProjects/java Programs/out/production/java Programs/finalProject/com/ui_inspo__1_-removebg-preview.png").getImage();
        characterRight = createMirroredImage(characterLeft);
        currentCharacter = characterLeft;

        // Play background music
        playBackgroundMusic();

        // Timer for loading bar
        loadingTimer = new Timer(100, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (loadingProgress < 100) {
                    loadingProgress += 2;
                    repaint();
                } else {
                    ((Timer) e.getSource()).stop();
                }
            }
        });
        loadingTimer.start();

        // Timer for blinking stars
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

        // Timer for character animation (flipping left and right)
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

    // Method to create a mirrored version of the character image
    private Image createMirroredImage(Image original) {
        int width = original.getWidth(null);
        int height = original.getHeight(null);
        BufferedImage mirrored = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = mirrored.createGraphics();
        g2d.drawImage(original, width, 0, -width, height, null);
        g2d.dispose();
        return mirrored;
    }

    // Method to play background music
    private void playBackgroundMusic() {
        try {
            File musicFile = new File("C:/Users/User/IdeaProjects/java Programs/out/production/java Programs/finalProject/com/super-mario.wav");
            AudioInputStream audioIn = AudioSystem.getAudioInputStream(musicFile);
            backgroundMusic = AudioSystem.getClip();
            backgroundMusic.open(audioIn);
            backgroundMusic.loop(Clip.LOOP_CONTINUOUSLY);
            backgroundMusic.start(); // Add this line
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        // Draw background
        g2d.drawImage(background, 0, 0, getWidth(), getHeight(), this);

        // Draw blinking stars
        g2d.setColor(new Color(255, 255, 255, starAlpha));
        for (int i = 0; i < 20; i++) {
            int x = (int) (Math.random() * getWidth());
            int y = (int) (Math.random() * getHeight());
            g2d.fillOval(x, y, 3, 3);
        }

        // Draw loading bar
        int barWidth = 200;
        int barHeight = 20;
        int barX = getWidth() / 2 - barWidth / 2;
        int barY = getHeight() - 100;
        g2d.setColor(Color.BLACK);
        g2d.fillRect(barX, barY, barWidth, barHeight);
        g2d.setColor(Color.GREEN);
        g2d.fillRect(barX, barY, (loadingProgress * barWidth) / 100, barHeight);

        // Draw loading text below bar
        g2d.setColor(Color.WHITE);
        g2d.setFont(new Font("Arial", Font.BOLD, 16));
        String loadingText = "  Loading...";
        int textWidth = g2d.getFontMetrics().stringWidth(loadingText);
        g2d.drawString(loadingText, getWidth() / 2 - textWidth / 2, barY + barHeight + 20);

        // Draw character moved slightly to the right, but not blocking the keys image
        int charWidth = currentCharacter.getWidth(null) / 2;
        int charHeight = currentCharacter.getHeight(null) / 2;
        int charX = getWidth() / 2 - charWidth / 2 + 2; // Reduced the distance moved to the right
        int charY = barY - charHeight - 20;
        g2d.drawImage(currentCharacter, charX, charY, charWidth, charHeight, this);
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("The Amazing Adventures of Sophia");
        GameIntroScreen panel = new GameIntroScreen();
        frame.add(panel);
        frame.setSize(800, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}
