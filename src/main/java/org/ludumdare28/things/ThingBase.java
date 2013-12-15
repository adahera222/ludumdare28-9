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
    private Inventory inventoryThingIsIn;
    private EdibleAspect edibleAspect;

    protected ThingBase(double posX ,double posY , EdibleAspect edibleAspect) {
        this.posX = posX;
        this.edibleAspect = edibleAspect;
        this.posY = posY;
        this.inventoryThingIsIn = null;
    }

    protected ThingBase(double posX, double posY) {
       this(posX, posY, null);
    }

    protected ThingBase() {
        this(0,0,null);
    }

    protected ThingBase(EdibleAspect edibleAspect) {
        this(0,0, edibleAspect);
    }

    @Override public Inventory getInventoryThingIsIn() {
        return inventoryThingIsIn;
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

    @Override public void setInventoryThingIsIn(Inventory inventoryThingIsIn) {
        this.inventoryThingIsIn = inventoryThingIsIn;

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

    @Override
    public boolean isStackable() {
        return false;
    }

    @Override
    public void delete() {
        // TODO: implement
    }
}
