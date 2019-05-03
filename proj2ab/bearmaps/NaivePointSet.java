package bearmaps;

import java.util.List;

import static bearmaps.Point.distance;

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

        Point target = new Point(x, y);

        Point nearestPoint = null;
        Double nearestDistance = 0.0;


        for (Point p: points) {
            if (nearestPoint == null) {
                nearestDistance = Math.sqrt(distance(p, target));
                nearestPoint = p;
            }
            if (nearestDistance > Math.sqrt(distance(p, target))) {
                nearestDistance = Math.sqrt(distance(p, target)) ;
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
