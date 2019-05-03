package bearmaps;

import java.util.List;

public class NaivePointSet implements PointSet {

    private List<Point> points;

    public NaivePointSet(List<Point> points) {
        this.points = points;
    }

    /**
     *  Finds the closest point to a given coordinate.
     * */
    @Override
    public Point nearest(double x, double y) {

        Point nearestPoint = null;
        Double nearestDistance = 0.0;

        for (Point p: points) {
            if (nearestPoint == null) {
                nearestDistance = euclideanDistance(p, x, y);
                nearestPoint = p;
            }
            if (nearestDistance > euclideanDistance(p, x, y)) {
                nearestDistance = euclideanDistance(p, x, y);
                nearestPoint = p;
            }
        }

        return nearestPoint;
    }

    /**
     *  Calculate Euclidean distance between two points
     * */
    private double euclideanDistance(Point p, double x, double y) {

        Double xPoints = Math.pow(x - p.getX(), 2);
        Double yPoints = Math.pow(y - p.getY(), 2);

        return Math.sqrt(xPoints + yPoints);
    }
}
