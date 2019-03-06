/**
 *  Tests Body class
 */
public class TestBody {

	 /**
     *  Tests Body class
     */
    public static void main(String[] args) {
        checkBody();
    }

  /**
     *  Checks whether or not two Doubles are equal and prints the result.
     *
     *  @param  expected    Expected double
     *  @param  actual      Double received
     *  @param  label       Label for the 'test' case
     *  @param  eps         Tolerance for the double comparison.
     */
    private static void checkEquals(double actual, double expected, String label, double eps) {
        if (Double.isNaN(actual) || Double.isInfinite(actual)) {
            System.out.println("FAIL: " + label
                    + ": Expected " + expected + " and you gave " + actual);
        } else if (Math.abs(expected - actual) <= eps * Math.max(expected, actual)) {
            System.out.println("PASS: " + label
                    + ": Expected " + expected + " and you gave " + actual);
        } else {
            System.out.println("FAIL: " + label
                    + ": Expected " + expected + " and you gave " + actual);
        }
    }



	/**
     *  Checks the Body class to make sure calcForceExertedByXY works.
     */
    private static void checkBody() {
        System.out.println("Checking Body class...");

        Body b1 = new Body(1.0, 1.0, 3.0, 4.0, 5.0, "jupiter.gif");
        Body b2 = new Body(2.0, 1.0, 3.0, 4.0, 4e11, "jupiter.gif");

        checkEquals(b1.calcForceExertedBy(b2), 133.4, "calcForceExertedBy()", 0.01);
        System.out.println("Pairwise force between b1 and b2 is..." + b1.calcForceExertedBy(b2));
   
    }

}