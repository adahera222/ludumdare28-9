package org.ludumdare28.things.snake;

import org.ludumdare28.Pic;
import org.ludumdare28.things.ImageAppearance;
import org.ludumdare28.things.Thing;
import org.ludumdare28.things.ThingBase;
import org.ludumdare28.things.player.Player;

import static java.lang.Math.*;

/**
 * Author: Shiera
 */
public class Snake extends ThingBase {
    private boolean hasPoison;
    private double poisonRegenTime;
    private double poisonAmount;
    private static final double MAX_BITING_DISTANCE = 0.5;
    private static final double MAX_TARGET_DISTANCE = 2;
    private final double maxDistFromHome;
    private final double minDistFromHome;
    private static final int STEPS_BETWEEN_TARGET_UPDATES = 59;
    private static double MAX_WORLD_SPEED_SQUARES_PER_SECOND = 1;
    private int stepsToTargetUpdate =(int)(random()*STEPS_BETWEEN_TARGET_UPDATES);
    private boolean alive;
    private double homeX;
    private double homeY;

    private double timeWithoutPoison;
    private Thing target;
    private final double movementRandomisatorMultiplicator = random()+0.5;
    private final double movementRandomisatorPlus = random()*100;
    private boolean farFromHome;
    private boolean closeToHome;

    public Snake(double homeX, double homeY){
        hasPoison = true;
        poisonRegenTime = 60 + (random()*60);
        poisonAmount = 10 + random()*15;
        maxDistFromHome = 3 + (random()*10);
        minDistFromHome = 0.5 + random()*3;
        setAppearance(new ImageAppearance(Pic.SPRING_WATER));
        this.homeX = homeX;
        this.homeY = homeY;
    }

    @Override
    public void update(double lastFrameDurationSeconds, double totalGameTime) {


        // if snake is lost it wants home
        if (distanceFromHome()> maxDistFromHome) farFromHome = true;
        // if snake is home it wants away
        if (distanceFromHome()< minDistFromHome) closeToHome = true;
        // going home
        if (farFromHome){
            moveTowardsTarget(homeX,homeY, lastFrameDurationSeconds);
            if (distanceFromHome() < maxDistFromHome /4) farFromHome = false;
        }
        // going home
        else if (closeToHome){
            moveFromTarget(homeX, homeY, lastFrameDurationSeconds);
            if (distanceFromHome() > minDistFromHome*2) closeToHome = false;
        }
        // random movement and player hunting
        else{
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
                    moveTowardsTarget(player.getX(), player.getY(), lastFrameDurationSeconds);
                }
                else {
                    moveFromTarget(player.getX(), player.getY(), lastFrameDurationSeconds);
                }
            }
            else{
                // random movement
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
    }

    public void bite(Player player){
        player.changePoison(poisonAmount);
        System.out.println("snakebite");
        hasPoison = false;
    }


    private void moveTowardsTarget(double targetX, double targetY, double dt){
       double distanceX = targetX - getX();
       double distanceY = targetY - getY();

        double angle = atan2(distanceY, distanceX);
        double dx = cos(angle) * MAX_WORLD_SPEED_SQUARES_PER_SECOND * dt;
        double dy = sin(angle) * MAX_WORLD_SPEED_SQUARES_PER_SECOND * dt;

        setPos(getX()+dx, getY()+dy);

    }


    private void moveFromTarget(double targetX, double targetY, double dt){
        double distanceX = targetX - getX();
        double distanceY = targetY - getY();



        double angle = atan2(distanceY, distanceX);
        double dx = cos(angle) * MAX_WORLD_SPEED_SQUARES_PER_SECOND * dt;
        double dy = sin(angle) * MAX_WORLD_SPEED_SQUARES_PER_SECOND * dt;

        setPos(getX()-dx, getY()-dy);
    }

    private double distanceFromHome(){
        double distanceX = homeX - getX();
        double distanceY = homeY- getY();
        return sqrt((distanceX*distanceX)+(distanceY*distanceY));

    }



}
