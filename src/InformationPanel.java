import javax.swing.*;
import java.awt.*;

/**klasa ma za zadanie utworzyć panel boczny*/
public class InformationPanel extends JPanel {
    /**szerokość panelu*/
    static final int WIDTH = 200;
    /**wysokość panelu*/
    static final int HEIGHT = 500;
    /**punkty gracza*/
    int score;
    /**poziom gracza*/
    int level = 1;

    InformationPanel() {
        this.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        this.setBackground(Color.DARK_GRAY);
    }
    /**metoda ma za zadanie uaktualniać level i punkty*/
    public void updateValues(int score, int level) {
        this.score = score;
        this.level = level;
        repaint();
    }
    /**metoda umożliwia rysowanie*/
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        draw(g);
    }
    /**metoda rysuje wszystkie informacje na panelu*/
    public void draw(Graphics g) {
        //drawing score on panel
        g.setColor(Color.white);
        g.setFont(new Font("Ink Free", Font.BOLD, 25));
        FontMetrics metrics1 = getFontMetrics(g.getFont());
        g.drawString("Score: " + score, (WIDTH - metrics1.stringWidth("Score: " + score))/2,
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
        g.drawString("KEYS:", (WIDTH - metrics3.stringWidth("KEYS:"))/2,
                HEIGHT - 60);
        g.setColor(Color.white);
        g.setFont(new Font("Ink Free", Font.BOLD, 15));
        FontMetrics metrics4 = getFontMetrics(g.getFont());
        g.drawString("<- -> = movement", (WIDTH - metrics4.stringWidth("<- -> = movement"))/2,
                HEIGHT - 30);
        g.setColor(Color.white);
        g.setFont(new Font("Ink Free", Font.BOLD, 15));
        FontMetrics metrics5 = getFontMetrics(g.getFont());
        g.drawString("mouse = play/pause", (WIDTH - metrics5.stringWidth("mouse = play/pause"))/2,
                HEIGHT - 10);
    }
}
