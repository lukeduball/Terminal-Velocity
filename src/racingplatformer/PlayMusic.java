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
    public static void music(String x, Game gameInstance) {
        if (gameInstance.getAreSoundEffectsActivated()) {
            try {
                AudioInputStream inputStream = AudioSystem.getAudioInputStream(new File(x));
                Clip clip = AudioSystem.getClip();
                clip.open(inputStream);
                clip.loop(Clip.LOOP_CONTINUOUSLY);
            } catch (LineUnavailableException | UnsupportedAudioFileException | IOException e) {
            }
        }
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
