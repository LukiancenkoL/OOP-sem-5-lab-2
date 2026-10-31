package lab.validator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.SAXException;
import javax.xml.XMLConstants;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import java.io.File;
import java.io.IOException;

public class XmlValidator {
    private static final Logger logger = LoggerFactory.getLogger(XmlValidator.class);

    public boolean validate(String xmlPath, String xsdPath) {
        try {
            Schema schema = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI)
                    .newSchema(new File(xsdPath));
            schema.newValidator().validate(new StreamSource(new File(xmlPath)));

            logger.info("Validation passed: {} conforms to {}", xmlPath, xsdPath);
            return true;
        } catch (SAXException | IOException e) {
            logger.error("Validation failed for {} against {}: {}", xmlPath, xsdPath, e.getMessage());
            return false;
        }
    }

}
