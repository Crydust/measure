package be.crydust.measure;

import java.math.BigDecimal;
import java.math.MathContext;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Measure {

    private final BigDecimal value;
    private final Unit unit;

    public Measure(double value, Unit unit) {
        this(BigDecimalHelper.toBigDecimal(value), unit);
    }

    public Measure(BigDecimal value, Unit unit) {
        assert value != null;
        assert unit != null;
        if (value.compareTo(BigDecimal.ZERO) == 0) {
            this.value = BigDecimal.ZERO;
        } else {
            this.value = value.stripTrailingZeros();
        }
        this.unit = unit;
    }

    public Measure convertTo(Unit otherUnit) {
        assert otherUnit != null;
        if (unit.equals(otherUnit)) {
            return this;
        }
        if (!unit.getDimension().equals(otherUnit.getDimension())) {
            throw new IllegalArgumentException("cannot convert to unit with other dimension");
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

    public double getValue() {
        return value.doubleValue();
    }

    public BigDecimal getBigDecimalValue() {
        return value;
    }

    public Unit getUnit() {
        return unit;
    }

    public Measure add(Measure augend) {
        return new Measure(this.value.add(augend.convertTo(this.unit).value, MathContext.DECIMAL128), this.unit);
    }

    public Measure subtract(Measure subtrahend) {
        return new Measure(this.value.subtract(subtrahend.convertTo(this.unit).value, MathContext.DECIMAL128), this.unit);
    }

    public Measure divide(double divisor) {
        return divide(BigDecimalHelper.toBigDecimal(divisor));
    }

    public Measure divide(long divisor) {
        return divide(BigDecimalHelper.toBigDecimal(divisor));
    }

    public Measure divide(BigDecimal divisor) {
        return new Measure(this.value.divide(divisor, MathContext.DECIMAL128), this.unit);
    }

    public Measure multiply(double multiplicand) {
        return divide(BigDecimalHelper.toBigDecimal(multiplicand));
    }

    public Measure multiply(long multiplicand) {
        return divide(BigDecimalHelper.toBigDecimal(multiplicand));
    }

    public Measure multiply(BigDecimal multiplicand) {
        return new Measure(this.value.multiply(multiplicand, MathContext.DECIMAL128), this.unit);
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 89 * hash + Objects.hashCode(this.unit);
        if (value == null) {
            hash = 89 * hash + 0;
        } else {
            hash = 89 * hash + Objects.hashCode(this.value.toString());
        }
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
        if (!Objects.equals(this.unit, other.unit)) {
            return false;
        }
        if (this.value != other.value) {
            if (this.value != null && other.value != null) {
                if (this.value.subtract(other.value, MathContext.DECIMAL128)
                        .abs()
                        .compareTo(BigDecimal.ZERO) > 0) {
                    return false;
                }
            } else if (!(this.value == null && other.value == null)) {
                return false;
            }
        }
        assert this.hashCode() == obj.hashCode();
        return true;
    }

    @Override
    public String toString() {
        return String.format("%s %s", new DecimalFormat("#.##").format(value), unit.getSymbol());
    }

}
