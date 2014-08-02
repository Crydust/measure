package be.crydust.measure.converter;

import be.crydust.measure.BigDecimalHelper;
import be.crydust.measure.UnitConverter;
import java.math.BigDecimal;
import java.math.MathContext;

public class AddConverter implements UnitConverter {

    private final BigDecimal offset;

    public AddConverter(double offset) {
        this(BigDecimalHelper.toBigDecimal(offset));
    }

    public AddConverter(long offset) {
        this(BigDecimalHelper.toBigDecimal(offset));
    }

    public AddConverter(BigDecimal offset) {
        this.offset = offset;
    }

    @Override
    public UnitConverter inverse() {
        return new SubtractConverter(offset);
    }

    @Override
    public double convert(double value) {
        return convert(BigDecimalHelper.toBigDecimal(value)).doubleValue();
    }

    @Override
    public BigDecimal convert(BigDecimal value) {
        return value.add(offset, MathContext.DECIMAL128);
    }

}
