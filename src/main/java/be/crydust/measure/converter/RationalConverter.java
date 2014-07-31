package be.crydust.measure.converter;

import be.crydust.measure.UnitConverter;

public class RationalConverter implements UnitConverter {

    private final double dividend;
    private final double divisor;

    public RationalConverter(double dividend, double divisor) {
        if (divisor == 0.0) {
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
    public double convert(double value) {
        return value * dividend / divisor;
    }
}
