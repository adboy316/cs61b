package bearmaps;

import edu.princeton.cs.algs4.Stopwatch;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class TimingTest {

    public static void main(String[] args) {

        ArrayHeapMinPQ<Integer> testPQLarge = new ArrayHeapMinPQ<>();
        NaiveMinPQ<Integer> testNaivePQ = new NaiveMinPQ<>();

        System.out.println("Conducting add() shortList......");
        Stopwatch sw = new Stopwatch();
        for (int i = 0; i < 2000000; i++ ) {
            testPQLarge.add(i, i);
        }
        System.out.println("Total time elapsed: " + sw.elapsedTime() +  " seconds.");

        Stopwatch sw2 = new Stopwatch();
        for (int i = 0; i < 200000; i++ ) {
            testNaivePQ.add(i, i);
        }
        System.out.println("Total time elapsed: " + sw2.elapsedTime() +  " seconds.");




        System.out.println("Conducting contain() shortList......");
        Stopwatch sw3 = new Stopwatch();
        for (int i = 0; i < 200000; i++ ) {
            testPQLarge.contains(new Random().nextInt(21000));
        }
        System.out.println("Total time elapsed: " + sw3.elapsedTime() +  " seconds.");
        Stopwatch sw4 = new Stopwatch();
        for (int i = 0; i < 20000; i++ ) {
            testNaivePQ.contains(new Random().nextInt(21000));
        }
        System.out.println("Total time elapsed: " + sw4.elapsedTime() +  " seconds.");


        List<Point> longList = new ArrayList<>();
        Random coord = new Random(20);
        for (int i = 0; i < 100000; i++) {
            longList.add(new Point (coord.nextDouble(), coord.nextDouble()));
        }

        NaivePointSet shortNaiveList = new NaivePointSet(longList);
        KDTree shortKDTree = new KDTree(longList);


        System.out.println("Conducting neareast() on NaivePointSet vs KDtree......");
        Stopwatch sw5 = new Stopwatch();
        Random coordNearest = new Random(20);
        for (int i = 0; i < 5000; i++) {
            Double xPoint = coordNearest.nextDouble();
            Double yPoint = coordNearest.nextDouble();
            shortNaiveList.nearest(xPoint, yPoint);
        }
        System.out.println("Total time elapsed for NaivePointSet: " + sw5.elapsedTime() +  " seconds.");

        Stopwatch sw6 = new Stopwatch();
        for (int i = 0; i < 5000; i++) {
            Double xPoint = coordNearest.nextDouble();
            Double yPoint = coordNearest.nextDouble();
            shortKDTree.nearest(xPoint, yPoint);
        }
        System.out.println("Total time elapsed for KDTree: " + sw6.elapsedTime() +  " seconds.");


    }
}
