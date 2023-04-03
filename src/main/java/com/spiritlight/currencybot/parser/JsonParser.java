package com.spiritlight.currencybot.parser;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import java.util.Map;


public interface JsonParser<K, V> {
    Map<K, V> parse(JsonObject object);

    default Map<K, V> parse(String json) throws JsonParseException {
        return parse(new Gson().fromJson(json, JsonObject.class));
    }
}
