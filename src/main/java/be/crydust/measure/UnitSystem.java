package be.crydust.measure;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

public class UnitSystem {

    private final Map<String, Dimension> symbolToDimensionMap;
    private final Map<String, Unit> symbolToUnitMap;
    private final Map<Dimension, Set<Unit>> dimensionToUnits;
    private final Set<Dimension> dimensions;
    private final Set<Unit> units;

    public UnitSystem() {
        symbolToDimensionMap = new LinkedHashMap<>();
        symbolToUnitMap = new LinkedHashMap<>();
        dimensionToUnits = new LinkedHashMap<>();
        dimensions = new LinkedHashSet<>();
        units = new LinkedHashSet<>();
    }

    public UnitSystem add(Dimension dimension) {
        if (dimensions.contains(dimension)) {
            throw new IllegalArgumentException("dimensions must be unique");
        }
        symbolToDimensionMap.put(dimension.getSymbol(), dimension);
        dimensionToUnits.put(dimension, new LinkedHashSet<Unit>());
        dimensions.add(dimension);
        return this;
    }

    public UnitSystem add(Unit unit) {
        if (units.contains(unit)) {
            throw new IllegalArgumentException("units must be unique");
        }
        if (!dimensions.contains(unit.getDimension())) {
            throw new IllegalArgumentException("unit has unknown dimension");
        }
        symbolToUnitMap.put(unit.getSymbol(), unit);
        dimensionToUnits.get(unit.getDimension()).add(unit);
        units.add(unit);
        return this;
    }

    public UnitSystem add(Dimension... dimensions) {
        for (Dimension dimension : dimensions) {
            add(dimension);
        }
        return this;
    }

    public UnitSystem add(Unit... units) {
        for (Unit unit : units) {
            add(unit);
        }
        return this;
    }

    public Dimension getDimension(String symbol) {
        return symbolToDimensionMap.get(symbol);
    }

    public Unit getUnit(String symbol) {
        return symbolToUnitMap.get(symbol);
    }

    public Set<Unit> getUnitsByDimension(String symbol) {
        Dimension dimension = getDimension(symbol);
        return getUnitsByDimension(dimension);
    }

    public Set<Unit> getUnitsByDimension(Dimension dimension) {
        return Collections.unmodifiableSet(dimensionToUnits.get(dimension));
    }

    public Set<Dimension> getDimensions() {
        return Collections.unmodifiableSet(dimensions);
    }

    public Set<Unit> getUnits() {
        return Collections.unmodifiableSet(units);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Dimension dimension : dimensions) {
            sb.append(" * ").append(dimension.getSymbol()).append(" = ").append(dimension.getDescription()).append(":\n");
            for (Unit unit : getUnitsByDimension(dimension)) {
                sb.append("    * ").append(unit.getSymbol()).append(" = ").append(unit.getDescription()).append("\n");
            }
        }
        return sb.toString();
    }

}
