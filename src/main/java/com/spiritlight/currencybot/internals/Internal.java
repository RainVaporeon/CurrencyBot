package com.spiritlight.currencybot.internals;

import com.spiritlight.currencybot.config.Config;

import java.io.IOException;
import java.io.UncheckedIOException;

public class Internal {
    private static final StateManager manager = new StateManager();

    public static final int INIT = 0;
    public static final int CONFIG_LOAD = 1;
    public static final int INIT_FINISH = 2;
    protected static class System {
        private static int state;

        public static void init() {
            Internal.manager.flag(Flag.INIT);

            try {
                Config.read();
                advanceState();
            } catch (IOException e) {
                throw new UncheckedIOException(e);
            }

        }

        private static void advanceState() {
            if(state++ >= INIT_FINISH) throw new IllegalStateException("Exceeding expected state");
        }
    }

    public static int getState() {
        return System.state;
    }

    protected static StateManager getManager() {
        return manager;
    }

    protected enum Flag {
        INIT,
        CONTROLLER_INIT
    }
}
