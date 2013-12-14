package org.ludumdare28.entities;

import org.ludumdare28.Inventory;
import org.ludumdare28.ground.Ground;

/**
 * Any object in the game.
 */
public interface Thing {

    /**
     * @return the inventory that the thing is in, or null if it is not in an inventory.
     */
    Inventory getInventory();

    /**
     * @return the ground that the thing is on, or null if it is not on a ground.
     */
    Ground getGround();

    /**
     * @return x coordinate of the thing on the ground.
     */
    double getX();

    /**
     * @return y coordinate of the thing on the ground.
     */
    double getY();

    /**
     * Updates the ground position of this thing.
     */
    void setPos(double x, double y);

    /**
     * @param inventory the inventory the thing is in, or null if it is not in an inventory.
     *                  If not null, should set ground to null.
     */
    void setInventory(Inventory inventory);

    /**
     * @param ground the ground the thing is in, or null if it is not on a ground.
     *                  If not null, should set inventory to null.
     */
    void setGround(Ground ground);

    /**
     * @param listener listener that is notified when the thing moves or does something else.
     */
    void addThingListener(ThingListener listener);

    /**
     * @param listener listener to remove.
     */
    void removeThingListener(ThingListener listener);

}
