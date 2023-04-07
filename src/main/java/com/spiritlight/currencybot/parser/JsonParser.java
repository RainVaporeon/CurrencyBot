package com.spiritlight.currencybot.parser;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import java.util.Map;

@FunctionalInterface
public interface JsonParser<K, V> {
    Map<K, V> parse(JsonObject object);

}
