package org.ludumdare28.things;

/**
 * Interface for things that other things can be harvested from.
 */
public interface Harvestable {

    /**
     * @return harvested thing, or null if none could be harvested.
     */
    Thing harvest();
}
