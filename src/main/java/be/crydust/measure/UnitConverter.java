package be.crydust.measure;

import java.math.BigDecimal;

public interface UnitConverter {

    UnitConverter inverse();

    BigDecimal convert(BigDecimal value);
}
