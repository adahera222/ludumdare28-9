package org.ludumdare28;

/**
 * Something that gets updated.
 */
public interface Updating {

    /**
     * Called regularly to update the state.
     * @param timeSinceLastCall seconds since the last call to update.
     * @param totalGameTime seconds that have elapsed since the game was started.
     */
    void update(double timeSinceLastCall, double totalGameTime);

}
