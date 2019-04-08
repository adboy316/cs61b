package es.datastructur.synthesizer;
//TODO: Make sure to that this class and all of its methods are public
//TODO: Make sure to add the override tag for all overridden methods
//TODO: Make sure to make this class implement BoundedQueue<T>

import java.util.Iterator;
import java.util.NoSuchElementException;

public class ArrayRingBuffer<T> implements BoundedQueue <T> {
    /* Index for the next dequeue or peek. */
    private int first;
    /* Index for the next enqueue. */
    private int last;
    /* Variable for the fillCount. */
    private int fillCount;
    /* Array for storing the buffer data. */
    private T[] rb;

    /**
     * Create a new ArrayRingBuffer with the given capacity.
     */
    public ArrayRingBuffer(int capacity) {
        // TODO: Create new array with capacity elements.
        //       first, last, and fillCount should all be set to 0.
        first = 0;
        last = 0;
        fillCount = 0;
        rb =  (T[]) new Object[capacity];
    }

    /**
     * Adds x to the end of the ring buffer. If there is no room, then
     * throw new RuntimeException("Ring buffer overflow").
     */

    @Override
    public void enqueue(T x) {
        // TODO: Enqueue the item. Don't forget to increase fillCount and update
        //       last.

        if (fillCount == capacity()) {
            throw new IllegalArgumentException("Buffer is full.");
        }

        rb[last] = x;
        last = 1 + (last % (rb.length ));
        if (last == rb.length ){
            last = 0;
        }
        fillCount += 1;

    }

    /**
     * Dequeue oldest item in the ring buffer. If the buffer is empty, then
     * throw new RuntimeException("Ring buffer underflow").
     */
    @Override
    public T dequeue() {
        if (fillCount == 0) {
            throw new IllegalArgumentException("Ring buffer underflow");
        }

        T firstItem = rb[first];

        rb[first] = null;
        first = 1 + (first % rb.length);
        if (first == rb.length ){
            first = 0;
        }
        fillCount -= 1;
        return firstItem;
    }
    @Override
    public int capacity() {
        return rb.length;
    }

    @Override
    public int fillCount() {
        return fillCount;
    }
    /**
     * Return oldest item, but don't remove it. If the buffer is empty, then
     * throw new RuntimeException("Ring buffer underflow").
     */
    public T peek() {
        if (fillCount == 0) {
            throw new IllegalArgumentException("Ring buffer underflow");
        }
        return rb[first];
    }

    // Methods for testing
//    public T getLast(){
//        return rb[(last % rb.length) - 1];
//    }
//
//
//    public int getLastIndex(){
//        return last;
//    }


    @Override
    public boolean equals (Object other) {
        if (this == other) {
            return true;
        }

        if (other == null) {
            return false;
        }

        if (other.getClass() != this.getClass()) {
            return false;
        }

        ArrayRingBuffer<T> o = (ArrayRingBuffer <T>) other;
        if (o.fillCount != this.fillCount) {
            return false;
        }

        if (o.capacity() != this.capacity()) {
            return false;
        }

       for (int i = 0; i < this.capacity()-1; i++) {
           if (this.rb[i] != o.rb[i]) {
               return false;
           }
       }
       return true;
    }

    /**
     * Returns an iterator over elements of type {@code T}.
     *
     * @return an Iterator.
     */
    @Override
    public Iterator<T> iterator() {
        return new ArrayRingBufferIterator();
    }

    private class ArrayRingBufferIterator implements Iterator<T> {

        private int bufferIteratorPos;

        public ArrayRingBufferIterator() {
            bufferIteratorPos = 0;
        }

        /**
         * Returns {@code true} if the iteration has more elements.
         * (In other words, returns {@code true} if {@link #next} would
         * return an element rather than throwing an exception.)
         *
         * @return {@code true} if the iteration has more elements
         */
        @Override
        public boolean hasNext() {
            return bufferIteratorPos < fillCount;
        }

        /**
         * Returns the next element in the iteration.
         *
         * @return the next element in the iteration
         * @throws NoSuchElementException if the iteration has no more elements
         */
        @Override
        public T next() {

            T returnItem = rb[bufferIteratorPos];
            bufferIteratorPos += 1;
            return returnItem;
        }

    }

}



