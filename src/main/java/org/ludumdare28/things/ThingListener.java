package org.ludumdare28.things;

import org.ludumdare28.ground.Ground;
import org.ludumdare28.inventory.Inventory;

/**
 * Something that is notified when a thing changes.
 */
public interface ThingListener {

    /**
     * Called when the thing moves
     */
    void onMoved(Thing thing, double oldX, double oldY, double newX, double newY);

    /**
     * Called when the thing is placed in an inventory.
     */
    void onPlacedInInventory(Thing thing, Inventory inventory);

    /**
     * Called when the thing is placed on a ground.
     */
    void onPlacedOnGround(Thing thing, Ground ground, double x, double y);
}
