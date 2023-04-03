package com.spiritlight.currencybot.internals;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class StateManager {
    private final Map<Internal.Flag, Boolean> state;

    protected StateManager() {
        try {
            Class.forName("java.util.Map");
            Class.forName("java.util.concurrent.ConcurrentHashMap");

            state = new ConcurrentHashMap<>();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    protected void flag(Internal.Flag flag) {
        if(flagged(flag)) throw new IllegalStateException("Attempting to re-flag " + flag);
        setFlag(flag, true);
    }

    protected void unflag(Internal.Flag flag) {
        setFlag(flag, false);
    }

    private void setFlag(Internal.Flag flag, boolean state) {
        if(flag == null) throw new NullPointerException();
        this.state.put(flag, state);
    }

    protected boolean flagged(Internal.Flag flag) {
        return state.getOrDefault(flag, false);
    }

    @SuppressWarnings("deprecation")
    @Override
    protected final void finalize() {}
}
