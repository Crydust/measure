package be.crydust.measure;

import static be.crydust.measure.KitchenUnitSystem.*;
import java.math.BigDecimal;
import static org.hamcrest.Matchers.*;
import org.junit.Test;
import static org.junit.Assert.*;

public class KitchenUnitSystemTest {

    @Test
    public void aMeasureMustBeConvertibleFromZeroKilogramToGram() {
        Measure zeroKilogram = new Measure(BigDecimal.ZERO, KILOGRAM);
        Measure zeroGram = new Measure(BigDecimal.ZERO, GRAM);
        assertThat(zeroKilogram, is(not(zeroGram)));
        Measure convertedToGram = zeroKilogram.convertTo(GRAM);
        assertThat(convertedToGram, is(zeroGram));
        Measure convertedToKilogram = convertedToGram.convertTo(KILOGRAM);
        assertThat(convertedToKilogram, is(zeroKilogram));
    }

    @Test
    public void aMeasureMustBeConvertibleFromOneKilogramToGram() {
        Measure oneKilogram = new Measure(BigDecimal.ONE, KILOGRAM);
        Measure thousandGram = new Measure(new BigDecimal(1000), GRAM);
        assertThat(oneKilogram, is(not(thousandGram)));
        assertThat(oneKilogram.convertTo(GRAM), is(thousandGram));
        assertThat(thousandGram.convertTo(KILOGRAM), is(oneKilogram));
    }

    @Test
    public void aMeasureMustBeConvertibleFromOneCelsiusToFahrenheit() {
        Measure oneCelsius = new Measure(BigDecimal.ONE, CELSIUS);
        Measure thirtyThreePointEightFahrenheit = new Measure(new BigDecimal("33.8"), FAHRENHEIT);
        assertThat(oneCelsius.convertTo(FAHRENHEIT), is(thirtyThreePointEightFahrenheit));
        assertThat(thirtyThreePointEightFahrenheit.convertTo(CELSIUS), is(oneCelsius));
    }

    @Test
    public void aMeasureMustBeConvertibleFromCupToTeaspoon() {
        Measure oneCup = new Measure(BigDecimal.ONE, CUP_US);
        Measure forthyEightTeaspoon = new Measure(new BigDecimal("48"), TEASPOON_US);
        assertThat(oneCup.convertTo(TEASPOON_US), is(forthyEightTeaspoon));
        assertThat(forthyEightTeaspoon.convertTo(CUP_US), is(oneCup));
    }

    @Test
    public void aMeasureMustBeConvertibleFromPoundToGram() {
        Measure onePound = new Measure(BigDecimal.ONE, POUND);
        Measure lotsOfGrams = new Measure(new BigDecimal("453.59237"), GRAM);
        assertThat(onePound.convertTo(GRAM), is(lotsOfGrams));
        assertThat(lotsOfGrams.convertTo(POUND), is(onePound));
    }

    @Test
    public void theKitchenUnitSingletonShouldBeUseful() {
        KitchenUnitSystem kitchen = KitchenUnitSystem.getInstance();
        Measure bodyheatInFahrenheit = new Measure(
                BigDecimal.valueOf(37L), kitchen.getUnit("°C"))
                .convertTo(kitchen.getUnit("°F"));
        assertThat(bodyheatInFahrenheit.toString(), is("98.6 °F"));
    }

}
