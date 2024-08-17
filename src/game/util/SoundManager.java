package game.util;

import javax.sound.sampled.*;

import java.io.File;

/**
 * The SoundManager class is responsible for managing sound effects in the game.
 */
public class SoundManager {
    private Clip clip; // local sound
    private static float volume = 100.0f; // volume in decibels percent

    /**
     * Constructor for the SoundManager class.
     * 
     * @param clip The sound clip to be managed.
     */
    public SoundManager(Clip clip) {
        this.clip = clip;
    }

    /**
     * Stops the currently playing sound clip.
     */
    public void stop() {
        this.clip.stop();
    }

    /**
     * Plays the sound clip.
     * 
     * @param loop Whether or not to loop the sound clip.
     */
    public void play(boolean loop) {
        setVolumeToSound(this.clip);
        this.clip.setFramePosition(0);
        this.clip.start();
        this.clip.loop(loop ? Clip.LOOP_CONTINUOUSLY : 0);
    }

    /**
     * Sets the sound clip to be managed.
     * 
     * @param sound The sound clip to be managed.
     */
    public void setSound(Clip sound) {
        this.clip = sound;
    }

    /**
     * Plays a sound clip.
     * 
     * @param clip The sound clip to be played.
     */
    public static void play(Clip clip) {
        setVolumeToSound(clip);
        clip.setFramePosition(0);
        clip.start();
    }

    /**
     * Sets the volume of the sound clip.
     * 
     * @param clip The sound clip to set the volume for.
     */
    private static void setVolumeToSound(Clip clip) {
        FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
        float dB = (float) (Math.log(volume / 100.0) / Math.log(10.0) * 20.0);
        gainControl.setValue(dB);
    }

    /**
     * Sets the volume of the sound clip.
     * 
     * @param volume The volume to set the sound clip to.
     */
    public static void setVolumeNumber(float volume) {
        SoundManager.volume = volume;
    }

    /**
     * Gets the volume of the sound clip.
     * 
     * @return The volume of the sound clip.
     */
    public static float getVolumeNumber() {
        return SoundManager.volume;
    }

    /**
     * Gets a sound clip from a file.
     * 
     * @param filename The name of the file to get the sound clip from.
     * @return The sound clip from the file.
     */
    public static Clip getClip(String filename) {
        Clip clip = null;
        try {
            clip = AudioSystem.getClip();
            AudioInputStream sample = AudioSystem.getAudioInputStream(new File(filename));
            clip.open(sample);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return clip;
    }
}