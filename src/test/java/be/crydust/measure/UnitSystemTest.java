package be.crydust.measure;

import static be.crydust.measure.KitchenUnitSystem.*;
import org.junit.Test;

/**
 *
 * @author kristof
 */
public class UnitSystemTest {
    
    @Test(expected = java.lang.IllegalArgumentException.class)
    public void aDimensionNeedsToBeUnique() {
        new UnitSystem().add(MASS).add(MASS);
    }
    
    @Test(expected = java.lang.IllegalArgumentException.class)
    public void aUnitNeedsToBeUnique() {
        UnitSystem metric = new UnitSystem();
        metric.add(MASS).add(KILOGRAM).add(KILOGRAM);
    }
    
    @Test(expected = java.lang.IllegalArgumentException.class)
    public void aUnitCanOnlyBeAddedForKnownDimensions() {
        new UnitSystem().add(KILOGRAM);
    }
}
