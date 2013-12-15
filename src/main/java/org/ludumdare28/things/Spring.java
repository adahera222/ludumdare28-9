package org.ludumdare28.things;

import org.ludumdare28.things.aspects.DrinkableAspect;

/**
 * Author: Shiera
 */
public class Spring extends ThingBase{

    public Spring() {
        super(new DrinkableAspect(Integer.MAX_VALUE));
    }

}
