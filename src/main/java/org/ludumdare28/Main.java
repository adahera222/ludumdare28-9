package org.ludumdare28;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.badlogic.gdx.graphics.Color;
import org.flowutils.Maths;
import org.ludumdare28.ground.Ground;
import org.ludumdare28.ground.GroundImpl;
import org.ludumdare28.things.bush.Bush;
import org.ludumdare28.things.ImageAppearance;
import org.ludumdare28.things.player.Player;
import org.ludumdare28.things.misc.Stone;
import org.ludumdare28.things.berry.*;
import org.ludumdare28.things.snake.Snake;
import org.ludumdare28.things.spring.Spring;
import org.ludumdare28.view.screens.WorldScreen;
import org.ludumdare28.world.World;
import org.ludumdare28.world.WorldImpl;

import java.util.Random;

/**
 * Main class for the game.
 */
public class Main {

    private static final String TITLE = "Keep On Living";
    private static final int WORLD_SIZE_X = 50;
    private static final int WORLD_SIZE_Y = 50;
    private static final double SPRING_X = WORLD_SIZE_X/4;
    private static final double SPRING_Y = WORLD_SIZE_Y/2;

    public static void main(String[] args) {

        // Define the window's size
        int width = 600, height = 480;

        // Texture file
        final String textureAtlasFile = "assets/texturepack.pack";

        // Create player
        Player player = new Player("Player One", 10);
        player.setAppearance(new ImageAppearance(Pic.STONE, 4, 1, 5));
        player.setPos(5, 0);

        // Create ground
        World world = createWorld(player);

        // Create view
        final WorldScreen worldScreen = new WorldScreen(world);
        worldScreen.setFocusedThing(player);

        // Create the Game that will receive the application events
        Game game = new Game(width, height, textureAtlasFile);
        game.setCurrentScreen(worldScreen);

        // Configure libgdx and start the game
        final LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
        config.width = width;
        config.height = height;
        config.title = TITLE;
        config.useGL20 = false;
        config.vSyncEnabled = true;
        new LwjglApplication(game, config);

    }

    private static World createWorld(Player player) {
        Random random = new Random();

        // Create game world
        Ground ground = new GroundImpl(WORLD_SIZE_X, WORLD_SIZE_Y, 42);
        World world = new WorldImpl(ground, player);

        // Add things

        // Stones
        for (int i = 0; i < 500; i++) {
            world.addThing(new Stone(random),
                           random.nextDouble() * WORLD_SIZE_X,
                           random.nextDouble() * WORLD_SIZE_Y);
        }

        // Bushes
        addBushes(world, 100, FoodBerry.class, random);
        addBushes(world, 50, HealthBerry.class, random);
        addBushes(world, 50, CaffeineBerry.class, random);
        addBushes(world, 50, PoisonBerry.class, random);
        addBushes(world, 50, HungerBerry.class, random);
        addBushes(world, 100, RandomBerry.class, random);
        addBushes(world, 50, TirednessBerry.class, random);


        //Spring
        world.addThing(new Spring(),SPRING_X,SPRING_Y);

        //Snake
        addSnakes(world, 18, random);


        return world;
    }

    private static void addBushes(World world, final int number, final Class<? extends BaseBerry> type, Random random) {
        final int appearanceSeed = random.nextInt();

        Color berryColor = randomBrightColor(random);
        Color bushColor = randomTintedColor(random, 0.7, -0.15);

        for (int i = 0; i < number; i++) {
            world.addThing(new Bush(type, berryColor, bushColor, appearanceSeed),
                           random.nextDouble() * WORLD_SIZE_X,
                           random.nextDouble() * WORLD_SIZE_Y);
        }
    }

    private static void addSnakes(World world, final int numberOfSnakes, Random random) {
        final int appearanceSeed = random.nextInt();

        //TODO: snakes whit different color
        Color snakeColor = randomBrightColor(random);


        for (int i = 0; i < numberOfSnakes; i++) {
            world.addThing(new Snake(SPRING_X,SPRING_Y),
                    random.nextDouble() * WORLD_SIZE_X,
                    random.nextDouble() * WORLD_SIZE_Y);
        }
    }

    private static Color randomBrightColor(Random random) {
        float r = (float) Maths.clamp0To1(random.nextInt(2) + random.nextGaussian() * 0.15 + 0.15);
        float g = (float) Maths.clamp0To1(random.nextInt(2) + random.nextGaussian() * 0.2 + 0.15);
        float b = (float) Maths.clamp0To1(random.nextInt(2) + random.nextGaussian() * 0.15 + 0.15);

        if (r + g + b < 0.8f) r = 1.0f;
        if (r + g + b > 2.5f) g = 0f;

        return new Color(r, g, b, 1);
    }

    private static Color randomTintedColor(Random random, double randomness, double offset) {
        float r = (float) (1 + random.nextGaussian() * randomness + offset);
        float g = (float) (1 + random.nextGaussian() * randomness + offset);
        float b = (float) (1 + random.nextGaussian() * randomness + offset);
        return new Color(r, g, b, 1);
    }
}
