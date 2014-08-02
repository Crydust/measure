package be.crydust.measure.converter;

import be.crydust.measure.BigDecimalHelper;
import be.crydust.measure.UnitConverter;
import java.math.BigDecimal;
import java.math.MathContext;

public class MultiplyConverter implements UnitConverter {

    private final BigDecimal offset;

    public MultiplyConverter(double offset) {
        this(BigDecimalHelper.toBigDecimal(offset));
    }

    public MultiplyConverter(long offset) {
        this(BigDecimalHelper.toBigDecimal(offset));
    }

    public MultiplyConverter(BigDecimal offset) {
        if (offset.compareTo(BigDecimal.ZERO) == 0) {
            throw new IllegalArgumentException("offset must not be zero");
        }
        this.offset = offset;
    }

    @Override
    public UnitConverter inverse() {
        return new DivideConverter(offset);
    }

    @Override
    public double convert(double value) {
        return convert(BigDecimalHelper.toBigDecimal(value)).doubleValue();
    }

    @Override
    public BigDecimal convert(BigDecimal value) {
        return value.multiply(offset, MathContext.DECIMAL128);
    }

}
