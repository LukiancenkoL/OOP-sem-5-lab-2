package lab.parser;

import lab.model.Planes.Plane;
import lab.model.Planes.Plane.Chars;
import lab.model.Planes.Plane.Chars.Ammunition;
import lab.model.Planes.Plane.Parameters;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.List;
import java.util.ArrayList;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;
import java.io.File;
import java.math.BigInteger;
import java.math.BigDecimal;
//import java.math.BigInteger;

public class DomPlaneParser implements PlaneParser {
    private static final Logger logger = LoggerFactory.getLogger(DomPlaneParser.class);

    @Override 
    public List<Plane> parse(String xmlPath){
        List<Plane> planes = new ArrayList<>();
        try{
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(new File(xmlPath)); 
            document.getDocumentElement().normalize();

            NodeList planeNode = document.getElementsByTagName("Plane");

            for (int i =0; i < planeNode.getLength(); i++){
                Node node = planeNode.item(i);
                if(node.getNodeType() == Node.ELEMENT_NODE){
                    Element element = (Element) node;
                    planes.add(buildPlane(element));
                }
            }
            logger.info("DOM Parser successfully loaded {} planes.", planes.size());
        } catch(Exception e){
            logger.error("DOM parsing error: {}", e.getMessage());            
        }
        return planes;
    }

    private Plane buildPlane(Element element) {
        Plane plane = new Plane();
        plane.setId(element.getAttribute("id"));
        plane.setModel(getTextContent(element, "Model"));
        plane.setOrigin(getTextContent(element, "Origin"));
        plane.setPrice(new BigInteger(getTextContent(element, "Price")));

        plane.setChars(buildChars(element));
        plane.setParameters(buildParameters(element));

        return plane;
    }

    private Chars buildChars(Element planeElement) {
        Element charsElement = (Element) planeElement.getElementsByTagName("Chars").item(0);
        Chars chars = new Chars();

        chars.setType(getTextContent(charsElement, "Type"));
        chars.setSeats(Integer.parseInt(getTextContent(charsElement, "Seats")));

        NodeList ammunitionNodes = charsElement.getElementsByTagName("Ammunition");
        if (ammunitionNodes.getLength() > 0) {
            Element ammoElement = (Element) ammunitionNodes.item(0);
            Ammunition ammunition = new Ammunition();
            ammunition.setMissiles(Integer.parseInt(getTextContent(ammoElement, "Missiles")));
            ammunition.setRadar(Boolean.parseBoolean(getTextContent(ammoElement, "Radar")));
            chars.setAmmunition(ammunition);
        }

        return chars;
    }

    private Parameters buildParameters(Element planeElement) {
        Element paramsElement = (Element) planeElement.getElementsByTagName("Parameters").item(0);
        Parameters parameters = new Parameters();

        parameters.setLength(new BigDecimal(getTextContent(paramsElement, "Length")));
        parameters.setWidth(new BigDecimal(getTextContent(paramsElement, "Width")));
        parameters.setHeight(new BigDecimal(getTextContent(paramsElement, "Height")));

        return parameters;
    }

    private String getTextContent(Element parent, String tagName) {
        NodeList list = parent.getElementsByTagName(tagName);
        if (list != null && list.getLength() > 0) {
            return list.item(0).getTextContent();
        }
        return "";
    }
}
