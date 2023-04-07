package com.spiritlight.currencybot.misc;

import com.google.common.collect.ImmutableMap;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.spiritlight.currencybot.collections.ImmutablePair;
import com.spiritlight.currencybot.collections.Pair;

import javax.annotation.concurrent.Immutable;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Immutable
public class PriceInfo {
    private final Map<String, Double> map;
    private final long time;

    public PriceInfo(Map<String, Double> map, long time) {
        this.map = ImmutableMap.copyOf(map);
        this.time = time;
    }

    /**
     * Gets info about a currency code
     * @param code The currency code
     * @return A pair containing the code as key, and the timestamp as value.
     */
    public Pair<Double, Long> getInfo(String code) {
        return ImmutablePair.of(map.get(code), time);
    }

    public Map<String, Double> getMap() {
        return ImmutableMap.copyOf(map);
    }

    public long getTime() {
        return time;
    }

    public int size() {
        return map.size();
    }

    // Should look something like:
    // {"timestamp": 0, data:[{"code": "USD", "value": 1}]}
    public JsonObject toJson() {

        JsonObject ret = new JsonObject();
        JsonArray array = new JsonArray();

        ret.addProperty("timestamp", time);
        for(Map.Entry<String, Double> entry : map.entrySet()) {
            JsonObject element = new JsonObject();
            element.addProperty("code", entry.getKey());
            element.addProperty("value", entry.getValue());
            array.add(element);
        }

        ret.add("data", array);
        return ret;
    }

    public static PriceInfo fromJson(JsonObject object) {
        Map<String, Double> ret = new HashMap<>();

        long time = object.get("timestamp").getAsLong();
        for(JsonElement element : object.getAsJsonArray("data")) {
            JsonObject o = (JsonObject) element;
            ret.put(o.get("code").getAsString(), o.get("value").getAsDouble());
        }

        return new PriceInfo(ret, time);
    }

    @Override
    public String toString() {
        return this.toJson().toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PriceInfo priceInfo = (PriceInfo) o;
        return time == priceInfo.time && Objects.equals(map, priceInfo.map);
    }

    @Override
    public int hashCode() {
        return Objects.hash(map, time);
    }
}
