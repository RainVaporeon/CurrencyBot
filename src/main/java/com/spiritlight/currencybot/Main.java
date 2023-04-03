package com.spiritlight.currencybot;

import com.spiritlight.currencybot.bot.Discord;
import com.spiritlight.currencybot.config.Config;
import com.spiritlight.currencybot.internals.InternalController;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.lang.reflect.Field;

public class Main {
    private static final InternalController.AccessKey accessKey;

    public static final Discord discord;

    static {
        accessKey = InternalController.instance.initController();

        try {
            Config.read();
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }

        try {
            discord = new Discord(Config.TOKEN);
        } catch (Exception e) {
            throw new ExceptionInInitializerError(e);
        }
    }

    public static void main(String[] args) {

    }

}