package es.datastructur.synthesizer;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/** Tests the ArrayRingBuffer class.
 *  @author Josh Hug
 */

public class TestArrayRingBuffer {
    @Test
    public void constructorTest() {
        ArrayRingBuffer arb = new ArrayRingBuffer(10);
        assertEquals(10, arb.capacity());
        assertEquals(0, arb.fillCount());

    }

    @Test
    public void testEnqueue() {
        ArrayRingBuffer arb = new ArrayRingBuffer(10);
        arb.enqueue(1);
        assertEquals(1, arb.fillCount());
        assertEquals(10, arb.capacity());
        assertEquals(1, arb.peek());
        assertEquals(1, arb.getLastIndex());
        assertEquals(1, arb.getLast());

        arb.enqueue(2);
        arb.enqueue(4);
        assertEquals(3, arb.fillCount());
        assertEquals(10, arb.capacity());
        assertEquals(1, arb.peek());
        assertEquals(3, arb.getLastIndex());
        assertEquals(4, arb.getLast());


    }

    @Test
    public void testDequeue() {

        ArrayRingBuffer arb = new ArrayRingBuffer(10);
        arb.enqueue(1);
        arb.enqueue(2);
        arb.enqueue(4);

        arb.dequeue();
        assertEquals(2, arb.fillCount());
        assertEquals(10, arb.capacity());
        assertEquals(2, arb.peek());
        assertEquals(3, arb.getLastIndex());
        assertEquals(4, arb.getLast());

        arb.dequeue();
        assertEquals(1, arb.fillCount());
        assertEquals(10, arb.capacity());
        assertEquals(4, arb.peek());
        assertEquals(3, arb.getLastIndex());
        assertEquals(4, arb.getLast());
    }


    @Test
    public void fillBuffer() {
        ArrayRingBuffer arb = new ArrayRingBuffer(5);
        arb.enqueue(1);
        arb.enqueue(2);
        arb.enqueue(4);
        arb.dequeue();
        arb.dequeue();
        arb.enqueue(1);
        arb.enqueue(1);
        arb.enqueue(2);
        arb.enqueue(2);
        arb.dequeue();
        arb.dequeue();
        arb.enqueue(1);
        assertEquals(4, arb.fillCount());
        assertEquals(5, arb.capacity());
        assertEquals(1, arb.peek());
        assertEquals(3, arb.getLastIndex());
        assertEquals(1, arb.getLast());
    }

    @Test
    public void wrapAroundDequeue() {
        ArrayRingBuffer arb = new ArrayRingBuffer(5);
        arb.enqueue(1);
        arb.enqueue(1);
        arb.enqueue(2);
        arb.dequeue();
        arb.dequeue();
        arb.dequeue();
        arb.enqueue(1);
        arb.enqueue(1);
        arb.enqueue(2);
        arb.enqueue(2);
        arb.dequeue();
        arb.dequeue();
        arb.dequeue();
        assertEquals(1, arb.fillCount());
        assertEquals(5, arb.capacity());
        assertEquals(2, arb.peek());
        assertEquals(2, arb.getLastIndex());
        assertEquals(2, arb.getLast());
    }

    @Test(expected = IllegalArgumentException.class)
    public void overfillBuffer() {
        ArrayRingBuffer arb = new ArrayRingBuffer(2);
        arb.enqueue(1);
        arb.enqueue(1);
        arb.enqueue(2);

    }

    @Test
    public void testEquals() {
        ArrayRingBuffer arb = new ArrayRingBuffer(5);
        ArrayRingBuffer arb2 = new ArrayRingBuffer(5);
        ArrayRingBuffer arb3 = new ArrayRingBuffer(3);

        assertEquals(true, arb.equals(arb2));
        assertEquals(false, arb.equals(arb3));

        arb.enqueue(2);
        arb.enqueue(3);
        arb2.enqueue(2);
        arb2.enqueue(3);
        arb3.enqueue(2);
        arb3.enqueue(3);

        assertEquals(true, arb.equals(arb2));
        assertEquals(false, arb.equals(arb3));

        ArrayRingBuffer arb22 = arb2;
        assertEquals(true, arb.equals(arb22));

    }
}
