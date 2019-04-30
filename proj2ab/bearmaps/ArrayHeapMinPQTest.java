package bearmaps;

import org.junit.Before;
import org.junit.Test;

import java.util.Map;
import java.util.Random;

import static org.junit.Assert.*;


public class ArrayHeapMinPQTest {
    ArrayHeapMinPQ<String> testPQ;
    ArrayHeapMinPQ<Integer> testPQLarge;
    NaiveMinPQ<Integer> testNaivePQ;


    ArrayHeapMinPQ<Integer> testPQLargeLoaded;
    NaiveMinPQ<Integer> testNaivePQLoaded;

    @Before
    public void setUp () {
        testPQ  = new ArrayHeapMinPQ<>(10);
        testPQLarge  = new ArrayHeapMinPQ<>(10);
        testNaivePQ = new NaiveMinPQ<>();

        testPQLargeLoaded  = new ArrayHeapMinPQ<>(10);
        testNaivePQLoaded = new NaiveMinPQ<>();


    }

    @Test
    public void testAddAndSize() {
        assertEquals(0, testPQ.size());
        testPQ.add("A", 3.0);
        assertEquals("A", testPQ.getSmallest());
        assertEquals(1, testPQ.size());
        testPQ.add("V", 2.0);
        testPQ.add("D", 1.0);
        assertEquals("D", testPQ.getSmallest());
        testPQ.add("C", 0.9);
        assertEquals(4, testPQ.size());
        assertEquals("C", testPQ.getSmallest());
    }

    @Test
    public void testRemoveSmallest() {
        testPQ.add("A", 3.0);
        assertEquals("A", testPQ.removeSmallest());
        assertEquals(0, testPQ.size());
        testPQ.add("A", 3.0);
        testPQ.add("B", 2.0);
        testPQ.add("C", 1.0);
        testPQ.add("D", 4.0);
        assertEquals("C", testPQ.removeSmallest());
        assertEquals(3, testPQ.size());
        assertEquals("B", testPQ.getSmallest());
    }

    @Test
    public void testContains() {
        assertFalse(testPQ.contains("A"));
        testPQ.add("A", 3.0);
        assertTrue(testPQ.contains("A"));
        assertFalse(testPQ.contains("B"));
        testPQ.add("G", 3.0);
        testPQ.add("C", 1.0);
        testPQ.add("D", 4.0);
        testPQ.add("F", 2.0);
        assertTrue(testPQ.contains("F"));
        assertTrue(testPQ.contains("D"));
    }

    @Test
    public void testChangePriority() {
        testPQ.add("A", 3.0);
        testPQ.add("B", 2.5);
        testPQ.add("C", 10.0);
        testPQ.add("D", 8.5);
        testPQ.add("E", 9.0);
        testPQ.add("F", 8.5);
        assertEquals("B", testPQ.getSmallest());
        testPQ.changePriority("A", 2.0);
        assertEquals("A", testPQ.getSmallest());
        testPQ.changePriority("C", 1.9);
        assertEquals("C", testPQ.getSmallest());
        testPQ.changePriority("F", 1.8);
        assertEquals("F", testPQ.getSmallest());
        testPQ.changePriority("F", 1.7);
        assertEquals("F", testPQ.getSmallest());
        testPQ.removeSmallest();
        assertEquals("C", testPQ.getSmallest());

    }

    @Test
    public void setTestPQLarge() {
        for (int i = 0; i < 500000  ; i++ ) {
            testPQLarge.add(i, i);
        }
    }

    @Test
    public void setTestNaivePQLarge() {
        for (int i = 0; i < 20000; i++ ) {
            testNaivePQ.add(i, i);
        }
    }


    @Test
    public void testNaivePQLargeContains() {
        for (int i = 0; i < 20000; i++ ) {
            testNaivePQLoaded.contains(i);
        }
    }

    @Test
    public void testPQLargeContains() {
        for (int i = 0; i < 20000; i++ ) {
            testPQLargeLoaded.contains(i);
        }
    }

    @Test
    public void testPQLargeChange() {

        for (int i = 0; i < 500000  ; i++ ) {
            testPQLargeLoaded.add(i, i);
        }

        for (Map.Entry<Integer, Integer> key: testPQLargeLoaded.items.entrySet()) {
            testPQLargeLoaded.changePriority(key.getKey(), new Random().nextInt(21000));
        }

    }

//    @Test
//    public void testPQNaiveChange() {
//
//        for (int i = 0; i < 500000; i++) {
//            testNaivePQLoaded.add(i, i);
//        }
//
//        for (int i = 0; i < 500000; i++) { testNaivePQLoaded.changePriority(testNaivePQLoaded.getItems(i), new Random().nextInt(21000));
//
//        }
//
//    }


}


