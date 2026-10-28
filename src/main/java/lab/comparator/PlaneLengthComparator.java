package lab.comparator;

import lab.model.Planes.Plane;
import java.util.Comparator;

public class PlaneLengthComparator implements Comparator<Plane> {
    @Override
    public int compare(Plane p1, Plane p2) {
        return p1.getParameters().getLength().compareTo(p2.getParameters().getLength());
    }
}