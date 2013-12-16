package org.ludumdare28.things.bush;

import com.badlogic.gdx.graphics.Color;
import org.ludumdare28.things.Harvestable;
import org.ludumdare28.things.Thing;
import org.ludumdare28.things.ThingBase;
import org.ludumdare28.things.berry.BaseBerry;

/**
 *
 */
public class Bush extends ThingBase implements Harvestable {

    private final Class<? extends BaseBerry> berryType;
    private final Color berryColor;
    private final int bushAppearanceSeed;
    private final double secondsForBerryToGrow = 60;
    private double secondsSinceLastBerry = 0;

    private int maxBerries = 10;
    private int berriesLeft = (int) (Math.random() * maxBerries);

    private BushAppearance bushAppearance;

    public Bush(Class<? extends BaseBerry> berryType, Color berryColor, Color bushColor, int bushAppearanceSeed) {
        this.berryType = berryType;
        this.berryColor = berryColor;
        this.bushAppearanceSeed = bushAppearanceSeed;

        bushAppearance = new BushAppearance(bushAppearanceSeed, berryColor, bushColor);
        setAppearance(bushAppearance);
    }

    @Override public void update(double timeSinceLastCall, double totalGameTime) {
        if (berriesLeft < maxBerries){
            secondsSinceLastBerry += timeSinceLastCall;
            if (secondsSinceLastBerry >= secondsForBerryToGrow){
                berriesLeft += 1;
                secondsSinceLastBerry = 0;
            }

        }
        bushAppearance.setBerryAmount(getBerryPercentageLeft());
    }

    /**
     * @return 0 for 0%, 1 for 100%.
     */
    public double getBerryPercentageLeft() {
        return (double)berriesLeft / maxBerries;
    }


    @Override public Thing harvest() {
        if (berriesLeft > 0) {
            berriesLeft--;
            return createBerry();
        }
        else {
            return null;
        }
    }

    private BaseBerry createBerry() {
        try {
            final BaseBerry berry = berryType.newInstance();
            // TODO: Set appearance with right color.
            return berry;
        } catch (Exception e) {
            e.printStackTrace();
            throw new IllegalStateException("Can not make berry instance for " + berryType + " " + e + ": " + e.getMessage(), e);
        }
    }
}
