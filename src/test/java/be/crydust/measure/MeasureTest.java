package be.crydust.measure;

import org.junit.Before;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import be.crydust.measure.converter.AddConverter;
import be.crydust.measure.converter.DivideConverter;
import be.crydust.measure.converter.MultiplyConverter;
import be.crydust.measure.converter.RationalConverter;
import java.math.BigDecimal;
import static org.hamcrest.Matchers.*;
import org.junit.Test;
import static org.junit.Assert.*;

public class MeasureTest {

    @Mock
    Unit unit;

    @Before
    public void initMocks() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void twoMeasuresWithZeroValueAreEqualTheyMustHaveTheSameHashcode() {
        Measure a = new Measure(BigDecimal.ZERO, unit);
        Measure b = new Measure(new BigDecimal("0"), unit);
        Measure c = new Measure(new BigDecimal("0.00"), unit);
        Measure d = new Measure(new BigDecimal("0.000"), unit);
        Measure e = new Measure(new BigDecimal("0E3"), unit);
        assertThat(a, is(b));
        assertThat(a.hashCode(), is(b.hashCode()));
        assertThat(b, is(a));
        assertThat(b.hashCode(), is(a.hashCode()));
        assertThat(a, is(c));
        assertThat(a.hashCode(), is(c.hashCode()));
        assertThat(a, is(d));
        assertThat(a.hashCode(), is(d.hashCode()));
        assertThat(a, is(e));
        assertThat(a.hashCode(), is(e.hashCode()));
    }

    @Test
    public void whenTwoMeasuresAreEqualTheyMustHaveTheSameHashcode() {
        Measure a = new Measure(BigDecimal.ONE, unit);
        Measure b = new Measure(new BigDecimal("1"), unit);
        Measure c = new Measure(new BigDecimal("1.00"), unit);
        Measure d = new Measure(new BigDecimal("1.000"), unit);
        Measure e = new Measure(new BigDecimal("0.00100E3"), unit);
        assertThat(a, is(b));
        assertThat(a.hashCode(), is(b.hashCode()));
        assertThat(b, is(a));
        assertThat(b.hashCode(), is(a.hashCode()));
        assertThat(a, is(c));
        assertThat(a.hashCode(), is(c.hashCode()));
        assertThat(a, is(d));
        assertThat(a.hashCode(), is(d.hashCode()));
        assertThat(a, is(e));
        assertThat(a.hashCode(), is(e.hashCode()));
    }

    @Test
    public void testAddNonLinear() {
        Dimension d = new Dimension("d", "d");
        Unit ua = new Unit(d, "a", "a");
        Unit ub = new Unit(d, "b", "b", ua, new AddConverter(3));
        Unit uc = new Unit(d, "c", "c", ub, new AddConverter(5));
        // 1 + 3 = 4
        assertThat(new Measure(1, ub).convertTo(ua).getValue(), is(4.0));
        // 1 + 5 = 6
        assertThat(new Measure(1, uc).convertTo(ub).getValue(), is(6.0));
        // 1 + 5 + 3 = 9
        assertThat(new Measure(1, uc).convertTo(ua).getValue(), is(9.0));
        // 1 ub + 1 uc = 7 ub
        // 1 ub + 6 ub = 7 ub = 10 ua
        assertThat((new Measure(1, ub).add(new Measure(1, uc))),
                is(new Measure(7, ub)));
        assertThat((new Measure(1, ub).add(new Measure(6, ub))),
                is(new Measure(7, ub)));
        assertThat((new Measure(1, ub).add(new Measure(1, uc))).convertTo(ua),
                is(new Measure(10, ua)));
    }

    @Test
    public void testAddLinear() {
        Dimension d = new Dimension("d", "d");
        Unit ua = new Unit(d, "a", "a");
        Unit ub = new Unit(d, "b", "b", ua, new MultiplyConverter(3));
        Unit uc = new Unit(d, "c", "c", ub, new MultiplyConverter(5));
        // 1 * 3 = 3
        assertThat(new Measure(1, ub).convertTo(ua).getValue(), is(3.0));
        // 1 * 5 = 5
        assertThat(new Measure(1, uc).convertTo(ub).getValue(), is(5.0));
        // 1 * 5 * 3 = 15
        assertThat(new Measure(1, uc).convertTo(ua).getValue(), is(15.0));
        // 1 ub + 1 uc = 6 ub
        // 1 ub + 5 ub = 6 ub
        // 3 ua + 15 ua = 18 ua
        assertThat((new Measure(1, ub).add(new Measure(1, uc))),
                is(new Measure(6, ub)));
        assertThat((new Measure(1, ub).add(new Measure(5, ub))),
                is(new Measure(6, ub)));
        assertThat((new Measure(1, ub).add(new Measure(1, uc))).convertTo(ua),
                is(new Measure(18, ua)));
        assertThat((new Measure(1, ub).convertTo(ua).add(new Measure(1, uc))),
                is(new Measure(18, ua)));
    }

    @Test
    public void testCompareTo() {
        Dimension mass = new Dimension("Weight", "M");
        Unit kg = new Unit(mass, "Kilogram", "kg");
        Unit g = new Unit(mass, "Gram", "g", kg, new DivideConverter(1000));
        Unit lb = new Unit(mass, "Pound", "lb", kg, new RationalConverter(45359237, 100000000));
        Unit oz = new Unit(mass, "Ounce", "oz", lb, new DivideConverter(16));
        assertThat(new Measure(1, g), is(lessThan(new Measure(1, kg))));
        assertThat(new Measure(1, kg), is(greaterThan(new Measure(1, g))));
        assertThat(new Measure(450, g), is(lessThan(new Measure(1, lb))));
        assertThat(new Measure(460, g), is(greaterThan(new Measure(1, lb))));
    }

    @Test
    public void testAddPoundsAndOunces() {
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
    }
}
