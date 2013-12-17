package org.ludumdare28;

import org.junit.Test;
import org.ludumdare28.things.player.Player;
import org.ludumdare28.things.berry.*;

import static org.junit.Assert.assertEquals;


/**
 * Author: Shiera
 */
public class BerryEatingTest {


    @Test
    public void EatHungerBerryTest(){
        //make player and berry
        Player testplayer  = new Player("testplayer", 1);
        HungerBerry testberry = new HungerBerry();
        // eat berry
        testberry.getEdibleAspect().eat(testplayer);
        assertEquals("hunger should increase when eating hungerberry", 1, testplayer.getHunger(), 0.001);
    }

    @Test
    public void EatFoodBerryTest(){
        // makwe pleyer whit a bit hunger and a berry
        Player testplayer  = new Player("testplayer", 1);
        FoodBerry testberry = new FoodBerry();
        testplayer.changeHunger(10);
        // eat berry
        testberry.getEdibleAspect().eat(testplayer);
        assertEquals("hunger should decrease when eating foodberry", 9, testplayer.getHunger(), 0.001);
    }
}
