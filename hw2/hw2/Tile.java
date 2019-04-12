/**
 * Represents a single tile in the Perlocation grid.
 */

package hw2;

public class Tile {
    int pos;
    Boolean open;
    Boolean full;


    public Tile() {
        this.open = false;
        this.full = false;
    }

    public Tile(int x) {
        this.open = false;
        this.full = false;
        this.pos = x;
    }
}
