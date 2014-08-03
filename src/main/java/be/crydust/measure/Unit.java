package be.crydust.measure;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class Unit {

    private final Dimension dimension;
    private final String description;
    private final String symbol;
    private final Unit parent;
    private final List<UnitConverter> converters;

    public Unit(Dimension dimension, String description, String symbol) {
        this(dimension, description, symbol, null);
    }

    public Unit(Dimension dimension, String description, String symbol, Unit parent, UnitConverter... converters) {
        assert dimension != null;
        assert description != null && !description.isEmpty();
        assert symbol != null && !symbol.isEmpty();
        assert converters != null;
        assert (parent == null && converters.length == 0)
                || (parent != null && dimension.equals(parent.getDimension()));
        this.dimension = dimension;
        this.symbol = symbol;
        this.description = description;
        this.parent = parent;
        if (parent == null) {
            this.converters = Collections.emptyList();
        } else {
            this.converters = Collections.unmodifiableList(Arrays.asList(converters));
        }
    }

    public Dimension getDimension() {
        return dimension;
    }

    public String getDescription() {
        return description;
    }

    public String getSymbol() {
        return symbol;
    }

    public boolean isCompatible(Unit otherUnit) {
        if (this.equals(otherUnit)) {
            return true;
        }
        return dimension.equals(otherUnit.dimension)
                && getBase().equals(otherUnit.getBase());
    }

    private Unit getBase() {
        Unit base = this;
        while (base.parent != null && !base.equals(base.parent)) {
            base = base.parent;
        }
        return base;
    }

    private List<UnitConverter> getBaseConverters() {
        List<UnitConverter> baseConverters = new ArrayList<>(converters);
        Unit base = this.parent;
        while (base != null && !base.equals(base.parent)) {
            baseConverters.addAll(0, base.converters);
            base = base.parent;
        }
        return Collections.unmodifiableList(baseConverters);
    }

    public BigDecimal convertToBase(BigDecimal value) {
        List<UnitConverter> unitConverters = getBaseConverters();
        BigDecimal convertedValue = value;
        for (int i = unitConverters.size() - 1; i >= 0; i--) {
            convertedValue = unitConverters.get(i)
                    .convert(convertedValue);
        }
        return convertedValue;
    }

    public BigDecimal convertFromBase(BigDecimal value) {
        List<UnitConverter> unitConverters = getBaseConverters();
        BigDecimal convertedValue = value;
        for (UnitConverter unitConverter : unitConverters) {
            convertedValue = unitConverter
                    .inverse()
                    .convert(convertedValue);
        }
        return convertedValue;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 73 * hash + Objects.hashCode(this.symbol);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Unit other = (Unit) obj;
        return Objects.equals(this.symbol, other.symbol);
    }

    @Override
    public String toString() {
        return "Unit{" + "description=" + description + ", symbol=" + symbol + '}';
    }

}
