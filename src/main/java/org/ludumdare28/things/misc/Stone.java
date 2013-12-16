package org.ludumdare28.things.misc;

import org.ludumdare28.Pic;
import org.ludumdare28.things.ImageAppearance;
import org.ludumdare28.things.ThingBase;
import org.ludumdare28.things.aspects.DrinkableAspect;
import org.ludumdare28.things.aspects.EdibleAspect;

import java.util.Random;

/**
 * Just a stone
 */
public class Stone extends ThingBase {

    public Stone() {
        this(new Random());
    }

    public Stone(int randomSeed) {
        this(new Random(randomSeed));
    }

    public Stone(final Random random) {
        float scaleX = 0.5f + random.nextFloat();
        float scaleY = 0.5f + random.nextFloat();
        setAppearance(new ImageAppearance(Pic.STONE, random.nextInt(), scaleX, scaleY));
    }
}
