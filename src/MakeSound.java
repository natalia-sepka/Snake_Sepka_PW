import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.io.File;

public class MakeSound {
    String file;
    public MakeSound(String file) {
        this.file = file;
    }
    public static void makeSound(String file){
        File sound = new File(file);

        try{
            Clip clip = AudioSystem.getClip();
            clip.open(AudioSystem.getAudioInputStream(sound));
            clip.start();
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}
