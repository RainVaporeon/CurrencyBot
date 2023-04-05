package com.spiritlight.currencybot.internals;

import com.spiritlight.currencybot.Main;
import com.spiritlight.currencybot.bot.SlashCommandListener;
import com.spiritlight.currencybot.bot.commands.QueryCommand;
import com.spiritlight.currencybot.config.Cache;
import com.spiritlight.currencybot.config.Config;
import com.spiritlight.currencybot.misc.Command;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.dv8tion.jda.api.interactions.commands.build.Commands;

import java.io.IOException;
import java.io.UncheckedIOException;

import static com.spiritlight.currencybot.bot.commands.QueryCommand.*;

public final class Internal {
    private static final StateManager manager = new StateManager();

    public static final int INIT = 0;
    public static final int CONFIG_LOAD = 1;
    public static final int CACHE_LOAD = 2;
    public static final int DISCORD_LOAD = 3;
    public static final int INIT_FINISH = 4;
    protected static class System {
        private static int state;

        public static void init() {
            // Ensure that this is really the initialization state
            Internal.manager.flag(Flag.INIT);
            checkState(INIT);
            advanceState();

            // Load config
            checkState(CONFIG_LOAD);
            try {
                Config.read();
            } catch (IOException e) {
                throw new UncheckedIOException(e);
            }
            advanceState();

            // Load cache into memory
            checkState(CACHE_LOAD);
            try {
                Cache.read();
            } catch (IOException e) {
                throw new UncheckedIOException(e);
            }
            advanceState();

            // Boot up discord after caches are finished loading
            checkState(DISCORD_LOAD);
            configureDiscord();
            advanceState();

            // Finalizing
            checkState(INIT_FINISH);

        }

        public static void configureDiscord() {
            Internal.manager.flag(Flag.DISCORD_INIT);

            JDA instance = Main.discord.getJDA();

            CommandData[] data = {
                    Commands.slash(Command.QUERY, "Queries the currency rate")
                            .addOption(OptionType.STRING, PROPERTY_FROM, "The currency to convert from", true)
                            .addOption(OptionType.STRING, PROPERTY_TO, "The currency to convert to", true)
                            .addOption(OptionType.INTEGER, PROPERTY_VALUE, "The value, default is 1", false)
            };

            SlashCommandListener[] commandListeners = {
                    new QueryCommand()
            };

            instance.updateCommands().addCommands(data).queue();
            instance.addEventListener((Object[]) commandListeners);
        }

        private static void advanceState(Runnable... action) {
            if(state++ >= INIT_FINISH) throw new IllegalStateException("Exceeding expected state");
            for(Runnable executors : action) executors.run();
        }

        private static void checkState(int state) {
            if(System.state != state) throw new IllegalStateException("Unexpected state");
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
