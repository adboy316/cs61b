public class OffByOne implements CharacterComparator {

    /** Returns true for characters that are different by exactly one */
    @Override
    public boolean equalChars(char x, char y) {

        if (x+1 == y || x == y+1){
            return true;
        }

        return false;
    }


}
