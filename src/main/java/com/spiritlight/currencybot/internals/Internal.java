package com.spiritlight.currencybot.internals;

import com.spiritlight.currencybot.Main;
import com.spiritlight.currencybot.config.Config;
import com.spiritlight.currencybot.misc.Command;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;

import java.io.IOException;
import java.io.UncheckedIOException;

public final class Internal {
    private static final StateManager manager = new StateManager();

    public static final int INIT = 0;
    public static final int CONFIG_LOAD = 1;
    public static final int DISCORD_LOAD = 2;
    public static final int INIT_FINISH = 3;
    protected static class System {
        private static int state;

        public static void init() {
            Internal.manager.flag(Flag.INIT);

            try {
                Config.read();
            } catch (IOException e) {
                throw new UncheckedIOException(e);
            }
            advanceState();

            configureDiscord();

        }

        public static void configureDiscord() {
            Internal.manager.flag(Flag.DISCORD_INIT);

            JDA instance = Main.discord.getJDA();

            CommandData[] data = {
                    new CommandData(Command.QUERY, "Queries this currency's current price"),

            };

            Main.discord.addCommands(data);

            advanceState();
        }

        private static void advanceState() {
            if(state++ >= INIT_FINISH) throw new IllegalStateException("Exceeding expected state");
        }
    }

    public static int getState() {
        return System.state;
    }

    static StateManager getManager() {
        return manager;
    }

    protected enum Flag {
        INIT,
        DISCORD_INIT,
        CONTROLLER_INIT
    }
}
