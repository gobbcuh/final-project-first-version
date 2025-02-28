package object;

import javax.imageio.ImageIO;
import java.io.IOException;

public class OBJ_Chess extends SuperObject {
    public OBJ_Chess() {
        name = "Chess";
        try {
            image = ImageIO.read(getClass().getResourceAsStream("/objects/chess.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
