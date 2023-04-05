package com.spiritlight.currencybot.util;

import com.spiritlight.currencybot.Main;
import com.spiritlight.currencybot.collections.Pair;
import com.spiritlight.currencybot.misc.PriceInfo;

public class PriceHelper {
    /**
     * A potentially lossy conversion from a currency to another
     * @param from The currency to convert from
     * @param to The currency to convert to
     * @param value The value of the currency converting from
     * @return The value of {value} {from} currency in {to} currency
     * @apiNote The API, normally, picks USD as its preferred method
     * of obtaining the value. Therefore, the calculation is in such way:
     * <p></p>
     * <pre>
     * {@code
     * double value = [The given currency quantity]
     * double from = [The value of the currency, exchanged by 1 USD]
     * double to = [The value of the currency, exchanged by 1 USD]
     *
     * double multiplier = to / from;
     * return value * multiplier;
     * }</pre>
     * <p></p>
     *
     * In other words, for example, 1 USD may be exchanged for 5 Currency A
     * and 10 Currency B, by calling {@code convert("A", "B", 1)}, the calculation
     * would be as following: <p></p>
     *
     * {@code (10 / 5) * 1 = 2.0}, denoting that 1 Currency A is worth 2 Currency B.
     */
    public static double convert(String from, String to, double value) {
        PriceInfo pi = Main.getPriceInfo().get(Main.getPriceInfo().size() - 1);
        Pair<Double, Long> fromPair = pi.getInfo(from);
        Pair<Double, Long> toPair = pi.getInfo(to);
        double multiplier = toPair.getKey() / fromPair.getKey();
        return value * multiplier;
    }
}
