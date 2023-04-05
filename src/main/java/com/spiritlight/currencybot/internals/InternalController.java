package com.spiritlight.currencybot.internals;

import java.util.Objects;
import java.util.UUID;

/**
 * A class for interacting with the internal system code in this program.
 * <p>
 * The key should not be shared at all, unless the
 */
public final class InternalController {
    public static final InternalController instance = new InternalController();

    private final AccessKey key = new AccessKey();
    private boolean checkKey = true;

    private InternalController() {

    }

    /**
     * @return The access key to this object. Almost all method
     * calls require the returned key in order to execute.
     * @apiNote You may disable the checking mechanism by calling
     * {@link InternalController#disableKeyChecking(AccessKey)}
     */
    public AccessKey initController() {
        Internal.getManager().flag(Internal.Flag.CONTROLLER_INIT);
        return key;
    }

    public void init(AccessKey key) {
        checkKey(key);
        Internal.System.init();
    }

    /**
     * Enables key checking. This is enabled by default and
     * enabling it again will throw an exception instead.
     */
    public void enableKeyChecking() {
        if(checkKey) throw new IllegalStateException("Cannot enable an already enabled feature.");
        this.checkKey = true;
    }

    /**
     * Disables key checking. This requires a key in order to
     * execute the action.
     * @param key The key to verify the action.
     */
    public void disableKeyChecking(AccessKey key) {
        checkKey(key);
        this.checkKey = false;
    }

    public void tick(AccessKey key) {
        checkKey(key);
        System.out.println("Ticked!");
    }

    private void checkKey(AccessKey key) {
        if(!checkKey) return;
        this.key.check(key);
    }

    /**
     * An object that acts as a sort of access key. Each key have
     * a UUID denoting its key content.
     * <p>
     * Although it is possible to use this key outside internal purposes,
     * it's advised that one make their own implementation instead.
     */
    public static class AccessKey {
        private final UUID key;

        public AccessKey() {
            this.key = UUID.randomUUID();
        }

        public void check(UUID key) {
            if(this.key == null) throw new IllegalStateException("Key-checking is disabled.");
            if(!this.key.equals(key)) throw new SecurityException("Access Denied");
        }

        public void check(AccessKey key) {
            this.check(key.key);
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            AccessKey accessKey = (AccessKey) o;
            return key.equals(accessKey.key);
        }

        @Override
        public int hashCode() {
            return Objects.hash(key);
        }
    }
}
