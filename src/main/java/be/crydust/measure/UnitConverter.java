package be.crydust.measure;

import java.math.BigDecimal;
import java.math.MathContext;

public interface UnitConverter {

    UnitConverter inverse();

    BigDecimal convert(BigDecimal value) throws ArithmeticException;

    BigDecimal convert(BigDecimal value, MathContext ctx) throws ArithmeticException;
}
