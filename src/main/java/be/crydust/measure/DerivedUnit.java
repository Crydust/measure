package be.crydust.measure;

import be.crydust.measure.converter.AddConverter;
import be.crydust.measure.converter.DivideConverter;
import be.crydust.measure.converter.MultiplyConverter;
import be.crydust.measure.converter.RationalConverter;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class DerivedUnit {

    private Dimension dimension = null;
    private String description = null;
    private String symbol = null;
    private Unit parent = null;
    private final List<UnitConverter> converters = new ArrayList<>();

    public DerivedUnit(String description, String symbol) {
        this.description = description;
        this.symbol = symbol;
    }

    private boolean isValid() {
        return dimension != null
                && description != null && !description.isEmpty()
                && symbol != null && !symbol.isEmpty()
                && parent != null && dimension.equals(parent.getDimension())
                && !converters.isEmpty();
    }

    public DerivedUnit of(Unit parent) {
        this.parent = parent;
        if (dimension == null) {
            dimension = parent.getDimension();
        }
        return this;
    }

    public DerivedUnit multiply(BigDecimal offset) {
        converters.add(new MultiplyConverter(offset));
        return this;
    }

    public DerivedUnit multiply(long offset) {
        converters.add(new MultiplyConverter(offset));
        return this;
    }

    public DerivedUnit multiply(double offset) {
        converters.add(new MultiplyConverter(offset));
        return this;
    }

    public DerivedUnit divide(BigDecimal offset) {
        converters.add(new DivideConverter(offset));
        return this;
    }

    public DerivedUnit divide(long offset) {
        converters.add(new DivideConverter(offset));
        return this;
    }

    public DerivedUnit divide(double offset) {
        converters.add(new DivideConverter(offset));
        return this;
    }

    public DerivedUnit add(BigDecimal offset) {
        converters.add(new AddConverter(offset));
        return this;
    }

    public DerivedUnit add(long offset) {
        converters.add(new AddConverter(offset));
        return this;
    }

    public DerivedUnit add(double offset) {
        converters.add(new AddConverter(offset));
        return this;
    }

    public DerivedUnit subtract(double offset) {
        converters.add(new AddConverter(offset));
        return this;
    }

    public DerivedUnit rational(BigDecimal dividend, BigDecimal divisor) {
        converters.add(new RationalConverter(dividend, divisor));
        return this;
    }

    public DerivedUnit rational(long dividend, long divisor) {
        converters.add(new RationalConverter(dividend, divisor));
        return this;
    }

    public DerivedUnit rational(double dividend, double divisor) {
        converters.add(new RationalConverter(dividend, divisor));
        return this;
    }

    public Unit build() {
        if (!isValid()) {
            throw new IllegalStateException();
        }
        return new Unit(dimension, description, symbol, parent, converters.toArray(new UnitConverter[converters.size()]));
    }
}
