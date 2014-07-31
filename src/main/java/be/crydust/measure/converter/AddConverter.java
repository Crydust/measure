package be.crydust.measure.converter;

import be.crydust.measure.UnitConverter;
import java.math.BigDecimal;
import java.math.MathContext;

public class AddConverter implements UnitConverter {

    private final BigDecimal offset;
    private static final MathContext MATHCONTEXT = MathContext.DECIMAL128;

    public AddConverter(BigDecimal offset) {
        this.offset = offset;
    }

    @Override
    public UnitConverter inverse() {
        return new SubtractConverter(offset);
    }

    @Override
    public BigDecimal convert(BigDecimal value) throws ArithmeticException {
        return convert(value, MATHCONTEXT);
    }

    @Override
    public BigDecimal convert(BigDecimal value, MathContext ctx) throws ArithmeticException {
        return value.add(offset, ctx);
    }
}
