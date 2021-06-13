package ru.cft.focusstart.task4;

import java.math.BigInteger;
import java.util.List;

public class FunctionUtil {
    private FunctionUtil() {
    }

    public static BigInteger sequentialPow(List<BigInteger> numbers) {
        BigInteger acc = BigInteger.ZERO;
        for (BigInteger value : numbers) {
            acc = acc.add(value.pow(2));
        }
        return acc;
    }
}
