package org.ludumdare28.things.berry;

/**
 * Author: Shiera
 */
public class RandomBerry extends BaseBerry {
    /**
     poisonberry:
     hunger    = random -1 ... 1
     damage    = random -1 ... 1
     tiredness = random -1 ... 1
     */
    public RandomBerry() {
        super((Math.random()*2)-1,(Math.random()*2)-1,(Math.random()*2)-1);

    }
}
