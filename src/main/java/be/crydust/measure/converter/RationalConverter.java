package be.crydust.measure.converter;

import be.crydust.measure.UnitConverter;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.MathContext;

public class RationalConverter implements UnitConverter {
    
    private final BigInteger dividend;
    private final BigInteger divisor;
    private static final MathContext MATHCONTEXT = MathContext.DECIMAL128;

    public RationalConverter(BigInteger dividend, BigInteger divisor) {
        if (BigInteger.ZERO.equals(divisor)){
            throw new IllegalArgumentException("Zero divisor");
        }
        this.dividend = dividend;
        this.divisor = divisor;
    }

    @Override
    public UnitConverter inverse() {
        return new RationalConverter(divisor, dividend);
    }

    @Override
    public BigDecimal convert(BigDecimal value) {
        return convert(value, MATHCONTEXT);
    }

    @Override
    public BigDecimal convert(BigDecimal value, MathContext ctx) throws ArithmeticException {
        BigDecimal decimalDividend = new BigDecimal(dividend);
        BigDecimal decimalDivisor = new BigDecimal(divisor);
        return value
                .multiply(decimalDividend, ctx)
                .divide(decimalDivisor, ctx);
    }
}
