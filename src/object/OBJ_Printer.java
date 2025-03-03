package object;

import finalProject2.com.GamePanel;

import javax.imageio.ImageIO;
import java.io.IOException;

public class OBJ_Printer extends SuperObject {
    GamePanel gp;
    public OBJ_Printer(GamePanel gp) {
        this.gp = gp;
        name = "Printer";
        try {
            image = ImageIO.read(getClass().getResourceAsStream("/objects/printer.png"));
            uTool.scaleImage(image, gp.tileSize, gp.tileSize);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
