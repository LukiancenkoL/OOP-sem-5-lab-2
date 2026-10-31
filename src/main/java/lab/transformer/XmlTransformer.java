package lab.transformer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class XmlTransformer {
    private static final Logger logger = LoggerFactory.getLogger(XmlTransformer.class);

    public boolean transform(String xmlPath, String xslPath, String outputPath) {
        try {
            Transformer transformer = TransformerFactory.newInstance()
                    .newTransformer(new StreamSource(new File(xslPath)));

            Path output = Path.of(outputPath).toAbsolutePath();
            Files.createDirectories(output.getParent());

            transformer.transform(new StreamSource(new File(xmlPath)), new StreamResult(output.toFile()));

            logger.info("XML successfully transformed to: {}", outputPath);
            return true;
        } catch (TransformerException | IOException e) {
            logger.error("XML transformation failed for {} with {}", xmlPath, xslPath, e);
            return false;
        }
    }

}
