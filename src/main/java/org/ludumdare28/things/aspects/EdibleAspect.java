package org.ludumdare28.things.aspects;

import org.ludumdare28.things.Player;

/**
 * Data about the nutrition value of some thing.
 */
public class EdibleAspect {

    // TODO: Energy change, poison change, etc.

    /**
     * Called when the thing is eaten
     * @param player creature that ate the thing, can be modified with any effects from eating it.
     */
    public void eat(Player player) {
        // TODO: Update energy etc
    }

}
