package com.spiritlight.currencybot.bot;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.hooks.EventListener;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.requests.restaction.CommandListUpdateAction;
import net.dv8tion.jda.api.utils.MemberCachePolicy;
import net.dv8tion.jda.api.utils.cache.CacheFlag;

public class Discord {
    private final JDA jda;

    public Discord(String token) {
        try {
            this.jda = JDABuilder.createDefault(token)
                    .enableIntents(GatewayIntent.DIRECT_MESSAGES)
                    .enableIntents(GatewayIntent.GUILD_MESSAGES)
                    .enableIntents(GatewayIntent.GUILD_MEMBERS)
                    .disableCache(CacheFlag.VOICE_STATE)
                    .disableCache(CacheFlag.EMOJI)
                    .disableCache(CacheFlag.STICKER)
                    .setMemberCachePolicy(MemberCachePolicy.ALL)
                    .setAutoReconnect(true)
                    .build().awaitReady();
        } catch (InterruptedException e) {
            throw new IllegalThreadStateException();
        }
    }

    public JDA getJDA() {
        return jda;
    }

    public void addListeners(EventListener... listeners) {
        jda.addEventListener((Object[]) listeners);
    }

    public void addCommand(CommandData data) {
        this.jda.upsertCommand(data).queue();
    }

    public void addCommands(CommandData... data) {
        CommandListUpdateAction action = this.jda.updateCommands();
        action.addCommands(data).queue();
    }

}
