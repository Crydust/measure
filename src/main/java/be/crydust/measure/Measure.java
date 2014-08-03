package be.crydust.measure;

import java.math.BigDecimal;
import java.math.MathContext;
import java.text.DecimalFormat;
import java.util.Objects;

public class Measure implements Comparable<Measure> {

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
        if (unit.isCompatible(otherUnit)) {
            return new Measure(otherUnit.convertFromBase(unit.convertToBase(value)), otherUnit);
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
        assert this.compareTo(other) == 0;
        return true;
    }

    /**
     * Compares this Measure with the specified Measure. Two Measure object that
     * have a different unit, but an equivalent value are considered equal by
     * this method. (this is not the same behavior as equals)
     *
     * @param otherMeasure
     * @return
     */
    @Override
    public int compareTo(Measure otherMeasure) {
        Objects.requireNonNull(otherMeasure);
        int result;
        Dimension dimension = this.unit.getDimension();
        Dimension otherimension = otherMeasure.unit.getDimension();
        if (dimension == null && otherimension == null) {
            result = 0;
        } else if (dimension == null) {
            result = -1;
        } else {
            result = dimension.compareTo(otherimension);
        }
        if (result != 0) {
            return result;
        }
        if (unit.equals(otherMeasure.unit)) {
            result = value.compareTo(otherMeasure.value);
        } else {
            result = unit.convertToBase(value).compareTo(otherMeasure.unit.convertToBase(otherMeasure.value));
        }
        return result;
    }

    @Override
    public String toString() {
        return String.format("%s %s", new DecimalFormat("#.##").format(value), unit.getSymbol());
    }

}
