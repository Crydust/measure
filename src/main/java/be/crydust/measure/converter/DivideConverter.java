package be.crydust.measure.converter;

import be.crydust.measure.BigDecimalHelper;
import be.crydust.measure.UnitConverter;
import java.math.BigDecimal;
import java.math.MathContext;

public class DivideConverter implements UnitConverter {

    private final BigDecimal offset;

    public DivideConverter(double offset) {
        this(BigDecimalHelper.toBigDecimal(offset));
    }

    public DivideConverter(long offset) {
        this(BigDecimalHelper.toBigDecimal(offset));
    }

    public DivideConverter(BigDecimal offset) {
        if (offset.compareTo(BigDecimal.ZERO) == 0) {
            throw new IllegalArgumentException("offset must not be zero");
        }
        this.offset = offset;
    }

    @Override
    public UnitConverter inverse() {
        return new MultiplyConverter(offset);
    }

    @Override
    public double convert(double value) {
        return convert(BigDecimalHelper.toBigDecimal(value)).doubleValue();
    }

    @Override
    public BigDecimal convert(BigDecimal value) {
        return value.divide(offset, MathContext.DECIMAL128);
    }
}
