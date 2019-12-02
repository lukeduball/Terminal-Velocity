/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package racingplatformer;
import java.io.*;
import javax.sound.sampled.*;
public class PlayMusic 
{
    public static Clip setupClip(Clip clip){
        try {
            AudioInputStream inputStream = AudioSystem.getAudioInputStream(new File("src/resources/music/TVRaceTheme1.wav"));
            clip = AudioSystem.getClip();
            clip.open(inputStream);
            clip.loop(Clip.LOOP_CONTINUOUSLY);
        } catch (LineUnavailableException | UnsupportedAudioFileException | IOException e) {
        }
        return clip;
    }

    public static void changeMusic(Clip clip, Game gameInstance) {
        if(gameInstance.getIsMusicActivated()){
            clip.setFramePosition(0);
            clip.start();
            clip.loop(Clip.LOOP_CONTINUOUSLY);
        }
        else {
            clip.stop();
        }
        return;
    }

    public static void soundFX(String x, Game gameInstance) {
        if (gameInstance.getAreSoundEffectsActivated()) {
            try {
                AudioInputStream inputStream = AudioSystem.getAudioInputStream(new File(x));
                Clip clip = AudioSystem.getClip();
                clip.open(inputStream);
                clip.start();
            } catch (LineUnavailableException | UnsupportedAudioFileException | IOException e) {
            }
        }
    }
} 
