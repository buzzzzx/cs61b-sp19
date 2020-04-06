import static org.junit.Assert.*;
import org.junit.Test;

public class TestUnionFind {

    @Test
    public void testUnionFind() {
        UnionFind wuf = new UnionFind(10);

        // case 1; false.
        boolean a1 = wuf.connected(2, 3);
        assertFalse(a1);

        // case 2; Union (1, 2), (5, 6)
        wuf.union(1, 2);
        wuf.union(5, 6);

        boolean a2 = wuf.connected(1, 2);
        boolean a3 = wuf.connected(5, 6);
        assertTrue(a2);
        assertTrue(a3);

        assertFalse(wuf.connected(2, 3));

        // case 3; parent.
        int a4 = wuf.parent(1);
        int a5 = wuf.parent(2);

        int e4 = 2;
        int e5 = -2;
        assertEquals(e4, a4);
        assertEquals(e5, a5);

        wuf.union(1, 0);
        wuf.union(5, 7);

        int a6 = wuf.parent(0);
        assertEquals(2, a6);
        assertEquals(-3, wuf.parent(2));

        // case 4; sizeOf.
        int a7 = wuf.sizeOf(0);
        int a8 = wuf.sizeOf(6);
        assertEquals(3, a7);
        assertEquals(3, a8);

        // case 5; union(1, 7).
        wuf.union(1, 7);

        int a9 = wuf.parent(2);
        assertEquals(6, a9);

        int a10 = wuf.sizeOf(0);
        assertEquals(6, a10);

        // case 6; union (8, 9) (5, 9) (0, 8).
        wuf.union(8, 9);
        wuf.union(5, 9);
        wuf.union(0, 8);
        int a11 = wuf.parent(0);
        int a12 = wuf.parent(8);

        assertEquals(6, a11);
        assertEquals(6, a12);

        int a13 = wuf.sizeOf(9);
        assertEquals(8, a13);
    }
}
