package lab.validator;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

class XmlValidatorTest {
    private final String validXmlPath = "src/main/resources/data/planes.xml";
    private final String xsdPath = "src/main/resources/data/planes.xsd";

    @Test
    void testValidXml() {
        XmlValidator validator = new XmlValidator();
        assertTrue(validator.validate(validXmlPath, xsdPath), "Valid XML should pass validation");
    }

    @Test
    void testInvalidFilePath() {
        XmlValidator validator = new XmlValidator();
        assertFalse(validator.validate("invalid.xml", xsdPath), "Non-existent file should return false");
    }

    @Test
    void testXmlThatBreaksSchema(@TempDir Path tmp) throws IOException {
        Path xml = Files.writeString(tmp.resolve("bad.xml"), "<Planes><Plane/></Planes>");

        XmlValidator validator = new XmlValidator();
        assertFalse(validator.validate(xml.toString(), xsdPath), "XML that breaks the schema should return false");
    }
}