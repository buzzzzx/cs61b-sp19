package hw2;

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private Site[][] sites;
    private int sideLength;
    private int numberOfOpenSites;
    private WeightedQuickUnionUF uf;
    private WeightedQuickUnionUF ufExcludeBottom;
    private int virtualTop;
    private int virtualBottom;

    private class Site {
        private boolean status;  // true: open; false: block;

        Site() {
            this.status = false;  // initially blocked.
        }

        public boolean isOpen() {
            return status;
        }

        /**
         * Open the site.
         */
        public void open() {
            status = true;
        }

    }

    /**
     * Constructor that create N-by-N grid, with all sites initially blocked.
     */
    public Percolation(int N) {
        if (N <= 0) {
            throw new IllegalArgumentException("N is equal or less than 0.");
        }

        sites = new Site[N][N];
        int i, j;
        for (i = 0; i < N; i += 1) {
            for (j = 0; j < N; j += 1) {
                sites[i][j] = new Site();
            }
        }

        sideLength = N;
        numberOfOpenSites = 0;
        uf = new WeightedQuickUnionUF(N * N + 2);
        ufExcludeBottom = new WeightedQuickUnionUF(N * N + 1);
        virtualTop = 0;
        virtualBottom = N * N + 1;

        setupUF();
    }

    /**
     * Set up union find initially that union top row to virtual top,
     * and union bottom row to virtual bottom.
     */
    private void setupUF() {
        int i;
        for (i = 0; i < sideLength; i += 1) {
            uf.union(virtualTop, xyTo1D(0, i));
            uf.union(virtualBottom, xyTo1D(sideLength - 1, i));

            ufExcludeBottom.union(virtualTop, xyTo1D(0, i));
        }
    }

    /**
     * Open the site (row, col) if it is not open already.
     */
    public void open(int row, int col) {
        validateIndices(row, col);

        if (!isOpen(row, col)) {
            sites[row][col].open();
            numberOfOpenSites += 1;

            unionNeighbors(row, col);
        }
    }

    /**
     * Return true if the site (row, col) is open.
     */
    public boolean isOpen(int row, int col) {
        validateIndices(row, col);

        return sites[row][col].isOpen();
    }

    /**
     * Return true if the site (row, col) is full.
     */
    public boolean isFull(int row, int col) {
        validateIndices(row, col);
        return isOpen(row, col) && ufExcludeBottom.connected(virtualTop, xyTo1D(row, col));

    }

    /**
     * Return the number of open sites.
     */
    public int numberOfOpenSites() {
        return numberOfOpenSites;
    }

    /**
     * Return true if the system percolates.
     */
    public boolean percolates() {
        return uf.connected(virtualBottom, virtualTop);
    }

    /**
     * Helper method that convert 2D position to 1D index.
     */
    private int xyTo1D(int row, int col) {
        return row * sideLength + col + 1;
    }

    /**
     * Helper method that union neighbors which are opened.
     */
    private void unionNeighbors(int row, int col) {
        /* union left. */
        int current = xyTo1D(row, col);
        if (col - 1 >= 0) {
            if (isOpen(row, col - 1)) {
                uf.union(current, xyTo1D(row, col - 1));
                ufExcludeBottom.union(current, xyTo1D(row, col - 1));
            }
        }

        /* union right. */
        if (col + 1 < sideLength) {
            if (isOpen(row, col + 1)) {
                uf.union(current, xyTo1D(row, col + 1));
                ufExcludeBottom.union(current, xyTo1D(row, col + 1));
            }
        }

        /* union above. */
        if (row - 1 >= 0) {
            if (isOpen(row - 1, col)) {
                uf.union(current, xyTo1D(row - 1, col));
                ufExcludeBottom.union(current, xyTo1D(row - 1, col));
            }
        }

        /* union below. */
        if (row + 1 < sideLength) {
            if (isOpen(row + 1, col)) {
                uf.union(current, xyTo1D(row + 1, col));
                ufExcludeBottom.union(current, xyTo1D(row + 1, col));
            }
        }
    }

    private void validateIndices(int row, int col) {
        if (((row < 0) || (row > sideLength - 1)) || ((col < 0) || (col > sideLength - 1))) {
            throw new IndexOutOfBoundsException();
        }
    }

    public static void main(String[] args) {
        // ...
    }

}
