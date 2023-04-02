package com.spiritlight.currencybot.bot;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.hooks.EventListener;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.utils.MemberCachePolicy;
import net.dv8tion.jda.api.utils.cache.CacheFlag;

import javax.security.auth.login.LoginException;

public class Discord {
    private final JDA jda;

    public Discord(String token) throws LoginException {
        this.jda = JDABuilder.createDefault(token)
                .enableIntents(GatewayIntent.DIRECT_MESSAGES)
                .enableIntents(GatewayIntent.GUILD_MESSAGES)
                .enableIntents(GatewayIntent.GUILD_MEMBERS)
                .disableCache(CacheFlag.VOICE_STATE)
                .disableCache(CacheFlag.EMOTE)
                .setMemberCachePolicy(MemberCachePolicy.ALL)
                .setAutoReconnect(true)
                .build();
    }

    public void addListener(EventListener... listeners) {
        for(EventListener listener : listeners) {
            jda.addEventListener(listener);
        }
    }


}
