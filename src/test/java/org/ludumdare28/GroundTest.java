package org.ludumdare28;

import org.junit.Before;
import org.junit.Test;
import org.ludumdare28.ground.GroundImpl;
import org.ludumdare28.things.Player;
import org.ludumdare28.world.World;
import org.ludumdare28.world.WorldImpl;

import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;

/**
 *
 */
public class GroundTest {

    private World world;
    private GroundImpl ground;
    private Player player;

    @Before
    public void setUp() throws Exception {
        ground = new GroundImpl(10, 10);
        player = new Player("igor", 10);
        world = new WorldImpl(ground, player);
    }

    @Test
    public void testMovingOnGround() throws Exception {
        final TestThing testThing = new TestThing();
        world.addThing(testThing);

        assertFalse(world.getThings().contains(testThing));

        world.update(1, 1);

        assertTrue(world.getThings().contains(testThing));

        testThing.moveToGround(ground);
        testThing.setPos(3.3, 3.2);

        assertEquals(ground.getCell(3, 3), ground.getCell(testThing.getX(), testThing.getY()));
        assertNotNull(ground.getCell(3, 3));
        assertTrue(ground.getCell(3, 3).getThings().contains(testThing));

    }

    @Test
    public void testMoveBetweenGroundAndInventory() throws Exception {
        final TestThing testThing = new TestThing();
        world.addThing(testThing);

        // Move to ground using ground.addThing
        ground.addThing(testThing);
        testThing.setPos(3.3, 3.2);
        assertIsOnGround(testThing);

        // Move to player inventory using inventory.addThing
        player.getInventory().addToInventory(testThing);
        assertIsInInventory(testThing);

        // Move to ground using thing.moveToGround
        testThing.moveToGround(ground);
        assertIsOnGround(testThing);

        // Move to inventory using thing.moveToInventory
        testThing.moveToInventory(player.getInventory());
        assertIsInInventory(testThing);


    }

    private void assertIsInInventory(TestThing testThing) {
        assertFalse(ground.getCell(3, 3).getThings().contains(testThing));
        assertNull(testThing.getGround());
        assertEquals(player.getInventory(), testThing.getInventoryThingIsIn());
    }

    private void assertIsOnGround(TestThing testThing) {
        assertTrue(ground.getCell(3, 3).getThings().contains(testThing));
        assertNull(testThing.getInventoryThingIsIn());
        assertEquals(ground, testThing.getGround());
    }
}
