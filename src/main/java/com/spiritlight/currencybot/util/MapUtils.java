package com.spiritlight.currencybot.util;

import com.spiritlight.currencybot.collections.ImmutablePair;
import com.spiritlight.currencybot.collections.Pair;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class MapUtils {

    public static <K, V> Pair<K, V> toPair(Map.Entry<K, V> entry) {
        return ImmutablePair.of(entry.getKey(), entry.getValue());
    }

    public static <K, V> List<Pair<K, V>> toPair(Map<K, V> map) {
        List<Pair<K, V>> ret = new LinkedList<>();
        for(Map.Entry<K, V> entry : map.entrySet()) {
            ret.add(MapUtils.toPair(entry));
        }
        return ret;
    }
}
