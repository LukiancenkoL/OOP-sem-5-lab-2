package lab.comparator;

import lab.model.Planes.Plane;
import java.util.Comparator;

public class PlaneModelComparator implements Comparator<Plane> {
    @Override
    public int compare(Plane p1, Plane p2) {
        return p1.getModel().compareToIgnoreCase(p2.getModel());
    }
}