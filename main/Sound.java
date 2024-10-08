package main;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.net.SocketImpl;
import java.net.URL;

public class Sound {

    Clip clip;
    URL soundURL[] = new URL[30];

    public Sound() {
        soundURL[0] = getClass().getResource("/res/sound/GymAdventure.wav");
        soundURL[1] = getClass().getResource("/res/sound/coin.wav");
        soundURL[2] = getClass().getResource("/res/sound/powerup.wav");
        soundURL[3] = getClass().getResource("/res/sound/unlock.wav");
        soundURL[4] = getClass().getResource("/res/sound/fanfare.wav");
    }

    public void setFile(int index) {

        try {
            AudioInputStream ais = AudioSystem.getAudioInputStream(soundURL[index]);
            clip = AudioSystem.getClip();
            clip.open(ais);

        } catch (Exception e) {
        }

    }
    public void play() {
        clip.start();
    }
    public void loop() {
        clip.loop(Clip.LOOP_CONTINUOUSLY);
    }
    public void stop() {
        clip.stop();
    }
}
