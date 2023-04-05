package com.spiritlight.currencybot.collections;

import java.util.Objects;

public abstract class AbstractPair<K, V> implements Pair<K, V> {

    protected K key;
    protected V value;

    public AbstractPair(K key, V value) {
        this.key = key;
        this.value = value;
    }

    public AbstractPair(AbstractPair<K, V> pair) {
        this.key = pair.key;
        this.value = pair.value;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AbstractPair<?, ?> that = (AbstractPair<?, ?>) o;
        return Objects.equals(key, that.key) && Objects.equals(value, that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(key, value);
    }
}
