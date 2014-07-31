package be.crydust.measure;

import java.math.BigDecimal;
import java.math.BigInteger;
import static org.hamcrest.Matchers.*;
import org.junit.Test;
import static org.junit.Assert.*;

public class AppTest {

    Dimension MASS = new Dimension("mass");
    Dimension TEMPERATURE = new Dimension("temperature");
    Dimension VOLUME = new Dimension("volume");

    Unit KILOGRAM = new Unit(MASS, "Kilogram", "kg");
    Unit GRAM = new Unit(MASS, "Gram", "g", KILOGRAM,
            new MultiplyConverter(new BigDecimal("1E-3")));

    Unit KELVIN = new Unit(TEMPERATURE, "Kelvin", "K");
    Unit CELSIUS = new Unit(TEMPERATURE, "Celsius", "°C", KELVIN,
            new AddConverter(new BigDecimal("273.15")));
    Unit RANKINE = new Unit(TEMPERATURE, "Rankine", "°R", KELVIN,
            new RationalConverter(
                    BigInteger.valueOf(5L),
                    BigInteger.valueOf(9L)));
    Unit FAHRENHEIT = new Unit(TEMPERATURE, "Fahrenheit", "°F", RANKINE,
            new AddConverter(new BigDecimal("459.67")));

    Unit CUBIC_METER = new Unit(VOLUME, "Cubic meter", "m3");

    @Test(expected = java.lang.IllegalArgumentException.class)
    public void aDimensionNeedsToBeUnique() {
        new UnitSystem().add(MASS).add(MASS);
    }

    @Test(expected = java.lang.IllegalArgumentException.class)
    public void aUnitNeedsToBeUnique() {
        UnitSystem metric = new UnitSystem();
        metric.add(MASS).add(KILOGRAM).add(KILOGRAM);
    }

    @Test
    public void aMeasureMustBeConvertibleFromZeroKilogramToGram() {
        Measure zeroKilogram = new Measure(BigDecimal.ZERO, KILOGRAM);
        Measure zeroGram = new Measure(BigDecimal.ZERO, GRAM);
        assertThat(zeroKilogram, is(not(zeroGram)));
        Measure convertedToGram = zeroKilogram.convertTo(GRAM);
        measureIsCloseTo(convertedToGram, zeroGram);
        Measure convertedToKilogram = convertedToGram.convertTo(KILOGRAM);
        measureIsCloseTo(convertedToKilogram, zeroKilogram);
    }

    @Test
    public void aMeasureMustBeConvertibleFromOneKilogramToGram() {
        Measure oneKilogram = new Measure(BigDecimal.ONE, KILOGRAM);
        Measure thousandGram = new Measure(new BigDecimal(1000), GRAM);
        assertThat(oneKilogram, is(not(thousandGram)));
        measureIsCloseTo(oneKilogram.convertTo(GRAM), thousandGram);
        measureIsCloseTo(thousandGram.convertTo(KILOGRAM), oneKilogram);
    }

    @Test
    public void aMeasureMustBeConvertibleFromOneCelsiusToFahrenheit() {
        Measure oneCelsius = new Measure(BigDecimal.ONE, CELSIUS);
        Measure thirtyThreePointEightFahrenheit = new Measure(new BigDecimal("33.8"), FAHRENHEIT);
        measureIsCloseTo(oneCelsius.convertTo(FAHRENHEIT), thirtyThreePointEightFahrenheit);
        measureIsCloseTo(thirtyThreePointEightFahrenheit.convertTo(CELSIUS), oneCelsius);
    }

    private static void measureIsCloseTo(Measure a, Measure b) {
        if (a.equals(b) || !a.getUnit().equals(b.getUnit())) {
            assertThat(a, is(b));
            return;
        }
        assertThat(a.getValue(), is(closeTo(b.getValue(), new BigDecimal("1E-15"))));
    }

}
