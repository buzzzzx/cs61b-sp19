import javax.swing.*;

public class BubbleGrid {
    private int[][] grid;
    private UnionFind wuf;
    private int numOfRow;
    private int numOfCol;
    private static final int CEILING = 0;

    /* Create new BubbleGrid with bubble/space locations specified by grid.
     * Grid is composed of only 1's and 0's, where 1's denote a bubble, and
     * 0's denote a space. */
    public BubbleGrid(int[][] grid) {
        this.grid = grid;
        numOfRow = grid.length;
        numOfCol = grid[0].length;
        wuf = new UnionFind(1 + numOfCol * numOfRow);  // 0 index for ceiling.
        setupWUF();
    }

    /**
     * Helper method that set up the weight union find.
     *     - Connect all the stuck items.
     */
    private void setupWUF() {
        int[] topmostRow = grid[0];

        /* Connect bubble in topmost row with ceiling. */
        int i;
        for (i = 0; i < numOfCol; i += 1) {
            if (topmostRow[i] == 1) {
                int current = convertIndex(0, i);
                wuf.union(current, CEILING);

                /* Connect bubbles in rest rows which are adjacent to the stuck bubble with adjacent stuck bubble. */
                connectNeighbor(current, 1, i);
            }
        }

    }

    /**
     * helper method that return the converted index used in wuf.
     */
    private int convertIndex(int row, int col) {
        return row * numOfCol + col + 1;
    }

    /**
     * Connect current with neighbors which value is 1.
     * Current is a stuck bubble.
     */
    private void connectNeighbor(int current, int neighborRowIndex, int neighborColIndex) {
        if (grid[neighborRowIndex][neighborColIndex] == 1 && !wuf.connected(current, convertIndex(neighborRowIndex, neighborColIndex))) {
            wuf.union(convertIndex(neighborRowIndex, neighborColIndex), current);

            current = convertIndex(neighborRowIndex, neighborColIndex);

            /* connect left. */
            if (neighborColIndex - 1 >= 0) {
                connectNeighbor(current, neighborRowIndex, neighborColIndex - 1);
            }

            /* connect right. */
            if (neighborColIndex + 1 < numOfCol) {
                connectNeighbor(current, neighborRowIndex, neighborColIndex + 1);
            }

            /* connect above. */
            if (neighborRowIndex - 1 >= 0) {
                connectNeighbor(current, neighborRowIndex - 1, neighborColIndex);
            }

            /* connect below. */
            if (neighborRowIndex + 1 < numOfRow) {
                connectNeighbor(current, neighborRowIndex + 1, neighborColIndex);
            }
        }
    }

    /* Returns an array whose i-th element is the number of bubbles that
     * fall after the i-th dart is thrown. Assume all elements of darts
     * are unique, valid locations in the grid. Must be non-destructive
     * and have no side-effects to grid. */
    public int[] popBubbles(int[][] darts) {
        int[] res = new int[darts.length];

        int i;
        for (i = 0; i < darts.length; i += 1) {
            int row = darts[i][0];
            int col = darts[i][1];
            if (grid[row][col] == 0) {
                res[i] = 0;
            } else {
                int count = wuf.sizeOf(CEILING);
                grid[row][col] = 0;  // bubble popped.
                wuf.clear();
                setupWUF();
                int newCount = wuf.sizeOf(CEILING);

                res[i] = count - newCount - 1;
            }
        }

        return res;
    }
}
