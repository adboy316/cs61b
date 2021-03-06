import org.junit.Test;
import static org.junit.Assert.*;

public class TestPalindrome {
    // You must use this palindrome, and not instantiate
    // new Palindromes, or the autograder might be upset.
    static Palindrome palindrome = new Palindrome();
    static CharacterComparator offByOne = new OffByOne();

    @Test
    public void testWordToDeque() {
        Deque d = palindrome.wordToDeque("persiflage");
        String actual = "";
        for (int i = 0; i < "persiflage".length(); i++) {
            actual += d.removeFirst();
        }
        assertEquals("persiflage", actual);
    }

    @Test
    public void testIsPalindrome() {
        assertTrue(palindrome.isPalindrome("racecar"));
        assertTrue(palindrome.isPalindrome("a"));
        assertTrue(palindrome.isPalindrome("BB"));
        assertFalse(palindrome.isPalindrome("cat"));
        assertFalse(palindrome.isPalindrome("aA"));
    }

    @Test
    public void testIsPalindromeOverload() {
       assertTrue(palindrome.isPalindrome("flake", offByOne));
       assertTrue(palindrome.isPalindrome("bdca", offByOne));
       assertTrue(palindrome.isPalindrome("a", offByOne));
       assertTrue(palindrome.isPalindrome("ab", offByOne));
       assertFalse(palindrome.isPalindrome("ac", offByOne));
       assertFalse(palindrome.isPalindrome("fail", offByOne));
       assertFalse(palindrome.isPalindrome("aba", offByOne));

    }
}