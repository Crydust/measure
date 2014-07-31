package be.crydust.measure;

import be.crydust.measure.converter.AddConverter;
import be.crydust.measure.converter.DivideConverter;
import be.crydust.measure.converter.MultiplyConverter;
import be.crydust.measure.converter.RationalConverter;
import java.math.BigDecimal;
import java.math.BigInteger;

public final class KitchenUnitSystem extends UnitSystem {

    public static final Dimension MASS = new Dimension("Weight", "M");

    public static final Unit KILOGRAM = new Unit(MASS, "Kilogram", "kg");
    public static final Unit GRAM = new Unit(MASS, "Gram", "g",
            KILOGRAM,
            new DivideConverter(BigDecimal.valueOf(1000L)));
    public static final Unit POUND = new Unit(MASS, "Pound (US)", "lb",
            KILOGRAM,
            new RationalConverter(
                    BigInteger.valueOf(45359237L),
                    BigInteger.valueOf(100000000L)));
    public static final Unit OUNCE_US = new Unit(MASS, "Ounce (US)", "oz",
            POUND,
            new DivideConverter(BigDecimal.valueOf(16L)));

    public static final Dimension TEMPERATURE = new Dimension("Temperature", "T");

    public static final Unit KELVIN = new Unit(TEMPERATURE, "Kelvin", "K");
    public static final Unit CELSIUS = new Unit(TEMPERATURE, "Celsius", "°C",
            KELVIN,
            new AddConverter(new BigDecimal("273.15")));
    public static final Unit RANKINE = new Unit(TEMPERATURE, "Rankine", "°R",
            KELVIN,
            new RationalConverter(
                    BigInteger.valueOf(5L),
                    BigInteger.valueOf(9L)));
    public static final Unit FAHRENHEIT = new Unit(TEMPERATURE, "Fahrenheit", "°F",
            RANKINE,
            new AddConverter(new BigDecimal("459.67")));

    public static final Dimension VOLUME = new Dimension("Volume", "V");

    public static final Unit CUBIC_METER = new Unit(VOLUME, "Cubic meter", "m³");
    public static final Unit LITER = new Unit(VOLUME, "Liter", "l",
            CUBIC_METER,
            new DivideConverter(BigDecimal.valueOf(1000L)));
    public static final Unit DECI_LITER = new Unit(VOLUME, "Deciliter", "dl",
            LITER,
            new DivideConverter(BigDecimal.valueOf(10L)));
    public static final Unit CENTI_LITER = new Unit(VOLUME, "Centiliter", "cl",
            LITER,
            new DivideConverter(BigDecimal.valueOf(100L)));
    public static final Unit MILLI_LITER = new Unit(VOLUME, "Milliliter", "ml",
            LITER,
            new DivideConverter(BigDecimal.valueOf(1000L)));

    public static final Unit CUBIC_INCH = new Unit(VOLUME, "Cubic inch", "in³",
            CUBIC_METER,
            new RationalConverter(
                    BigInteger.valueOf(16387064L),
                    BigInteger.valueOf(1000000L)));

    public static final Unit GALLON_UK = new Unit(VOLUME, "Gallon (Imperial)", "gal_br",
            LITER,
            new RationalConverter(
                    BigInteger.valueOf(454609L),
                    BigInteger.valueOf(100000L)));
    public static final Unit QUART_UK = new Unit(VOLUME, "Quart (Imperial)", "qt_br",
            GALLON_UK,
            new DivideConverter(BigDecimal.valueOf(4L)));
    public static final Unit PINT_UK = new Unit(VOLUME, "Pint (Imperial)", "pt_br",
            QUART_UK,
            new DivideConverter(BigDecimal.valueOf(2L)));
    public static final Unit FLUID_OUNCE_UK = new Unit(VOLUME, "Fluid Ounce (Imperial)", "oz_fl_uk",
            GALLON_UK,
            new DivideConverter(BigDecimal.valueOf(160L)));

    public static final Unit GALLON_US = new Unit(VOLUME, "Gallon (US)", "gal_us",
            CUBIC_INCH,
            new MultiplyConverter(BigDecimal.valueOf(231L)));
    public static final Unit QUART_US = new Unit(VOLUME, "Quart (US)", "qt_us",
            GALLON_US,
            new DivideConverter(BigDecimal.valueOf(4L)));
    public static final Unit PINT_US = new Unit(VOLUME, "Pint (US)", "pt_us",
            QUART_US,
            new DivideConverter(BigDecimal.valueOf(2L)));
    public static final Unit FLUID_OUNCE_US = new Unit(VOLUME, "Fluid Ounce (US)", "oz_fl_us",
            GALLON_US,
            new DivideConverter(BigDecimal.valueOf(128L)));
    public static final Unit TABLESPOON_US = new Unit(VOLUME, "Tablespoon", "tbsp",
            FLUID_OUNCE_US, new DivideConverter(BigDecimal.valueOf(2L)));
    public static final Unit TEASPOON_US = new Unit(VOLUME, "Teaspoon", "tsp",
            TABLESPOON_US,
            new DivideConverter(BigDecimal.valueOf(3L)));
    public static final Unit CUP_US = new Unit(VOLUME, "Cup", "cup",
            TABLESPOON_US,
            new MultiplyConverter(BigDecimal.valueOf(16L)));
    public static final Unit FLUID_DRAM_US = new Unit(VOLUME, "Dram (US)", "fl_dr",
            FLUID_OUNCE_US,
            new DivideConverter(BigDecimal.valueOf(8L)));

    private static final KitchenUnitSystem INSTANCE = new KitchenUnitSystem();

    public static final KitchenUnitSystem getInstance() {
        return INSTANCE;
    }

    private KitchenUnitSystem() {
        super();
        add(MASS)
                .add(POUND)
                .add(OUNCE_US)
                .add(KILOGRAM)
                .add(GRAM);

        add(TEMPERATURE)
                .add(CELSIUS)
                .add(FAHRENHEIT)
                .add(KELVIN)
                .add(RANKINE);

        add(VOLUME)
                .add(GALLON_UK)
                .add(GALLON_US)
                .add(QUART_US)
                .add(PINT_US)
                .add(FLUID_OUNCE_US)
                .add(CUP_US)
                .add(TABLESPOON_US)
                .add(TEASPOON_US)
                .add(FLUID_DRAM_US)
                .add(LITER)
                .add(DECI_LITER)
                .add(CENTI_LITER)
                .add(MILLI_LITER)
                .add(CUBIC_METER)
                .add(CUBIC_INCH)
                .add(QUART_UK)
                .add(PINT_UK);
    }

}
