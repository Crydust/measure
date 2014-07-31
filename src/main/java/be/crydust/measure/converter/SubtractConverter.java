package be.crydust.measure.converter;

import be.crydust.measure.UnitConverter;
import java.math.BigDecimal;
import java.math.MathContext;

public class SubtractConverter implements UnitConverter {

    private final BigDecimal offset;
    private static final MathContext MATHCONTEXT = MathContext.DECIMAL128;

    public SubtractConverter(BigDecimal offset) {
        this.offset = offset;
    }

    @Override
    public UnitConverter inverse() {
        return new AddConverter(offset);
    }

    @Override
    public BigDecimal convert(BigDecimal value) throws ArithmeticException {
        return convert(value, MATHCONTEXT);
    }

    @Override
    public BigDecimal convert(BigDecimal value, MathContext ctx) throws ArithmeticException {
        return value.subtract(offset, ctx);
    }

}
