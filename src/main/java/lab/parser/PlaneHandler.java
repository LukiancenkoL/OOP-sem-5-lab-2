package lab.parser;

import lab.model.Planes.Plane;
import lab.model.Planes.Plane.Chars;
import lab.model.Planes.Plane.Chars.Ammunition;
import lab.model.Planes.Plane.Parameters;
import org.xml.sax.Attributes;
import org.xml.sax.helpers.DefaultHandler;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

public class PlaneHandler extends DefaultHandler {
    private final List<Plane> planes = new ArrayList<>();
    private final StringBuilder currentText = new StringBuilder();
    private Plane currentPlane;
    private Chars currentChars;
    private Ammunition currentAmmunition;
    private Parameters currentParameters;

    public List<Plane> getPlanes() {
        return planes;
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) {
        handleStart(qName, attributes.getValue("id"));
    }

    void handleStart(String name, String idAttribute) {
        currentText.setLength(0);
        switch (name) {
            case "Plane":
                currentPlane = new Plane();
                currentPlane.setId(idAttribute);
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
            default:
                break;
        }
    }

    @Override
    public void characters(char[] ch, int start, int length) {
        handleText(new String(ch, start, length));
    }

    void handleText(String data) {
        currentText.append(data);
    }

    @Override
    public void endElement(String uri, String localName, String qName) {
        handleEnd(qName);
    }

    void handleEnd(String name) {
        String text = currentText.toString().trim();
        switch (name) {
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
            default:
                break;
        }
    }

}
