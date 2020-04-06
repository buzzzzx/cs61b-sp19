public class UnionFind {

    private int[] parent;

    /* Creates a UnionFind data structure holding n vertices. Initially, all
       vertices are in disjoint sets. */
    public UnionFind(int n) {
        parent = new int[n];

        int i;
        for (i = 0; i < n; i += 1) {
            parent[i] = -1;
        }
    }

    /* Throws an exception if v1 is not a valid index. */
    private void validate(int vertex) {
        if (vertex < 0 || vertex >= parent.length) {
            throw new IllegalArgumentException(vertex + "is not a valid index.");
        }
    }

    /* Returns the size of the set v1 belongs to. */
    public int sizeOf(int v1) {
        validate(v1);
        return -parent(find(v1));
    }

    /* Returns the parent of v1. If v1 is the root of a tree, returns the
       negative size of the tree for which v1 is the root. */
    public int parent(int v1) {
        validate(v1);
        return parent[v1];
    }

    /* Returns true if nodes v1 and v2 are connected. */
    public boolean connected(int v1, int v2) {
        validate(v1);
        validate(v2);
        return find(v1) == find(v2);
    }

    /* Connects two elements v1 and v2 together. v1 and v2 can be any valid 
       elements, and a union-by-size heuristic is used. If the sizes of the sets
       are equal, tie break by connecting v1's root to v2's root. Unioning a 
       vertex with itself or vertices that are already connected should not 
       change the sets but may alter the internal structure of the data. */
    public void union(int v1, int v2) {
        validate(v1);
        validate(v2);

        int r1 = find(v1);
        int r2 = find(v2);

        if (!connected(v1, v2)) {  // v1 v2 are not already connected.
            int sizeOfV1 = sizeOf(r1);
            int sizeOfV2 = sizeOf(r2);

            if (sizeOfV1 <= sizeOfV2) {
                parent[r1] = r2;
                int p = parent[r2];
                parent[r2] = p - sizeOfV1;
            } else {
                parent[r2] = r1;
                int p = parent[r1];
                parent[r1] = p - sizeOfV2;
            }
        } else {  // path compression
            int p = parent[v1];
            while (p != r1) {
                parent[v1] = r1;
                v1 = p;
                p = parent[v1];
            }

            p = parent[v2];
            while (p != r1) {
                parent[v2] = r1;
                v2 = p;
                p = parent[v2];
            }
        }


    }

    /* Returns the root of the set V belongs to. Path-compression is employed
       allowing for fast search-time. */
    public int find(int vertex) {

        while (parent[vertex] >= 0) {
            vertex = parent[vertex];
        }
        return vertex;
    }

}
