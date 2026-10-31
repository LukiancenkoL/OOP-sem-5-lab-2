package lab.parser;

import lab.model.Planes.Plane;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamReader;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class StaxPlaneParser implements PlaneParser {
    private static final Logger logger = LoggerFactory.getLogger(StaxPlaneParser.class);

    @Override
    public List<Plane> parse(String xmlPath) {
        PlaneHandler handler = new PlaneHandler();
        try (InputStream in = new FileInputStream(xmlPath)) {
            XMLStreamReader reader = XMLInputFactory.newInstance().createXMLStreamReader(in);
            try {
                while (reader.hasNext()) {
                    switch (reader.next()) {
                        case XMLStreamConstants.START_ELEMENT:
                            handler.handleStart(reader.getLocalName(), reader.getAttributeValue(null, "id"));
                            break;
                        case XMLStreamConstants.CHARACTERS:
                            handler.handleText(reader.getText());
                            break;
                        case XMLStreamConstants.END_ELEMENT:
                            handler.handleEnd(reader.getLocalName());
                            break;
                        default:
                            break;
                    }
                }
            } finally {
                reader.close();
            }
            List<Plane> planes = handler.getPlanes();
            logger.info("StAX parser successfully loaded {} planes.", planes.size());
            return planes;
        } catch (Exception e) {
            logger.error("StAX parsing failed for {}", xmlPath, e);
            return new ArrayList<>();
        }
    }

}
