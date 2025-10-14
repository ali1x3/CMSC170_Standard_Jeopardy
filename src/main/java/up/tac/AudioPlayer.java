package up.tac;

import java.awt.event.PaintEvent;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineEvent;

public class AudioPlayer {
    record SoundData(AudioFormat format, byte[] pcm, int size) {}        

    private static AudioInputStream audioInputStream ;
    private static List<Clip> playingClips = Collections.synchronizedList(new ArrayList<>());
    private static Clip bgmClip;
    private static SoundData data;
    private static HashMap<String, SoundData> clipDataMap = new HashMap<>();
    private static HashMap<String, Clip> clipMap = new HashMap<>();
    private static float bgmVolume = 0.5f;
    private static float volume = 0.5f;

    public static void play(String filePath, boolean isStoppable) {
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
            
            adjustVolume(clip, volume);
            clip.start();
            if (isStoppable) {
                playingClips.add(clip);
            }
            clip.addLineListener(event -> {
                if (event.getType() == LineEvent.Type.STOP) {
                    clip.close();
                    if (playingClips.contains(clip)){
                        playingClips.remove(clip);
                    }
                    System.out.println("clip removed");
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
         
            adjustVolume(bgmClip, bgmVolume);
            bgmClip.start();
            bgmClip.loop(Clip.LOOP_CONTINUOUSLY);

        } catch (Exception ex) {
            System.out.println("Error with playing sound.");
            ex.printStackTrace();
        }
    }
    public static void stop() {
        synchronized (playingClips) {
            for (Clip c : playingClips) {
                c.stop();
            }
            playingClips.clear();
        }
    }

    // these functions take a float between 0 and 1
    // 0 being 0% and 1.0 being 100%
    public static void setVolume(float newVal) {
        volume = newVal;
        volume = Math.max(0f, Math.min(volume, 1f));
    }
    
    public static void setBgmVolume(float newVal) {
        bgmVolume = newVal;
        volume = Math.max(0f, Math.min(volume, 1f));
    }

    // DO not touch this only set the volume with the 2 methods above
    private static void adjustVolume(Clip clip, float volume) {
        if (clip.isControlSupported(FloatControl.Type.MASTER_GAIN)) {
            FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);

            float min = gainControl.getMinimum();
            float max = gainControl.getMaximum();

            float dB;
            if (volume == 0f) {
                dB = min;
            } else {
                dB = (float)(Math.log10(volume) * 20.0);
                dB = Math.max(min, Math.min(dB, max));
            }

            gainControl.setValue(dB);
            System.out.println(dB);
        } else {
            System.out.println("MASTER_GAIN not supported on this clip.");
        }
    }
}
