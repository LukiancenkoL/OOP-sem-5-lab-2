package lab;

import lab.validator.XmlValidator;

public class Main {
    public static void main(String[] args) {
        XmlValidator validator = new XmlValidator();
        
        String xmlPath = "src/main/resources/data/planes.xml";
        String xsdPath = "src/main/resources/data/planes.xsd";
        
        validator.validate(xmlPath, xsdPath);
    }
}
