import java.util.ArrayList;
import java.util.List;

public class UnionFind {
    /**
     * Array that represents sets. Each set is assigned the id of its parent.
     * If an item has no parent, then it's root has a negative value.
     * */
    private int[] parent;

    /* Creates a UnionFind data structure holding n vertices. Initially, all
       vertices are in disjoint sets. */
    public UnionFind(int n) {
        parent = new int[n];
        for (int i = 0; i < n; i++) {
            parent[i] = -1;
        }
    }

    /* Throws an exception if v1 is not a valid index. */
    private void validate(int vertex) {
        if (vertex > parent.length - 1 || vertex < 0) {
            throw new IllegalArgumentException("Vertex is not a valid index.");
        }
    }

    /* Returns the size of the set v1 belongs to. */
    public int sizeOf(int v1) {
        // Go to the root of v1... so find(v1),
        int x = find(v1);
        //  Return the contents of v1, which is the size of that tree
        return parent[x];
    }

    /* Returns the parent of v1. If v1 is the root of a tree, returns the
       negative size of the tree for which v1 is the root. */
    public int parent(int v1) {
        if (v1 == find(v1)){
            return parent[v1];
        }
       return find(v1);
    }

    /* Returns true if nodes v1 and v2 are connected. */
    public boolean connected(int v1, int v2) {
        return find(v1) == find(v2);
    }

    /* Connects two elements v1 and v2 together. v1 and v2 can be any valid 
       elements, and a union-by-size heuristic is used. If the sizes of the sets
       are equal, tie break by connecting v1's root to v2's root. Unioning a 
       vertex with itself or vertices that are already connected should not 
       change the sets but may alter the internal structure of the data. */
    public void union(int v1, int v2) {

        if (v1 > parent.length -1 || v2 > parent.length -1) {
            throw new IllegalArgumentException("Index out of bound");
        }

        // Find the root of both vertex
        int rootOfV1 = find(v1);
        int rootOfV2 = find(v2);
        if (rootOfV1 == rootOfV2) {
            return;
        }

        // Record the size of the roots of v1 and v2
        int sizeOfV1 = sizeOf(v1);
        int sizeOfV2 = sizeOf(v2);

        //  Link the root of the smaller tree to the larger tree.
        if (sizeOfV1 == sizeOfV2) {
            parent[rootOfV2] = rootOfV1;
            parent[rootOfV1] += sizeOfV2;
        }
        // In this case < actually means a greater size
        else if (sizeOfV1 < sizeOfV2) {
            parent[rootOfV2] = rootOfV1;
            parent[rootOfV1] += sizeOfV2;
        }
        else {
            parent[rootOfV1] = rootOfV2;
            parent[rootOfV2] += sizeOfV1;
        }


    }
    /* Returns the root of the set V belongs to. Path-compression is employed
       allowing for fast search-time. */
    public int find(int vertex) {

        List<Integer> path = new ArrayList<>();

        while (parent[vertex] >= 0) {
            path.add(vertex);
            vertex = parent[vertex];
        }

        for (int v : path) {
            parent[v] = vertex;
        }
        return vertex;
    }

    public static void main(String[] args) {
        UnionFind u = new UnionFind(16);

        u.union(0, 3);
        u.union(0, 10);
        u.union(0, 4);
        u.union(0, 15);
        u.union(0, 11);
        u.union(5, 12);
        u.union(1, 7);
        u.union(6, 13);
        u.union(1, 6);
        u.union(2, 9);
        u.union(8, 14);
        u.union(2, 8);
        u.union(0, 5);
        u.union(0, 1);
        u.union(0, 2);
        u.union(14, 13);







    }

}
