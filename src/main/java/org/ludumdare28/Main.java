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
    private static final int WORLD_SIZE_X = 60;
    private static final int WORLD_SIZE_Y = 60;
    private static final double SPRING_X = WORLD_SIZE_X * 0.6;
    private static final double SPRING_Y = WORLD_SIZE_Y * 0.5;

    private static List<String> randomBushPrefix = Arrays.asList("Cloud", "Fire", "Sea", "Desert", "YumYum ", "Strange", "Jinga", "Zing", "Hell", "Bing", "Zugzug", "Gor", "Doge", "Rash", "Mort", "Devil", "Death", "Wonder", "Burr", "Smoke", "Angel", "Heavens", "Island", "Sailors", "Blood", "Gold");
    private static List<String> randomBushPostfix = Arrays.asList("berry", "berry", "berry", "berry", "bush", "bush", "fruit", "fruit", "grape", "grape", "thorn", "rose", "weed", "grass", "plant", "plant", "leaf");

    public static void main(String[] args) {

        // Define the window's size
        int width = 800, height = 600;

        // Texture file
        final String textureAtlasFile = "assets/texturepack.pack";

        // Create player
        Player player = new Player("Player One", 10);

        // Create ground
        World world = createWorld(player);

        // Find first shore square from left for player
        boolean playerPosFound = false;
        double playerY = WORLD_SIZE_Y * 0.45;
        for (int x = 0; x < WORLD_SIZE_X / 2 && !playerPosFound; x++) {
            double playerX = x + 0.5;
            if (!world.getGround().getCell(playerX, playerY).getTerrainType().isWater()) {
                player.setPos(playerX, playerY);
                playerPosFound = true;
            }
        }

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
        for (int i = 0; i < 700; i++) {
            world.addThing(new Stone(random),
                           random.nextDouble() * WORLD_SIZE_X,
                           random.nextDouble() * WORLD_SIZE_Y);
        }

        // Bushes
        addBushes(world, 100, FoodBerry.class, random, TerrainType.GRASS, TerrainType.JUNGLE, TerrainType.SAND);
        addBushes(world, 50, HealthBerry.class, random, TerrainType.GRASS, TerrainType.JUNGLE);
        addBushes(world, 100, CaffeineBerry.class, random, TerrainType.SAND, TerrainType.WET_SAND);
        addBushes(world, 40, PoisonBerry.class, random, TerrainType.GRASS, TerrainType.JUNGLE);
        addBushes(world, 30, PoisonBerry.class, random, TerrainType.GRASS, TerrainType.JUNGLE);
        addBushes(world, 50, WaterBerry.class, random, TerrainType.SAND, TerrainType.GRASS);
        addBushes(world, 50, HungerBerry.class, random, TerrainType.GRASS, TerrainType.JUNGLE, TerrainType.SAND);
        addBushes(world, 100, RandomBerry.class, random, TerrainType.GRASS, TerrainType.JUNGLE);
        addBushes(world, 100, RandomBerry.class, random, TerrainType.SAND);
        addBushes(world, 50, RandomBerry.class, random, TerrainType.ROCKY);
        addBushes(world, 50, TirednessBerry.class, random, TerrainType.GRASS, TerrainType.JUNGLE);


        //Spring
        world.addThing(new Spring(),SPRING_X,SPRING_Y);

        //Snake
        addSnakes(world, 16, random, SPRING_X, SPRING_Y, 15);
        addSnakes(world, 16, random, WORLD_SIZE_X*0.5, WORLD_SIZE_Y*0.8, 40);


        return world;
    }

    private static Ground createGround(Random random) {
        final GroundImpl ground = new GroundImpl(WORLD_SIZE_X, WORLD_SIZE_Y, 42);

        // Setup terrain altitude lookup
        TreeMap<Double, TerrainType> terrainLookup = new TreeMap<Double, TerrainType>();
        terrainLookup.put(-10000000.0, TerrainType.DEEP_WATER);
        terrainLookup.put(-22.0, TerrainType.WATER);
        terrainLookup.put(-12.0, TerrainType.WATER_SHORE);
        terrainLookup.put(0.0, TerrainType.WET_SAND);
        terrainLookup.put(7.0, TerrainType.SAND);
        terrainLookup.put(22.0, TerrainType.GRASS);
        terrainLookup.put(40.0, TerrainType.JUNGLE);
        terrainLookup.put(70.0, TerrainType.ROCKY);


        double mountainX = WORLD_SIZE_X * 0.6;
        double mountainY = WORLD_SIZE_Y * 0.6;
        double mountainRadius = (WORLD_SIZE_X + WORLD_SIZE_Y) * 0.2;
        double mountainTopAltitude = 100;
        double mountainRimAltitude = -20;
        double roughnessAtTip = 50;
        double roughnessAtMid = 0;
        double roughnessAtRim = 5;

        for (int y = 0; y < WORLD_SIZE_Y; y++) {
            for (int x = 0; x < WORLD_SIZE_X; x++) {
                final GroundCell cell = ground.getCell(x, y);
                final double topDistance = Maths.distance(x, y, mountainX, mountainY);
                double altitude = Maths.map(topDistance, 0, mountainRadius, mountainTopAltitude, mountainRimAltitude);

                // Add some roughness
                double roughness;
                if (topDistance < mountainRadius/2) {
                    roughness = Maths.map(topDistance, 0, mountainRadius/2, roughnessAtTip, roughnessAtMid);
                }
                else {
                    roughness = Maths.map(topDistance, mountainRadius/2, mountainRadius, roughnessAtMid, roughnessAtRim);
                }
                double variance = SimplexGradientNoise.sdnoise2(x, y) * roughness;
                altitude += variance;

                // Smooth near 0
                if (altitude > 0 && altitude < 7) altitude = Math.sqrt(altitude);

                // Slope down at edges
                double edgeDistanceX = Math.min(x, WORLD_SIZE_X - x);
                double edgeDistanceY = Math.min(y, WORLD_SIZE_Y - y);
                double edgeDistance = Math.min(edgeDistanceX, edgeDistanceY);
                double edgeAdjust = Math.max(15 - edgeDistance, 0) * 2;
                altitude -= edgeAdjust;

                // Create a path to the spring
                final double pathY = SPRING_Y + (x - SPRING_X) * 0.25;
                if (x < SPRING_X && y > pathY - 1.5 && y < pathY +1) {
                    double pathDistance = Math.abs(y - pathY);
                    altitude = Math.min(altitude, 1+pathDistance*1.5);
                }

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

    private static void addSnakes(World world,
                                  final int numberOfSnakes,
                                  Random random,
                                  final double homeX,
                                  final double homeY, final int maxDistanceFromHome) {
        for (int i = 0; i < numberOfSnakes; i++) {
            Color snakeTopColor = randomBrightEarthColor(random);
            Color snakeBaseColor = randomDarkEarthColor(random);
            world.addThing(new Snake(homeX, homeY, random, snakeBaseColor, snakeTopColor, maxDistanceFromHome),
                    random.nextDouble() * WORLD_SIZE_X,
                    random.nextDouble() * WORLD_SIZE_Y);
        }
    }

    private static Color randomDarkEarthColor(Random random) {
        float r = (float) Maths.clamp0To1(random.nextGaussian() * 0.15 + 0.1);
        float g = (float) Maths.clamp0To1(random.nextGaussian() * 0.3 + 0.3);
        float b = (float) Maths.clamp0To1(random.nextGaussian() * 0.2 + 0.2);

        return new Color(r, g, b, 1);
    }

    private static Color randomBrightEarthColor(Random random) {
        float r = (float) Maths.clamp0To1(random.nextGaussian() * 0.14 + 0.3);
        float g = (float) Maths.clamp0To1(random.nextGaussian() * 0.2 + 0.6);
        float b = (float) Maths.clamp0To1(random.nextGaussian() * 0.1 + 0.6);

        return new Color(r, g, b, 1);
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
