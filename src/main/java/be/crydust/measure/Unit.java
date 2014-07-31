package be.crydust.measure;

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

    Unit getParent() {
        return parent == null ? this : parent;
    }

    public List<UnitConverter> getConverters() {
        return this.converters;
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
        if (!Objects.equals(this.symbol, other.symbol)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Unit{" + "description=" + description + ", symbol=" + symbol + '}';
    }

}
