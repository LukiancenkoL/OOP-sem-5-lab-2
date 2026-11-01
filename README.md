# Laboratory Work 2: Military Planes (XML, XSD, XSLT)

Task variant: Military planes. Describe military planes in an XML file with an XSD schema, generate a Java class from the schema, load the planes with DOM, SAX and StAX parsers, sort them with `Comparator`, validate the XML against the XSD and transform it with XSLT.

## Features
* **XML + XSD:** `planes.xml` is described by `planes.xsd` (simple and complex types, enumeration, boundary values, required attribute of type `ID`).
* **Class generation:** the Java classes (`Planes`, `Plane`, `Chars`, `Ammunition`, `Parameters`) are generated from the XSD by JAXB (`xjc`) during the Maven build.
* **Three parsers:** the same file is loaded with DOM, SAX and StAX. SAX and StAX share the mapping code in `PlaneHandler`.
* **Sorting:** planes are sorted with `Comparator` by price, model, origin, seats and length.
* **Validation:** the XML is validated against the XSD before it is parsed. Invalid input stops the program.
* **XSLT:** the list of planes is grouped by type (`Escort`, `Fighter`, `Interceptor`, `Scout`, `Support`), and planes inside a group are sorted by price.
* **Logging:** all messages go through SLF4J + Logback (short lines on the console, full lines in `logs/app.log`).


## Plane Description

    Planes (root)
    └── Plane {id}                      required, type ID, unique
        ├── Model                       non-empty text
        ├── Origin                      non-empty text
        ├── Chars
        │   ├── Type                    Support | Escort | Fighter | Interceptor | Scout
        │   ├── Seats                   1 or 2
        │   └── Ammunition (optional)   missing for scouts
        │       ├── Missiles            0 - 10
        │       └── Radar               true | false
        ├── Parameters                  in metres, greater than 0
        │   ├── Length
        │   ├── Width
        │   └── Height
        └── Price                       in thalers, greater than 0


## XSLT Result

Before (`planes.xml`):

    <Planes>
        <Plane id="P1"> ... <Type>Fighter</Type> ... </Plane>
        <Plane id="P2"> ... <Type>Scout</Type> ... </Plane>
        <Plane id="P8"> ... <Type>Fighter</Type> ... </Plane>
    </Planes>

After (`target/grouped_planes.xml`):

    <Planes>
        <Fighter>
            <Plane id="P8"> ... </Plane>
            <Plane id="P1"> ... </Plane>
        </Fighter>
        <Scout>
            <Plane id="P2"> ... </Plane>
        </Scout>
    </Planes>


## Project Structure

    src/main/java/lab
    ├── Main.java                     runs the whole program
    ├── comparator/                   5 comparators (price, model, origin, seats, length)
    ├── parser/                       DomPlaneParser, SaxPlaneParser, StaxPlaneParser, PlaneHandler
    ├── transformer/XmlTransformer    applies the XSL file
    └── validator/XmlValidator        validates XML against the XSD
    src/main/resources
    ├── data/planes.xml, planes.xsd, transform.xsl
    └── logback.xml
    src/test/java/lab                 unit tests

The `lab.model` classes are generated into `target/generated-sources/xjc` and are not stored in git.


## Technologies Used
* Java 21
* Apache Maven
* JAXB (Jakarta XML Binding, `xjc` via `jaxb2-maven-plugin`)
* SLF4J + Logback
* JUnit 5

## How to Run

Ensure you have Java 21 and Maven installed on your system.

1. **Compile and execute the program:**

    `mvn compile exec:java -Dexec.mainClass="lab.Main"`

The program prints the planes in file order, then sorted by every field, and writes the grouped XML to `target/grouped_planes.xml`. Log messages are also written to `logs/app.log`.


## Running Tests


1. **Run the test suite:**

    `mvn test`