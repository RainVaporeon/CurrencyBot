package com.spiritlight.currencybot.collections;

public class MutablePair<K, V> implements Pair<K, V> {

    private K key;
    private V value;

    public MutablePair(K key, V value) {
        this.key = key;
        this.value = value;
    }

    @Override
    public K getKey() {
        return key;
    }

    @Override
    public V getValue() {
        return value;
    }

    @Override
    public void setKey(K key) {
        this.key = key;
    }

    @Override
    public void setValue(V value) {
        this.value = value;
    }

    public static <K, V> MutablePair<K, V> of(K key, V value) {
        return new MutablePair<>(key, value);
    }

    public ImmutablePair<K, V> toImmutable() {
        return ImmutablePair.of(key, value);
    }
}
