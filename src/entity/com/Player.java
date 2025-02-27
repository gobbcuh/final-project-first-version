package entity.com;

import finalProject2.com.GamePanel;
import finalProject2.com.KeyHandler;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Player extends Entity {
    GamePanel gp;
    KeyHandler keyH;

    public Player(GamePanel gp, KeyHandler keyH) {
        this.gp = gp;
        this.keyH = keyH;
        setDefaultValues();
        getPlayerImage();
    }
    public void setDefaultValues() {
        x = 100;
        y = 100;
        speed = 4;
        direction = "down";
    }
    public void getPlayerImage() {
        try {
            up1 = ImageIO.read(getClass().getResourceAsStream("/player/1W.png"));
            up2 = ImageIO.read(getClass().getResourceAsStream("/player/2W.png"));
            down1 = ImageIO.read(getClass().getResourceAsStream("/player/1S.png"));
            down2 = ImageIO.read(getClass().getResourceAsStream("/player/2S.png"));
            left1 = ImageIO.read(getClass().getResourceAsStream("/player/1A.png"));
            left2 = ImageIO.read(getClass().getResourceAsStream("/player/2A.png"));
            right1 = ImageIO.read(getClass().getResourceAsStream("/player/1D.png"));
            right2 = ImageIO.read(getClass().getResourceAsStream("/player/2D.png"));
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    public void update() {
        if (keyH.upPressed) {
            direction = "up";
            y = y - speed;
        }
        else if (keyH.downPressed == true) {
            direction = "down";
            y = y + speed;
        }
        else if (keyH.leftPressed == true) {
            direction = "left";
            x = x - speed;
        }
        else if (keyH.rightPressed == true) {
            direction = "right";
            x = x + speed;
        }
    }
    public void draw(Graphics2D g2) {
//        g2.setColor(Color.white);
//        g2.fillRect(x, y, gp.tileSize, gp.tileSize);

        BufferedImage image = null;

        switch(direction) {
            case "up":
                image = up1;
                break;
            case "down":
                image = down1;
                break;
            case "left":
                image = left1;
                break;
            case "right":
                image = right1;
                break;
        }
        g2.drawImage(image, x, y, gp.tileSize, gp.tileSize,null);
    }
}
