package be.crydust.measure;

import java.math.BigDecimal;
import java.math.MathContext;

public class AddConverter implements UnitConverter {

    private final BigDecimal offset;
    private static final BigDecimal MINUS_ONE = new BigDecimal("-1");
    private static final MathContext MATHCONTEXT = MathContext.DECIMAL128;

    public AddConverter(BigDecimal offset) {
        this.offset = offset;
    }

    @Override
    public UnitConverter inverse() {
        return new AddConverter(offset.multiply(MINUS_ONE, MATHCONTEXT));
    }

    @Override
    public BigDecimal convert(BigDecimal value) throws ArithmeticException {
        return value.add(offset, MATHCONTEXT);
    }
}
