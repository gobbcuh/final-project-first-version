package object;

import finalProject2.com.GamePanel;

import javax.imageio.ImageIO;
import java.io.IOException;

public class OBJ_Chess extends SuperObject {
    GamePanel gp;
    public OBJ_Chess(GamePanel gp) {
        this.gp = gp;
        name = "Chess";
        try {
            image = ImageIO.read(getClass().getResourceAsStream("/objects/chest.png"));
            uTool.scaleImage(image, gp.tileSize, gp.tileSize);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
