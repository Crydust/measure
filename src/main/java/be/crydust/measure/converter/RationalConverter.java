package be.crydust.measure.converter;

import be.crydust.measure.BigDecimalHelper;
import be.crydust.measure.UnitConverter;
import java.math.BigDecimal;
import java.math.MathContext;

public class RationalConverter implements UnitConverter {

    private final BigDecimal dividend;
    private final BigDecimal divisor;

    public RationalConverter(double dividend, double divisor) {
        this(BigDecimalHelper.toBigDecimal(dividend),
                BigDecimalHelper.toBigDecimal(divisor));
    }

    public RationalConverter(long dividend, long divisor) {
        this(BigDecimalHelper.toBigDecimal(dividend),
                BigDecimalHelper.toBigDecimal(divisor));
    }

    public RationalConverter(BigDecimal dividend, BigDecimal divisor) {
        if (dividend.compareTo(BigDecimal.ZERO) == 0) {
            throw new IllegalArgumentException("dividend must not be zero");
        }
        if (divisor.compareTo(BigDecimal.ZERO) == 0) {
            throw new IllegalArgumentException("divisor must not be zero");
        }
        this.dividend = dividend;
        this.divisor = divisor;
    }

    @Override
    public UnitConverter inverse() {
        return new RationalConverter(divisor, dividend);
    }

    @Override
    public double convert(double value) {
        return convert(BigDecimalHelper.toBigDecimal(value)).doubleValue();
    }

    @Override
    public BigDecimal convert(BigDecimal value) {
        return value.multiply(dividend, MathContext.DECIMAL128)
                .divide(divisor, MathContext.DECIMAL128);
    }
}
