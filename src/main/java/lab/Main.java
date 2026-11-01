package lab;

import lab.comparator.PlaneLengthComparator;
import lab.comparator.PlaneModelComparator;
import lab.comparator.PlaneOriginComparator;
import lab.comparator.PlanePriceComparator;
import lab.comparator.PlaneSeatsComparator;
import lab.model.Planes.Plane;
import lab.parser.DomPlaneParser;
import lab.parser.SaxPlaneParser;
import lab.parser.StaxPlaneParser;
import lab.transformer.XmlTransformer;
import lab.validator.XmlValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Comparator;
import java.util.List;

public class Main {
    private static final Logger logger = LoggerFactory.getLogger(Main.class);

    static final String XML_PATH = "src/main/resources/data/planes.xml";
    static final String XSD_PATH = "src/main/resources/data/planes.xsd";
    static final String XSL_PATH = "src/main/resources/data/transform.xsl";
    static final String OUTPUT_PATH = "target/grouped_planes.xml";

    public static void main(String[] args) {
        run(XML_PATH, XSD_PATH, XSL_PATH, OUTPUT_PATH);
    }

    static boolean run(String xmlPath, String xsdPath, String xslPath, String outputPath) {
        if (!new XmlValidator().validate(xmlPath, xsdPath)) {
            logger.error("Processing stopped: the XML file is not valid.");
            return false;
        }

        List<Plane> planes = new DomPlaneParser().parse(xmlPath);
        new SaxPlaneParser().parse(xmlPath);
        new StaxPlaneParser().parse(xmlPath);
        logPlanes("Planes in file order", planes);

        sortAndLog(planes, "price", new PlanePriceComparator());
        sortAndLog(planes, "model", new PlaneModelComparator());
        sortAndLog(planes, "origin", new PlaneOriginComparator());
        sortAndLog(planes, "seats", new PlaneSeatsComparator());
        sortAndLog(planes, "length", new PlaneLengthComparator());

        return new XmlTransformer().transform(xmlPath, xslPath, outputPath);
    }

    private static void sortAndLog(List<Plane> planes, String field, Comparator<Plane> comparator) {
        planes.sort(comparator);
        logPlanes("Sorted by " + field, planes);
    }

    private static void logPlanes(String title, List<Plane> planes) {
        logger.info("{}:", title);
        for (Plane p : planes) {
            logger.info("  {}: {}, {}, {}, seats {}, length {} m, price {}",
                    p.getId(), p.getModel(), p.getOrigin(), p.getChars().getType(),
                    p.getChars().getSeats(), p.getParameters().getLength(), p.getPrice());
        }
    }
}