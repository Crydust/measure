package be.crydust.measure.converter;

import be.crydust.measure.BigDecimalHelper;
import be.crydust.measure.UnitConverter;
import java.math.BigDecimal;
import java.math.MathContext;

public class SubtractConverter implements UnitConverter {

    private final BigDecimal offset;

    public SubtractConverter(double offset) {
        this(BigDecimalHelper.toBigDecimal(offset));
    }

    public SubtractConverter(long offset) {
        this(BigDecimalHelper.toBigDecimal(offset));
    }

    public SubtractConverter(BigDecimal offset) {
        this.offset = offset;
    }

    @Override
    public UnitConverter inverse() {
        return new AddConverter(offset);
    }

    @Override
    public double convert(double value) {
        return convert(BigDecimalHelper.toBigDecimal(value)).doubleValue();
    }

    @Override
    public BigDecimal convert(BigDecimal value) {
        return value.subtract(offset, MathContext.DECIMAL128);
    }

}
