package org.ludumdare28.input;

import java.util.*;

/**
 *
 */
public class ControllableImpl implements Controllable {
    private Set<InputAction> activeActions = EnumSet.noneOf(InputAction.class);
    private Set<Controllable> chainedListeners = new HashSet<Controllable>(3);

    @Override public void begin(InputAction action) {
        activeActions.add(action);

        for (Controllable listener : chainedListeners) {
            listener.begin(action);
        }
    }

    @Override public void end(InputAction action) {
        activeActions.remove(action);

        for (Controllable listener : chainedListeners) {
            listener.end(action);
        }
    }

    public Set<InputAction> getActiveActions() {
        return activeActions;
    }

    public boolean isActive(InputAction action) {
        return activeActions.contains(action);
    }

    public void addListener(Controllable controllable) {
        chainedListeners.add(controllable);
    }

    public void removeListener(Controllable controllable) {
        chainedListeners.remove(controllable);
    }
}
