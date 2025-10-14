package up.tac;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineEvent;

public class AudioPlayer {
    record SoundData(AudioFormat format, byte[] pcm, int size) {}        

    private static AudioInputStream audioInputStream ;
    private static ArrayList<Clip> clips = new ArrayList<>();
    private static Clip bgmClip;
    private static SoundData data;
    private static HashMap<String, SoundData> clipDataMap = new HashMap<>();

    public static void play(String filePath) {
        try{
            data = clipDataMap.computeIfAbsent(filePath, p -> {
                try {
                    URL url = AudioPlayer.class.getResource(p);
                    audioInputStream = AudioSystem.getAudioInputStream(loadStream(url.openStream()));
                    AudioFormat format = audioInputStream.getFormat();
                    int size = (int) (format.getFrameSize() * audioInputStream.getFrameLength());
                    byte[] audio = new byte[size];
                    audioInputStream.read(audio, 0, size);
                    
                    return new SoundData(format, audio, size);
                }
                catch (Exception e) {
                    return null;
                }
            });
            Clip clip = AudioSystem.getClip();
            clip.open(data.format, data.pcm(), 0, data.size());

            clip.setFramePosition(0);
            clip.start();
            System.out.println("clone running");
            clip.addLineListener(event -> {
                if (event.getType() == LineEvent.Type.STOP) {
                    clip.close();
                    System.out.println("clip closed");
                }
            });

        } catch (Exception ex) {
            System.out.println("Error with playing sound.");
            ex.printStackTrace();
        }
    }

    private static ByteArrayInputStream loadStream(InputStream inputstream)
              throws IOException
      {
            ByteArrayOutputStream bytearrayoutputstream = new ByteArrayOutputStream();
            byte data[] = new byte[1024];
            for(int i = inputstream.read(data); i != -1; i = inputstream.read(data))
                  bytearrayoutputstream.write(data, 0, i);

            inputstream.close();
            bytearrayoutputstream.close();
            data = bytearrayoutputstream.toByteArray();
            return new ByteArrayInputStream(data);
    }

    public static void playBGM(String filePath) {
        try{
            if(bgmClip != null && bgmClip.isRunning()) {
                bgmClip.stop();
                bgmClip.close();
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

}
