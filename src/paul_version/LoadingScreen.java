package paul_version;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoadingScreen extends JFrame {
    private JProgressBar progressBar;
    private JLabel loadingLabel;
    private JLabel textLabel;
    private int animationFrame = 0;
    private MainMenu menu;

    public LoadingScreen() {
        this.setTitle("The Amazing Adventures of Sophia");
        this.setSize(800, 600);
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null); // Center the window

        // Create a JLabel for the loading image
        loadingLabel = new JLabel();
        loadingLabel.setLayout(new BorderLayout());

        // Add loading text
        textLabel = new JLabel("Loading, please wait...", JLabel.CENTER);
        textLabel.setForeground(Color.WHITE);
        loadingLabel.add(textLabel, BorderLayout.SOUTH);

        // Add the JLabel to the frame
        add(loadingLabel);

        // Start the animation
        startAnimation();

        // Initialize the MainMenu
        menu = new MainMenu();

        // Set a timer to close the loading screen and show the main menu after a delay
        Timer loadingTimer = new Timer(5000, new ActionListener() { // 5000 milliseconds = 5 seconds
            @Override
            public void actionPerformed(ActionEvent e) {
                hideLoadingScreen();
            }
        });
        loadingTimer.setRepeats(false); // Only execute once
        loadingTimer.start();
    }

    private void startAnimation() {
        Timer timer = new Timer(180, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Update the loading image or text for animation
                updateLoadingImage();
            }
        });
        timer.start();
    }

    private void updateLoadingImage() {
        // Load and resize the image
        ImageIcon originalIcon = new ImageIcon("C:\\Users\\lored\\Downloads\\mainMenu1.png");
        Image img = originalIcon.getImage();
        Image resizedImg = img.getScaledInstance(800, 600, Image.SCALE_SMOOTH); // Resize to 800x600
        loadingLabel.setIcon(new ImageIcon(resizedImg));

        // Optionally, you can change the text or perform other animations
        animationFrame++;
        if (animationFrame % 10 == 0) {
            textLabel.setText("Loading, please wait... " + (animationFrame / 10));
        }
    }

    public void showLoadingScreen() {
        setVisible(true);
    }

    public void hideLoadingScreen() {
        dispose(); // Close the loading screen
        menu.setVisible(true); // Show the main menu
    }
}
