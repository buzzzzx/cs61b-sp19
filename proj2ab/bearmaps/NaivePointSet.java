package bearmaps;

import java.util.List;
import java.util.Arrays;

public class NaivePointSet implements PointSet {

    private List<Point> points;

    /**
     * Constructor.
     */
    public NaivePointSet(List<Point> points) {
        this.points = points;
    }

    /**
     * Runtime: O(N).
     */
    @Override
    public Point nearest(double x, double y) {
        Point target = new Point(x, y);
        Point nearestPoint = points.get(0);
        double nearestDistance = Point.distance(target, nearestPoint);
        int i;
        for (i = 1; i < points.size(); i += 1) {
            double dist = Point.distance(target, points.get(i));
            if (dist < nearestDistance) {
                nearestDistance = dist;
                nearestPoint = points.get(i);
            }
        }

        return nearestPoint;
    }

    public static void main(String[] args) {
        Point p1 = new Point(1.1, 2.2); // constructs a Point with x = 1.1, y = 2.2
        Point p2 = new Point(3.3, 4.4);
        Point p3 = new Point(-2.9, 4.2);

        NaivePointSet nn = new NaivePointSet(List.of(p1, p2, p3));
        Point ret = nn.nearest(3.0, 4.0); // returns p2
        System.out.println(ret.getX()); // evaluates to 3.3
        System.out.println(ret.getY()); // evaluates to 4.4
    }
}
