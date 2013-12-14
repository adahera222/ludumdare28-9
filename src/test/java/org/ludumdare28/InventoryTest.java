package org.ludumdare28;

import static org.junit.Assert.*;

import org.junit.Test;
import org.ludumdare28.entities.Thing;

import java.util.List;

public class InventoryTest {

    @Test
    public void testInventoryMaxCapacity() throws Exception {
        // Create inventory
        Inventory inventory = new InventoryImpl(4);

        // Add some things
        inventory.addToInventory(new TestThing());
        inventory.addToInventory(new TestThing());
        inventory.addToInventory(new TestThing());
        inventory.addToInventory(new TestThing());

        // Adding more than the max slots should throw an exception
        try {
            inventory.addToInventory(new TestThing());
            fail("Inventory should be full and throw an exception");
        }
        catch (Exception e) {
            // Ok
        }
    }

    @Test
    public void testInventoryCapacity() throws Exception {
        // Create inventory
        Inventory inventory = new InventoryImpl(4);

        assertEquals("Four empty slots initially", 4, inventory.getOpenSlots());

        // Add some things
        inventory.addToInventory(new TestThing());

        assertEquals("One slot used, should be three open", 3, inventory.getOpenSlots());

        inventory.addToInventory(new TestThing());
        inventory.addToInventory(new TestThing());
        inventory.addToInventory(new TestThing());

        assertEquals("All slots used, should be 0 open", 0, inventory.getOpenSlots());
    }

    @Test
    public void testSelection() throws Exception {
        // Create inventory
        Inventory inventory = new InventoryImpl(4);

        // Add some things
        final TestThing thing1 = new TestThing();
        final TestThing thing2 = new TestThing();
        inventory.addToInventory(thing1);
        inventory.addToInventory(thing2);

        assertNull("No think should be selected initially", inventory.getSelectedThing());

        // Select
        inventory.setSelectedThing(thing2);

        assertEquals("Selected thing should be remembered", thing2, inventory.getSelectedThing());
    }

    @Test
    public void testGetThingsAndRemove() throws Exception {
        // Create inventory
        Inventory inventory = new InventoryImpl(4);

        // Add some things
        final TestThing thing1 = new TestThing();
        final TestThing thing2 = new TestThing();
        final TestThing thing3 = new TestThing();
        inventory.addToInventory(thing1);
        inventory.addToInventory(thing2);
        inventory.addToInventory(thing3);

        final List<Thing> things = inventory.getThings();
        assertTrue("Inventory should contain added things", things.contains(thing1));
        assertTrue("Inventory should contain added things", things.contains(thing2));
        assertTrue("Inventory should contain added things", things.contains(thing3));
        assertEquals("Number of open slots should be correct", 1, inventory.getOpenSlots());

        // Remove
        inventory.removeFromInventory(thing2);

        assertTrue("Inventory should contain previously added things", things.contains(thing1));
        assertFalse("Inventory should not contain removed things", things.contains(thing2));
        assertTrue("Inventory should contain previously added things", things.contains(thing3));
        assertEquals("Number of open slots should be correct", 2, inventory.getOpenSlots());
    }

    @Test
    public void testIfSelectedItemIsRemovedNoItemShouldBeSelected() throws Exception {
        // Create inventory
        Inventory inventory = new InventoryImpl(4);

        // Add some things
        final TestThing thing1 = new TestThing();
        final TestThing thing2 = new TestThing();
        inventory.addToInventory(thing1);
        inventory.addToInventory(thing2);

        // Select one
        inventory.setSelectedThing(thing2);

        // Remove it
        inventory.removeFromInventory(thing2);

        assertEquals("No item should be selected if the selected item was removed", null, inventory.getSelectedThing());

    }
}
