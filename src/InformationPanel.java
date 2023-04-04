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
        //drawing score on panel
        g.setColor(Color.white);
        g.setFont(new Font("Ink Free", Font.BOLD, 25));
        FontMetrics metrics = getFontMetrics(g.getFont());
        g.drawString("Score: " + foodEaten, (WIDTH - metrics.stringWidth("Score: " + foodEaten))/2,
                HEIGHT/3);
        //drawing level on panel
        g.setColor(Color.white);
        g.setFont(new Font("Ink Free", Font.BOLD, 25));
        FontMetrics metrics2 = getFontMetrics(g.getFont());
        g.drawString("Level: " + level, (WIDTH - metrics2.stringWidth("Level: " + level))/2,
                HEIGHT/6);
        //drawing keys legend on panel
        g.setColor(Color.white);
        g.setFont(new Font("Ink Free", Font.BOLD, 15));
        FontMetrics metrics3 = getFontMetrics(g.getFont());
        g.drawString("<- -> movement", (WIDTH - metrics3.stringWidth("<- -> movement"))/2,
                HEIGHT);
    }
}
