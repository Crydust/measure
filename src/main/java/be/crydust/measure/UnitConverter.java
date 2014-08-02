package be.crydust.measure;

import java.math.BigDecimal;

public interface UnitConverter {

    UnitConverter inverse();

    double convert(double value);

    public BigDecimal convert(BigDecimal value);
}
