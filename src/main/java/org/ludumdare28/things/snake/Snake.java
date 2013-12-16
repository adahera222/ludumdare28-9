package org.ludumdare28.things.snake;

import org.ludumdare28.Pic;
import org.ludumdare28.things.ImageAppearance;
import org.ludumdare28.things.Thing;
import org.ludumdare28.things.ThingBase;
import org.ludumdare28.things.player.Player;

/**
 * Author: Shiera
 */
public class Snake extends ThingBase {
    private boolean hasPoison;
    private double poisonRegenTime;
    private double poisonAmount;
    private static final double MAX_BITING_DISTANCE = 0.5;
    private static final double MAX_TARGET_DISTANCE = 2;
    private static final int STEPS_BETWEEN_TARGET_UPDATES = 59;
    private static double MAX_WORLD_SPEED_SQUARES_PER_SECOND = 1;
    private int stepsToTargetUpdate =(int)(Math.random()*STEPS_BETWEEN_TARGET_UPDATES);
    private boolean alive;
    private double homeX;
    private double homeY;
    private double maxDistFromHome;
    private double timeWithoutPoison;
    private Thing target;
    private boolean homeSet = false;
    private final double movementRandomisatorMultiplicator = Math.random()+0.5;
    private final double movementRandomisatorPlus = Math.random()*100;

    public Snake(){
        hasPoison = true;
        poisonRegenTime = 60 + (Math.random()*60);
        poisonAmount = 5 + Math.random()*15;
        maxDistFromHome = 2 + (Math.random()*3);
        setAppearance(new ImageAppearance(Pic.SPRING_WATER));
    }

    @Override
    public void update(double lastFrameDurationSeconds, double totalGameTime) {
        if (!homeSet) {
            homeX = getX();
            homeY = getY();
            homeSet = true;
        }

        // Update target
        if (stepsToTargetUpdate <= 0) {
            stepsToTargetUpdate = STEPS_BETWEEN_TARGET_UPDATES;
            target = getClosestThing(MAX_TARGET_DISTANCE);
        }
        else {
            stepsToTargetUpdate--;
        }

        // charges player if has poison left and player is target, if no poison runs from player,
        // if player not target random move
        // TODO: check if player is some other than the closest thing
        if (target instanceof Player) {
            Player player = (Player) target;
            if (hasPoison){
                if (this.getDistance(player) < MAX_BITING_DISTANCE){
                     bite(player);
                }
                //TODO: follow player
            }
            else {
                //TODO: run from player
            }
        }
        else{
            //TODO move close to home
            double dx = Math.cos(totalGameTime * 6 / 17.13* movementRandomisatorMultiplicator+movementRandomisatorPlus) * lastFrameDurationSeconds * (1.5*Math.random()+0.25);
            double dy = Math.sin(totalGameTime * 6 /13.23* movementRandomisatorMultiplicator+movementRandomisatorPlus) * lastFrameDurationSeconds * (1.5*Math.random()+0.25);
            dx += Math.cos(totalGameTime * 6 / 1.13* movementRandomisatorMultiplicator+movementRandomisatorPlus) * lastFrameDurationSeconds * 0.1;
            dy += Math.sin(totalGameTime * 6 / 0.23* movementRandomisatorMultiplicator+movementRandomisatorPlus) * lastFrameDurationSeconds * 0.1;

            setPos(getX() + dx, getY() +dy);
        }
        if (!hasPoison){
            timeWithoutPoison += lastFrameDurationSeconds;
            if (timeWithoutPoison >= poisonRegenTime){
                hasPoison = true;
                timeWithoutPoison = 0;
            }
        }
    }

    public void bite(Player player){
        player.changePoison(poisonAmount);
        System.out.println("snakebite");
        hasPoison = false;
    }



}
