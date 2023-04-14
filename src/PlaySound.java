import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.io.File;

/**klasa obsługująca melodie w grze*/
public class PlaySound {
    /**metoda odpowiada za melodię podczas przegranej*/
    public static void playGameOver(){
        File sound = new File("src/game_over.wav");
        new Thread(){
            public void run(){
                try {
                    Clip clip = AudioSystem.getClip();
                    clip.open(AudioSystem.getAudioInputStream(sound));
                    clip.start();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }
    /**metoda odpowiada za melodię podczas zdobycia punktu*/
    public static void playScore(){
        File sound = new File("src/score.wav");

        new Thread(){
            public void run(){
                try {
                    Clip clip = AudioSystem.getClip();
                    clip.open(AudioSystem.getAudioInputStream(sound));
                    clip.start();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }
    /**metoda odpowiada za melodię podczas przejścia na nowy level*/
    public static void playNewLevel(){
        File sound = new File("src/level.wav");
        new Thread(){
            public void run(){
                try {
                    Clip clip = AudioSystem.getClip();
                    clip.open(AudioSystem.getAudioInputStream(sound));
                    clip.start();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }
}
