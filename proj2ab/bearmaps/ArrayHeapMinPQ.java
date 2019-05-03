
package bearmaps;

import java.util.HashMap;
import java.util.NoSuchElementException;

public class ArrayHeapMinPQ<T extends Comparable> implements ExtrinsicMinPQ<T> {

    private PriorityNode[] pq;             // store items at indices 1 to n
    private int n;                         // number of items on priority queue
    private HashMap<T, Integer> items;     // item and its place in the pq
    /**
     * Initializes an empty priority queue with the given initial capacity.
     *
     * @param  initCapacity the initial capacity of this priority queue
     */
    public ArrayHeapMinPQ(final int initCapacity) {
        pq = new PriorityNode[initCapacity + 1];
        n = 0;
        items = new HashMap<>();
    }

    public ArrayHeapMinPQ() {
        this(2);
    }

    /**
     * Adds an item with the given priority value. Throws an
     * IllegalArgumentExceptionb if item is already present.
     * You may assume that item is never null.
     *
     * @param item
     * @param priority
     */
    @Override
    public void add(T item, double priority) {
        if (contains(item)) {
            throw new IllegalArgumentException("Item is already present.");
        }
        if (n == pq.length - 1) {
            resize (2 * pq.length);
        }
        pq[++n] = new PriorityNode(item, priority, n);
        items.put(item, n);
        swim(n);
    }

    /**
     * Returns true if the PQ contains the given item.
     *
     * @param item
     */
    @Override
    public boolean contains(T item) {
        if (n == 0) {
            return false;
        }
        return items.containsKey(item);
    }

    /**
     * Returns the minimum item. Throws NoSuchElementException if the PQ is empty.
     * @return
     */
    @Override
    public T getSmallest() {
        if (n == 0) {
            throw new NoSuchElementException("The PQ is empty.");
        }
        return (T) pq[1].returnItem();
    }

    /**
     * Removes and returns the minimum item. Throws NoSuchElementException if the PQ is empty.
     * @return
     */
    @Override
    public T removeSmallest() {
        if (n == 0) {
            throw new NoSuchElementException("The PQ is empty.");
        }
        T smallest = (T) pq[1].returnItem();
        swap(1, n--);
        sink(1);
        pq[n+1] = null;
        if ((n > 0) && (n == (pq.length - 1) / 4)) {
            resize(pq.length / 2);
        }
        items.remove(smallest);

        return smallest;
    }

    /**
     * Returns the number of items in the PQ.
     */
    @Override
    public int size() {
        return n;
    }

    /**
     * Changes the priority of the given item. Throws NoSuchElementException if the item
     * doesn't exist.
     *
     * @param item
     * @param priority
     */
    @Override
    public void changePriority(T item, double priority) {
        if (items.containsKey(item)) {
            int itemPos = items.get(item);
            pq[itemPos].setPriority(priority);
            swim(itemPos);
            sink(itemPos);
            return;
        }
        throw new NoSuchElementException("PQ does not contain " + item);
    }

    /**
     * Is I greater than J?
     * */
    private boolean greater (int i, int j) {
        return pq[i].compareTo(pq[j]) == 1;
    }

    /**
     * Double size of PQ heap array
     * */
    private void resize (int capacity) {
        assert capacity > n;
        PriorityNode[] temp = new PriorityNode[capacity];
        for (int i = 1; i <= n; i++) {
            temp[i] = pq[i];
        }
        pq = temp;
    }

    /**
     * Move k up the tree if it smaller than its parent
     * */
    private void swim(int k){
        if (k > 1 && greater(parent(k), k)) {
            swap(k, parent(k));
            swim (parent(k));
        }
    }

    /**
     * Move k down the three if it is bigger than its children
     * */
    private void sink(int k) {
        while (2*k <= n) {
            int j = 2*k;
            if (j < n && greater(j, j+1)) j++;
            if (!greater(k, j)) break;
            swap(k, j);
            k = j;
        }
    }

    /**
     * Returns the parent of k
     * */
    private int parent(int k) {
        return k / 2;
    }

    /**
     * Swaps k with its parent
     * */
    private void swap (int k, int parent){

        items.replace((T) pq[k].returnItem(), parent);
        items.replace((T) pq[parent].returnItem(), k);

        PriorityNode temp = pq[k];
        pq[k] = pq[parent];
        pq[parent] = temp;
    }

    /**
     * Is PQ empty?
     * */
    private boolean isEmpty() {
        return n == 0;
    }

    HashMap<T, Integer> getItems() {
        return items;
    }

}
