package org.ludumdare28;

import org.junit.Test;
import org.ludumdare28.things.Player;
import org.ludumdare28.things.berry.*;

/**
 * Author: Shiera
 */
public class BerryEatingTest {


    @Test
    public void EatHungerBerryTest(){
        Player testplayer  = new Player("testplayer", 1);
        HungerBerry testberry = new HungerBerry();
        testberry.getEdibleAspect().eat(testplayer);
    }
}
