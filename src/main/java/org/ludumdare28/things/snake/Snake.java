package org.ludumdare28.things.snake;

import com.badlogic.gdx.graphics.Color;
import org.ludumdare28.things.Thing;
import org.ludumdare28.things.ThingBase;
import org.ludumdare28.things.player.Player;
import org.ludumdare28.utils.StringUtils;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

import static java.lang.Math.*;

/**
 * Author: Shiera
 */
public class Snake extends ThingBase {

    private static final List<String> namePrefix = Arrays.asList("Huggo ", "Bitty ", "Snappy ", "Slith ", "Igor ", "Mary ","Ripper ", "Killy ", "Rippy ", "Hissy ", "Wishy ", "Sneaky ", "Riggy ", "Slinky ", "Hippy ", "Snork ");
    private static final List<String> namePostfix = Arrays.asList("the Snake", "the Snake", "the Snake", "the Wyrm", "Greentail", "the Swift", "the Squeaky", "the Brave", "the Silent", "the Fast", "the Slow", "the Cold", "the Viper", "the Viper", "the Viper", "the Meek", "the Venomous");


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

    private boolean attacking;
    private boolean movingLeft;

    public Snake(double homeX,
                 double homeY,
                 Random random,
                 Color baseColor,
                 Color topColor,
                 final int maxDistanceFromHome){
        hasPoison = true;
        poisonRegenTime = 60 + (random()*60);
        poisonAmount = 8 + random()*13;
        maxDistFromHome = 3 + (random()* maxDistanceFromHome);
        minDistFromHome = 0.25 + random()*2;
        setAppearance(new SnakeAppearance(this, baseColor, topColor));
        this.homeX = homeX;
        this.homeY = homeY;
        movingLeft = false;
        attacking = false;

        setName(StringUtils.createRandomName(random, namePrefix, namePostfix));
        setCanWalkAnywhere(true);
    }

    @Override
    public void update(double lastFrameDurationSeconds, double totalGameTime) {
        super.update(lastFrameDurationSeconds, totalGameTime);

        // if snake is lost it wants home
        if (distanceFromHome()> maxDistFromHome) farFromHome = true;
        // if snake is home it wants away
        if (distanceFromHome()< minDistFromHome) closeToHome = true;
        // going home
        if (farFromHome){
            moveTowardsTarget(homeX,homeY, lastFrameDurationSeconds);
            if (distanceFromHome() < maxDistFromHome /2) farFromHome = false;
            attacking = false;
        }
        // going away
        else if (closeToHome){
            moveFromTarget(homeX, homeY, lastFrameDurationSeconds);
            if (distanceFromHome() > minDistFromHome*1.5) closeToHome = false;
            attacking = false;
        }
        // random movement and player hunting
        else{
            // Update target
            if (stepsToTargetUpdate <= 0) {
                stepsToTargetUpdate = STEPS_BETWEEN_TARGET_UPDATES;
                target = getClosestThing(MAX_TARGET_DISTANCE, Player.class);
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
                    attacking = true;
                }
                else {
                    moveFromTarget(player.getX(), player.getY(), lastFrameDurationSeconds);
                    attacking = false;
                }
            }
            else{
                // random movement
                double dx = Math.cos(totalGameTime * 6 / 17.13* movementRandomisatorMultiplicator+movementRandomisatorPlus) * lastFrameDurationSeconds * (1.5*Math.random()+0.25);
                double dy = Math.sin(totalGameTime * 6 /13.23* movementRandomisatorMultiplicator+movementRandomisatorPlus) * lastFrameDurationSeconds * (1.5*Math.random()+0.25);
                dx += Math.cos(totalGameTime * 6 / 1.13* movementRandomisatorMultiplicator+movementRandomisatorPlus) * lastFrameDurationSeconds * 0.1;
                dy += Math.sin(totalGameTime * 6 / 0.23* movementRandomisatorMultiplicator+movementRandomisatorPlus) * lastFrameDurationSeconds * 0.1;

                setSnakePos(dx, dy);
                attacking = false;
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
        hasPoison = false;
    }


    private void moveTowardsTarget(double targetX, double targetY, double dt){
       double distanceX = targetX - getX();
       double distanceY = targetY - getY();

        double angle = atan2(distanceY, distanceX);
        double dx = cos(angle) * MAX_WORLD_SPEED_SQUARES_PER_SECOND * dt;
        double dy = sin(angle) * MAX_WORLD_SPEED_SQUARES_PER_SECOND * dt;

        setSnakePos(dx, dy);

    }


    private void moveFromTarget(double targetX, double targetY, double dt){
        double distanceX = targetX - getX();
        double distanceY = targetY - getY();
        double angle = atan2(distanceY, distanceX);
        double dx = cos(angle) * MAX_WORLD_SPEED_SQUARES_PER_SECOND * dt;
        double dy = sin(angle) * MAX_WORLD_SPEED_SQUARES_PER_SECOND * dt;
        setSnakePos(-dx,-dy);

    }

    private double distanceFromHome(){
        double distanceX = homeX - getX();
        double distanceY = homeY- getY();
        return sqrt((distanceX*distanceX)+(distanceY*distanceY));

    }

    private void setSnakePos(double dx, double dy){
        setPos(getX()+dx, getY()+dy);
        if (dx < 0 ) movingLeft = true;
        else movingLeft = false;
    }

    public boolean isAttacking() {
        return attacking;
    }

    public boolean isMovingLeft() {
        return movingLeft;
    }
}
