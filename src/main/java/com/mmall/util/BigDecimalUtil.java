package com.mmall.util;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * @author Admin
 */
public class BigDecimalUtil {

    private BigDecimalUtil() {

    }

    public static BigDecimal add(double v1, double v2) {
        String val1 = Double.toString(v1);
        String val2 = Double.toString(v2);
        BigDecimal b1 = new BigDecimal(val1);
        BigDecimal b2 = new BigDecimal(val2);
        return b1.add(b2);
    }

    public static BigDecimal sub(double v1, double v2) {
        String val1 = Double.toString(v1);
        String val2 = Double.toString(v2);
        BigDecimal b1 = new BigDecimal(val1);
        BigDecimal b2 = new BigDecimal(val2);
        return b1.subtract(b2);
    }

    public static BigDecimal mul(double v1, double v2) {
        String val1 = Double.toString(v1);
        String val2 = Double.toString(v2);
        BigDecimal b1 = new BigDecimal(val1);
        BigDecimal b2 = new BigDecimal(val2);
        return b1.multiply(b2);
    }

    public static BigDecimal div(double v1, double v2) {
        String val1 = Double.toString(v1);
        String val2 = Double.toString(v2);
        BigDecimal b1 = new BigDecimal(val1);
        BigDecimal b2 = new BigDecimal(val2);
        //除不尽的情况
        return b1.divide(b2, 2, BigDecimal.ROUND_HALF_UP);
    }


}
