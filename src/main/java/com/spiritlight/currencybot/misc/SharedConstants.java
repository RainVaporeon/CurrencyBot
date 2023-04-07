package com.spiritlight.currencybot.misc;

import com.spiritlight.currencybot.collections.Pair;

public class SharedConstants {

    public static final String CURRENCY_API = "https://api.currencyapi.com/v3/latest?apikey={0}";

    @SafeVarargs
    public static String getCurrencyApi(String token, Pair<String, String>... parameters) {
        String base = CURRENCY_API.replace("{0}", token);
        if(parameters == null) return base;
        StringBuilder sb = new StringBuilder(base);
        for(Pair<String, String> param : parameters) {
            sb.append("&").append(param.getKey()).append("=").append(param.getValue());
        }
        return sb.toString();
    }
}
