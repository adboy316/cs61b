package bearmaps;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import static junit.framework.TestCase.assertEquals;

public class KDTreeTest {


    List<Point> largeList = new ArrayList<>();
    List<Point> extraLargeList = new ArrayList<>();

    @Before
    public void constructLists () {

//        Random coordLarge = new Random(2000);
//        for (int i = 0; i < 50000; i++) {
//            largeList.add(new Point (coordLarge.nextDouble(), coordLarge.nextDouble()));
//        }
//
//        Random coordExtraLarge = new Random(20000);
//        for (int i = 0; i < 5000000; i++) {
//            largeList.add(new Point (coordExtraLarge.nextDouble(), coordExtraLarge.nextDouble()));
//        }
    }


    @Test
    public void testNearest() {
        Point p1 = new Point(1.1, 2.2); // constructs a Point with x = 1.1, y = 2.2
        Point p2 = new Point(3.3, 4.4);
        Point p3 = new Point(-2.9, 4.2);

        List<Point> testList = Arrays.asList(p1, p2, p3);
        KDTree nn = new KDTree(testList);
        NaivePointSet naiveNN = new NaivePointSet(testList);
        Point ret = nn.nearest(3.0, 4.0);

        assertEquals(3.3, ret.getX(), 0.5);
        assertEquals(4.3, ret.getY(), 0.5);
        assertEquals(naiveNN.nearest(3.0, 4.0), nn.nearest(3.0, 4.0));

    }

    @Test
    public void testLongList() {
        List<Point> longList = new ArrayList<>();
        Random coord = new Random(20);
        for (int i = 0; i < 100000; i++) {
            longList.add(new Point (coord.nextDouble(), coord.nextDouble()));
        }

        NaivePointSet shortNaiveList = new NaivePointSet(longList);
        KDTree shortKDTree = new KDTree(longList);

        Random coordNearest = new Random(20);
        for (int i = 0; i < 5000; i++) {
            Double xPoint = coordNearest.nextDouble();
            Double yPoint = coordNearest.nextDouble();

            assertEquals(shortNaiveList.nearest(xPoint, yPoint), shortKDTree.nearest(xPoint, yPoint));
        }
    }



}
