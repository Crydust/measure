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

Adding Measures together works too:

    // 8 lb 5 oz in g
    Dimension mass = new Dimension("Weight", "M");
    Unit kg = new Unit(mass, "Kilogram", "kg");
    Unit g = new Unit(mass, "Gram", "g", kg, new DivideConverter(1000));
    Unit lb = new Unit(mass, "Pound", "lb", kg, new RationalConverter(45359237, 100000000));
    Unit oz = new Unit(mass, "Ounce", "oz", lb, new DivideConverter(16));
    Measure grams = new Measure(8, lb).add(new Measure(5, oz)).convertTo(g);
    assertThat(grams.toString(), is("3770.49 g"));
    assertThat(grams, allOf(
            greaterThan(new Measure(3770.45, g)),
            lessThan(new Measure(3770.50, g)),
            is(new Measure(new BigDecimal("3770.486575625"), g))
    ));

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
