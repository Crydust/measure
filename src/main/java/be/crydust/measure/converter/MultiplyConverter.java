package be.crydust.measure.converter;

import be.crydust.measure.UnitConverter;

public class MultiplyConverter implements UnitConverter {

    private final double offset;

    public MultiplyConverter(double offset) {
        if (offset == 0.0) {
            throw new IllegalArgumentException("offset must not be zero");
        }
        this.offset = offset;
    }

    @Override
    public UnitConverter inverse() {
        return new DivideConverter(offset);
    }

    @Override
    public double convert(double value) {
        return value * offset;
    }

}
