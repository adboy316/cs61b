package hw2;

import org.junit.Before;
import org.junit.Test;

import static junit.framework.TestCase.assertTrue;

public class PerlocationTest {

    Percolation test;

    @Before
    public void setUp() {
        test = new Percolation(5);
    }


    @Test
    public void testOpen () {
        test.open(0, 0);
        assertTrue(test.isOpen(0,0));
    }

//    @Test
//    public void testNeighbors() {
//        // Case 1
//        int[] expected = new int[] {1, 5};
//        assertArrayEquals(expected, test.checkNeighbors(0,0));
//        // Case 2
//        expected = new int[] {3, 9};
//        assertArrayEquals(expected, test.checkNeighbors(0,4));
//        // Case 3
//        expected = new int[] {1, 3, 7};
//        assertArrayEquals(expected, test.checkNeighbors(0,2));
//        // Case 4
//        expected = new int[] {15, 21};
//        assertArrayEquals(expected, test.checkNeighbors(4,0));
//        // Case 5
//        expected = new int[] {19, 23};
//        assertArrayEquals(expected, test.checkNeighbors(4,4));
//        // Case 6
//        expected = new int[] {20, 22, 16};
//        assertArrayEquals(expected, test.checkNeighbors(4,1));
//        // Case 7
//        expected = new int[] {0, 10, 6};
//        assertArrayEquals(expected, test.checkNeighbors(1,0));
//        // Case 8
//        expected = new int[] {4, 14, 8};
//        assertArrayEquals(expected, test.checkNeighbors(1,4));
//        // Else case
//        expected = new int[] {6, 16, 10, 12};
//        assertArrayEquals(expected, test.checkNeighbors(2,1));
//
//    }
}
