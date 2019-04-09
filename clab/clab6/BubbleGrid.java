public class    BubbleGrid {
    private int[][] grid;
    private int rows;
    private int columns;
    private int gridSize;

    /* Create new BubbleGrid with bubble/space locations specified by grid.
     * Grid is composed of only 1's and 0's, where 1's denote a bubble, and
     * 0's denote a space. */
    public BubbleGrid(int[][] grid) {

        this.grid = grid;
        this.rows = grid.length;
        this.columns = grid[0].length;
        this.gridSize = rows * columns;


    }

    /* Returns an array whose i-th element is the number of bubbles that
     * fall after the i-th dart is thrown. Assume all elements of darts
     * are unique, valid locations in the grid. Must be non-destructive
     * and have no side-effects to grid. */
    public int[] popBubbles(int[][] darts) {

        UnionFind stuckBubbles = findStuckBubbles();
        int [] dartPositions = translateDartPositions(darts);
        int [] fallenBubbles = new int[dartPositions.length];

        for (int i = 0; i < fallenBubbles.length; i++) {

           fallenBubbles[i] = stuckBubbles.size(dartPositions[i]);
        }
        return fallenBubbles;
    }

    /* Takes an array of positions in a 2d array, and translates
     * them into the corresponding position in a 1d array.*/
    public int [] translateDartPositions(int[][] darts) {
        int translatedDarts[] = new int [darts.length];
        for(int i=0; i<darts.length; i++) {
            for (int j = 0; j < darts[0].length -1; j++) {
                translatedDarts[i] = (darts[i][j] * columns) + darts[i][j+1];
            }
        }
        return translatedDarts;
    }

    /* Returns all the connected bubbles.*/
    private UnionFind findStuckBubbles() {

        UnionFind connections  = new UnionFind(gridSize);

        for (int i = 0; i < columns ; i++) {
            for (int j = 1; j < rows; j++) {
                if (grid[0][i] == 1  && grid[0][i] == grid[j][i]){
                    connections.union(i, (j * columns) + i );
                }
            }
        }
        return connections;
    }
}
