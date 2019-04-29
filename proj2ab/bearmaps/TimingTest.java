package bearmaps;

import edu.princeton.cs.algs4.Stopwatch;

public class TimingTest {

    public static void main(String[] args) {

        ArrayHeapMinPQ<Integer> testPQLarge = new ArrayHeapMinPQ<>();
        NaiveMinPQ<Integer> testNaivePQ = new NaiveMinPQ<>();


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
    }
}
