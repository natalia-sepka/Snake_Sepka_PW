import java.awt.event.KeyEvent;

public class PanelController {

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
        return 'Z'; //returns dummy char
    }
}
