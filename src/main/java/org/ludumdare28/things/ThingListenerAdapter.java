package org.ludumdare28.things;

import org.ludumdare28.ground.Ground;
import org.ludumdare28.inventory.Inventory;

/**
 *
 */
public class ThingListenerAdapter implements ThingListener {
    @Override public void onMoved(Thing thing, double oldX, double oldY, double newX, double newY) {
    }

    @Override public void onPlacedInInventory(Thing thing, Inventory inventory) {
    }

    @Override public void onPlacedOnGround(Thing thing, Ground ground, double x, double y) {
    }
}
