import javax.swing.*;
import java.awt.*;

/**klasa tworzy pusty panel*/
public class BlankPanel extends JPanel {
    static final int MIN_DIMENSION = 10;
    BlankPanel(int width, int height) {
        this.setPreferredSize(new Dimension(width, height));
        this.setBackground(Color.DARK_GRAY);
    }
}
