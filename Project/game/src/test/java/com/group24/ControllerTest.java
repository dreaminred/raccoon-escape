package com.group24;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.event.KeyEvent;

import static org.junit.jupiter.api.Assertions.*;

class ControllerTest {

    Controller controller;

    @BeforeEach
    void setUp() {controller = new Controller();}

    @AfterEach
    void tearDown() {controller = null;}

    @Test
    void keyPressed() {
        // Press W and check if input is 1
        controller.keyPressed(new KeyEvent(new java.awt.Component() {}, 0, 0, 0, KeyEvent.VK_W, 'W'));
        assertEquals(1, controller.input);

        // Press D and check if input is 2
        controller.keyPressed(new KeyEvent(new java.awt.Component() {}, 0, 0, 0, KeyEvent.VK_D, 'D'));
        assertEquals(2, controller.input);

        // Press S and check if input is 3
        controller.keyPressed(new KeyEvent(new java.awt.Component() {}, 0, 0, 0, KeyEvent.VK_S, 'S'));
        assertEquals(3, controller.input);

        // Press A and check if input is 4
        controller.keyPressed(new KeyEvent(new java.awt.Component() {}, 0, 0, 0, KeyEvent.VK_A, 'A'));
        assertEquals(4, controller.input);
    }
}