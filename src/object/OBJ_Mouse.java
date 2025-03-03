package object;

import finalProject2.com.GamePanel;

import javax.imageio.ImageIO;
import java.io.IOException;

public class OBJ_Mouse extends SuperObject {
    GamePanel gp;
    public OBJ_Mouse(GamePanel gp) {
        this.gp = gp;
        name = "Mouse";
        try {
            image = ImageIO.read(getClass().getResourceAsStream("/objects/mouse.png"));
            uTool.scaleImage(image, gp.tileSize, gp.tileSize);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
