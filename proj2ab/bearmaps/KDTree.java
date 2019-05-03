package bearmaps;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import static bearmaps.Point.distance;

/**
 * K-d tree implementation, only has to work for 2-dimensional case.
 * */
public class KDTree implements PointSet {
    private Node root;

    // Orientation of tree, x == true, y == false
    private Boolean orientation;
    private Node    goodSide;
    private Node    badside;
    private Double  bestDistance;

    private class Node {
        private Point p;
        private Double x;
        private Double y;
        private Node left;   //partition subspace into up and down (by X)
        private Node right;  //partition subspace into left and right (by Y)
        private String orientation;

        public Node (Point p) {
            this.p = p;
            this.x = p.getX();
            this.y = p.getY();
            this.left = null;
            this.right = null;
        }
    }


    /**
     * Constructs a new KDTree from a List of points;
     * */
    public KDTree(List<Point> points) {
       // this.points = points;
        this.orientation = true;
        for (Point p: points) {
            insert(p);
        }
    }

    /**
     * Insert a new Point p into the KDTree.
     * */
    public void insert (Point p) {
        if (p == null) {
            throw new IllegalArgumentException("Calls insert() with a null Point.");
        }
        orientation = false;
        root = insert (root, p);
    }

    /**
     * Helper method for insert.
     * */
    private Node insert(Node x, Point p) {
        orientation = !orientation;
        if (x == null) {
            Node returnNode = new Node(p);
            if (orientation) {
                returnNode.orientation = "x";
            } else {
                returnNode.orientation = "y";
            }
            return returnNode;
        }
        if (x.p.equals(p)){
            return x;
        }
        // partition by x
        if (orientation == true) {
            int cmp = xComparator.compare(p.getX(), x.x);
            insert(x, p, cmp);
        }
        // partition by y
        else  {
            int cmp = xComparator.compare(p.getY(), x.y);
            insert(x, p, cmp);
        }
        return x;
    }

    /**
     * Another helper method for insert.
     * */
    private void insert(Node x, Point p, int cmp) {
        if (cmp < 0) x.left = insert(x.left, p);
        else if (cmp > 0) x.right = insert(x.right, p);
        else  x.right = insert(x.right, p);
    }

    /**
     * Returns the closest point to the inputted coordinates.
     * This should take O(logN) time on average, where N is the number of points.
     * */
    @Override
    public Point nearest(double x, double y) {
        Point goal = new Point(x, y);
        return nearest(root, goal, root).p;
    }

    /**
     * Helper method for nearest.
     * */
    private Node nearest(Node n, Point goal, Node best) {
        if (n == null) {
            return best;
        }

        if (bestDistance == null) {
            bestDistance = Math.sqrt(distance(goal, n.p));
        }

        if (Math.sqrt(distance(n.p, goal)) < Math.sqrt(distance(best.p, goal)) ) {
            best = n;
            bestDistance = Math.sqrt(distance(goal, n.p));
        }
        // calculating the good side
        if (n.orientation == "x") {
            int cmp = xComparator.compare(goal.getX(), n.p.getX());
            findGoodSide(n, cmp);

        } else if (n.orientation == "y") {
            int cmp = xComparator.compare(goal.getY(), n.p.getY());
            findGoodSide(n, cmp);
        }
        best = nearest(goodSide, goal, best);

        if (badside != null && goal.getY() - badside.y < bestDistance) {
            best = nearest (badside, goal, best);
        }

        return best;
    }

    /**
     * Finds the good side and bad side of a node.
     * */
    private void findGoodSide(Node n, int cmp) {
        if (cmp < 0) {
            goodSide = n.left;
            badside = n.right;
        } else {
           goodSide = n.right;
           badside = n.left;
        }
    }

    Comparator<Double> xComparator = Comparator.comparingDouble(i -> i);


    // Main method for testing, DELETE when done
    public static void main(String[] args) {
        Point p1 = new Point(2.0, 3.0); // constructs a Point with x = 1.1, y = 2.2
        Point p2 = new Point(4.0, 2.0);
        Point p3 = new Point(4, 5);
        Point p4 = new Point(6, 7);
        Point p5 = new Point(8, 9);
        Point p6 = new Point(10, 11);
        Point p7 = new Point(11, 10);

        List<Point> testList = Arrays.asList(p1, p2, p3, p4, p5, p6, p7);

        KDTree test = new KDTree(testList);

    }

}
