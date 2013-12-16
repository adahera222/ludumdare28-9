package org.ludumdare28.things.snake;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import org.ludumdare28.Pic;
import org.ludumdare28.things.Appearance;
import com.badlogic.gdx.graphics.Color;
import java.awt.*;

/**
 * Author: Shiera
 */
public class SnakeAppearance implements Appearance{
    private Snake thisSnake;
    private  int pictureFrame;
    private int frameNumber;
    private final Color baseColor;
    private final Color topColor;
    private int snakeMinAttackPic = 20;
    private int thisSnakeAttackPic = 0;
    private boolean delaySnakeAtackOn = false;


    public SnakeAppearance(Snake thisSnake, Color baseColor, Color topColor) {
        this.thisSnake = thisSnake;
        this.baseColor = baseColor;
        this.topColor = topColor;
        pictureFrame = (int)(4*Math.random());
        frameNumber = (int)(4*Math.random());
    }

    @Override
    public void render(TextureAtlas textureAtlas, SpriteBatch spriteBatch, float screenX, float screenY) {
        if (thisSnake.isAttacking() || delaySnakeAtackOn){
            delaySnakeAtackOn = true;
            drawPic(textureAtlas, spriteBatch, screenX, screenY, Pic.BITING_SNAKE_BASE.getTexture(textureAtlas, pictureFrame), baseColor);
            drawPic(textureAtlas, spriteBatch, screenX, screenY, Pic.BITING_SNAKE_TOP.getTexture(textureAtlas, pictureFrame), topColor);
        }
        else{
            drawPic(textureAtlas, spriteBatch, screenX, screenY, Pic.MOVING_SNAKE_BASE.getTexture(textureAtlas, pictureFrame), baseColor);
            drawPic(textureAtlas, spriteBatch, screenX, screenY, Pic.MOVING_SNAKE_TOP.getTexture(textureAtlas, pictureFrame), topColor);
        }

    }

    @Override
    public void update(double timeSinceLastCall, double totalGameTime) {
        if (frameNumber >= 5){
            if (delaySnakeAtackOn){
                thisSnakeAttackPic ++;
                if (thisSnakeAttackPic>snakeMinAttackPic){
                    delaySnakeAtackOn = false;
                    thisSnakeAttackPic = 0;
                }
            }
            frameNumber = 0;
            pictureFrame++;
            if (pictureFrame > 4) pictureFrame = 0;
        }
        frameNumber ++;
    }

    private void drawPic(TextureAtlas textureAtlas, SpriteBatch spriteBatch, float screenX, float screenY, TextureRegion texture, Color color) {

        float sizeX = texture.getRegionWidth();
        float sizeY = texture.getRegionHeight();
        float y = screenY - sizeY * 0.25f;
        spriteBatch.setColor(color);
        if (thisSnake.isMovingLeft()){
            float x = screenX - sizeX/2;
            spriteBatch.draw(texture, x, y, sizeX, sizeY);
        }
        else{
            float x = screenX + sizeX/2;
            spriteBatch.draw(texture, x, y, 0, 0, sizeX, sizeY, -1, 1, 0);
        }
        spriteBatch.setColor(Color.WHITE);



    }
}
