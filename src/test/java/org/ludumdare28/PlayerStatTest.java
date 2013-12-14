package org.ludumdare28;

/**
 * Author: Shiera
 */
import static org.junit.Assert.*;

import org.junit.Test;

import java.util.List;

public class PlayerStatTest {


    @Test
    /** tests that all stats is zero in the beginning */
    public void testStatsZeroInBeginning() throws Exception {
        Player testplayer  = new Player("testplayer", 1);
        assertEquals("hunger is ont 0 in beginning", 0, testplayer.getHunger(), 0.001);
        assertEquals("thirst is ont 0 in beginning", 0, testplayer.getThirst(), 0.001);
        assertEquals("tiredness is ont 0 in beginning", 0, testplayer.getTiredness(), 0.001);
        assertEquals("damage is ont 0 in beginning", 0, testplayer.getDamage(), 0.001);
    }

    @Test
    /**tests that the right stats increases right */
    public void statsChangesCorrectly1() throws Exception {
        Player testplayer  = new Player("testplayer", 1);
        testplayer.changeDamage(10);
        assertEquals("damage is not changing correctly", 10, testplayer.getDamage(), 0.001);
        testplayer.changeDamage(10);
        testplayer.changeHunger(10);
        assertEquals("hunger is not changing correctly", 10, testplayer.getHunger(), 0.001);
        testplayer.changeHunger(10);
        testplayer.changeThirst(10);
        assertEquals("thirst is not changing correctly", 10, testplayer.getThirst(), 0.001);
        testplayer.changeThirst(10);
        testplayer.changeTiredness(10);
        assertEquals("tiredness is not changing correctly", 10, testplayer.getTiredness(), 0.001);
    }

    @Test
    /**Tests that stat decreasing works */
    public void statsChangesCorrectly2() throws Exception {
        Player testplayer  = new Player("testplayer", 1);
        testplayer.changeDamage(1);
        testplayer.changeDamage(-15);
        assertEquals("damage is not changing correctly", 0, testplayer.getDamage(), 0.001);
        testplayer.changeHunger(1);
        testplayer.changeHunger(-15);
        assertEquals("hunger is not changing correctly", 0, testplayer.getHunger(), 0.001);
        testplayer.changeThirst(1);
        testplayer.changeThirst(-15);
        assertEquals("thirst is not changing correctly", 0, testplayer.getThirst(), 0.001);
        testplayer.changeTiredness(1);
        testplayer.changeTiredness(-15);
        assertEquals("tiredness is not changing correctly", 0, testplayer.getTiredness(), 0.001);
    }

    @Test
    public void testsPlayerDiesFromDamage() throws Exception {
        Player testplayer  = new Player("testplayer", 1);
        testplayer.changeDamage(testplayer.getMaxStat());
        assertEquals("player should have died", false, testplayer.isAlive());
    }

    @Test
    public void testsPlayerDiesFromThirst() throws Exception {
        Player testplayer  = new Player("testplayer", 1);
        testplayer.changeThirst(testplayer.getMaxStat());
        assertEquals("player should have died", false, testplayer.isAlive());
    }

    @Test
    public void testsPlayerDiesFromHunger() throws Exception {
        Player testplayer  = new Player("testplayer", 1);
        testplayer.changeHunger(testplayer.getMaxStat());
        assertEquals("player should have died", false, testplayer.isAlive());
    }

    @Test
    public void playerFallsAslepWhenTooTired() throws Exception{
        Player testplayer  = new Player("testplayer", 1);
        testplayer.changeTiredness(testplayer.getMaxStat());
        assertEquals("player should have died", false, testplayer.isAwake());
    }
}
