import javax.swing.*;
import java.awt.*;

/**klasa ma za zadanie utworzyć okno i wszystkie panele gry*/
public class Window extends JFrame implements SendResultsCallback
 {
    InformationPanel informationPanel;
    Window(){
        int globalWidth = GamePanel.GP_WIDTH + InformationPanel.WIDTH;

        this.setTitle("Snake Game - NATALIA SĘPKA");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setSize(globalWidth, GamePanel.GP_HEIGHT);

        informationPanel = new InformationPanel();
        add(informationPanel, BorderLayout.EAST);
        add(new GamePanel(this), BorderLayout.CENTER);
        add(new BlankPanel(BlankPanel.MIN_DIMENSION, GamePanel.GP_HEIGHT), BorderLayout.WEST);
        add(new BlankPanel(globalWidth, BlankPanel.MIN_DIMENSION), BorderLayout.SOUTH);
        add(new BlankPanel(globalWidth, BlankPanel.MIN_DIMENSION), BorderLayout.NORTH);

        this.pack();
        this.setVisible(true);
        this.setLocationRelativeTo(null);
    }

    @Override
    public void sendResults(int score, int level) {
        informationPanel.updateValues(score, level);
    }
}
