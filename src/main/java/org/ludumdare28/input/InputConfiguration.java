package org.ludumdare28.input;

import com.badlogic.gdx.Input;

import java.util.HashMap;
import java.util.Map;

/**
 *
 */
public class InputConfiguration {

    private Map<Integer, InputAction> bindings = new HashMap<Integer, InputAction>();

    public static InputConfiguration WASD_AND_SPACE = createWasdSpaceConfiguration();
    public static InputConfiguration ARROWS_AND_WASD = createArrowsAndWasdConfiguration();
    public static InputConfiguration ARROWS_AND_ENTER = createArrowsAndEnterConfiguration();

    public static InputConfiguration createWasdSpaceConfiguration() {
        final InputConfiguration inputConfiguration = new InputConfiguration();
        inputConfiguration.bind(Input.Keys.W, InputAction.UP);
        inputConfiguration.bind(Input.Keys.A, InputAction.LEFT);
        inputConfiguration.bind(Input.Keys.S, InputAction.DOWN);
        inputConfiguration.bind(Input.Keys.D, InputAction.RIGHT);
        inputConfiguration.bind(Input.Keys.SPACE, InputAction.USE);
        inputConfiguration.bind(Input.Keys.ENTER, InputAction.USE);
        inputConfiguration.bind(Input.Keys.CONTROL_LEFT, InputAction.USE);
        inputConfiguration.bind(Input.Keys.ESCAPE, InputAction.MENU);

        return inputConfiguration;
    }

    public static InputConfiguration createArrowsAndWasdConfiguration() {
        final InputConfiguration inputConfiguration = new InputConfiguration();
        inputConfiguration.bind(Input.Keys.W, InputAction.UP);
        inputConfiguration.bind(Input.Keys.A, InputAction.LEFT);
        inputConfiguration.bind(Input.Keys.S, InputAction.DOWN);
        inputConfiguration.bind(Input.Keys.D, InputAction.RIGHT);
        inputConfiguration.bind(Input.Keys.UP, InputAction.UP);
        inputConfiguration.bind(Input.Keys.LEFT, InputAction.LEFT);
        inputConfiguration.bind(Input.Keys.DOWN, InputAction.DOWN);
        inputConfiguration.bind(Input.Keys.RIGHT, InputAction.RIGHT);
        inputConfiguration.bind(Input.Keys.SPACE, InputAction.USE);
        inputConfiguration.bind(Input.Keys.ENTER, InputAction.USE);
        inputConfiguration.bind(Input.Keys.CONTROL_LEFT, InputAction.USE);
        inputConfiguration.bind(Input.Keys.CONTROL_RIGHT, InputAction.USE);
        inputConfiguration.bind(Input.Keys.ESCAPE, InputAction.MENU);

        return inputConfiguration;
    }

    public static InputConfiguration createArrowsAndEnterConfiguration() {
        final InputConfiguration inputConfiguration = new InputConfiguration();
        inputConfiguration.bind(Input.Keys.UP, InputAction.UP);
        inputConfiguration.bind(Input.Keys.LEFT, InputAction.LEFT);
        inputConfiguration.bind(Input.Keys.DOWN, InputAction.DOWN);
        inputConfiguration.bind(Input.Keys.RIGHT, InputAction.RIGHT);
        inputConfiguration.bind(Input.Keys.ENTER, InputAction.USE);
        inputConfiguration.bind(Input.Keys.CONTROL_RIGHT, InputAction.USE);
        inputConfiguration.bind(Input.Keys.ESCAPE, InputAction.MENU);

        return inputConfiguration;
    }

    public void bind(int keyCode, InputAction action) {
        bindings.put(keyCode, action);
    }

    public void unbind(int keyCode) {
        bindings.remove(keyCode);
    }

    public Map<Integer, InputAction> getBindings() {
        return bindings;
    }

    public InputAction getBinding(int keyCode) {
        return bindings.get(keyCode);
    }
}
