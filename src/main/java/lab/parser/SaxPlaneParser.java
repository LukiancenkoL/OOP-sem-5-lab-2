package lab.parser;

import java.util.ArrayList;
import java.util.List;
import lab.model.Planes.Plane;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.File;

public class SaxPlaneParser implements PlaneParser {
    private static final Logger logger = LoggerFactory.getLogger(SaxPlaneParser.class);

    @Override
    public List<Plane> parse(String xmlPath) {
        List<Plane> planes = new ArrayList<>();
        try {
            SAXParserFactory factory = SAXParserFactory.newInstance();
            SAXParser saxParser = factory.newSAXParser();
            PlaneHandler handler = new PlaneHandler();

            saxParser.parse(new File(xmlPath), handler);
            planes = handler.getPlanes();

            logger.info("SAX Parser successfully loaded {} planes.", planes.size());
        } catch (Exception e) {
            logger.error("SAX parsing error: {}", e.getMessage());
        }

        return planes;
    }
}
