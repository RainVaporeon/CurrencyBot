package com.spiritlight.currencybot.util;

public class StringUtils {
    public static String defaultOrNull(String message, String ifNull) {
        return message == null ? ifNull : message;
    }

    public static String parse(String message) {
        return defaultOrNull(message, "null");
    }
}
