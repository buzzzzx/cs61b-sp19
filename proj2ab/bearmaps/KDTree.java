package bearmaps;


import java.util.List;

public class KDTree implements PointSet {
    private class Node {
        private Point point;
        private boolean splitDim;  // true: horizontal; false: vertical.
        private Node left;
        private Node right;

        Node(Point p, boolean splitDim) {
            point = p;
            this.splitDim = splitDim;
        }

        public boolean pointEqualOrSmallerThan(Point p) {
            if (splitDim) {
                return point.getX() <= p.getX();
            } else {
                return point.getY() <= p.getY();
            }

        }

        public Point findBestPoint(Point goal) {
            if (splitDim) {
                return new Point(point.getX(), goal.getY());
            } else {
                return new Point(goal.getX(), point.getY());
            }
        }
    }

    private static final boolean HORIZONTAL = true;
    private Node root;
    private List<Point> points;

    public KDTree(List<Point> points) {
        this.points = points;

        setup();
    }

    /**
     * Setup KDTree.
     */
    private void setup() {
        int i;
        for (i = 0; i < points.size(); i += 1) {
            root = insert(root, points.get(i), HORIZONTAL);
        }
    }

    /**
     * Insert node to the KDTree without check duplicated node.
     */
    private Node insert(Node node, Point p, boolean splitDim) {
        if (node == null) {
            return new Node(p, splitDim);
        }
        if (node.pointEqualOrSmallerThan(p)) {
            node.right = insert(node.right, p, !splitDim);
        } else {
            node.left = insert(node.left, p, !splitDim);
        }

        return node;
    }

    @Override
    public Point nearest(double x, double y) {
        Point goal = new Point(x, y);
        return nearest(root, goal, root.point);
    }

    private Point nearest(Node node, Point goal, Point best) {
        if (node == null) {
            return best;
        }

        if (Point.distance(node.point, goal) < Point.distance(best, goal)) {
            best = node.point;
        }

        Node goodSide;
        Node badSide;
        if (node.pointEqualOrSmallerThan(goal)) {
            goodSide = node.right;
            badSide = node.left;
        } else {
            goodSide = node.left;
            badSide = node.right;
        }

        best = nearest(goodSide, goal, best);

        if (isWorthCheckingBadSide(node, goal, best)) {
            best = nearest(badSide, goal, best);
        }

        return best;
    }

    private boolean isWorthCheckingBadSide(Node node, Point goal, Point best) {
        Point bestCase = node.findBestPoint(goal);

        if (Point.distance(bestCase, goal) < Point.distance(best, goal)) {
            return true;
        } else {
            return false;
        }
    }

}
