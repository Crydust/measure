measure
=============

A simple replacement for jsr-275, but with a slightly different api.

The commandline app can be used as such:

    java -jar measure.jar 37 °C °F

The KitchenUnitSystem class can be used when you need a String -> Unit mapping.
Obviously You can create your own UnitSystems, KitchenUnitSystem is merely an example.
Each Unit has a unique symbol, so store that in the database when persisting Units.

    KitchenUnitSystem kitchen = KitchenUnitSystem.getInstance();
    Measure bodyheatInFahrenheit = new Measure(
            BigDecimal.valueOf(37L), kitchen.getUnit("°C"))
            .convertTo(kitchen.getUnit("°F"));
    System.out.println(bodyheatInFahrenheit.toString());
    // prints "98.6 °F"

These are the supported dimensions and units:

 * M = Weight:
    * lb = Pound (US)
    * oz = Ounce (US)
    * kg = Kilogram
    * g = Gram
 * T = Temperature:
    * °C = Celsius
    * °F = Fahrenheit
    * K = Kelvin
    * °R = Rankine
 * V = Volume:
    * gal_br = Gallon (Imperial)
    * gal_us = Gallon (US)
    * qt_us = Quart (US)
    * pt_us = Pint (US)
    * oz_fl_us = Fluid Ounce (US)
    * cup = Cup
    * tbsp = Tablespoon
    * tsp = Teaspoon
    * fl_dr = Dram (US)
    * l = Liter
    * dl = Deciliter
    * cl = Centiliter
    * ml = Milliliter
    * m³ = Cubic meter
    * in³ = Cubic inch
    * qt_br = Quart (Imperial)
    * pt_br = Pint (Imperial)
