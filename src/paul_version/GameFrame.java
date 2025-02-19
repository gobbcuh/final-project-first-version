package paul_version;

import javax.swing.*;

public class GameFrame {
    JFrame frame = new JFrame();

    GameFrame() {
        frame.setTitle("The Amazing Adventures of Sophia");
        frame.setSize(800, 600);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null); // Center the window
        frame.setVisible(true);

        GamePanel gamePanel = new GamePanel();

        frame.add(gamePanel);
        frame.pack();
        frame.setVisible(true);

        gamePanel.startGameThread();
    }
}
