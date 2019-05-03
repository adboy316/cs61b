package bearmaps;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static junit.framework.TestCase.assertEquals;

public class KDTreeTest {

    List<Point> test = new ArrayList<>();

    @Before
    public void constructShortList () {
        for (int i = 0; i < 10; i++) {
            test.add(new Point (i, i+1));
        }
    }


    @Test
    public void testNearest() {
        Point p1 = new Point(1.1, 2.2); // constructs a Point with x = 1.1, y = 2.2
        Point p2 = new Point(3.3, 4.4);
        Point p3 = new Point(-2.9, 4.2);

        List<Point> testList = Arrays.asList(p1, p2, p3);
        KDTree nn = new KDTree(testList);
        Point ret = nn.nearest(3.0, 4.0);

        assertEquals(3.3, ret.getX(), 0.5);
        assertEquals(4.3, ret.getY(), 0.5);

    }

    @Test
    public void testShortList() {

        KDTree shortKDTree = new KDTree(test);


    }

}
