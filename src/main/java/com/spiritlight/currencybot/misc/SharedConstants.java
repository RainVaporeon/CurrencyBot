package com.spiritlight.currencybot.misc;

import java.util.Map;

public class SharedConstants {
    // Ratelimit: 300/mo (average 10/day)
    public static final String CURRENCY_API = "https://api.currencyapi.com/v3/latest?apikey={0}";

    public static String getCurrencyApi(String token, Map<String, String> parameters) {
        String base = CURRENCY_API.replace("{0}", token);
        if(parameters == null) return base;
        StringBuilder sb = new StringBuilder(base);
        if(!parameters.isEmpty()) sb.append("&");
        for(Map.Entry<String, String> param : parameters.entrySet()) {
            sb.append(param.getKey()).append("=").append(param.getValue());
            sb.append("&");
        }
        return sb.toString();
    }
}
