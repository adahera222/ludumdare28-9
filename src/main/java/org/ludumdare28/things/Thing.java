package org.ludumdare28.things;

import org.ludumdare28.Updating;
import org.ludumdare28.ground.GroundCell;
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
     * @return the ground cell this thing is on, or null if it is not on a ground cell.
     */
    GroundCell getGroundCell();

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
    void moveToInventory(Inventory inventory);

    /**
     * @param ground the ground the thing is in, or null if it is not on a ground.
     *                  If not null, should set inventory to null.
     */
    void moveToGround(Ground ground);

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

    Appearance getAppearance();


    boolean isStackable();

    void delete();

    boolean isPickable();

    void setAppearance(Appearance appearance);

    /**
     * @param other thing to get squared distance to.
     * @return square of the distance to the specified thing.  Throws exception if the things are on different grounds.
     */
    double getDistanceSquared(Thing other);

    /**
     * @param other thing to get distance to.
     * @return distance to the specified thing.  Throws exception if the things are on different grounds.
     */
    double getDistance(Thing other);

    /**
     * @param maxDistance maximum distance to search within, specified as map squares.
     * @return the closest other thing to this thing, within the specified max distance, or null if there is nothing close
     * (or if this thing is in an inventory).
     */
    Thing getClosestThing(double maxDistance);
}
