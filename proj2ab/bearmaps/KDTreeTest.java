package bearmaps;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static org.junit.Assert.assertEquals;

public class KDTreeTest {

    private static Random r = new Random(500);

    @Test
    public void simpleTest() {
        Point p1 = new Point(2, 3);
        Point p2 = new Point(4, 2);
        Point p3 = new Point(4, 5);
        Point p4 = new Point(3, 3);
        Point p5 = new Point(1, 5);
        Point p6 = new Point(4, 4);
        KDTree kdTree = new KDTree(List.of(p1, p2, p3, p4, p5, p6));
        Point actual = kdTree.nearest(0, 7);
        assertEquals(p5, actual);
    }

    @Test
    public void randomTestCorrectness() {
        int numPoints = 10000;
        int numQueries = 2000;
        List<Point> randomPoints = randomPoints(numPoints);
        List<Point> queries = randomPoints(numQueries);
        KDTree kdTree = new KDTree(randomPoints);
        NaivePointSet nps = new NaivePointSet(randomPoints);

        int i;
        for (i = 0; i < numQueries; i += 1) {
            Point goal = queries.get(i);
            assertEquals(nps.nearest(goal.getX(), goal.getY()), kdTree.nearest(goal.getX(), goal.getY()));
        }
    }

    @Test
    public void randomTestRuntime() {
        int numPoints = 100000;
        int numQueries = 10000;
        List<Point> randomPoints = randomPoints(numPoints);
        List<Point> queries = randomPoints(numQueries);
        KDTree kdTree = new KDTree(randomPoints);
        NaivePointSet nps = new NaivePointSet(randomPoints);

        long start1 = System.currentTimeMillis();
        for (Point p : queries) {
            kdTree.nearest(p.getX(), p.getY());
        }
        long end1 = System.currentTimeMillis();

        long start2 = System.currentTimeMillis();
        for (Point p : queries) {
            nps.nearest(p.getX(), p.getY());
        }
        long end2 = System.currentTimeMillis();

        System.out.println("KDTree 10000 queries on 100000 points: " + (end1 - start1) / 1000.0 + " seconds.");
        System.out.println("Naive 10000 queries on 100000 points: " + (end2 - start2) / 1000.0 + " seconds.");
    }

    private Point randomPoint() {
        double x = (double) r.nextInt(20);
        double y = (double) r.nextInt(20);
        return new Point(x, y);
    }

    private List<Point> randomPoints(int n) {
        List<Point> points = new ArrayList<>();
        int i;
        for (i = 0; i < n; i += 1) {
            points.add(randomPoint());
        }

        return points;
    }
}
