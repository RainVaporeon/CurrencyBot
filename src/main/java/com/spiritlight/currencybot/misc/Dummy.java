package com.spiritlight.currencybot.misc;

import java.io.Closeable;
import java.io.IOException;

public class Dummy {

    public static class DummyCloseable implements AutoCloseable {
        @Override
        public void close() {

        }
    }
}
