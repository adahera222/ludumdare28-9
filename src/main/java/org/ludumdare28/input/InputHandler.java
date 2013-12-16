package org.ludumdare28.input;

import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.InputProcessor;
import org.flowutils.Check;

import java.util.HashMap;
import java.util.Map;

import static org.flowutils.Check.*;
import static org.flowutils.Check.notNull;

/**
 * Handler for input
 */
public class InputHandler extends InputAdapter {

    private Map<Controllable, InputConfiguration> controllables = new HashMap<Controllable, InputConfiguration>();

    /**
     * @param controllable something that can be controlled.
     * @param inputConfiguration configuration of input keys to actions.
     */
    public void addControllable(Controllable controllable, InputConfiguration inputConfiguration) {
        notNull(controllable, "controllable");
        notNull(inputConfiguration, "inputConfiguration");

        controllables.put(controllable, inputConfiguration);
    }

    @Override public boolean keyDown(int keycode) {
        boolean processed = false;

        // Check if the key matches any binding
        for (Map.Entry<Controllable, InputConfiguration> entry : controllables.entrySet()) {
            final InputConfiguration inputConfiguration = entry.getValue();
            final InputAction action = inputConfiguration.getBinding(keycode);
            if (action != null) {
                // Tell the controllable to start an action.
                final Controllable controllable = entry.getKey();
                controllable.begin(action);
                processed = true;
            }
        }

        return processed;
    }

    @Override public boolean keyUp(int keycode) {
        boolean processed = false;

        // Check if the key matches any binding
        for (Map.Entry<Controllable, InputConfiguration> entry : controllables.entrySet()) {
            final InputConfiguration inputConfiguration = entry.getValue();
            final InputAction action = inputConfiguration.getBinding(keycode);
            if (action != null) {
                // Tell the controllable to stop the action.
                final Controllable controllable = entry.getKey();
                controllable.end(action);
                processed = true;
            }
        }

        return processed;
    }


}
