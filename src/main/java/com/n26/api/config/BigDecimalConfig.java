package com.n26.api.config;

import java.math.RoundingMode;

public class BigDecimalConfig{
    private BigDecimalConfig(){}

    public static final RoundingMode ROUNDING_MODE = RoundingMode.HALF_UP;
    public static final int DECIMAL_PLACES = 2;
}
