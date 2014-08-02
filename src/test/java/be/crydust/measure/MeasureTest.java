package be.crydust.measure;

import static be.crydust.measure.KitchenUnitSystem.MASS;
import java.math.BigDecimal;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static be.crydust.measure.KitchenUnitSystem.*;
import be.crydust.measure.converter.AddConverter;
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
    public void testAdd() {
        // TODO use linear units, they are easier to understand
        Dimension d = new Dimension("d", "d");
        Unit ua = new Unit(d, "a", "a");
        Unit ub = new Unit(d, "b", "b", ua, new AddConverter(3));
        Unit uc = new Unit(d, "c", "c", ub, new AddConverter(5));
        assertThat(new Measure(1, ub).convertTo(ua).getValue(), is(4.0));
        assertThat(new Measure(1, uc).convertTo(ua).getValue(), is(9.0));
        assertThat(new Measure(1, uc).convertTo(ub).getValue(), is(6.0));
        // 1 ub + 1 uc = 7 ub
        // 1 ub + 6 ub = 7 ub = 10 ua
        assertThat((new Measure(1, ub).add(new Measure(1, uc))),
                is(new Measure(7, ub)));
        assertThat((new Measure(1, ub).add(new Measure(6, ub))),
                is(new Measure(7, ub)));
        assertThat((new Measure(1, ub).add(new Measure(1, uc))).convertTo(ua),
                is(new Measure(10, ua)));
    }

}
