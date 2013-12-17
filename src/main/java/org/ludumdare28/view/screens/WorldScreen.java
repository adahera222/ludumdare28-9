package org.ludumdare28.view.screens;

import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import org.ludumdare28.SkinFactory;
import org.ludumdare28.ground.view.GroundView;
import org.ludumdare28.input.InputConfiguration;
import org.ludumdare28.input.InputHandler;
import org.ludumdare28.things.Thing;
import org.ludumdare28.things.player.PlayerAction;
import org.ludumdare28.things.player.PlayerAttribute;
import org.ludumdare28.things.player.PlayerListener;
import org.ludumdare28.view.ScreenView;
import org.ludumdare28.world.World;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 *
 */
public class WorldScreen implements Screen {
    private final World world;

    private GroundView groundView;
    private InputHandler inputHandler;
    private InputMultiplexer inputMultiplexer;
    private ScreenView screenView;
    private final Map<PlayerAttribute, Slider> attributeSliders = new HashMap<PlayerAttribute, Slider>();

    private Stage stage;
    private AppearanceViewUi targetView;
    private BitmapFont font;
    private static final java.util.List<String> introTexts = Arrays.asList(
            "You have been stranded on an island and now you have to stay alive.",
            "You can walk whit W,S,A,D or the ARROW KEYS and you eat or drink the targeted object whit SPACE.",
            "Feel free to explore the island and try the berries, hopefully there is some fresh water here.",
            "Beside your attribute bar you can see a picture of the thing you are targeting."

    );

    public WorldScreen(World world) {
        this.world = world;

        groundView = new GroundView();
        groundView.setGround(world.getGround());
    }

    @Override public void open(InputHandler inputHandler, InputMultiplexer inputMultiplexer, ScreenView screenView) {
        this.inputHandler = inputHandler;
        this.inputMultiplexer = inputMultiplexer;
        this.screenView = screenView;
        inputHandler.addControllable(world.getPlayer().getControllable(), InputConfiguration.ARROWS_AND_WASD);

        if (stage != null) inputMultiplexer.addProcessor(stage);
    }

    @Override public void update(double lastStepDurationSeconds, double totalGameTime) {
        world.update(lastStepDurationSeconds, totalGameTime);
        groundView.update(lastStepDurationSeconds, totalGameTime);
        if (targetView != null) targetView.update(lastStepDurationSeconds, totalGameTime);

        if (stage != null) stage.act((float) lastStepDurationSeconds);

        // Check for player death
        if (world.getPlayer().isTrulyDead()) {
            screenView.setCurrentScreen(new DeathScreen());
        }
    }

    @Override public void render(TextureAtlas textureAtlas, SpriteBatch spriteBatch, OrthographicCamera camera) {
        if (stage == null) {
            stage = createStage(textureAtlas, spriteBatch, camera);
            inputMultiplexer.addProcessor(stage);
        }

        // Draw world
        groundView.render(textureAtlas, spriteBatch, camera);

        // Draw ui
        stage.getCamera().update();
        stage.getSpriteBatch().setProjectionMatrix(stage.getCamera().combined);
        stage.getRoot().draw(spriteBatch, 1);
    }

    @Override public void close() {
        inputHandler.removeControllable(world.getPlayer().getControllable());
        if (stage != null) inputMultiplexer.removeProcessor(stage);
    }

    public void setFocusedThing(Thing focusedThing) {groundView.setFocusedThing(focusedThing);}

    public float getCameraCenterY() {return groundView.getCameraCenterY();}

    public float getCameraCenterX() {return groundView.getCameraCenterX();}

    private Stage createStage(TextureAtlas textureAtlas, SpriteBatch spriteBatch, OrthographicCamera camera) {
        final Skin skin = SkinFactory.createSkin();
        font = new BitmapFont();

        final Stage stage = new Stage(camera.viewportWidth, camera.viewportHeight, true, spriteBatch);

        // Create a table that fills the screen. Everything else will go inside this table.
        Table table = new Table();
        table.setFillParent(true);
        table.bottom();
        stage.addActor(table);

        float uiHeight = 100;
        float targetViewWidth = 128;

        Table attributeTable = new Table(skin);
        attributeTable.setBackground(skin.newDrawable("white", Color.BLACK));
        // Create slider indicators for attributes
        for (PlayerAttribute attribute : PlayerAttribute.values()) {
            createAttributeView(attributeTable, skin, attribute, 50, 100, uiHeight / PlayerAttribute.values().length);
        }
        table.add(attributeTable);

        // Add an image actor. Have to set the size, else it would be the size of the drawable (which is the 1x1 texture).
        targetView = new AppearanceViewUi(font, targetViewWidth, uiHeight, textureAtlas);
        table.add(targetView);

        // Listen to player
        world.getPlayer().addListener(new PlayerListener() {
            @Override
            public void onChanged(PlayerAttribute attribute, double currentValue, double oldValue, double maxValue) {
                final Slider slider = attributeSliders.get(attribute);
                slider.setRange(0, (float) maxValue);
                slider.setValue((float) currentValue);
            }

            @Override public void onTargetChanged(Thing targetedThing, Thing oldTargetedThing) {
                targetView.setViewedThing(targetedThing);
            }

            @Override public void onPlayerAction(PlayerAction action, Thing target, Thing tool) {
                // TODO: Implement

            }
        });


        return stage;
    }

    private void createAttributeView(Table table,
                                     Skin skin,
                                     final PlayerAttribute attribute,
                                     float labelWidth,
                                     float sliderWidth,
                                     float height) {
        Label label = new Label(attribute.getName(), skin);
        label.setWidth(labelWidth);
        label.setHeight(height);
        table.add(label).left().padLeft(5).padRight(5).height(height);

        final Slider slider = new Slider(0, 100, 1, false, skin);
        slider.setColor(attribute.getColor());
        slider.setAnimateDuration(0.5f);
        slider.setTouchable(Touchable.disabled);
        slider.setAnimateInterpolation(Interpolation.linear);
        slider.setWidth(sliderWidth);
        slider.setHeight(height);
        table.add(slider).height(height);

        table.row();

        attributeSliders.put(attribute, slider);
    }

    @Override public boolean isQuit() {
        return false;
    }
}
