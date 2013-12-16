package org.ludumdare28.things.berry;

import org.ludumdare28.things.ThingBase;
import org.ludumdare28.things.aspects.EdibleAspect;

/**
 * Author: Shiera
 */
public abstract class BaseBerry extends ThingBase {

    protected BaseBerry(double hungerChange, double poisonChange, double tirednessChange, double thirstChange){
        super(new EdibleAspect(hungerChange, poisonChange, tirednessChange, thirstChange));
        setName("Berry");
    }

    @Override
    public boolean isStackable() {
        return true;
    }

    @Override
    public boolean isPickable() {
        return true;
    }
}
