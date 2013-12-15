package org.ludumdare28;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import org.ludumdare28.ground.Ground;
import org.ludumdare28.ground.GroundImpl;
import org.ludumdare28.things.Player;
import org.ludumdare28.things.Stone;
import org.ludumdare28.view.screens.WorldScreen;
import org.ludumdare28.world.World;
import org.ludumdare28.world.WorldImpl;

import java.util.Random;

/**
 * Main class for the game.
 */
public class Main {

    private static final String TITLE = "Keep On Living";
    private static final int WORLD_SIZE_X = 5;
    private static final int WORLD_SIZE_Y = 5;

    public static void main(String[] args) {

        // Define the window's size
        int width = 800, height = 480;

        // Texture file
        final String textureAtlasFile = "assets/texturepack.pack";

        // Create ground
        Ground ground = new GroundImpl(WORLD_SIZE_X, WORLD_SIZE_Y, 42);

        // Create player
        Player player = new Player("Player One", 10);

        // Create game world
        World world = new WorldImpl(ground, player);

        // Add things
        Random random = new Random(42);
        for (int i = 0; i < 100; i++) {
            world.addThing(new Stone(random),
                           random.nextDouble() * WORLD_SIZE_X,
                           random.nextDouble() * WORLD_SIZE_Y);
        }


        // Create view
        final WorldScreen worldScreen = new WorldScreen(world);

        // Create the Game that will receive the application events
        Game game = new Game(width, height, textureAtlasFile);
        game.setCurrentScreen(worldScreen);

        // Whether to use OpenGL ES 2.0
        boolean useOpenGLES2 = false;

        // Start the game
        new LwjglApplication(game, TITLE, width, height, useOpenGLES2 );

    }
}
