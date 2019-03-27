public class Palindrome {

    /** Given a String, wordToDeque should return a Deque where the characters appear in the
     * same order as in the String. For example, if the word is “persiflage”, then the
     * returned Deque should have ‘p’ at the front, followed by ‘e’, and so forth.*/
    public Deque<Character> wordToDeque(String word) {
        Deque wordToDeque = new LinkedListDeque();
        for (char c: word.toCharArray()) {
            wordToDeque.addLast(c);
        }
        return wordToDeque;
    }

    public Deque<Character> wordToDequeBackwards(String word) {
        Deque wordToDeque = new LinkedListDeque();
        for (char c: word.toCharArray()) {
            wordToDeque.addFirst(c);
        }
        return wordToDeque;
    }

    /** The isPalindrome method should return true if the given word is a palindrome,
     * and false otherwise. */
    public boolean isPalindrome(String word) {
        Deque wordDeque = wordToDequeBackwards(word);
        for (int i = 0; i < word.length(); i++) {
            char wordLetter = word.toCharArray()[i];
            char worBackwardsLetter = (char) wordDeque.get(i);
            if (wordLetter != worBackwardsLetter) {
                return false;
            }
        }
        return true;
    }

    /** The method will return true if the word is a palindrome
     * according to the character comparison test provided by the
     * CharacterComparator passed in as argument cc */
    public boolean isPalindrome(String word, CharacterComparator cc) {
        Deque wordDeque = wordToDequeBackwards(word);
        for (int i = 0; i < word.length(); i++) {
            if (word.length() % 2 != 0 && i % word.length() == i) {
                continue;
            }
            char wordLetter = word.toCharArray()[i];
            char worBackwardsLetter = (char) wordDeque.get(i);
            if (!cc.equalChars(wordLetter, worBackwardsLetter)) {
                return false;
            }
        }
        return true;
    }
}
