package be.crydust.measure;

import static be.crydust.measure.KitchenUnitSystem.*;
import java.math.BigDecimal;
import static org.hamcrest.Matchers.*;
import org.junit.Test;
import static org.junit.Assert.*;

public class KitchenUnitSystemTest {

    @Test
    public void aMeasureMustBeConvertibleFromZeroKilogramToGram() {
        Measure zeroKilogram = new Measure(0.0, KILOGRAM);
        Measure zeroGram = new Measure(0.0, GRAM);
        assertThat(zeroKilogram, is(not(zeroGram)));
        Measure convertedToGram = zeroKilogram.convertTo(GRAM);
        assertThat(convertedToGram.getValue(),
                is(zeroKilogram.getValue()));
        assertThat(convertedToGram.getBigDecimalValue(),
                is(closeTo(zeroKilogram.getBigDecimalValue(), BigDecimal.ZERO)));
        assertThat(convertedToGram, is(zeroGram));
        Measure convertedToKilogram = convertedToGram.convertTo(KILOGRAM);
        assertThat(convertedToKilogram, is(zeroKilogram));
    }

    @Test
    public void aMeasureMustBeConvertibleFromOneKilogramToGram() {
        Measure oneKilogram = new Measure(1.0, KILOGRAM);
        Measure thousandGram = new Measure(1000.0, GRAM);
        assertThat(oneKilogram, is(not(thousandGram)));
        assertThat(oneKilogram.convertTo(GRAM), is(thousandGram));
        assertThat(thousandGram.convertTo(KILOGRAM), is(oneKilogram));
    }

    @Test
    public void aMeasureMustBeConvertibleFromOneCelsiusToFahrenheit() {
        Measure oneCelsius = new Measure(1.0, CELSIUS);
        Measure thirtyThreePointEightFahrenheit = new Measure(33.8, FAHRENHEIT);
        assertThat(oneCelsius.convertTo(FAHRENHEIT).getValue(),
                is(thirtyThreePointEightFahrenheit.getValue()));
        assertThat(thirtyThreePointEightFahrenheit.convertTo(CELSIUS).getValue(),
                is(oneCelsius.getValue()));
    }

    @Test
    public void aMeasureMustBeConvertibleFromCupToTeaspoon() {
        Measure oneCup = new Measure(1.0, CUP_US);
        Measure forthyEightTeaspoon = new Measure(48, TEASPOON_US);
        assertThat(oneCup.convertTo(TEASPOON_US), is(forthyEightTeaspoon));
        assertThat(forthyEightTeaspoon.convertTo(CUP_US), is(oneCup));
    }

    @Test
    public void aMeasureMustBeConvertibleFromPoundToGram() {
        Measure onePound = new Measure(1.0, POUND);
        Measure lotsOfGrams = new Measure(453.59237, GRAM);
        assertThat(onePound.convertTo(GRAM), is(lotsOfGrams));
        assertThat(lotsOfGrams.convertTo(POUND), is(onePound));
    }

    @Test
    public void theKitchenUnitSingletonShouldBeUseful() {
        KitchenUnitSystem kitchen = KitchenUnitSystem.getInstance();
        Measure bodyheatInFahrenheit = new Measure(
                37.0, kitchen.getUnit("°C"))
                .convertTo(kitchen.getUnit("°F"));
        assertThat(bodyheatInFahrenheit.toString(), is("98.6 °F"));
    }

}
