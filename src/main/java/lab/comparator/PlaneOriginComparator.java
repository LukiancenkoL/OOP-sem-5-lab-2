package lab.comparator;

import lab.model.Planes.Plane;
import java.util.Comparator;

public class PlaneOriginComparator implements Comparator<Plane> {
    @Override
    public int compare(Plane p1, Plane p2) {
        return p1.getOrigin().compareToIgnoreCase(p2.getOrigin());
    }
}
