package entity.com;

import finalProject2.com.GamePanel;
import finalProject2.com.KeyHandler;
import finalProject2.com.UtilityTool;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Player extends Entity {
    GamePanel gp;
    KeyHandler keyH;
    public final int screenX;
    public final int screenY;
    public int hasKey = 0;
    int standCounter = 0;
    boolean moving = false;
    int pixelCounter = 0;

    public Player(GamePanel gp, KeyHandler keyH) {
        this.gp = gp;
        this.keyH = keyH;

        screenX = gp.screenWidth / 2 - (gp.tileSize / 2);
        screenY = gp.screenHeight / 2 - (gp.tileSize / 2);

        solidArea = new Rectangle();
        solidArea.x = 1;
        solidArea.y = 1;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
        solidArea.width = 46;
        solidArea.height = 46;

        setDefaultValues();
        getPlayerImage();
    }
    public void setDefaultValues() {
        worldX = gp.tileSize * 23;
        worldY = gp.tileSize * 21;
        speed = 4;
        direction = "down";
    }
    public void getPlayerImage() {
        up1 = setup("1W");
        up2 = setup("2W");
        down1 = setup("1S");
        down2 = setup("2S");
        left1 = setup("1A");
        left2 = setup("2A");
        right1 = setup("1D");
        right2 = setup("2D");
    }
    public BufferedImage setup(String imageName) {
        UtilityTool uTool = new UtilityTool();
        BufferedImage image = null;
        try {
            image = ImageIO.read(getClass().getResourceAsStream("/player/" + imageName + ".png"));
            image = uTool.scaleImage(image, gp.tileSize, gp.tileSize);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return image;
    }

    public void update() {
        if (moving == false) {
            if (keyH.upPressed == true || keyH.downPressed == true || keyH.leftPressed || keyH.rightPressed) {

                if (keyH.upPressed == true) {
                    direction = "up";
                } else if (keyH.downPressed == true) {
                    direction = "down";
                } else if (keyH.leftPressed == true) {
                    direction = "left";
                } else if (keyH.rightPressed == true) {
                    direction = "right";
                }

                moving = true;

                // CHECK TILE COLLISION
                collisionOn = false;
                gp.cChecker.checkTile(this);

                // CHECK OBJECT COLLISION
                int objIndex = gp.cChecker.checkObject(this, true);
                pickUpObject(objIndex);
            }
            else {
                standCounter++;

                if (standCounter == 20) {
                    spriteNum = 1;
                    standCounter = 0;
                }
            }
        }
        if (moving == true) {
            // IF COLLISION IS FALSE, PLAYER CAN MOVE
            if (collisionOn == false) {
                switch (direction) {
                    case "up":
                        worldY = worldY - speed;
                        break;
                    case "down":
                        worldY = worldY + speed;
                        break;
                    case "left":
                        worldX = worldX - speed;
                        break;
                    case "right":
                        worldX = worldX + speed;
                        break;
                }
            }
            spriteCounter++;
            if (spriteCounter > 12) {
                if (spriteNum == 1) {
                    spriteNum = 2;
                }
                else if (spriteNum == 2) {
                    spriteNum = 1;
                }
                spriteCounter = 0;
            }
            pixelCounter += speed;
            if (pixelCounter == 48) {
                moving = false;
                pixelCounter = 0;
            }
        }
    }

    public void pickUpObject(int i) {
        if (i != 999) {
            String objectName = gp.obj[i].name;

            switch (objectName) {
                case "Key":
                    gp.playSE(1);
                    hasKey++;
                    gp.obj[i] = null;
                    gp.ui.showMessage("You got a key!");
                    break;
                case "Door":
                    if (hasKey > 0) {
                        gp.playSE(3);
                        gp.obj[i] = null;
                        hasKey--;
                        gp.ui.showMessage("You opened the door!");
                    }
                    else {
                        gp.ui.showMessage("You need a key!");
                    }
                    break;
                case "Boots":
                    gp.playSE(2);
                    speed += 2;
                    gp.obj[i] = null;
                    gp.ui.showMessage("Speed Up!");
                    break;
                case "Chess":
                    gp.ui.gameFinished = true;
                    gp.stopMusic();
                    gp.playSE(4);
                    break;
            }
        }
    }

    public void draw(Graphics2D g2) {
//        g2.setColor(Color.white);
//        g2.fillRect(x, y, gp.tileSize, gp.tileSize);

        BufferedImage image = null;

        switch(direction) {
            case "up":
                if (spriteNum == 1) {
                    image = up1;
                }
                if (spriteNum == 2) {
                    image = up2;
                }
                break;
            case "down":
                if (spriteNum == 1) {
                    image = down1;
                }
                if (spriteNum == 2) {
                    image = down2;
                }
                break;
            case "left":
                if (spriteNum == 1) {
                    image = left1;
                }
                if (spriteNum == 2) {
                    image = left2;
                }
                break;
            case "right":
                if (spriteNum == 1) {
                    image = right1;
                }
                if (spriteNum == 2) {
                    image = right2;
                };
                break;
        }
        g2.drawImage(image, screenX, screenY,null);
    }
}
