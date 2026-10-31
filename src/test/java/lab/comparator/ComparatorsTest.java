package lab.comparator;

import lab.model.Planes.Plane;
import lab.model.Planes.Plane.Chars;
import lab.model.Planes.Plane.Parameters;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.math.BigDecimal;
import java.math.BigInteger;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ComparatorsTest {
    private Plane p1;
    private Plane p2;

    @BeforeEach
    void setUp() {
        p1 = new Plane();
        p2 = new Plane();

        Chars chars1 = new Chars();
        chars1.setSeats(1);
        p1.setChars(chars1);
        Chars chars2 = new Chars();
        chars2.setSeats(2);
        p2.setChars(chars2);

        Parameters params1 = new Parameters();
        params1.setLength(new BigDecimal("15.5"));
        p1.setParameters(params1);
        Parameters params2 = new Parameters();
        params2.setLength(new BigDecimal("19.2"));
        p2.setParameters(params2);
    }

    @Test
    void testPriceComparator() {
        p1.setPrice(new BigInteger("1000"));
        p2.setPrice(new BigInteger("2000"));
        assertTrue(new PlanePriceComparator().compare(p1, p2) < 0, "p1 price should be less than p2 price");
    }

    @Test
    void testModelComparator() {
        p1.setModel("A-Plane");
        p2.setModel("B-Plane");
        assertTrue(new PlaneModelComparator().compare(p1, p2) < 0, "A-Plane should come before B-Plane");
    }

    @Test
    void testOriginComparator() {
        p1.setOrigin("EU");
        p2.setOrigin("USA");
        assertTrue(new PlaneOriginComparator().compare(p1, p2) < 0, "EU should come before USA alphabetically");
    }

    @Test
    void testSeatsComparator() {
        assertTrue(new PlaneSeatsComparator().compare(p1, p2) < 0, "p1 should have fewer seats than p2");
    }

    @Test
    void testLengthComparator() {
        assertTrue(new PlaneLengthComparator().compare(p1, p2) < 0, "p1 length should be less than p2 length");
    }
}