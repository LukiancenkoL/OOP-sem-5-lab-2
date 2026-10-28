package lab.parser;

import lab.model.Planes.Plane;
import lab.model.Planes.Plane.Chars;
import lab.model.Planes.Plane.Chars.Ammunition;
import lab.model.Planes.Plane.Parameters;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamReader;
import java.io.FileInputStream;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

public class StaxPlaneParser implements PlaneParser {
    private static final Logger logger = LoggerFactory.getLogger(StaxPlaneParser.class);

    @Override
    public List<Plane> parse(String xmlPath) {
        List<Plane> planes = new ArrayList<>();
        try {
            XMLInputFactory factory = XMLInputFactory.newInstance();
            XMLStreamReader reader = factory.createXMLStreamReader(new FileInputStream(xmlPath));

            Plane currentPlane = null;
            Chars currentChars = null;
            Ammunition currentAmmunition = null;
            Parameters currentParameters = null;
            StringBuilder currentText = new StringBuilder();

            while (reader.hasNext()) {
                int event = reader.next();

                switch (event) {
                    case XMLStreamConstants.START_ELEMENT:
                        currentText.setLength(0);
                        String startName = reader.getLocalName();
                        switch (startName) {
                            case "Plane":
                                currentPlane = new Plane();
                                currentPlane.setId(reader.getAttributeValue(null, "id"));
                                break;
                            case "Chars":
                                currentChars = new Chars();
                                break;
                            case "Ammunition":
                                currentAmmunition = new Ammunition();
                                break;
                            case "Parameters":
                                currentParameters = new Parameters();
                                break;
                        }
                        break;
                    case XMLStreamConstants.CHARACTERS:
                        currentText.append(reader.getText());
                        break;
                    case XMLStreamConstants.END_ELEMENT:
                        String endName = reader.getLocalName();
                        String text = currentText.toString().trim();
                        switch (endName) {
                            case "Plane":
                                planes.add(currentPlane);
                                break;
                            case "Model":
                                currentPlane.setModel(text);
                                break;
                            case "Origin":
                                currentPlane.setOrigin(text);
                                break;
                            case "Price":
                                currentPlane.setPrice(new BigInteger(text));
                                break;
                            case "Type":
                                currentChars.setType(text);
                                break;
                            case "Seats":
                                currentChars.setSeats(Integer.parseInt(text));
                                break;
                            case "Missiles":
                                currentAmmunition.setMissiles(Integer.parseInt(text));
                                break;
                            case "Radar":
                                currentAmmunition.setRadar(Boolean.parseBoolean(text));
                                break;
                            case "Ammunition":
                                currentChars.setAmmunition(currentAmmunition);
                                break;
                            case "Chars":
                                currentPlane.setChars(currentChars);
                                break;
                            case "Length":
                                currentParameters.setLength(new BigDecimal(text));
                                break;
                            case "Width":
                                currentParameters.setWidth(new BigDecimal(text));
                                break;
                            case "Height":
                                currentParameters.setHeight(new BigDecimal(text));
                                break;
                            case "Parameters":
                                currentPlane.setParameters(currentParameters);
                                break;
                        }
                        break;
                }
            }
            logger.info("StAX Parser successfully loaded {} planes.", planes.size());
        } catch (Exception e) {
            logger.error("StAX parsing error: ", e);
        }

        return planes;
    }

}
