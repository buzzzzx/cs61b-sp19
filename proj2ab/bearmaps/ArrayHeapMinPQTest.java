package bearmaps;

import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class ArrayHeapMinPQTest {

    @Test
    public void testArrayHeapMinPQ() {
        ArrayHeapMinPQ<String> PQ = new ArrayHeapMinPQ<>();

        // test add.
        PQ.add("aa", 5);
        PQ.add("bb", 7);
        String a1 = PQ.getSmallest();
        String e1 = "aa";
        assertEquals(e1, a1);

        PQ.add("cc", 3);
        String a2 = PQ.getSmallest();
        String e2 = "cc";
        assertEquals(e2, a2);

        // test contains.
        PQ.add("dd", 6);
        boolean a3 = PQ.contains("dd");
        assertTrue(a3);

        // test getSmallest.
        PQ.add("ee", 1);
        String a4 = PQ.getSmallest();
        String e4 = "ee";
        assertEquals(e4, a4);

        // test removeSmallest.
        String a5 = PQ.removeSmallest();
        String e5 = "ee";
        assertEquals(e5, a5);

        String a6 = PQ.getSmallest();
        String e6 = "cc";
        assertEquals(e6, a6);

        String a7 = PQ.removeSmallest();
        String a8 = PQ.getSmallest();
        String e8 = "aa";
        assertEquals(e6, a6);

        // test size.
        int a9 = PQ.size();
        int e9 = 3;
        assertEquals(e9, a9);

        // test changePriority.
        PQ.add("ff", 8);
        PQ.changePriority("bb", 2);
        String a10 = PQ.getSmallest();
        String e10 = "bb";
        assertEquals(e10, a10);

        PQ.changePriority("bb", 100);
        String a11 = PQ.getSmallest();
        String e11 = "aa";

    }

    @Test
    public void testRuntime() {
        long startTime = System.currentTimeMillis();
        ArrayHeapMinPQ<Integer> minHeap = new ArrayHeapMinPQ<>();
        for (int i = 0; i < 200000; i += 1) {
            minHeap.add(i, 100000 - i);
        }
        for (int i = 0; i < 200000; i += 1) {
            minHeap.removeSmallest();
        }
        long endTime = System.currentTimeMillis();
        System.out.println("Total time elapsed: " + (endTime - startTime) / 1000.0 +  " seconds.");


        long start2 = System.currentTimeMillis();
        NaiveMinPQ<Integer> naiveMinPQ = new NaiveMinPQ<>();
        for (int j = 0; j < 200000; j += 1) {
            naiveMinPQ.add(j, 100000 - j);
        }
        for (int j = 0; j < 200000; j += 1) {
            naiveMinPQ.removeSmallest();
        }
        long end2 = System.currentTimeMillis();
        System.out.println("Total time elapsed: " + (end2 - start2) / 1000.0 +  " seconds.");
    }

}
