package org.ludumdare28.things.aspects;

import org.ludumdare28.things.player.Player;

/**
 * Author: Shiera
 */
public class DrinkableAspect {
    private double thirstChange;

    /**positive numbers lessens thirst*/
    public DrinkableAspect(double thirstChange) {
        // - so positive would lessen thirst
        this.thirstChange = -thirstChange;
    }

    public void drink(Player player){
        player.changeThirst(thirstChange);
    }
}
