package org.ludumdare28.things.player;

import com.badlogic.gdx.graphics.Color;

/**
 *
 */
public enum PlayerAttribute {
    THIRST("Thirst", new Color(0.1f, 0.1f, 0.9f, 1)),
    HUNGER("Hunger", new Color(0.7f, 0.4f, 0.2f, 1)),
    TIREDNESS("Tiredness", new Color(0.1f, 0.4f, 0.6f, 1)),
    POISON("Poison", new Color(0.7f, 0.2f, 0.7f, 1)),

    ;

    private final String name;
    private final Color color;

    private PlayerAttribute(String name, Color color) {
        this.name = name;
        this.color = color;
    }

    public String getName() {
        return name;
    }

    public Color getColor() {
        return color;
    }
}
