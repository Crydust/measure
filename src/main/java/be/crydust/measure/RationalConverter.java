package be.crydust.measure;

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
        BigDecimal decimalDividend = new BigDecimal(dividend);
        BigDecimal decimalDivisor = new BigDecimal(divisor);
        return value
                .multiply(decimalDividend, MATHCONTEXT)
                .divide(decimalDivisor, MATHCONTEXT);
    }
}
