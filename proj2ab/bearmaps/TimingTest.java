package bearmaps;

import edu.princeton.cs.algs4.Stopwatch;

import java.util.Random;

public class TimingTest {

    public static void main(String[] args) {

        ArrayHeapMinPQ<Integer> testPQLarge = new ArrayHeapMinPQ<>();
        NaiveMinPQ<Integer> testNaivePQ = new NaiveMinPQ<>();

        System.out.println("Conducting add() test......");
        Stopwatch sw = new Stopwatch();
        for (int i = 0; i < 20000; i++ ) {
            testPQLarge.add(i, i);
        }
        System.out.println("Total time elapsed: " + sw.elapsedTime() +  " seconds.");

        Stopwatch sw2 = new Stopwatch();
        for (int i = 0; i < 200000; i++ ) {
            testNaivePQ.add(i, i);
        }
        System.out.println("Total time elapsed: " + sw2.elapsedTime() +  " seconds.");




        System.out.println("Conducting contain() test......");
        Stopwatch sw3 = new Stopwatch();
        for (int i = 0; i < 20000; i++ ) {
            testPQLarge.contains(new Random().nextInt(21000));
        }
        System.out.println("Total time elapsed: " + sw3.elapsedTime() +  " seconds.");
        Stopwatch sw4 = new Stopwatch();
        for (int i = 0; i < 20000; i++ ) {
            testNaivePQ.contains(new Random().nextInt(21000));
        }
        System.out.println("Total time elapsed: " + sw4.elapsedTime() +  " seconds.");
    }
}
