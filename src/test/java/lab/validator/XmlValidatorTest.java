package lab.validator;

import org.junit.jupiter.api.Test;
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
}