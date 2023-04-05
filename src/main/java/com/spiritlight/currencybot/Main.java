package com.spiritlight.currencybot;

import com.spiritlight.currencybot.bot.Discord;
import com.spiritlight.currencybot.config.Config;
import com.spiritlight.currencybot.internals.InternalController;
import com.spiritlight.currencybot.misc.PriceInfo;
import com.spiritlight.currencybot.util.Collector;
import com.spiritlight.currencybot.util.PriceCollector;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Main {
    private static final InternalController.AccessKey accessKey;

    public static final Discord discord;

    private static final Collector<PriceInfo> collector;

    // This is almost always collected and loaded by order, that is,
    // when accessing the first element in this list, the data is
    // the oldest, and when accessing the last one, it's latest.
    private static final List<PriceInfo> info = new LinkedList<>();

    static {
        accessKey = InternalController.instance.initController();

        try {
            Config.read();
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }

        try {
            discord = new Discord(Config.TOKEN.get());
        } catch (Exception e) {
            throw new ExceptionInInitializerError(e);
        }

        collector = new PriceCollector();
    }

    private static final ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();
    public static void main(String[] args) {
        InternalController.instance.init(accessKey);

        // We are setting a 60 seconds limit from initialization to finish in order to not waste the free rates.
        // For anyone that has a valid key that has more than this limit, just change 60 to 0, and 86400 to something
        // shorter if you want more scans (collector, delay before starting, delay between executions, time unit)
        executor.scheduleAtFixedRate(() -> appendInfo(collector.collect()), 60, 86400, TimeUnit.SECONDS);
    }

    public static List<PriceInfo> getPriceInfo() {
        return List.copyOf(info);
    }

    public static boolean prepared() { return !info.isEmpty(); }

    public static void appendInfo(PriceInfo pi) {
        System.out.println("Info collected at size " + pi.size());
        info.add(pi);
    }

    public static void removeInfo(PriceInfo pi) {
        info.remove(pi);
    }
}