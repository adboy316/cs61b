package hw2;

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {

    private Tile[][] grid;
    private int N;
    private int openSites;
    private int topSite;
    private int bottomSite;
    private int virtualTopSite;
    private WeightedQuickUnionUF connections;
    private WeightedQuickUnionUF virtualConnections;


    /**
     * Create N-by-N grid, with all sites initially blocked
     */
    public Percolation(int N) {

        if (N <= 0)throw new IllegalArgumentException("N must be greater than 0.");

        this.N = N;
        int position = 0;
        this. grid = new Tile[N][N];
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length ; j++) {
                grid[i][j] = new Tile(position);
                position += 1;
            }
        }
        connections = new WeightedQuickUnionUF(gridSize() + 2);
        virtualConnections = new WeightedQuickUnionUF(gridSize()+1);
        this.topSite = gridSize() ;
        this.bottomSite = gridSize() + 1;
        this.virtualTopSite = gridSize();
        this.openSites = 0;
        connectTopSite(topSite);
        connectBottomSite(bottomSite);
        connectTopVirtualSite(virtualTopSite);
    }

    /**
     * Open the site (row, col) if it is not open already
     */
    public void open(int row, int col) {
        if (row > N || col > N) throw new IndexOutOfBoundsException();

        if (grid[row][col].open) return;

        if (N == 1){
            grid[row][col].open = true;
            grid[row][col].full = true;
            openSites = 1;
            return;
        }

        grid[row][col].open = true;
        int[] neighborhs = checkNeighbors(row, col);
        connectOpenTiles(row, col, neighborhs);
        checkFull(grid[row][col]);
        openSites += 1;
    }

    /**
     * Is the site (row, col) open?
     */
    public boolean isOpen(int row, int col) {
        if (row > N || col > N) throw new IndexOutOfBoundsException();
        return grid[row][col].open;
    }

    /**
     * Is the site (row, col) full?
     */
    public boolean isFull(int row, int col) {
        if (row > N || col > N) throw new IndexOutOfBoundsException();
        return grid[row][col].full;
    }

    /**
     * Number of open sites
     */
    public int numberOfOpenSites() {
        return openSites;
    }

    /**
     * Does the system percolate?
     */
    public boolean percolates()  {

        if (N == 1) {
            if (numberOfOpenSites() == 1) return true;
            else return false;
        }

        return connections.connected(topSite, bottomSite);
    }

    /**
     * Connect top row to topSite.
     */
    private void connectTopSite(int topSite) {
        for (int i = 0; i < N; i++) {
            connections.union(topSite, i);
            grid[i/N][i%N].root = topSite;
        }
    }

    private void connectTopVirtualSite(int topVirtualSite) {
        for (int i = 0; i < N; i++) {
            virtualConnections.union(topSite, i);
            grid[i/N][i%N].root = topSite;
        }
    }


    /**
     * Connect bottom row to bottomSite.
     */
    private void connectBottomSite(int bottomSite) {
        int startOfLastRow = (N * N) - N;
        for (int i = startOfLastRow; i < (N*N) ; i++) {
            if (i == gridSize()){
                continue;
            }
            connections.union(i, bottomSite);
            grid[i/N][i%N].root = bottomSite;
        }
    }

    /**
     * Connects neighboring open tiles.
     */
    private void connectOpenTiles(int row, int col, int[] neighborhs) {
        for (int n : neighborhs) {
            if (grid[n / N][n % N].open) {
                connections.union(n, grid[row][col].pos);
                virtualConnections.union(n, grid[row][col].pos);

            }
        }
    }

    /**
     * Sets open tiles to full if they contain a topsite as an open tile and they are open
     */
    private void checkFull(Tile tile) {

        // Base case
        if (tile.full){
            return;
        }

        if (tile.root == topSite){
            tile.full = true;
        }

        if (isConnectedToTopSite(tile) && tile.open){
           tile.full = true;
           tile.root = topSite;
           int[] neighborhs = checkNeighbors(tile.pos / N, tile.pos % N);
            for (int x : neighborhs) {
                if (grid[x/N][x%N].open) {
                    if (isConnectedToTopSite(grid[x / N][x % N])) {
                        checkFull(grid[x / N][x % N]);
                    }
                }
            }
        }
}

    /**
     * Check if tile is connected to the topsite?
     */
    private boolean isConnectedToTopSite(Tile tile) {
        return virtualConnections.connected(tile.pos,virtualTopSite);
    }

    /**
     * Return all the neighbors of a given pos in the grid.
     * There is probably a more efficient way of doing this.
     * Come back and refactor later if you have more time.
     */
    private int[] checkNeighbors(int row, int col) {
        int[] neighbors;
        int pos = grid[row][col].pos;
        // Case 1 top left corner
        if (row == 0 && col == 0) {
            return neighbors = new int[]{pos + 1, pos + N};
        }
        // Case 2 top right corner
        else if (row == 0 && col == N-1) {
            return neighbors = new int[]{pos - 1, pos + N};
        }
        // Case 3 middle of top row
        else if (row == 0 && col > 0 && col < N-1) {
            return neighbors = new int[]{pos - 1, pos + 1, pos + N};
        }
        // Case 4 bottom left corner
        else if (row == N-1 && col == 0) {
            return neighbors = new int[]{pos - N, pos + 1};
        }
        // Case 5 bottom right corner
        else if (row == N-1 && col == N-1) {
            return neighbors = new int[]{pos - N, pos - 1};
        }
        // Case 6 middle bottom row
        else if (row == N - 1  && col > 0 && col < N-1 ) {
            return neighbors = new int[]{pos - 1, pos +1, pos - N};
        }
        // Case 7 left column
        else if (col == 0 && row > 0 && row < N -1 ) {
            return neighbors = new int[]{pos - N, pos + N, pos + 1};
        }
        // Case 8 right column
        else if (col == N-1 && row > 0 && row < N-1 ) {
            return neighbors = new int[]{pos - N, pos + N, pos - 1};
        }
        // Case 9 everything else
        else {
            return neighbors = new int[]{pos - N, pos + N, pos - 1, pos + 1};
        }
    }

    // Helper methods for testing
    private int gridSize() {
        return grid.length * grid[0].length;
    }

    /**
     * Use for unit testing (not required, but keep this here for the autograder)
     */
    public static void main(String[] args) {
    }

}
