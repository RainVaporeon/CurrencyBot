package com.spiritlight.currencybot.util;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.spiritlight.currencybot.config.Config;
import com.spiritlight.currencybot.connection.Connection;
import com.spiritlight.currencybot.misc.PriceInfo;
import com.spiritlight.currencybot.misc.SharedConstants;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.time.Instant;
import java.util.Map;

public class PriceCollector implements Collector<PriceInfo> {

    // Collects as USD
    @Override
    public PriceInfo collect() {
        String content;
        try {
            content = Connection.get(SharedConstants.getCurrencyApi(Config.API_KEY.get(), Map.of("base_currency", "USD")));
        } catch (IOException e) { throw new UncheckedIOException(e); }

        JsonObject o = new Gson().fromJson(content, JsonObject.class);
        Map<String, Double> result = Parsers.currencyApi().parse(o);

        String timestamp = o.getAsJsonObject("meta").get("last_updated_at").getAsString();
        long time = Instant.parse(timestamp).toEpochMilli();

        return new PriceInfo(result, time);
    }
}
