package com.spiritlight.currencybot.bot;

import net.dv8tion.jda.api.events.GenericEvent;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.EventListener;
import org.jetbrains.annotations.NotNull;

@FunctionalInterface
public interface SlashCommandListener extends EventListener {

    void onSlashCommand(SlashCommandInteractionEvent event);

    @Override
    default void onEvent(@NotNull GenericEvent event) {
        if(event instanceof SlashCommandInteractionEvent) this.onSlashCommand((SlashCommandInteractionEvent) event);
    }
}
