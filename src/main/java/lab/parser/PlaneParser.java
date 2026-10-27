package lab.parser;

import lab.model.Planes.Plane;
import java.util.List;

public interface PlaneParser {
    List<Plane> parse(String xmlPath);
}
