package bearmaps;

import com.sun.tools.javac.util.List;
import org.junit.Test;

import static junit.framework.TestCase.assertEquals;

public class TestNaivePointSet {

    @Test
    public void testNearest() {
        Point p1 = new Point(1.1, 2.2); // constructs a Point with x = 1.1, y = 2.2
        Point p2 = new Point(3.3, 4.4);
        Point p3 = new Point(-2.9, 4.2);
        List<Point> originalPoints =  (List.of(p1, p2, p3));


        NaivePointSet nn = new NaivePointSet(List.of(p1, p2, p3));
        Point ret = nn.nearest(3.0, 4.0); // returns p2

        assertEquals(3.3, ret.getX(), 0.5);
        assertEquals(4.3, ret.getY(), 0.5);
        assertEquals((List.of(p1, p2, p3)), originalPoints);

    }

    @Test
    public void testNearestHard() {
        Point p1 = new Point(1.1, 2.2); // constructs a Point with x = 1.1, y = 2.2
        Point p2 = new Point(3.3, 4.4);
        Point p3 = new Point(-2.9, 4.2);
        Point p4 = new Point(2.9, 4.2);
        Point p5 = new Point(-5.9, -4.2);
        Point p6 = new Point(12.9, 4.2);


        NaivePointSet nn = new NaivePointSet(List.of(p1, p2, p3));
        Point ret = nn.nearest(3.0, 4.0); // returns p2

        assertEquals(3.3, ret.getX(), 0.5);
        assertEquals(4.3, ret.getY(), 0.5);
    }
}
