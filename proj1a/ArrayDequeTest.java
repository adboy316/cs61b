import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ArrayDequeTest {

    ArrayDeque testArray = new ArrayDeque<String>();
    ArrayDeque testArrayInts = new ArrayDeque<Integer>();

    @Before
    public void setUp() {
        int N = 1000;
        for (int i = 0; i < N; i += 1) {
            testArrayInts.addFirst(i);
        }

    }

    @Test
    public void testConstructor(){
        assertEquals(testArray.getItems().length, 8);
        assertEquals(null, testArray.getItems()[0]);
        assertEquals(testArray.getNextFirst(), 1);
        assertEquals(testArray.getNextLast(), 0);
        assertEquals(testArray.getSize(), 0);
    }


    @Test
    public void testAddFirst() {
        testArray.addFirst("a");
        assertEquals(testArray.getFirstItem(), "a");
        testArray.addFirst("b");
        testArray.addLast("c");
        assertEquals(testArray.getLastItem(), "c");
        assertEquals(testArray.getFirstItem(), "b");
        testArrayInts.addFirst(1001);
        assertEquals(testArrayInts.getFirstItem(), 1001);
    }

    @Test
    public void testAddLast() {
        testArray.addLast("a");
        assertEquals(testArray.getLastItem(), "a");
        testArray.addFirst("b");
        testArray.addLast("c");
        assertEquals(testArray.getFirstItem(), "b");
        assertEquals(testArray.getLastItem(), "c");
        testArrayInts.addLast(1001);
        assertEquals(testArrayInts.getLastItem(), 1001);
    }

    @Test
    public void testIsEmpty(){
        assertEquals(testArray.isEmpty(), true);
        assertEquals(testArrayInts.isEmpty(), false);
    }

}
