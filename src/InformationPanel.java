import javax.swing.*;
import java.awt.*;

public class InformationPanel extends JPanel {

    static final int WIDTH = 200;
    static final int HEIGHT = 500;
    int foodEaten;
    int level = 1;

    InformationPanel() {
        this.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        this.setBackground(Color.DARK_GRAY);
    }

    public void updateValues(int foodEaten, int level) {
        //System.out.println("Received food: " + foodEaten);
        this.foodEaten = foodEaten;
        this.level = level;
        repaint();
    }
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        draw(g);
    }

    public void draw(Graphics g) {
        g.setColor(Color.white);
        g.setFont(new Font("Ink Free", Font.BOLD, 25));
        FontMetrics metrics = getFontMetrics(g.getFont());
        g.drawString("Punkty: " + foodEaten, (WIDTH - metrics.stringWidth("Punkty: " + foodEaten))/2,
                HEIGHT/2);

        g.setColor(Color.white);
        g.setFont(new Font("Ink Free", Font.BOLD, 25));
        FontMetrics metrics2 = getFontMetrics(g.getFont());
        g.drawString("Poziom: " + level, (WIDTH - metrics2.stringWidth("Poziom: " + level))/2,
                HEIGHT/3);
    }
}
