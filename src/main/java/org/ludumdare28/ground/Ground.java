package org.ludumdare28.ground;

import org.ludumdare28.Updating;
import org.ludumdare28.things.Thing;
import org.ludumdare28.things.ThingBase;

/**
 * Information about the ground terrain and all things located on the ground.
 */
public interface Ground extends Updating {

    /**
     * Get information about one location on the ground.
     *
     * @param cellX cell x coordinate.
     * @param cellY cell y coordinate.
     * @return the cell at the specified location, or null if it is outside the map.
     */
    GroundCell getCell(int cellX, int cellY);

    /**
     * Adds a thing to the ground.
     */
    void addThing(Thing thing);

    /**
     * @param thing thing to remove from the ground.
     */
    void removeThing(Thing thing);

    GroundCell getCell(double x, double y);

    /**
     * @param reference reference thing to get something close to
     * @param maxDistance maximum distance to the thing to get.
     * @return the thing closest to the reference thing, within the specified max distance (in grid cells),
     * or null if there is nothing close.
     */
    Thing getClosestThing(Thing reference, double maxDistance);

    Thing getClosestThing(Thing reference, double maxDistance, Class<? extends Thing> thingType);
}
