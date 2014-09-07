package be.crydust.measure;

import static be.crydust.measure.KitchenUnitSystem.*;
import java.math.BigDecimal;
import static org.hamcrest.Matchers.*;
import org.junit.Test;
import static org.junit.Assert.*;

public class KitchenUnitSystemTest {

    @Test
    public void aMeasureMustBeConvertibleFromZeroKilogramToGram() {
        Quantity zeroKilogram = new Quantity(0.0, KILOGRAM);
        Quantity zeroGram = new Quantity(0.0, GRAM);
        assertThat(zeroKilogram, is(not(zeroGram)));
        Quantity convertedToGram = zeroKilogram.convertTo(GRAM);
        assertThat(convertedToGram.getValue(),
                is(zeroKilogram.getValue()));
        assertThat(convertedToGram.getBigDecimalValue(),
                is(closeTo(zeroKilogram.getBigDecimalValue(), BigDecimal.ZERO)));
        assertThat(convertedToGram, is(zeroGram));
        Quantity convertedToKilogram = convertedToGram.convertTo(KILOGRAM);
        assertThat(convertedToKilogram, is(zeroKilogram));
    }

    @Test
    public void aMeasureMustBeConvertibleFromOneKilogramToGram() {
        Quantity oneKilogram = new Quantity(1.0, KILOGRAM);
        Quantity thousandGram = new Quantity(1000.0, GRAM);
        assertThat(oneKilogram, is(not(thousandGram)));
        assertThat(oneKilogram.convertTo(GRAM), is(thousandGram));
        assertThat(thousandGram.convertTo(KILOGRAM), is(oneKilogram));
    }

    @Test
    public void aMeasureMustBeConvertibleFromOneCelsiusToFahrenheit() {
        Quantity oneCelsius = new Quantity(1.0, CELSIUS);
        Quantity thirtyThreePointEightFahrenheit = new Quantity(33.8, FAHRENHEIT);
        assertThat(oneCelsius.convertTo(FAHRENHEIT).getValue(),
                is(thirtyThreePointEightFahrenheit.getValue()));
        assertThat(thirtyThreePointEightFahrenheit.convertTo(CELSIUS).getValue(),
                is(oneCelsius.getValue()));
    }

    @Test
    public void aMeasureMustBeConvertibleFromCupToTeaspoon() {
        Quantity oneCup = new Quantity(1.0, CUP_US);
        Quantity forthyEightTeaspoon = new Quantity(48, TEASPOON_US);
        assertThat(oneCup.convertTo(TEASPOON_US), is(forthyEightTeaspoon));
        assertThat(forthyEightTeaspoon.convertTo(CUP_US), is(oneCup));
    }

    @Test
    public void aMeasureMustBeConvertibleFromPoundToGram() {
        Quantity onePound = new Quantity(1.0, POUND);
        Quantity lotsOfGrams = new Quantity(453.59237, GRAM);
        assertThat(onePound.convertTo(GRAM), is(lotsOfGrams));
        assertThat(lotsOfGrams.convertTo(POUND), is(onePound));
    }

    @Test
    public void theKitchenUnitSingletonShouldBeUseful() {
        UnitSystem kitchen = KitchenUnitSystem.getInstance();
        Quantity bodyheatInFahrenheit = new Quantity(
                37.0, kitchen.getUnit("°C"))
                .convertTo(kitchen.getUnit("°F"));
        assertThat(bodyheatInFahrenheit.toString(), is("98.6 °F"));
    }

}
