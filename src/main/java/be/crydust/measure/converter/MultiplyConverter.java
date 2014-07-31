package be.crydust.measure.converter;

import be.crydust.measure.UnitConverter;
import java.math.BigDecimal;
import java.math.MathContext;

public class MultiplyConverter implements UnitConverter {

    private final BigDecimal offset;
    private static final BigDecimal ZERO = BigDecimal.ZERO;
    private static final BigDecimal ONE = BigDecimal.ONE;
    private static final MathContext MATHCONTEXT = MathContext.DECIMAL128;

    public MultiplyConverter(BigDecimal offset) {
        if (ZERO.equals(offset)) {
            throw new IllegalArgumentException("offset must not be zero");
        }
        this.offset = offset;
    }

    @Override
    public UnitConverter inverse() {
        return new DivideConverter(offset);
    }

    @Override
    public BigDecimal convert(BigDecimal value) {
        return convert(value, MATHCONTEXT);
    }

    @Override
    public BigDecimal convert(BigDecimal value, MathContext ctx) throws ArithmeticException {
        return value.multiply(offset, ctx);
    }

}
