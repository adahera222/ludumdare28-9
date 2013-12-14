package org.ludumdare28.entities;

/**
 * Something that is notified when a thing changes.
 */
public interface ThingListener {

    /**
     * Called when the thing moves
     */
    void onMoved(double oldX, double oldY, double newX, double newY);
}
