package es.datastructur.synthesizer;
import org.junit.Test;
import static org.junit.Assert.*;

/** Tests the ArrayRingBuffer class.
 *  @author Josh Hug
 */

public class TestArrayRingBuffer {
    @Test
    public void someTest() {
        ArrayRingBuffer<String> arb = new ArrayRingBuffer<>(10);

        arb.enqueue("hello");
        arb.enqueue("java");
        arb.enqueue("C++");
        arb.enqueue("task2");

        int a1 = arb.fillCount();
        assertEquals(4, a1);

        String a2 = arb.dequeue();
        int a3 = arb.fillCount();
        assertEquals("hello", a2);
        assertEquals(3, a3);

        String a4 = arb.peek();
        int a5 = arb.fillCount();
        assertEquals("java", a4);
        assertEquals(3, a5);
    }
}
