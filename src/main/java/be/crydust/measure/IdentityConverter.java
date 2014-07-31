package be.crydust.measure;

import java.math.BigDecimal;

public class IdentityConverter implements UnitConverter {

    public IdentityConverter() {
    }

    @Override
    public UnitConverter inverse() {
        return this;
    }

    @Override
    public BigDecimal convert(BigDecimal value) {
        return value;
    }

}
