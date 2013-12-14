package org.ludumdare28;

/**
 * Author: Shiera
 */
public class TestThing extends Thing implements Etable{
    int hungerRemovedWhenEating;

    @Override
    public int Eat() {
        return hungerRemovedWhenEating;
    }

    @Override
    public boolean use() {
        return false;

    }
}
