package lab;

import lab.model.Planes.Plane;
import lab.parser.DomPlaneParser;
import lab.parser.PlaneParser;
import lab.parser.SaxPlaneParser;
import lab.parser.StaxPlaneParser;
import lab.validator.XmlValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.List;

public class Main {
    private static final Logger logger = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) {
        String xmlPath = "src/main/resources/data/planes.xml";
        String xsdPath = "src/main/resources/data/planes.xsd";

        XmlValidator validator = new XmlValidator();

        if (validator.validate(xmlPath, xsdPath)) {
            logger.info("--- DOM Parser ---");
            PlaneParser domParser = new DomPlaneParser();
            List<Plane> planes = domParser.parse(xmlPath);
            for (Plane plane : planes) {
                logger.info("Parsed Plane -> ID: {}, Model: {}, Origin: {}, Price: {}",
                        plane.getId(),
                        plane.getModel(),
                        plane.getOrigin(),
                        plane.getPrice());
            }

            logger.info("--- SAX Parser ---");
            PlaneParser saxParser = new SaxPlaneParser();
            List<Plane> saxPlanes = saxParser.parse(xmlPath);
            for (Plane plane : saxPlanes) {
                logger.info("Parsed Plane -> ID: {}, Model: {}, Origin: {}, Price: {}",
                        plane.getId(),
                        plane.getModel(),
                        plane.getOrigin(),
                        plane.getPrice());
            }

            logger.info("--- StAX Parser ---");
            PlaneParser staxParser = new StaxPlaneParser();
            List<Plane> staxPlanes = staxParser.parse(xmlPath);
            for (Plane plane : staxPlanes) {
                logger.info("Parsed Plane -> ID: {}, Model: {}, Origin: {}, Price: {}",
                        plane.getId(),
                        plane.getModel(),
                        plane.getOrigin(),
                        plane.getPrice());
            }
        } else {
            logger.error("Parsing aborted due to validation failure.");
        }
    }
}