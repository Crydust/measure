package be.crydust.measure.converter;

import be.crydust.measure.UnitConverter;

public class AddConverter implements UnitConverter {

    private final double offset;

    public AddConverter(double offset) {
        this.offset = offset;
    }

    @Override
    public UnitConverter inverse() {
        return new SubtractConverter(offset);
    }

    @Override
    public double convert(double value) {
        return value + offset;
    }

}
