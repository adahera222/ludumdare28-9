package org.ludumdare28;

import org.ludumdare28.entities.Thing;
import org.ludumdare28.entities.ThingListener;
import org.ludumdare28.ground.Ground;
import org.ludumdare28.inventory.Inventory;

/**
 * Dummy thing used for unit testing
 */
public class TestThing implements Thing {
    @Override public Inventory getInventory() {
        // TODO: Implement
        return null;
    }

    @Override public Ground getGround() {
        // TODO: Implement
        return null;
    }

    @Override public double getX() {
        // TODO: Implement
        return 0;
    }

    @Override public double getY() {
        // TODO: Implement
        return 0;
    }

    @Override public void setPos(double x, double y) {
        // TODO: Implement

    }

    @Override public void setInventory(Inventory inventory) {
        // TODO: Implement

    }

    @Override public void setGround(Ground ground) {
        // TODO: Implement

    }

    @Override public void addThingListener(ThingListener listener) {
        // TODO: Implement

    }

    @Override public void removeThingListener(ThingListener listener) {
        // TODO: Implement

    }
}
