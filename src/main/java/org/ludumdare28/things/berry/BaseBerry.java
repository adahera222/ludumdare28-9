package org.ludumdare28.things.berry;

import org.ludumdare28.entities.Thing;
import org.ludumdare28.entities.ThingListener;
import org.ludumdare28.ground.Ground;
import org.ludumdare28.inventory.Inventory;
import org.ludumdare28.things.Edible;

/**
 * Author: Shiera
 */
public class BaseBerry implements Thing, Edible {
//TODO

    @Override
    public int Eat() {
        return 0;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public Inventory getInventory() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public Ground getGround() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public double getX() {
        return 0;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public double getY() {
        return 0;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void setPos(double x, double y) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void setInventory(Inventory inventory) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void setGround(Ground ground) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void addThingListener(ThingListener listener) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void removeThingListener(ThingListener listener) {
        //To change body of implemented methods use File | Settings | File Templates.
    }
}
