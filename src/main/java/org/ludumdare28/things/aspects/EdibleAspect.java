package org.ludumdare28.things.aspects;

import org.ludumdare28.things.player.Player;
import org.ludumdare28.things.Thing;

/**
 * Data about the nutrition value of some thing.
 */
public class EdibleAspect {
    private double hungerChange;
    private double poisonChange;
    private double tirednessChange;
    private Thing hostThing;

    /** Changes "heal" when negative*/
    public EdibleAspect(double hungerChange, double poisonChange, double tirednessChange) {
        this.hungerChange = hungerChange;
        this.poisonChange = poisonChange;
        this.tirednessChange = tirednessChange;
    }

    /**
     * Called when the thing is eaten
     * @param player creature that ate the thing, can be modified with any effects from eating it.
     */
    public void eat(Player player) {
        player.changeDamage(poisonChange);
        player.changeHunger(hungerChange);
        player.changeTiredness(tirednessChange);
        hostThing.delete();
    }

    public Thing getHostThing() {
        return hostThing;
    }

    public void setHostThing(Thing hostThing) {
        this.hostThing = hostThing;
    }
}
