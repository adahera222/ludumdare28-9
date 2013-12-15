package org.ludumdare28.things;

import org.ludumdare28.ground.Ground;
import org.ludumdare28.inventory.Inventory;
import org.ludumdare28.things.aspects.EdibleAspect;

/**
 *
 */
public abstract class ThingBase implements Thing {
    private double posX;
    private double posY;

    private EdibleAspect edibleAspect = null;


    @Override public Inventory getInventory() {
        // TODO: Implement
        return null;
    }

    @Override public Ground getGround() {
        // TODO: Implement
        return null;
    }

    @Override public double getX() {
        return posX;
    }

    @Override public double getY() {
        return posY;
    }

    @Override public void setPos(double x, double y) {
        posX = x;
        posY = y;

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

    @Override public EdibleAspect getEdibleAspect() {
        return edibleAspect;
    }

    public void setEdibleAspect(EdibleAspect edibleAspect) {
        this.edibleAspect = edibleAspect;
    }
}
