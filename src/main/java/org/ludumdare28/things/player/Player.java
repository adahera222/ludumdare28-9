package org.ludumdare28.things.player;

import org.flowutils.Maths;
import org.ludumdare28.input.Controllable;
import org.ludumdare28.input.ControllableImpl;
import org.ludumdare28.input.InputAction;
import org.ludumdare28.inventory.Inventory;
import org.ludumdare28.inventory.InventoryImpl;
import org.ludumdare28.things.Harvestable;
import org.ludumdare28.things.Thing;
import org.ludumdare28.things.ThingBase;
import org.ludumdare28.things.aspects.EdibleAspect;

import java.util.ArrayList;
import java.util.List;

/**
 * Author: Shiera
 */
public class Player  extends ThingBase {
    private static final double MAX_EATING_DISTANCE = 1.2;
    private static final double MAX_TARGET_DISTANCE = 1.2;
    private static final int STEPS_BETWEEN_TARGET_UPDATES = 5;
    private String name;
    private double baseSpeed;
    private Inventory inventory;
    private double hunger;
    private double thirst;
    private double tiredness;
    private double damage;
    private final int maxStat = 100;
    private double hungerSpeedModifier;
    private double thirstSpeedModifier;
    private double tirednessSpeedModifier;
    private double damageSpeedModifier;
    private boolean alive;
    private boolean awake;

    private Thing target;

    private static double MAX_WORLD_SPEED_SQUARES_PER_SECOND = 2;
    private static double HUNGER_INCREASE_PER_SECOND = 0.3;
    private static double THIRST_INCREASE_PER_SECOND = 1;
    private static double TIREDNESS_INCREASE_PER_SECOND = 0.4;

    private int stepsToTargetUpdate = STEPS_BETWEEN_TARGET_UPDATES;

    private List<PlayerListener> listeners = new ArrayList<PlayerListener>(3);

    private ControllableImpl controllable = new ControllableImpl();

    /**
    -the player have a name that can be given in the beginning of the game.
    -the player have a speed that tells how long time it takes to do different tasks, the speed
     consist of the players basespeed that can be lowered because of hunger, thirst and need to sleep
     -The player have an inventory wher all his things are
     - maxstat tells how big hunger, thirst and need to sleep can be (ex if hunger goes ower maxStat you die in hunger)
     **/
    public Player(String name, int maxInventorySlots) {
        this.name = name;
        this.baseSpeed = maxStat;
        //speed = baseSpeed;
        hunger = 0;
        thirst = 0;
        tiredness = 0;
        damage = 0;
        alive = true;
        awake = true;
        hungerSpeedModifier = 0;
        thirstSpeedModifier = 0;
        damageSpeedModifier = 0;
        tirednessSpeedModifier = 0;
        inventory = new InventoryImpl(maxInventorySlots);

        controllable.addListener(new Controllable() {
            @Override public void begin(InputAction action) {
                if (action == InputAction.USE) {
                    // Start eating
                    startEating();
                }
            }

            @Override public void end(InputAction action) {
                if (action == InputAction.USE) {
                    // Stop eating
                }
            }
        });

        setName("Adventurer");
    }

    private void startEating() {
        // TODO: Always show the closest target in the UI
        // Find target
        //final Thing closestThing = getClosestThing(MAX_EATING_DISTANCE);
        if (target == null) return;
        if (target instanceof Harvestable) {
            Harvestable harvestable = (Harvestable) target;
            final Thing harvest = harvestable.harvest();
            if (harvest != null) {
                final EdibleAspect edibleAspect = harvest.getEdibleAspect();
                if (edibleAspect != null) {
                    edibleAspect.eat(this);

                    System.out.println("Player.startEating");
                    System.out.println("edibleAspect = " + edibleAspect);

                    // TODO: play eat sound

                    // Notify listeners about eating
                    for (PlayerListener listener : listeners) {
                        listener.onPlayerAction(PlayerAction.EAT, target, null);
                    }
                }
            }
        }
        // if target = drinkable
        if (target.getDrinkableAspect() != null){
            target.getDrinkableAspect().drink(this);
            //TODO: drinking sound
            for (PlayerListener listener : listeners) {
                listener.onPlayerAction(PlayerAction.DRINK, target, null);
            }

        }
    }

    public void changeHunger(double hungerAmount){
        if (hungerAmount != 0) {
            double oldValue = hunger;
            hunger += hungerAmount;
            if (hunger < 0) hunger = 0;
            if (hunger >= maxStat) alive = false;
            if (hunger >= 50) hungerSpeedModifier = -(hunger-50)/2;
            if (hunger < 50) hungerSpeedModifier = 0;

            for (PlayerListener listener : listeners) {
                listener.onChanged(PlayerAttribute.HUNGER, hunger, oldValue, maxStat);
            }
        }

    }

    public void changeTiredness(double tirednessAmount){
        if (tirednessAmount != 0) {
            double oldValue = tiredness;
            tiredness += tirednessAmount;
            if (tiredness < 0) tiredness = 0;
            if (tiredness >= maxStat) awake = false;
            if (tiredness >= 50) tirednessSpeedModifier = -(tiredness-50)/2;
            if (tiredness < 50) tirednessSpeedModifier = 0;

            for (PlayerListener listener : listeners) {
                listener.onChanged(PlayerAttribute.TIREDNESS, tiredness, oldValue, maxStat);
            }
        }

    }

    public void changeThirst(double thirstAmount){
        if (thirstAmount != 0) {
            double oldValue = thirst;
            thirst += thirstAmount;
            if (thirst < 0) thirst = 0;
            if (thirst >= maxStat) alive = false;
            if (thirst >= 50) thirstSpeedModifier = -(thirst-50)/2;
            if (thirst < 50) thirstSpeedModifier = 0;

            for (PlayerListener listener : listeners) {
                listener.onChanged(PlayerAttribute.THIRST, thirst, oldValue, maxStat);
            }
        }

    }

    public void changePoison(double damageAmount){
        if (damageAmount != 0) {
            double oldValue = damage;
            damage += damageAmount;
            if (damage < 0) damage = 0;
            if (damage >= maxStat) alive = false;
            if (damage >= 50) damageSpeedModifier = -(damage-50)/2;
            if (damage < 50) damageSpeedModifier = 0;

            for (PlayerListener listener : listeners) {
                listener.onChanged(PlayerAttribute.POISON, damage, oldValue, maxStat);
            }
        }
    }

    public double getDamage() {
        return damage;
    }

    public double getTiredness() {
        return tiredness;
    }

    public double getHunger() {
        return hunger;
    }

    public double getThirst() {
        return thirst;
    }

    public boolean isAlive() {
        return alive;
    }

    public boolean isAwake() {
        return awake;
    }

    public double getSpeed(){
        return baseSpeed + damageSpeedModifier + hungerSpeedModifier + thirstSpeedModifier + tirednessSpeedModifier;
    }

    public int getMaxStat() {
        return maxStat;
    }

    /**
     * @return inventory of the player.
     */
    public Inventory getInventory() {
        return inventory;
    }


    @Override public void update(double lastFrameDurationSeconds, double totalGameTime) {
        /*
        double x = Math.cos(totalGameTime / 4) * 6 + 7;
        double y = Math.sin(totalGameTime / 4) * 6 + 5;
        setPos(x,y);
        */

        // Increase properties
        changeHunger(HUNGER_INCREASE_PER_SECOND * lastFrameDurationSeconds);
        changeThirst(THIRST_INCREASE_PER_SECOND * lastFrameDurationSeconds);
        changeTiredness(TIREDNESS_INCREASE_PER_SECOND * lastFrameDurationSeconds);

        // Move
        final double speed = getSpeed();
        double delta = Maths.mapAndClamp(speed, 0, 100, 0, MAX_WORLD_SPEED_SQUARES_PER_SECOND) * lastFrameDurationSeconds;
        double dx = 0;
        double dy = 0;
        if (controllable.isActive(InputAction.LEFT))  dx -= delta;
        if (controllable.isActive(InputAction.RIGHT)) dx += delta;
        if (controllable.isActive(InputAction.UP))    dy += delta;
        if (controllable.isActive(InputAction.DOWN))  dy -= delta;

        // Diagonal movement should be at the same speed as normal movement.
        if (dx != 0 && dy != 0) {
            dx /= Math.sqrt(2);
            dy /= Math.sqrt(2);
        }

        setPos(getX() + dx,
               getY() + dy);

        // TODO: Make sure player can't walk on water

        // Update target
        if (stepsToTargetUpdate <= 0) {
            stepsToTargetUpdate = STEPS_BETWEEN_TARGET_UPDATES;
            Thing oldTarget = target;
            target = getClosestThing(MAX_TARGET_DISTANCE);
            for (PlayerListener listener : listeners) {
                listener.onTargetChanged(target, oldTarget);
            }
        }
        else {
            stepsToTargetUpdate--;
        }

    }

    public Controllable getControllable() {
        return controllable;
    }

    public void addListener(PlayerListener listener) {
        if (!listeners.contains(listener)) {
            listeners.add(listener);
        }
    }

    public void removeListener(PlayerListener listener) {
        listeners.remove(listener);
    }
}
