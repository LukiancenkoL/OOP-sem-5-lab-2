package lab.parser;

import lab.model.Planes.Plane;
import lab.model.Planes.Plane.Chars;
import lab.model.Planes.Plane.Chars.Ammunition;
import lab.model.Planes.Plane.Parameters;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.helpers.DefaultHandler;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

public class DomPlaneParser implements PlaneParser {
    private static final Logger logger = LoggerFactory.getLogger(DomPlaneParser.class);

    @Override
    public List<Plane> parse(String xmlPath) {
        try {
            DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            builder.setErrorHandler(new DefaultHandler());
            Document document = builder.parse(new File(xmlPath));

            NodeList planeNodes = document.getElementsByTagName("Plane");
            List<Plane> planes = new ArrayList<>();
            for (int i = 0; i < planeNodes.getLength(); i++) {
                planes.add(buildPlane((Element) planeNodes.item(i)));
            }

            logger.info("DOM parser successfully loaded {} planes.", planes.size());
            return planes;
        } catch (Exception e) {
            logger.error("DOM parsing failed for {}", xmlPath, e);
            return new ArrayList<>();
        }
    }

    private Plane buildPlane(Element element) {
        Plane plane = new Plane();
        plane.setId(element.getAttribute("id"));
        plane.setModel(getText(element, "Model"));
        plane.setOrigin(getText(element, "Origin"));
        plane.setPrice(new BigInteger(getText(element, "Price")));
        plane.setChars(buildChars(element));
        plane.setParameters(buildParameters(element));
        return plane;
    }

    private Chars buildChars(Element planeElement) {
        Element charsElement = (Element) planeElement.getElementsByTagName("Chars").item(0);
        Chars chars = new Chars();
        chars.setType(getText(charsElement, "Type"));
        chars.setSeats(Integer.parseInt(getText(charsElement, "Seats")));

        NodeList ammunitionNodes = charsElement.getElementsByTagName("Ammunition");
        if (ammunitionNodes.getLength() > 0) {
            Element ammoElement = (Element) ammunitionNodes.item(0);
            Ammunition ammunition = new Ammunition();
            ammunition.setMissiles(Integer.parseInt(getText(ammoElement, "Missiles")));
            ammunition.setRadar(Boolean.parseBoolean(getText(ammoElement, "Radar")));
            chars.setAmmunition(ammunition);
        }
        return chars;
    }

    private Parameters buildParameters(Element planeElement) {
        Element paramsElement = (Element) planeElement.getElementsByTagName("Parameters").item(0);
        Parameters parameters = new Parameters();
        parameters.setLength(new BigDecimal(getText(paramsElement, "Length")));
        parameters.setWidth(new BigDecimal(getText(paramsElement, "Width")));
        parameters.setHeight(new BigDecimal(getText(paramsElement, "Height")));
        return parameters;
    }

    private String getText(Element parent, String tagName) {
        Node node = parent.getElementsByTagName(tagName).item(0);
        if (node == null) {
            throw new IllegalStateException("Missing element <" + tagName + ">");
        }
        return node.getTextContent().trim();
    }
}
