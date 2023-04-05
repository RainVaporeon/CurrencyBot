package com.spiritlight.currencybot.collections;

import java.io.Serializable;
import java.util.Comparator;
import java.util.function.BiFunction;

public interface Pair<K, V> extends Serializable {
    K getKey();

    V getValue();

    void setKey(K key);

    void setValue(V value);

    static <K, V> Pair<K, V> of(K key, V value) {
        return ImmutablePair.of(key, value);
    }



    default <R> R operate(BiFunction<K, V, R> function) {
        return function.apply(getKey(), getValue());
    }
}
