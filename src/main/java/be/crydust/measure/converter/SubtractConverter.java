package be.crydust.measure.converter;

import be.crydust.measure.UnitConverter;

public class SubtractConverter implements UnitConverter {

    private final double offset;

    public SubtractConverter(double offset) {
        this.offset = offset;
    }

    @Override
    public UnitConverter inverse() {
        return new AddConverter(offset);
    }

    @Override
    public double convert(double value) {
        return value - offset;
    }

}
