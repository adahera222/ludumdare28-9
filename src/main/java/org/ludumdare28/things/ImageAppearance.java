package org.ludumdare28.things;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import org.ludumdare28.Pic;

import java.util.Random;

/**
 * Simple image based appearance
 */
public class ImageAppearance implements Appearance {
    private Pic pic;
    private int seed;
    private float scaleX = 1;
    private float scaleY = 1;

    public ImageAppearance(Pic pic) {
        this(pic, new Random().nextInt());
    }

    public ImageAppearance(Pic pic, int seed) {
        this(pic, seed, 1, 1);
    }

    public ImageAppearance(Pic pic, int seed, float scaleX, float scaleY) {
        this.pic = pic;
        this.seed = seed;
        this.scaleX = scaleX;
        this.scaleY = scaleY;
    }

    @Override public void render(TextureAtlas textureAtlas, SpriteBatch spriteBatch, float screenX, float screenY) {
        final TextureRegion texture = pic.getTexture(textureAtlas, seed);
        float sizeX = texture.getRegionWidth() * scaleX;
        float sizeY = texture.getRegionHeight() * scaleY;
        float x = screenX - sizeX/2;
        float y = screenY;
        spriteBatch.draw(texture, x, y, sizeX, sizeY);
    }

    @Override public void update(double timeSinceLastCall, double totalGameTime) {
        // Nothing to update
    }

    public Pic getPic() {
        return pic;
    }

    public void setPic(Pic pic) {
        this.pic = pic;
    }
}
