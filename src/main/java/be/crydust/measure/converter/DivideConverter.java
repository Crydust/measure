package be.crydust.measure.converter;

import be.crydust.measure.UnitConverter;

public class DivideConverter implements UnitConverter {

    private final double offset;

    public DivideConverter(double offset) {
        if (offset == 0.0) {
            throw new IllegalArgumentException("offset must not be zero");
        }
        this.offset = offset;
    }

    @Override
    public UnitConverter inverse() {
        return new MultiplyConverter(offset);
    }

    @Override
    public double convert(double value) {
        return value / offset;
    }
}
