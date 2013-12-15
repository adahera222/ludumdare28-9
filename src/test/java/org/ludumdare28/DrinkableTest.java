package org.ludumdare28;

import org.junit.Test;
import org.ludumdare28.things.Player;
import org.junit.Test;
import org.ludumdare28.things.Spring;

import static org.junit.Assert.assertEquals;

/**
 * Author: Shiera
 */
public class DrinkableTest {

    @Test
    public void drinkingRemovesThirst(){
        // makes player and spring
        Player testplayer  = new Player("testplayer", 1);
        Spring testSpring = new Spring();

        // makes player tirsty
        testplayer.changeThirst(50);

        // drinks
        testSpring.getDrinkableAspect().drink(testplayer);

        assertEquals("drinking should remove thirst", 0, testplayer.getThirst(), 0.001);

    }

}
