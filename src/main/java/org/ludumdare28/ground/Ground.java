package org.ludumdare28.ground;

import org.ludumdare28.entities.Thing;

/**
 * Information about the ground terrain and all things located on the ground.
 */
public interface Ground {

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

}
