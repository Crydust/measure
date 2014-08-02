package be.crydust.measure;

import java.math.BigDecimal;

public final class BigDecimalHelper {

    private BigDecimalHelper() {
    }

    public static BigDecimal toBigDecimal(double val) {
        assert !Double.isNaN(val);
        assert !Double.isInfinite(val);
        return new BigDecimal(Double.toString(val));
    }

    public static BigDecimal toBigDecimal(long val) {
        return new BigDecimal(val);
    }

}
