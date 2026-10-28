package lab.parser;

import lab.model.Planes.Plane;
import lab.model.Planes.Plane.Chars;
import lab.model.Planes.Plane.Chars.Ammunition;
import lab.model.Planes.Plane.Parameters;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

public class PlaneHandler extends DefaultHandler {
    private List<Plane> planes;
    private Plane currentPlane;
    private Chars currentChars;
    private Ammunition currentAmmunition;
    private Parameters currentParameters;
    private StringBuilder currentText;

    public PlaneHandler() {
        planes = new ArrayList<>();
    }

    public List<Plane> getPlanes() {
        return planes;
    }

    @Override
    public void startElement(String uri, String localName, String qName,
            Attributes attributes) throws SAXException {
        currentText = new StringBuilder();

        switch (qName) {
            case "Plane":
                currentPlane = new Plane();
                currentPlane.setId(attributes.getValue("id"));
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
    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        if (currentText != null) {
            currentText.append(ch, start, length);
        }
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        String text = currentText.toString().trim();

        switch (qName) {
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
    }
}
