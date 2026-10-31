package lab.parser;

import lab.model.Planes.Plane;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import java.io.IOException;
import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

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

    @Test
    void testAllParsersReadSameSeats() {
        for (PlaneParser parser : List.of(new DomPlaneParser(), new SaxPlaneParser(), new StaxPlaneParser())) {
            List<Plane> planes = parser.parse(xmlPath);

            assertEquals(1, planes.get(0).getChars().getSeats(), "F-22 has 1 seat");
            assertEquals(2, planes.get(1).getChars().getSeats(), "SR-71 has 2 seats");
        }
    }

    @Test
    void testScoutHasNoAmmunition() {
        List<Plane> planes = new SaxPlaneParser().parse(xmlPath);

        assertEquals("SR-71 Blackbird", planes.get(1).getModel());
        assertEquals(null, planes.get(1).getChars().getAmmunition(), "Scout should have no ammunition");
    }

    @Test
    void testMissingFileGivesEmptyList() {
        assertTrue(new DomPlaneParser().parse("invalid.xml").isEmpty(), "DOM should return empty list");
        assertTrue(new SaxPlaneParser().parse("invalid.xml").isEmpty(), "SAX should return empty list");
        assertTrue(new StaxPlaneParser().parse("invalid.xml").isEmpty(), "StAX should return empty list");
    }

    @Test
    void testDomParserRejectsPlaneWithoutModel(@TempDir Path tmp) throws IOException {
        Path xml = Files.writeString(tmp.resolve("broken.xml"), "<Planes><Plane id=\"P1\"></Plane></Planes>");

        assertTrue(new DomPlaneParser().parse(xml.toString()).isEmpty(), "Broken file should give empty list");
    }
}