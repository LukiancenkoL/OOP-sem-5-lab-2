package lab;

import lab.comparator.PlaneLengthComparator;
import lab.comparator.PlaneModelComparator;
import lab.comparator.PlaneOriginComparator;
import lab.comparator.PlanePriceComparator;
import lab.comparator.PlaneSeatsComparator;
import lab.model.Planes.Plane;
//import lab.parser.DomPlaneParser;
import lab.parser.PlaneParser;
//import lab.parser.SaxPlaneParser;
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
            // logger.info("--- DOM Parser ---");
            // PlaneParser domParser = new DomPlaneParser();
            // List<Plane> planes = domParser.parse(xmlPath);
            // for (Plane plane : planes) {
            // logger.info("Parsed Plane -> ID: {}, Model: {}, Origin: {}, Price: {}",
            // plane.getId(),
            // plane.getModel(),
            // plane.getOrigin(),
            // plane.getPrice());
            // }

            // logger.info("--- SAX Parser ---");
            // PlaneParser saxParser = new SaxPlaneParser();
            // List<Plane> saxPlanes = saxParser.parse(xmlPath);
            // for (Plane plane : saxPlanes) {
            // logger.info("Parsed Plane -> ID: {}, Model: {}, Origin: {}, Price: {}",
            // plane.getId(),
            // plane.getModel(),
            // plane.getOrigin(),
            // plane.getPrice());
            // }

            logger.info("--- StAX Parser ---");
            PlaneParser staxParser = new StaxPlaneParser();
            List<Plane> staxPlanes = staxParser.parse(xmlPath);
            // for (Plane plane : staxPlanes) {
            // logger.info("Parsed Plane -> ID: {}, Model: {}, Origin: {}, Price: {}",
            // plane.getId(),
            // plane.getModel(),
            // plane.getOrigin(),
            // plane.getPrice());
            // }

            logger.info("--- Comparator (Sorting by Price) ---");
            staxPlanes.sort(new PlanePriceComparator());

            for (Plane plane : staxPlanes) {
                logger.info("Sorted Plane -> ID: {}, Price: {}, Model: {}",
                        plane.getId(),
                        plane.getPrice(),
                        plane.getModel());
            }

            logger.info("--- Comparator (Sorting by Model) ---");
            staxPlanes.sort(new PlaneModelComparator());
            for (Plane plane : staxPlanes) {
                logger.info("Sorted by Model -> Model: {}, ID: {}, Origin: {}",
                        plane.getModel(), plane.getId(), plane.getOrigin());
            }

            logger.info("--- Comparator (Sorting by Origin) ---");
            staxPlanes.sort(new PlaneOriginComparator());
            for (Plane plane : staxPlanes) {
                logger.info("Sorted by Origin -> Origin: {}, ID: {}, Model: {}",
                        plane.getOrigin(), plane.getId(), plane.getModel());
            }

            logger.info("--- Comparator (Sorting by Seats) ---");
            staxPlanes.sort(new PlaneSeatsComparator());
            for (Plane plane : staxPlanes) {
                logger.info("Sorted by Seats -> Seats: {}, ID: {}, Model: {}",
                        plane.getChars().getSeats(), plane.getId(), plane.getModel());
            }

            logger.info("--- Comparator (Sorting by Length) ---");
            staxPlanes.sort(new PlaneLengthComparator());
            for (Plane plane : staxPlanes) {
                logger.info("Sorted by Length -> Length: {}m, ID: {}, Model: {}",
                        plane.getParameters().getLength(), plane.getId(), plane.getModel());
            }

        } else {
            logger.error("Parsing aborted due to validation failure.");
        }
    }
}