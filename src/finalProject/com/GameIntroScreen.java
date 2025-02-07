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
    private Timer loadingTimer;
    private Timer starTimer;
    private Timer backgroundTimer; // cycling through background images
    private int starAlpha = 255;
    private boolean fadeOut = true;
    private Clip backgroundMusic;
    private JFrame parentFrame;
    private int currentBackgroundIndex = 0; // Index to track the current background image
    private String loadingText = "Loading";
    private int dotCount = 0;
    private Timer textAnimationTimer; // timer for loading text animation

    public GameIntroScreen(JFrame frame) {
        this.parentFrame = frame;

        // loading the background images
        backgrounds = new Image[4];
        for (int i = 0; i < 4; i++) {
            backgrounds[i] = new ImageIcon("C:/Users/User/IdeaProjects/java Programs/out/production/java Programs/finalProject/com/loading-bg" + (i + 1) + ".png").getImage();
        }

        playBackgroundMusic();

        // timer for loading text animation
        textAnimationTimer = new Timer(500, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dotCount = (dotCount + 1) % 6; // Cycle through 0 to 5 dots
                loadingText = "Loading" + ".".repeat(dotCount); // Update loading text
                repaint();
            }
        });
        textAnimationTimer.start();

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

        // loading completion
        loadingTimer = new Timer(10000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ((Timer) e.getSource()).stop();
                transitionToNextScreen();
            }
        });
        loadingTimer.start();
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

        // Draw dynamic loading text
        g2d.setColor(Color.WHITE);
        g2d.setFont(new Font("Lucida Console", Font.BOLD, 16));
        int textWidth = g2d.getFontMetrics().stringWidth(loadingText);
        int textX = getWidth() / 2 - textWidth / 2;
        int textY = getHeight() - 100;
        g2d.drawString(loadingText, textX, textY);
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