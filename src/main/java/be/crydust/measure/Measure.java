package be.crydust.measure;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Measure {

    private final BigDecimal value;
    private final Unit unit;

    public Measure(BigDecimal value, Unit unit) {
        this.value = value;
        this.unit = unit;
    }

    public Measure convertTo(Unit otherUnit) {
        if (unit.equals(otherUnit)) {
            return this;
        }
        List<UnitConverter> unitConverters = new ArrayList<>(unit.getConverters());
        Unit parent = unit.getParent();
        while (!parent.equals(parent.getParent())) {
            unitConverters.addAll(0, parent.getConverters());
            parent = parent.getParent();
        }
        List<UnitConverter> otherUnitConverters = new ArrayList<>(otherUnit.getConverters());
        Unit otherParent = otherUnit.getParent();
        while (!otherParent.equals(otherParent.getParent())) {
            otherUnitConverters.addAll(0, otherParent.getConverters());
            otherParent = otherParent.getParent();
        }
        if (parent.equals(otherParent)) {
            BigDecimal convertedValue = value;
            // convert to parent
            for (int i = unitConverters.size() - 1; i >= 0; i--) {
                convertedValue = unitConverters.get(i)
                        .convert(convertedValue);
            }
            // convert from parent to other unit
            for (UnitConverter otherUnitConverter : otherUnitConverters) {
                convertedValue = otherUnitConverter.inverse()
                        .convert(convertedValue);
            }
            return new Measure(convertedValue, otherUnit);
        }
        throw new IllegalArgumentException("cannot convert to unit with other parent");
    }

    public BigDecimal getValue() {
        return value;
    }

    public Unit getUnit() {
        return unit;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 89 * hash + Objects.hashCode(this.value);
        hash = 89 * hash + Objects.hashCode(this.unit);
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
        final Measure other = (Measure) obj;
        if (!Objects.equals(this.value, other.value)) {
            // close enough?
            //if (!(this.value.subtract(other.value, MathContext.DECIMAL128).abs().subtract(DELTA, MathContext.DECIMAL128).stripTrailingZeros().compareTo(BigDecimal.ZERO) <= 0)) {
            return false;
            //}
        }
        if (!Objects.equals(this.unit, other.unit)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return String.format("%s %s", value.toPlainString(), unit.getSymbol());
    }

}
