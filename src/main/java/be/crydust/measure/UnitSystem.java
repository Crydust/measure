package be.crydust.measure;

import java.util.HashSet;
import java.util.Set;

public class UnitSystem {

    private final Set<Dimension> dimensions;
    private final Set<Unit> units;

    public UnitSystem() {
        dimensions = new HashSet<>();
        units = new HashSet<>();
    }

    public UnitSystem add(Dimension dimension) {
        if (dimensions.contains(dimension)) {
            throw new IllegalArgumentException("dimensions must be unique");
        }
        dimensions.add(dimension);
        return this;
    }

    public UnitSystem add(Unit unit) {
        if (units.contains(unit)) {
            throw new IllegalArgumentException("units must be a unique");
        }
        if (!dimensions.contains(unit.getDimension())) {
            throw new IllegalArgumentException("unit has unknown dimension");
        }
        units.add(unit);
        return this;
    }

}
