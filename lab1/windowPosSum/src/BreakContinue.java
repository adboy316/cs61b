public class BreakContinue {

    public static void windowPosSum(int[] a, int n) {

        for (int pos0 = 0; pos0 < a.length-1; pos0++) {
            if (a[pos0] < 0){
                continue;
            }
            for (int i = pos0+1, j = 0; j < n; i++, j++) {
                if (i > a.length-1) {
                    break;
                }
                a[pos0] = a[pos0] + a[i];
            }
        }
    }

    public static void main(String[] args) {
        int[] a = {1, 2, -3, 4, 5, 4};
        int n = 3;
        windowPosSum(a, n);

        // Should print 4, 8, -3, 13, 9, 4
        System.out.println(java.util.Arrays.toString(a));
    }

}
