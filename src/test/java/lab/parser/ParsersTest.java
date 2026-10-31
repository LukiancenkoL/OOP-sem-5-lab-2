package lab.parser;

import lab.model.Planes.Plane;
import org.junit.jupiter.api.Test;
import java.math.BigInteger;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class ParsersTest {
    private final String xmlPath = "src/main/resources/data/planes.xml";

    @Test
    void testDomParser() {
        PlaneParser parser = new DomPlaneParser();
        List<Plane> planes = parser.parse(xmlPath);
        
        assertEquals(8, planes.size(), "DOM parser should parse 8 planes");
        assertEquals("F-22 Raptor", planes.get(0).getModel(), "First plane model should be F-22 Raptor");
    }

    @Test
    void testSaxParser() {
        PlaneParser parser = new SaxPlaneParser();
        List<Plane> planes = parser.parse(xmlPath);
        
        assertEquals(8, planes.size(), "SAX parser should parse 8 planes");
        assertNotNull(planes.get(0).getChars(), "Chars object should not be null");
        assertEquals("USA", planes.get(0).getOrigin(), "Origin should be USA");
    }

    @Test
    void testStaxParser() {
        PlaneParser parser = new StaxPlaneParser();
        List<Plane> planes = parser.parse(xmlPath);
        
        assertEquals(8, planes.size(), "StAX parser should parse 8 planes");
        assertEquals(new BigInteger("150000000"), planes.get(0).getPrice(), "Price of F-22 should be 150000000");
    }
}