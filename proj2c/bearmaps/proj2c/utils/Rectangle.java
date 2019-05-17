package bearmaps.proj2c.utils;

import bearmaps.proj2ab.Point;


/**
 * @Source: https://www.baeldung.com/java-check-if-two-rectangles-overlap
 * */
public class Rectangle {
    private Point topLeft;
    private Point bottomRight;

    public Rectangle (Point topLeft, Point bottomRight){
        this.topLeft = topLeft;
        this.bottomRight = bottomRight;
    }

    public boolean isOverlapping(Rectangle other) {
        if (this.topLeft.getY() < other.bottomRight.getY()
                || other.topLeft.getY() < this.bottomRight.getY()) {
            return false;
        }
        if (this.topLeft.getX() > other.bottomRight.getX()
                || other.topLeft.getX() > this.bottomRight.getX()) {
            return false;
        }
        return true;
    }

    public Point getTopLeft() {
        return topLeft;
    }

    public Point getBottomRight() {
        return bottomRight;
    }

}
