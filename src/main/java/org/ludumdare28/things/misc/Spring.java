package org.ludumdare28.things.misc;

import org.ludumdare28.Pic;
import org.ludumdare28.things.ImageAppearance;
import org.ludumdare28.things.ThingBase;
import org.ludumdare28.things.aspects.DrinkableAspect;

/**
 * Author: Shiera
 */
public class Spring extends ThingBase {

    public Spring() {
        super(new DrinkableAspect(Integer.MAX_VALUE));

        setAppearance(new ImageAppearance(Pic.SPRING_BASE));
    }

}
