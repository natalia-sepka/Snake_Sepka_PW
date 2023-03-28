import javax.swing.*;
import java.awt.*;

public class InformationPanel extends JPanel {

    static final int WIDTH = 200;
    static final int HEIGHT = 500;

    InformationPanel() {
        this.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        this.setBackground(Color.DARK_GRAY);
    }

    public void updateFoodEaten(int foodEaten) {
        System.out.println("Received food: " + foodEaten);
    }
}
