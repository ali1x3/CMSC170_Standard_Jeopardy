package up.tac;

import java.applet.AudioClip;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

import javax.print.attribute.standard.Media;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineEvent;

public class AudioPlayer {
    record SoundData(AudioFormat format, byte[] pcm, int size) {}        

    private static AudioInputStream audioInputStream ;
    private static ArrayList<Clip> clips = new ArrayList<>();
    private static Clip clip;
    private static Clip bgmClip;
    private static HashMap<String, Clip> clipMap = new HashMap<>();
    private static HashMap<String, SoundData> clipData = new HashMap<>();

    public static void play(String filePath, boolean isStoppable) {
        try{
            clip = clipMap.computeIfAbsent(filePath, p -> {
                try {
                    URL url = AudioPlayer.class.getResource(p);
                    audioInputStream = AudioSystem.getAudioInputStream(loadStream(url.openStream()));
                    clip = AudioSystem.getClip();
                    //clip.open(audioInputStream);
                    AudioFormat format = audioInputStream.getFormat();
                    int size = (int) (format.getFrameSize() * audioInputStream.getFrameLength());
                    byte[] audio = new byte[size];
                    DataLine.Info info = new DataLine.Info(Clip.class, format, size);
                    audioInputStream.read(audio, 0, size);

                    clipData.put(filePath,
                        new SoundData(format, audio, size)
                    );
                    return clip;
                }
                catch (Exception e) {
                    return null;
                }
            });
            if (true) {
                System.out.println("the clip got rerun");
                runClone(filePath);
                return;
            }
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

    private static void runClone(String filePath) {
        try {
            SoundData data = clipData.get(filePath);
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
        }
        catch (Exception e) {
            e.printStackTrace();
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
