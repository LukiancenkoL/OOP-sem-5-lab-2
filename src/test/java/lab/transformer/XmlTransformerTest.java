package lab.transformer;

import org.junit.jupiter.api.Test;
import java.io.File;
import static org.junit.jupiter.api.Assertions.assertTrue;

class XmlTransformerTest {
    private final String xmlPath = "src/main/resources/data/planes.xml";
    private final String xslPath = "src/main/resources/data/transform.xsl";
    private final String outPath = "src/main/resources/data/test_transform.xml";

    @Test
    void testTransformationSuccess() {
        XmlTransformer transformer = new XmlTransformer();
        boolean result = transformer.transform(xmlPath, xslPath, outPath);

        assertTrue(result, "Transformation should return true");

        File outputFile = new File(outPath);
        assertTrue(outputFile.exists(), "Output file should be created");

        outputFile.delete();
    }
}