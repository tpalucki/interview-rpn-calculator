package io.github.tpalucki.dolarapp;

import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

class MainTest {

    @Test
    void whenNameRequested_thenShouldReturnAppName() {
        Main main = new Main();

        var appName = main.getAppName();

        assertEquals("DolarApp", appName);

    }
}