package org.ludumdare28;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import org.ludumdare28.ground.Ground;
import org.ludumdare28.ground.GroundImpl;
import org.ludumdare28.things.Player;
import org.ludumdare28.view.screens.WorldScreen;
import org.ludumdare28.world.World;
import org.ludumdare28.world.WorldImpl;

/**
 * Main class for the game.
 */
public class Main {

    private static final String TITLE = "Keep On Living";

    public static void main(String[] args) {

        // Define the window's size
        int width = 800, height = 480;

        // Texture file
        final String textureAtlasFile = "assets/texturepack.pack";

        // Create ground
        Ground ground = new GroundImpl(100, 100, 42);

        // Create player
        Player player = new Player("Player One", 10);

        // Create game world
        World world = new WorldImpl(ground, player);

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
