package com.group24;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.sound.sampled.AudioSystem;
import java.net.MalformedURLException;
import java.net.URL;

import static org.junit.jupiter.api.Assertions.*;



class SoundTest {

    Sound sound;

    @BeforeEach
    void setUp() {
        sound = new Sound();
    }

    @AfterEach
    void tearDown() {
        sound = null;
    }

    @Test
    void test1() {

        this.sound.setSoundClip(0);
        this.sound.play();
        this.sound.loop();
        this.sound.stop();
    }

    
    @Test
    public void test2() throws MalformedURLException {
        // Use an invalid URL to trigger an exception
        URL invalidUrl = new URL("file:///invalid/path/to/audio.wav");
        sound.filePaths[1] = invalidUrl;

        // Verify that RuntimeException is thrown
        assertThrows(RuntimeException.class, () -> sound.setSoundClip(1));
    }

}