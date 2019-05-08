import edu.princeton.cs.algs4.Queue;
import org.junit.Test;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertTrue;

public class TestSortAlgs {

    @Test
    public void testQuickSort() {
        Queue<String> tas = new Queue<String>();
        tas.enqueue("3");
        tas.enqueue("6");
        tas.enqueue("7");
        tas.enqueue("2");
        tas.enqueue("1");
        tas.enqueue("8");

        tas = QuickSort.quickSort(tas);

        assertEquals(6, tas.size());

        while (tas.size() > 1) {
            String first = tas.dequeue();
            String second = tas.peek();
            assertTrue(first.compareTo(second) < 0);
        }

    }

    @Test
    public void testMergeSort() {
        Queue<String> tas = new Queue<String>();
        tas.enqueue("Joe");
        tas.enqueue("Omar");
        tas.enqueue("Itai");
        tas.enqueue("Gtai");
        tas.enqueue("Ftai");
        tas.enqueue("blue");

        tas = MergeSort.mergeSort(tas);
        assertEquals(6, tas.size());

        while (tas.size() > 1) {
            String first = tas.dequeue();
            String second = tas.peek();
            assertTrue(first.compareTo(second) < 0);
        }

    }

    /**
     * Returns whether a Queue is sorted or not.
     *
     * @param items  A Queue of items
     * @return       true/false - whether "items" is sorted
     */
    private <Item extends Comparable> boolean isSorted(Queue<Item> items) {
        if (items.size() <= 1) {
            return true;
        }
        Item curr = items.dequeue();
        Item prev = curr;
        while (!items.isEmpty()) {
            prev = curr;
            curr = items.dequeue();
            if (curr.compareTo(prev) < 0) {
                return false;
            }
        }
        return true;
    }
}
