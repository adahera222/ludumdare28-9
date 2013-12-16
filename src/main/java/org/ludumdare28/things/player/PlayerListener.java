package org.ludumdare28.things.player;

import org.ludumdare28.things.Thing;

/**
 * Listener that is notified about player actions
 */
public interface PlayerListener {

    /**
     * Called when a player attribute has changed.
     */
    void onChanged(PlayerAttribute attribute, double currentValue, double oldValue, double maxValue);

    /**
     * Called when the thing the player actions will be focused on is changed.
     */
    void onTargetChanged(Thing targetedThing, Thing oldTargetedThing);

    /**
     * Called when the player does some action.
     *
     * @param action the action done
     * @param target the target of the action, or null if no target
     * @param tool the tool used, or null if no tool used.
     */
    void onPlayerAction(PlayerAction action, Thing target, Thing tool);
}
