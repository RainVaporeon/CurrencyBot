package com.spiritlight.currencybot.util;

@FunctionalInterface
public interface StringFunction<R> {

    R apply(String context);
}
