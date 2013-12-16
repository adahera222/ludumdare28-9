package org.ludumdare28.view.screens;

import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import org.ludumdare28.SkinFactory;
import org.ludumdare28.ground.view.GroundView;
import org.ludumdare28.input.InputConfiguration;
import org.ludumdare28.input.InputHandler;
import org.ludumdare28.things.Thing;
import org.ludumdare28.things.player.PlayerAction;
import org.ludumdare28.things.player.PlayerAttribute;
import org.ludumdare28.things.player.PlayerListener;
import org.ludumdare28.world.World;

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
    private final Map<PlayerAttribute, Slider> attributeSliders = new HashMap<PlayerAttribute, Slider>();

    private Stage stage;

    public WorldScreen(World world) {
        this.world = world;

        groundView = new GroundView();
        groundView.setGround(world.getGround());
    }

    @Override public void open(InputHandler inputHandler, InputMultiplexer inputMultiplexer) {
        this.inputHandler = inputHandler;
        this.inputMultiplexer = inputMultiplexer;
        inputHandler.addControllable(world.getPlayer().getControllable(), InputConfiguration.ARROWS_AND_WASD);

        if (stage != null) inputMultiplexer.addProcessor(stage);
    }

    @Override public void update(double lastStepDurationSeconds, double totalGameTime) {
        world.update(lastStepDurationSeconds, totalGameTime);
        groundView.update(lastStepDurationSeconds, totalGameTime);

        if (stage != null) stage.act((float) lastStepDurationSeconds);
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

        final Stage stage = new Stage(camera.viewportWidth, camera.viewportHeight, true, spriteBatch);

        // Create a table that fills the screen. Everything else will go inside this table.
        Table table = new Table();
        table.setFillParent(true);
        table.bottom();
        stage.addActor(table);

        // Create a button with the "default" TextButtonStyle. A 3rd parameter can be used to specify a name other than "default".
        final TextButton button = new TextButton("Click me!", skin);
        table.add(button);

        // Add a listener to the button. ChangeListener is fired when the button's checked state changes, eg when clicked,
        // Button#setChecked() is called, via a key press, etc. If the event.cancel() is called, the checked state will be reverted.
        // ClickListener could have been used, but would only fire when clicked. Also, canceling a ClickListener event won't
        // revert the checked state.
        button.addListener(new ChangeListener() {
            public void changed (ChangeEvent event, Actor actor) {
                System.out.println("Clicked! Is checked: " + button.isChecked());
                button.setText("Good job!");
            }
        });

        Table attributeTable = new Table(skin);
        // Create slider indicators for attributes
        for (PlayerAttribute attribute : PlayerAttribute.values()) {
            createSlider(attributeTable, skin, attribute);
        }
        table.add(attributeTable);

        // Add an image actor. Have to set the size, else it would be the size of the drawable (which is the 1x1 texture).
        table.add(new Image(skin.newDrawable("white", Color.RED))).size(64);

        // Listen to player
        world.getPlayer().addListener(new PlayerListener() {
            @Override
            public void onChanged(PlayerAttribute attribute, double currentValue, double oldValue, double maxValue) {
                final Slider slider = attributeSliders.get(attribute);
                slider.setRange(0, (float) maxValue);
                slider.setValue((float) currentValue);
            }

            @Override public void onTargetChanged(Thing targetedThing, Thing oldTargetedThing) {
                // TODO: Show target

            }

            @Override public void onPlayerAction(PlayerAction action, Thing target, Thing tool) {
                // TODO: Implement

            }
        });


        return stage;
    }

    private Slider createSlider(Table table, Skin skin, final PlayerAttribute attribute) {
        final Slider slider = new Slider(0, 100, 1, false, skin);
        slider.setColor(attribute.getColor());
        slider.setAnimateDuration(3);
        slider.setTouchable(Touchable.disabled);
        slider.setAnimateInterpolation(Interpolation.sine);
        table.add(slider);
        table.row();
        attributeSliders.put(attribute, slider);
        return slider;
    }
}
