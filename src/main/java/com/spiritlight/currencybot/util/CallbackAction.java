package com.spiritlight.currencybot.util;


import java.util.function.Consumer;

public class CallbackAction<T> {
    private final T t;

    public CallbackAction(T t) {
        this.t = t;
    }

    public T asObject() {
        return t;
    }

    public void then(Consumer<T> action) {
        action.accept(t);
    }

    public static <T> CallbackAction<T> create(T t) {
        return new CallbackAction<>(t);
    }
}
