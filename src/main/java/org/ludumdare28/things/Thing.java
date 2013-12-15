package org.ludumdare28.things;

import org.ludumdare28.Updating;
import org.ludumdare28.inventory.Inventory;
import org.ludumdare28.ground.Ground;
import org.ludumdare28.things.aspects.DrinkableAspect;
import org.ludumdare28.things.aspects.EdibleAspect;
import org.ludumdare28.world.World;

/**
 * Any object in the game.
 */
// TODO: Refactor move to an aspect
public interface Thing extends Updating {

    /**
     * @return the inventory that the thing is in, or null if it is not in an inventory.
     */
    Inventory getInventoryThingIsIn();

    /**
     * @return the ground that the thing is on, or null if it is not on a ground.
     */
    Ground getGround();

    /**
     * @return the world the thing is in.
     */
    World getWorld();

    /**
     * @param world the world the thing is in.
     */
    void setWorld(World world);

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
    void setInventoryThingIsIn(Inventory inventory);

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

    /**
     * @return data bout the nutrition value of this thing if it is edible, null if it is not edible.
     */
    EdibleAspect getEdibleAspect();

    DrinkableAspect getDrinkableAspect();

    boolean isStackable();

    void delete();

    boolean isPickable();
}
