package ru.cft.focusstart.task2.utils;

import org.apache.commons.math3.util.Precision;

public enum RoundingFormat {
    TWO_DECIMALS_FORMAT(2);

    private final int decimalsAfterDot;

    RoundingFormat(int decimalsAfterDot) {
        this.decimalsAfterDot = decimalsAfterDot;
    }

    public double format(double value) {
        return Precision.round(value, decimalsAfterDot);
    }
}
