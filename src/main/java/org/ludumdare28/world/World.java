package org.ludumdare28.world;

import org.ludumdare28.things.Player;
import org.ludumdare28.Updating;
import org.ludumdare28.ground.Ground;

/**
 *
 */
public interface World extends Updating {

    Ground getGround();

    Player getPlayer();


}
