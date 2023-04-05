package com.spiritlight.currencybot.util;

public class LockedReference<T> {
    private final Object lock;

    private T field;

    /**
     * Creates a reference that is locked with a given lock.
     * This field holds an object and setting the field
     * can only be achieved if and only if the lock
     * provided when creating this object was used.
     *
     * @param field The field for this object to hold
     * @param lock The lock, or {@code null} if no further mutation is needed
     */
    public LockedReference(T field, Object lock) {
        this.field = field;
        this.lock = lock;
    }

    public T get() {
        return field;
    }

    public void set(T field, Object lock) {
        if(this.lock == null || !this.lock.equals(lock)) throw new IllegalArgumentException();
        this.field = field;
    }
}
