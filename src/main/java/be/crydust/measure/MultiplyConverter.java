package be.crydust.measure;

import java.math.BigDecimal;
import java.math.MathContext;

public class MultiplyConverter implements UnitConverter {

    private final BigDecimal offset;
    private static final BigDecimal ONE = BigDecimal.ONE;
    private static final MathContext MATHCONTEXT = MathContext.DECIMAL128;

    public MultiplyConverter(BigDecimal offset) {
        this.offset = offset;
    }

    @Override
    public UnitConverter inverse() {
        return new MultiplyConverter(ONE.divide(offset, MATHCONTEXT));
    }

    @Override
    public BigDecimal convert(BigDecimal value) {
        return value.multiply(offset, MATHCONTEXT);
    }

}
