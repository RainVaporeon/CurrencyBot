package com.spiritlight.currencybot;

import com.spiritlight.currencybot.bot.Discord;
import com.spiritlight.currencybot.config.Config;
import com.spiritlight.currencybot.internals.Internal;
import com.spiritlight.currencybot.internals.InternalController;
import com.spiritlight.currencybot.misc.Dummy;

import javax.security.auth.login.LoginException;
import java.io.Closeable;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

public class Main {
    private static final InternalController.AccessKey accessKey;

    public static final Discord discord =null;

    static {
        accessKey = InternalController.instance.initController();

        try {
            Config.read();
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }

        try {
            // discord = new Discord(Config.TOKEN);
        } catch (Exception e) {
            throw new ExceptionInInitializerError(e);
        }
    }

    public static void main(String[] args) {
        InternalController.instance.init(accessKey);
        try {
            Field f = InternalController.instance.getClass().getDeclaredField("key");
            f.setAccessible(true);
            InternalController.instance.tick((InternalController.AccessKey) f.get(InternalController.instance));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}