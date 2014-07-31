package be.crydust.measure;

import java.math.BigDecimal;

public class App {

    public static void main(String[] args) {
        KitchenUnitSystem kitchen = KitchenUnitSystem.getInstance();
        if (args.length != 3) {
            System.err.println("Usage:");
            System.err.println("java -jar measure.jar 1 °C °F");
            System.err.println("The available units are:");
            System.err.println(kitchen);
            throw new IllegalArgumentException("three arguments expected");
        }
        final String originalValueAsString = args[0];
        final String originalUnitSymbol = args[1];
        final String convertedUnitSymbol = args[2];
        Measure original = new Measure(
                new BigDecimal(originalValueAsString),
                kitchen.getUnit(originalUnitSymbol));
        Measure converted = original
                .convertTo(kitchen.getUnit(convertedUnitSymbol));
        System.out.printf("%s = %s%n", original, converted);
    }
}
