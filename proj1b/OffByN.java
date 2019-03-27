public class OffByN implements CharacterComparator{

    int n;

    public OffByN(int N) {
        this.n = N;
    }


    /** Returns true for characters that are different by N */
    @Override
    public boolean equalChars(char x, char y) {

        if (x+n == y || x == y+n){
            return true;
        }

        return false;
    }
}
