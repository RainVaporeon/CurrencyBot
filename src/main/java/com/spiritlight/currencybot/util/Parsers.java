package com.spiritlight.currencybot.util;

import com.google.gson.JsonObject;
import com.spiritlight.currencybot.parser.JsonParser;

import java.util.HashMap;
import java.util.Map;

public class Parsers {
    // Parses given code and value
    public static final JsonParser<String, Double> CURRENCY_API = json -> {
        Map<String, Double> currencyMap = new HashMap<>();

        JsonObject structure = json.getAsJsonObject("data");

        for(String element : structure.keySet()) {
            JsonObject currency = structure.getAsJsonObject(element);
            currencyMap.put(element, currency.get("value").getAsDouble());
        }

        return currencyMap;
    };

    public static JsonParser<String, Double> currencyApi() {
        return CURRENCY_API;
    }
}
