package be.crydust.measure;

public interface UnitConverter {

    UnitConverter inverse();

    double convert(double value);

}
