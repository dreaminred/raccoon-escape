package com.group24;
import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

public class Sound {

    private Clip soundClip;
    URL[] filePaths = new URL[20];

    public Sound() {
        // Add music files here
        // Background Music
        //try {
            //File file = new File(backgroundDirectory);
            //URL backgroundURL = file.toURI().toURL();
            URL backgroundURL = getClass().getClassLoader().getResource("Nagz-MyDogLivesOnTheMoon.wav");
            this.filePaths[0] = backgroundURL;
        //} catch (MalformedURLException e) {
        //    throw new RuntimeException(e);
        //}
    }

    /**
     *  Sets the file to play
     * @param trackNumber
     */
    public void setSoundClip(int trackNumber) {
        try {
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(this.filePaths[trackNumber]);
            this.soundClip = AudioSystem.getClip();
            this.soundClip.open(audioInputStream);
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Plays song
     */
    public void play() {
        this.soundClip.start();
    }

    /**
     * Stops song
     */
    public void stop() {
        this.soundClip.stop();
    }

    /**
     * Loops song until manually stopped
     */
    public void loop() {
        this.soundClip.loop(Clip.LOOP_CONTINUOUSLY);
    }

}