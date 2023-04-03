import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.io.File;

public class MakeSound {
    String fileName;
    public MakeSound(String fileName) {
        this.fileName = fileName;
    }
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
