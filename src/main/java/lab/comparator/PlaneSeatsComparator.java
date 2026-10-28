package lab.comparator;

import lab.model.Planes.Plane;
import java.util.Comparator;

public class PlaneSeatsComparator implements Comparator<Plane> {
    @Override
    public int compare(Plane p1, Plane p2) {
        return Integer.compare(p1.getChars().getSeats(), p2.getChars().getSeats());
    }
}