package org.ludumdare28;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.badlogic.gdx.graphics.Color;
import org.flowutils.Maths;
import org.flowutils.SimplexGradientNoise;
import org.ludumdare28.ground.Ground;
import org.ludumdare28.ground.GroundCell;
import org.ludumdare28.ground.GroundImpl;
import org.ludumdare28.ground.TerrainType;
import org.ludumdare28.things.bush.Bush;
import org.ludumdare28.things.ImageAppearance;
import org.ludumdare28.things.player.Player;
import org.ludumdare28.things.misc.Stone;
import org.ludumdare28.things.berry.*;
import org.ludumdare28.things.snake.Snake;
import org.ludumdare28.things.spring.Spring;
import org.ludumdare28.utils.StringUtils;
import org.ludumdare28.view.screens.WorldScreen;
import org.ludumdare28.world.World;
import org.ludumdare28.world.WorldImpl;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.TreeMap;

/**
 * Main class for the game.
 */
public class Main {

    private static final String TITLE = "Keep On Living";
    private static final int WORLD_SIZE_X = 50;
    private static final int WORLD_SIZE_Y = 50;
    private static final double SPRING_X = WORLD_SIZE_X/4;
    private static final double SPRING_Y = WORLD_SIZE_Y/2;

    private static List<String> randomBushPrefix = Arrays.asList("Cloud", "Fire", "Sea", "Desert", "YumYum ", "Strange", "Jinga", "Zing", "Hell", "Bing", "Zugzug", "Gor", "Doge", "Rash", "Mort", "Devil", "Death", "Wonder", "Burr", "Smoke", "Angel", "Heavens", "Island");
    private static List<String> randomBushPostfix = Arrays.asList("berry", "berry", "berry", "berry", "bush", "bush", "fruit", "fruit", "grape", "grape", "thorn", "rose", "weed", "grass", "plant", "plant", "leaf");

    public static void main(String[] args) {

        // Define the window's size
        int width = 800, height = 600;

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
        Ground ground = createGround(random);

        World world = new WorldImpl(ground, player);

        // Add things

        // Stones
        for (int i = 0; i < 500; i++) {
            world.addThing(new Stone(random),
                           random.nextDouble() * WORLD_SIZE_X,
                           random.nextDouble() * WORLD_SIZE_Y);
        }

        // Bushes
        addBushes(world, 100, FoodBerry.class, random, TerrainType.GRASS, TerrainType.JUNGLE, TerrainType.SAND);
        addBushes(world, 50, HealthBerry.class, random, TerrainType.GRASS, TerrainType.JUNGLE);
        addBushes(world, 50, CaffeineBerry.class, random, TerrainType.SAND);
        addBushes(world, 40, PoisonBerry.class, random, TerrainType.GRASS, TerrainType.JUNGLE);
        addBushes(world, 50, PoisonBerry.class, random, TerrainType.ROCKY);
        addBushes(world, 30, PoisonBerry.class, random, TerrainType.GRASS, TerrainType.JUNGLE);
        addBushes(world, 50, HungerBerry.class, random, TerrainType.GRASS, TerrainType.JUNGLE, TerrainType.SAND);
        addBushes(world, 100, RandomBerry.class, random, TerrainType.GRASS, TerrainType.JUNGLE);
        addBushes(world, 100, RandomBerry.class, random, TerrainType.SAND);
        addBushes(world, 50, RandomBerry.class, random, TerrainType.ROCKY);
        addBushes(world, 50, TirednessBerry.class, random, TerrainType.GRASS, TerrainType.JUNGLE);


        //Spring
        world.addThing(new Spring(),SPRING_X,SPRING_Y);

        //Snake
        addSnakes(world, 16, random);


        return world;
    }

    private static Ground createGround(Random random) {
        final GroundImpl ground = new GroundImpl(WORLD_SIZE_X, WORLD_SIZE_Y, 42);

        // Setup terrain altitude lookup
        TreeMap<Double, TerrainType> terrainLookup = new TreeMap<Double, TerrainType>();
        terrainLookup.put(-100.0, TerrainType.DEEP_WATER);
        terrainLookup.put(-20.0, TerrainType.WATER);
        terrainLookup.put(-10.0, TerrainType.WATER_SHORE);
        terrainLookup.put(-1.0, TerrainType.WET_SAND);
        terrainLookup.put(3.0, TerrainType.SAND);
        terrainLookup.put(20.0, TerrainType.GRASS);
        terrainLookup.put(40.0, TerrainType.JUNGLE);
        terrainLookup.put(70.0, TerrainType.ROCKY);


        double mountainX = WORLD_SIZE_X * 0.7;
        double mountainY = WORLD_SIZE_Y * 0.7;
        double mountainRadius = (WORLD_SIZE_X + WORLD_SIZE_Y) / 2;
        double mountainTopAltitude = 100;
        double mountainRimAltitude = -20;
        double roughnessAtTip = 30;
        double roughnessAtRim = 6;

        for (int y = 0; y < WORLD_SIZE_Y; y++) {
            for (int x = 0; x < WORLD_SIZE_X; x++) {
                final GroundCell cell = ground.getCell(x, y);
                final double topDistance = Maths.distance(x, y, mountainX, mountainY);
                double altitude = Maths.map(topDistance, 0, mountainRadius, mountainTopAltitude, mountainRimAltitude);

                // Add some roughness
                double roughness = Maths.map(topDistance, 0, mountainRadius, roughnessAtTip, roughnessAtRim);
                double variance = SimplexGradientNoise.sdnoise2(x, y) * roughness;
                altitude += variance;

                cell.setAltitude(altitude);

                // Lookup terrain
                final TerrainType terrain = terrainLookup.floorEntry(altitude).getValue();
                cell.setTerrainType(terrain);
            }
        }


        return ground;
    }

    private static void addBushes(World world, final int number, final Class<? extends BaseBerry> type, Random random, TerrainType ... acceptableTerrains) {
        final int appearanceSeed = random.nextInt();

        Color berryColor = randomBrightColor(random);
        Color bushColor = randomTintedColor(random, 0.7, -0.15);

        String name = StringUtils.createRandomName(random, randomBushPrefix, randomBushPostfix);

        for (int i = 0; i < number; i++) {
            final double x = random.nextDouble() * WORLD_SIZE_X;
            final double y = random.nextDouble() * WORLD_SIZE_Y;

            // Only add in acceptable terrain types
            final TerrainType terrainType = world.getGround().getCell(x, y).getTerrainType();
            if (contains(acceptableTerrains, terrainType)) {
                world.addThing(new Bush(type, berryColor, bushColor, appearanceSeed, name), x, y);
            }
        }
    }

    private static boolean contains(TerrainType[] acceptableTerrains, final TerrainType terrainType) {
        for (TerrainType acceptableTerrain : acceptableTerrains) {
            if (terrainType == acceptableTerrain) return true;
        }

        return false;
    }

    private static void addSnakes(World world, final int numberOfSnakes, Random random) {
        final int appearanceSeed = random.nextInt();

        //TODO: snakes whit different color
        Color snakeColor = randomBrightColor(random);


        for (int i = 0; i < numberOfSnakes; i++) {
            Color snakeTopColor = randomBrightColor(random);
            Color snakeBaseColor = randomTintedColor(random, 0.7, -0.15);
            world.addThing(new Snake(SPRING_X,SPRING_Y, random, snakeBaseColor, snakeTopColor),
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
