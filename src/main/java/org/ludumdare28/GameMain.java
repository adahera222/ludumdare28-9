package org.ludumdare28;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;

/**
 * Main class for the game.
 */
public class GameMain {

    public static void main(String[] args) {

        // Create the Game that will receive the application events
        Game listener = new Game();

        // Define the window's title
        String title = "KeepOnLiving";

        // Define the window's size
        int width = 800, height = 480;

        // Whether to use OpenGL ES 2.0
        boolean useOpenGLES2 = false;

        // Start the game
        new LwjglApplication( listener, title, width, height, useOpenGLES2 );

    }
}
