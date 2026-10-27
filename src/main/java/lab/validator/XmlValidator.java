package lab.validator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javax.xml.XMLConstants;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import java.io.File;
import javax.xml.validation.Validator;
import org.xml.sax.SAXException;
import java.io.IOException;

public class XmlValidator {
    private static final Logger logger = LoggerFactory.getLogger(XmlValidator.class);

    public boolean validate(String xmlPath, String xsdPath) {
        try {
            SchemaFactory schemaFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
            Schema schema = schemaFactory.newSchema(new File(xsdPath));
            Validator validator = schema.newValidator();

            validator.validate(new StreamSource(new File(xmlPath)));

            logger.info("Validation passed: {} conforms to {}", xmlPath, xsdPath);
            return true;

        } catch (SAXException e) {
            logger.error("XML is not valid. Reason: {}", e.getMessage());
        } catch (IOException e) {
            logger.error("File reading error: {}", e.getMessage());
        }
        return false;
    }

}
