package org.ludumdare28.things.player;

/**
 * Possible actions the player can take.
 */
public enum PlayerAction {
    EAT("Eat", true, false),
    SLEEP("Sleep", false, false),

    ;


    private final String name;
    private final boolean requiresTarget;
    private final boolean requiresTool;

    private PlayerAction(String name, boolean requiresTarget, boolean requiresTool) {
        this.name = name;
        this.requiresTarget = requiresTarget;
        this.requiresTool = requiresTool;
    }
}
