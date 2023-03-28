import javax.swing.*;
import java.awt.*;

public class Window extends JFrame implements SendResultsCallback{
    InformationPanel informationPanel;
    Window(){
        int globalWidth = GamePanel.WIDTH + InformationPanel.WIDTH;

        this.setTitle("Snake Game - NATALIA SĘPKA");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.pack();
        this.setVisible(true);
        this.setLocationRelativeTo(null);
        this.setSize(globalWidth, GamePanel.HEIGHT);

        informationPanel = new InformationPanel();

        add(informationPanel, BorderLayout.EAST);
        add(new GamePanel(this), BorderLayout.CENTER);
        add(new BlankPanel(BlankPanel.MIN_DIMENSION, GamePanel.HEIGHT), BorderLayout.WEST);
        add(new BlankPanel(globalWidth, BlankPanel.MIN_DIMENSION), BorderLayout.SOUTH);
        add(new BlankPanel(globalWidth, BlankPanel.MIN_DIMENSION), BorderLayout.NORTH);
    }

    @Override
    public void sendFoodEaten(int foodEaten) {
        informationPanel.updateFoodEaten(foodEaten);
    }
}
