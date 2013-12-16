package org.ludumdare28.things.berry;

/**
 * Author: Shiera
 */
public class RandomBerry extends BaseBerry {
    /**
     RandomBerry:
     hunger    = random -2 ... 2
     damage    = random -2 ... 2
     tiredness = random -2 ... 2
     */
    public RandomBerry() {
        super(randomMinusOneToOne(), randomMinusOneToOne(), randomMinusOneToOne());

    }

    private static double randomMinusOneToOne() {
        return (Math.random() * 4) - 2;
    }
}
