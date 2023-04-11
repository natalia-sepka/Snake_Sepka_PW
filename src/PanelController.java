import java.awt.event.KeyEvent;

/**klasa obsługujaca funkcjonalność klawiatury*/
public class PanelController {

    /**metoda obsługuje funkcjonalność klawiszy*/
    //coding keys functionality
    public static char controlPanel(KeyEvent e, char direction) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_LEFT -> {
                if (direction != 'R') {
                    return 'L';
                }
            }
            case KeyEvent.VK_RIGHT -> {
                if (direction != 'L') {
                    return 'R';
                }
            }
            case KeyEvent.VK_UP -> {
                if (direction != 'D') {
                    return 'U';
                }
            }
            case KeyEvent.VK_DOWN -> {
                if (direction != 'U') {
                    return 'D';
                }
            }
        }
        //every other pressed key moves snake down
        return 'D';
    }
}
