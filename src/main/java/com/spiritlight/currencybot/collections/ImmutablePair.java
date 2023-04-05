package com.spiritlight.currencybot.collections;

public class ImmutablePair<K, V> extends AbstractPair<K, V> {

    public ImmutablePair(K key, V value) {
        super(key, value);
    }

    @Override
    public void setKey(K key) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void setValue(V value) {
        throw new UnsupportedOperationException();
    }

    public static <K, V> ImmutablePair<K, V> of(K key, V value) {
        return new ImmutablePair<>(key, value);
    }

    public MutablePair<K, V> toMutable() {
        return MutablePair.of(key, value);
    }
}
