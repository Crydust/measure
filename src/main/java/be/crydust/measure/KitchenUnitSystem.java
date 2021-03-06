package be.crydust.measure;

import java.math.BigDecimal;

public final class KitchenUnitSystem {

    public static final Dimension MASS = new Dimension("Weight", "M");

    public static final Unit KILOGRAM = new Unit(MASS, "Kilogram", "kg");
    public static final Unit GRAM = new DerivedUnit("Gram", "g")
            .of(KILOGRAM).divide(1000).build();

    public static final Unit POUND = new DerivedUnit("Pound (US)", "lb")
            .of(KILOGRAM).rational(45359237, 100000000).build();
    public static final Unit OUNCE_US = new DerivedUnit("Ounce (US)", "oz")
            .of(POUND).divide(16).build();

    public static final Dimension TEMPERATURE = new Dimension("Temperature", "T");

    private static final Unit KELVIN = new Unit(TEMPERATURE, "Kelvin", "K");
    public static final Unit CELSIUS = new DerivedUnit("Celsius", "°C")
            .of(KELVIN).add(new BigDecimal("273.15")).build();
    private static final Unit RANKINE = new DerivedUnit("Rankine", "°R")
            .of(KELVIN).rational(5, 9).build();
    public static final Unit FAHRENHEIT = new DerivedUnit("Fahrenheit", "°F")
            .of(RANKINE).add(new BigDecimal("459.67")).build();

    public static final Dimension VOLUME = new Dimension("Volume", "V");

    private static final Unit CUBIC_METER = new Unit(VOLUME, "Cubic meter", "m³");
    public static final Unit LITER = new DerivedUnit("Liter", "l")
            .of(CUBIC_METER).divide(1000).build();
    public static final Unit DECI_LITER = new DerivedUnit("Deciliter", "dl")
            .of(LITER).divide(10).build();
    public static final Unit CENTI_LITER = new DerivedUnit("Centiliter", "cl")
            .of(LITER).divide(100).build();
    public static final Unit MILLI_LITER = new DerivedUnit("Milliliter", "ml")
            .of(LITER).divide(1000).build();

    private static final Unit CUBIC_INCH = new DerivedUnit("Cubic inch", "in³")
            .of(CUBIC_METER).rational(16387064, 1000000).build();

    public static final Unit GALLON_UK = new DerivedUnit("Gallon (Imperial)", "gal_br")
            .of(LITER).rational(454609, 100000).build();
    public static final Unit QUART_UK = new DerivedUnit("Quart (Imperial)", "qt_br")
            .of(GALLON_UK).divide(4).build();
    public static final Unit PINT_UK = new DerivedUnit("Pint (Imperial)", "pt_br")
            .of(QUART_UK).divide(2).build();
    public static final Unit FLUID_OUNCE_UK = new DerivedUnit("Fluid Ounce (Imperial)", "oz_fl_uk")
            .of(GALLON_UK).divide(160).build();

    public static final Unit GALLON_US = new DerivedUnit("Gallon (US)", "gal_us")
            .of(CUBIC_INCH).multiply(231).build();
    public static final Unit QUART_US = new DerivedUnit("Quart (US)", "qt_us")
            .of(GALLON_US).divide(4).build();
    public static final Unit PINT_US = new DerivedUnit("Pint (US)", "pt_us")
            .of(QUART_US).divide(2).build();
    public static final Unit FLUID_OUNCE_US = new DerivedUnit("Fluid Ounce (US)", "oz_fl_us")
            .of(GALLON_US).divide(128).build();
    public static final Unit TABLESPOON_US = new DerivedUnit("Tablespoon", "tbsp")
            .of(FLUID_OUNCE_US).divide(2).build();
    public static final Unit TEASPOON_US = new DerivedUnit("Teaspoon", "tsp")
            .of(TABLESPOON_US).divide(3).build();
    public static final Unit CUP_US = new DerivedUnit("Cup", "cup")
            .of(TABLESPOON_US).multiply(16).build();
    public static final Unit FLUID_DRAM_US = new DerivedUnit("Dram (US)", "fl_dr")
            .of(FLUID_OUNCE_US).divide(8).build();

    private static final UnitSystem INSTANCE = new UnitSystem()
            .add(MASS)
            .add(POUND, OUNCE_US, KILOGRAM, GRAM)
            .add(TEMPERATURE)
            .add(CELSIUS, FAHRENHEIT)
            .add(VOLUME)
            .add(GALLON_UK, GALLON_US, QUART_US, PINT_US, FLUID_OUNCE_US,
                    CUP_US, TABLESPOON_US, TEASPOON_US, FLUID_DRAM_US, LITER,
                    DECI_LITER, CENTI_LITER, MILLI_LITER, QUART_UK, PINT_UK);

    public static final UnitSystem getInstance() {
        return INSTANCE;
    }

    private KitchenUnitSystem() {
    }

}
