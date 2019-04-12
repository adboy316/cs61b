package hw2;

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {

    private Tile[][] grid;
    private int openSites;
    private int N;

    WeightedQuickUnionUF connections;

    /**
     * Create N-by-N grid, with all sites initially blocked
     */
    public Percolation(int N) {
        this.N = N;
        int position = 0;
        this. grid = new Tile[N][N];
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length ; j++) {
                grid[i][j] = new Tile(position);
                position += 1;
            }
        }

        connections = new WeightedQuickUnionUF(gridSize());
    }

    /**
     * Open the site (row, col) if it is not open already
     */
    public void open(int row, int col) {

        grid[row][col].open = true;
        if (grid[row][col].pos < N) { // If tile is in row 0, set to full
            grid[row][col].full = true;
        }
        openSites += 1;
        int[] neighborhs = checkNeighbors(row, col);

        // Connects open tiles
        for (int n : neighborhs) {
            if (grid[n / N][n % N].open) {
                connections.union(n, grid[row][col].pos);
            }
        }

        // Sets open tiles to full if they contain a root as an open tile
        // Find root
       if (isConnectedToTopRow(grid[row][col])){
           for (int i = 0; i < N*N; i++) {
               if (connections.connected(grid[row][col].pos, i)) {
                   grid[i/N][i%N].full = true;
               }
           }
       }

    }

    private boolean isConnectedToTopRow(Tile tile) {
        for (int i = 0; i < N ; i++) {
            if (connections.connected(tile.pos, i)) {
                return true;
            }
        }
        return false;
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

    /**
     * Is the site (row, col) open?
     */
    public boolean isOpen(int row, int col) {
        return grid[row][col].open;
    }

    /**
     * Is the site (row, col) full?
     */
    public boolean isFull(int row, int col) {
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
        // if any of the bottom slides connect to the top row, return true
        int startOfLastRow = (N * N) - N;
        for (int i = startOfLastRow; i < N*N ; i++) {
            if (isConnectedToTopRow(grid[i/N][i%N])) {
                return true;
            }
        }

        return false;
    }

    /**
     * Use for unit testing (not required, but keep this here for the autograder)
     */
    public static void main(String[] args) {
    }


    // Helper methods for testing
    public int gridSize() {
        return grid.length * grid[0].length;
    }

}
