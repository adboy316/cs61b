import org.junit.Test;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertFalse;

public class testFlik {

    @Test
    public void testFlik() {
        int a = 128;
        int b = 128;
        int c = 129;

        assertTrue(Flik.isSameNumber(a, b));
        assertFalse(Flik.isSameNumber(a, c));

    }

}
