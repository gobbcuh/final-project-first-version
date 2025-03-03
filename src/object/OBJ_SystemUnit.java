package object;

import finalProject2.com.GamePanel;

import javax.imageio.ImageIO;
import java.io.IOException;

public class OBJ_SystemUnit extends SuperObject {
    GamePanel gp;
    public OBJ_SystemUnit(GamePanel gp) {
        this.gp = gp;
        name = "System Unit";
        try {
            image = ImageIO.read(getClass().getResourceAsStream("/objects/system-unit.png"));
            uTool.scaleImage(image, gp.tileSize, gp.tileSize);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
