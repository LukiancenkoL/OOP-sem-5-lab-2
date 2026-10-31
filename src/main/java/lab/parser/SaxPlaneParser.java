package lab.parser;

import java.util.ArrayList;
import java.util.List;
import lab.model.Planes.Plane;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javax.xml.parsers.SAXParserFactory;
import java.io.File;

public class SaxPlaneParser implements PlaneParser {
    private static final Logger logger = LoggerFactory.getLogger(SaxPlaneParser.class);

    @Override
    public List<Plane> parse(String xmlPath) {
        try {
            PlaneHandler handler = new PlaneHandler();
            SAXParserFactory.newInstance().newSAXParser().parse(new File(xmlPath), handler);

            List<Plane> planes = handler.getPlanes();
            logger.info("SAX parser successfully loaded {} planes.", planes.size());
            return planes;
        } catch (Exception e) {
            logger.error("SAX parsing failed for {}", xmlPath, e);
            return new ArrayList<>();
        }
    }
}
