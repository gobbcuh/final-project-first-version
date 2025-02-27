package entity.com;

import finalProject2.com.GamePanel;
import finalProject2.com.KeyHandler;

import java.awt.*;

public class Player extends Entity {
    GamePanel gp;
    KeyHandler keyH;

    public Player(GamePanel gp, KeyHandler keyH) {
        this.gp = gp;
        this.keyH = keyH;
        setDefaultValues();
    }
    public void setDefaultValues() {
        x = 100;
        y = 100;
        speed = 4;
    }
    public void update() {
        if (keyH.upPressed == true) {
            y = y - speed;
        }
        else if (keyH.downPressed == true) {
            y = y + speed;
        }
        else if (keyH.leftPressed == true) {
            x = x - speed;
        }
        else if (keyH.rightPressed == true) {
            x = x + speed;
        }
    }
    public void draw(Graphics2D g2) {
        g2.setColor(Color.white);
        g2.fillRect(x, y, gp.tileSize, gp.tileSize);
    }
}
