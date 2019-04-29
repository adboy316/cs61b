package bearmaps;

import java.util.ArrayList;
import java.util.NoSuchElementException;

public class ArrayHeapMinPQ<T extends Comparable> implements ExtrinsicMinPQ<T> {

    private PriorityNode[] pq;             // store items at indices 1 to n
    private int n;                         // number of items on priority queue
    ArrayList<PriorityNode> sortedPQ;

    /**
     * Initializes an empty priority queue with the given initial capacity.
     *
     * @param  initCapacity the initial capacity of this priority queue
     */
    public ArrayHeapMinPQ(int initCapacity) {
        pq = new PriorityNode[initCapacity + 1];
        n = 0;
        sortedPQ = new ArrayList<>();
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
        if (contains(item)) throw new IllegalArgumentException("Item is already present.");
        if (n == pq.length - 1) resize (2 * pq.length);
        pq[++n] = new PriorityNode(item, priority);
        swim(n);
        assert isMinHeap();

        sortedPQ.add(new PriorityNode(item, priority));
    }

    /**
     * Returns true if the PQ contains the given item.
     *
     * @param item
     */
    @Override
    public boolean contains(T item) {
        // TODO: Improve efficiency? (Right this is running O(N)... but how can we make O(log N)?
        return binarySearch(sortedPQ, item);
    }

    public boolean binarySearch(ArrayList<PriorityNode> pq, T item) {

        int start = 0;
        int end = n-1;
        while (start <= end) {
            int mid = (start + end) / 2;
            if (item.equals(pq.get(mid).returnItem())) {
                return true;
            }
            if (item.compareTo(pq.get(mid).returnItem()) == -1) {
                end = mid - 1;
            } else {
                start = mid + 1;
            }
        }
        return false;
    }


    /**
     * Returns the minimum item. Throws NoSuchElementException if the PQ is empty.
     * @return
     */
    @Override
    public T getSmallest() {
        if (n == 0) throw new NoSuchElementException("The PQ is empty.");
        return (T) pq[1].returnItem();
    }

    /**
     * Removes and returns the minimum item. Throws NoSuchElementException if the PQ is empty.
     * @return
     */
    @Override
    public T removeSmallest() {
        if (n == 0) throw new NoSuchElementException("The PQ is empty.");
        T smallest = (T) pq[1].returnItem();
        swap(1, n--);
        sink(1);
        pq[n+1] = null;
        if ((n > 0) && (n == (pq.length - 1) / 4)) resize(pq.length / 2);
        assert isMinHeap();

        sortedPQ.remove(smallest);

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
        // TODO: May also require optimization...
        for (int i = 1; i < n+1; i++) {
            if (item.equals(pq[i].returnItem())) {
                pq[i].setPriority(priority);
                swim(i);
                sink(i);
                return;
            }
        }
        throw new NoSuchElementException("PQ does not contain " + item);
    }


    /**
     * Checks if PQ is a MinHeap
     * */
    private boolean isMinHeap() {
        return isMinHeap(1);
    }

    /**
     * Checks if specific subtree of PQ is MinHeap
     * */
    private boolean isMinHeap(int k) {
        if (k > n) return true;
        int left = 2*k;
        int right = 2*k + 1;
        if (left  <= n && greater(k, left))  return false;
        if (right <= n && greater(k, right)) return false;
        return isMinHeap(left) && isMinHeap(right);
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

}