package be.crydust.measure.converter;

import be.crydust.measure.UnitConverter;
import java.math.BigDecimal;
import java.math.MathContext;

public class DivideConverter implements UnitConverter {

    private final BigDecimal offset;
    private static final BigDecimal ZERO = BigDecimal.ZERO;
    private static final BigDecimal ONE = BigDecimal.ONE;
    private static final MathContext MATHCONTEXT = MathContext.DECIMAL128;

    public DivideConverter(BigDecimal offset) {
        if (ZERO.equals(offset)) {
            throw new IllegalArgumentException("offset must not be zero");
        }
        this.offset = offset;
    }

    @Override
    public UnitConverter inverse() {
        return new MultiplyConverter(offset);
    }

    @Override
    public BigDecimal convert(BigDecimal value) {
        return convert(value, MATHCONTEXT);
    }

    @Override
    public BigDecimal convert(BigDecimal value, MathContext ctx) throws ArithmeticException {
        return value.divide(offset, ctx);
    }
}
