import org.junit.Test;
import static org.junit.Assert.*;

public class BubbleGridTest {

    @Test
    public void testBasic() {

        int[][] grid = {{1, 0, 0, 0},
                        {1, 1, 1, 0}};
        int[][] darts = {{1, 0}};
        int[] expected = {2};

        validate(grid, darts, expected);
    }

    @Test
    public void testBiggerGrid () {
        int[][] grid = {{1, 1, 0},
                        {1, 0, 0},
                        {1, 1, 0},
                        {1, 1, 1}};

        int[][] darts = {{2, 2}, {2, 0}};
        int[] expected = {0, 4};
        validate(grid, darts, expected);
    }



    @Test
    public void testTranslateDartPositions() {
        int[][] grid = {{1, 0, 0, 0},
                        {1, 1, 1, 0},
                        {0, 1, 0, 0}};
        BubbleGrid sol = new BubbleGrid(grid);
        int[][] darts = {{1, 1},
                         {2, 1},
                         {1, 0}};
        int [] expected = {5, 9, 4};
        assertArrayEquals(expected, sol.translateDartPositions(darts));


        int[][] grid2 = {{1, 0, 0, 0},
                         {1, 1, 1, 0}};
        BubbleGrid sol2 = new BubbleGrid(grid2);
        int[][] darts2 = {{1, 0},   //pos: 4
                         {1, 1}};  // pos : 5
        int [] expected2 = {4, 5};
        assertArrayEquals(expected2, sol2.translateDartPositions(darts2));
    }

    private void validate(int[][] grid, int[][] darts, int[] expected) {
        BubbleGrid sol = new BubbleGrid(grid);
        assertArrayEquals(expected, sol.popBubbles(darts));
    }
}
