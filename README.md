measure
=============

A simple replacement for jsr-275, but with a slightly different api.

The commandline app can be used as such:

    $ java -jar measure.jar 37 °C °F
    37 °C = 98.6 °F

The KitchenUnitSystem class can be used when you need a String -> Unit mapping.
Obviously You can create your own UnitSystems, KitchenUnitSystem is merely an example.
Each Unit has a unique symbol, so store that in the database when persisting Units.

    Measure bodyheatInFahrenheit = new Measure(
            37.0, KitchenUnitSystem.CELSIUS)
            .convertTo(KitchenUnitSystem.FAHRENHEIT);
    System.out.println(bodyheatInFahrenheit.toString());
    // prints "98.6 °F"

    KitchenUnitSystem kitchen = KitchenUnitSystem.getInstance();
    double input = 37.0;
    Unit celcius = kitchen.getUnit("°C");
    Unit fahrenheit = kitchen.getUnit("°F");
    Measure bodyheatInFahrenheit = new Measure(input, celcius)
            .convertTo(fahrenheit);
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
    * qt_br = Quart (Imperial)
    * pt_br = Pint (Imperial)
