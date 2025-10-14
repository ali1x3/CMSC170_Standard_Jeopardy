package up.tac;

import java.applet.AudioClip;
import java.util.ArrayList;
import java.util.HashMap;

import javax.print.attribute.standard.Media;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class AudioPlayer {
        
    private static AudioInputStream audioInputStream ;
    private static ArrayList<Clip> clips = new ArrayList<>();
    private static Clip clip;
    private static Clip bgmClip;
    private static HashMap<String, Clip> clipMap = new HashMap<>();

    public static void play(String filePath, boolean isStoppable) {
        try{
            clip = clipMap.computeIfAbsent(filePath, p -> {
                try {
                audioInputStream = AudioSystem.getAudioInputStream(AudioPlayer.class.getResource(filePath));
                clip = AudioSystem.getClip();
                clip.open(audioInputStream);
                return clip;
                }
                catch (Exception e) {
                    return null;
                }
            });
            clip.setFramePosition(0);
            clip.start();

            if (isStoppable) {
                clips.add(clip);
            }

        } catch (Exception ex) {
            System.out.println("Error with playing sound.");
            ex.printStackTrace();
        }
    }

    public static void playBGM(String filePath) {
        try{
            if(bgmClip != null && bgmClip.isRunning()) {
                bgmClip.stop();
            }

            audioInputStream = AudioSystem.getAudioInputStream(AudioPlayer.class.getResource(filePath));
            bgmClip = AudioSystem.getClip();
            bgmClip.open(audioInputStream);
            bgmClip.start();
            bgmClip.loop(Clip.LOOP_CONTINUOUSLY);

        } catch (Exception ex) {
            System.out.println("Error with playing sound.");
            ex.printStackTrace();
        }
    }

    public static void stop() {
        for(Clip c : clips) {
            c.stop();
        }
        clips.clear();
    }

}
