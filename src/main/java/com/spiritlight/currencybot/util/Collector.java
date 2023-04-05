package com.spiritlight.currencybot.util;

@FunctionalInterface
public interface Collector<T> {

    /**
     * Singleton action that runs something and returns something else.
     */
    T collect();
}
