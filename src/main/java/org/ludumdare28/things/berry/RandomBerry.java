package org.ludumdare28.things.berry;

/**
 * Author: Shiera
 */
public class RandomBerry extends BaseBerry {
    /**
     RandomBerry:
     hunger    = random -1 ... 1
     damage    = random -1 ... 1
     tiredness = random -1 ... 1
     */
    public RandomBerry() {
        super(randomMinusOneToOne(), randomMinusOneToOne(), randomMinusOneToOne());

    }

    private static double randomMinusOneToOne() {
        return (Math.random() * 2) - 1;
    }
}
