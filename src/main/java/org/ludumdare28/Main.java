package org.ludumdare28;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import org.ludumdare28.ground.Ground;
import org.ludumdare28.ground.GroundImpl;
import org.ludumdare28.things.Player;
import org.ludumdare28.world.World;
import org.ludumdare28.world.WorldImpl;

/**
 * Main class for the game.
 */
public class Main {

    public static void main(String[] args) {

        // Create ground
        Ground ground = new GroundImpl(100, 100);

        // Create player
        Player player = new Player("Player One", 10);

        // Create game world
        World world = new WorldImpl(ground, player);



        // Create the Game that will receive the application events
        Game listener = new Game(world);

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
