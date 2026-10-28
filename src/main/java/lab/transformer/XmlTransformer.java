package lab.transformer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import java.io.File;

public class XmlTransformer {
    private static final Logger logger = LoggerFactory.getLogger(XmlTransformer.class);

    public boolean transform(String xmlPath, String xslPath, String outputPath) {
        try {
            TransformerFactory factory = TransformerFactory.newInstance();
            Transformer transformer = factory.newTransformer(new StreamSource(new File(xslPath)));

            transformer.transform(new StreamSource(new File(xmlPath)), new StreamResult(new File(outputPath)));

            logger.info("XML successfully transformed to: {}", outputPath);
            return true;
        } catch (Exception e) {
            logger.error("XML transformation error: ", e);
            return false;
        }
    }

}
