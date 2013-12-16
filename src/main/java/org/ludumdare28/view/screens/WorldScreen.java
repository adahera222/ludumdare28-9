package org.ludumdare28.view.screens;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import org.ludumdare28.ground.view.GroundView;
import org.ludumdare28.things.Thing;
import org.ludumdare28.world.World;

/**
 *
 */
public class WorldScreen implements Screen {
    private final World world;

    private GroundView groundView;

    public WorldScreen(World world) {
        this.world = world;

        groundView = new GroundView();
        groundView.setGround(world.getGround());
    }

    @Override public void open() {
    }

    @Override public void update(double timeSinceLastCall, double totalGameTime) {
        world.update(timeSinceLastCall, totalGameTime);
        groundView.update(timeSinceLastCall, totalGameTime);
    }

    @Override public void render(TextureAtlas textureAtlas, SpriteBatch spriteBatch, OrthographicCamera camera) {
        groundView.render(textureAtlas, spriteBatch, camera);
    }

    @Override public void close() {
    }

    public void setFocusedThing(Thing focusedThing) {groundView.setFocusedThing(focusedThing);}

    public float getCameraCenterY() {return groundView.getCameraCenterY();}

    public float getCameraCenterX() {return groundView.getCameraCenterX();}
}
