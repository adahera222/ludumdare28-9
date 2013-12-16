package org.ludumdare28.things.snake;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import org.ludumdare28.Pic;
import org.ludumdare28.things.Appearance;

/**
 * Author: Shiera
 */
public class SnakeAppearance implements Appearance{
    private Snake thisSnake;
    private  int pictureFrame;
    private int frameNumber;

    public SnakeAppearance(Snake thisSnake) {
        this.thisSnake = thisSnake;
        pictureFrame = (int)(4*Math.random());
        frameNumber = (int)(4*Math.random());
    }

    @Override
    public void render(TextureAtlas textureAtlas, SpriteBatch spriteBatch, float screenX, float screenY) {
        if (thisSnake.isAttacking()){
            drawPic(textureAtlas, spriteBatch, screenX, screenY, Pic.BITING_SNAKE_BASE.getTexture(textureAtlas, pictureFrame));
            drawPic(textureAtlas, spriteBatch, screenX, screenY, Pic.BITING_SNAKE_TOP.getTexture(textureAtlas, pictureFrame));
        }
        else{
            drawPic(textureAtlas, spriteBatch, screenX, screenY, Pic.MOVING_SNAKE_BASE.getTexture(textureAtlas, pictureFrame));
            drawPic(textureAtlas, spriteBatch, screenX, screenY, Pic.MOVING_SNAKE_TOP.getTexture(textureAtlas, pictureFrame));
        }

    }

    @Override
    public void update(double timeSinceLastCall, double totalGameTime) {
        if (frameNumber >= 5){
            frameNumber = 0;
            pictureFrame++;
            if (pictureFrame > 4) pictureFrame = 0;
        }
        frameNumber ++;
    }

    private void drawPic(TextureAtlas textureAtlas, SpriteBatch spriteBatch, float screenX, float screenY, TextureRegion texture) {

        float sizeX = texture.getRegionWidth();
        float sizeY = texture.getRegionHeight();
        float y = screenY - sizeY * 0.25f;

        if (thisSnake.isMovingLeft()){
            float x = screenX - sizeX/2;
            spriteBatch.draw(texture, x, y, sizeX, sizeY);
        }
        else{
            float x = screenX + sizeX/2;
            spriteBatch.draw(texture, x, y, 0, 0, sizeX, sizeY, -1, 1, 0);
        }
    }
}
