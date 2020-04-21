import edu.princeton.cs.algs4.Queue;

import org.junit.Test;
import static org.junit.Assert.*;

public class TestSortAlgs {

    @Test
    public void testQuickSort() {
        Queue<String> unsorted1 = new Queue<>();
        unsorted1.enqueue("Joe");
        unsorted1.enqueue("Omar");
        unsorted1.enqueue("Itai");
        unsorted1.enqueue("Eila");
        unsorted1.enqueue("Anna");
        unsorted1.enqueue("Judy");

        Queue<String> actual = QuickSort.quickSort(unsorted1);
        assertTrue(isSorted(actual));

        Queue<Integer> unsorted2 = new Queue<>();
        unsorted2.enqueue(5);
        unsorted2.enqueue(3);
        unsorted2.enqueue(8);
        unsorted2.enqueue(1);
        unsorted2.enqueue(4);
        unsorted2.enqueue(7);
        unsorted2.enqueue(2);
        unsorted2.enqueue(6);
        Queue<Integer> actual2 = QuickSort.quickSort(unsorted2);
        assertTrue(isSorted(actual2));

        Queue<Integer> unsorted3 = new Queue<>();
        unsorted3.enqueue(7);
        unsorted3.enqueue(6);
        unsorted3.enqueue(5);
        unsorted3.enqueue(4);
        unsorted3.enqueue(3);
        unsorted3.enqueue(2);
        unsorted3.enqueue(1);
        Queue<Integer> actual3 = QuickSort.quickSort(unsorted3);
        assertTrue(isSorted(actual3));

    }

    @Test
    public void testMergeSort() {
        Queue<String> unsorted1 = new Queue<>();
        unsorted1.enqueue("Joe");
        unsorted1.enqueue("Omar");
        unsorted1.enqueue("Itai");
        unsorted1.enqueue("Eila");
        unsorted1.enqueue("Anna");
        unsorted1.enqueue("Judy");

        Queue<String> actual = MergeSort.mergeSort(unsorted1);
        assertTrue(isSorted(actual));

        Queue<Integer> unsorted2 = new Queue<>();
        unsorted2.enqueue(5);
        unsorted2.enqueue(3);
        unsorted2.enqueue(8);
        unsorted2.enqueue(1);
        unsorted2.enqueue(4);
        unsorted2.enqueue(7);
        unsorted2.enqueue(2);
        unsorted2.enqueue(6);
        Queue<Integer> actual2 = MergeSort.mergeSort(unsorted2);
        assertTrue(isSorted(actual2));

        Queue<Integer> unsorted3 = new Queue<>();
        unsorted3.enqueue(7);
        unsorted3.enqueue(6);
        unsorted3.enqueue(5);
        unsorted3.enqueue(4);
        unsorted3.enqueue(3);
        unsorted3.enqueue(2);
        unsorted3.enqueue(1);
        Queue<Integer> actual3 = MergeSort.mergeSort(unsorted3);
        assertTrue(isSorted(actual3));

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
