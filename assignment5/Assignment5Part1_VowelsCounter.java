package assignment5;

import com.shpp.cs.a.console.TextProgram;

/**
 * This program counts quantity of syllables in the entered word and returns result in console
 */
public class Assignment5Part1_VowelsCounter extends TextProgram {
    public static final char[] VOWELS = {'a', 'e', 'i', 'o', 'u', 'y'};

    public void run() {
        /* Repeatedly prompt the user for a word and print out the estimated
         * number of syllables in that word.
         */
        while (true) {
            String word = readLine("Enter a single word: ");
            println("  Syllable count: " + syllablesInWord(word));
        }
    }


    /**
     * Given a word, estimates the number of syllables in that word according to the
     * heuristic specified in the handout.
     *
     * @param word A string containing a single word.
     * @return An estimate of the number of syllables in that word.
     */
    private int syllablesInWord(String word) {
        if (word.length() == 0) {
            return 0;
        }
        word = word.toLowerCase();
        int vowelCounter = 0;
        boolean isPreviousVowel = false;
        for (int i = 0; i < word.length(); i++) {
            if (!isVowel(word.charAt(i))) {     //skip if not vowel
                isPreviousVowel = false;
                continue;
            }
            if (!isPreviousVowel) {               //if previous isn't vowel
                vowelCounter++;
            }
            isPreviousVowel = true;
        }

        if (word.charAt(word.length() - 1) == 'e'           //check for "e" in the end
                && vowelCounter > 1
                && word.charAt(word.length() - 2) != 'e') { //check for "ee" in the end
            vowelCounter--;
        }
        if(vowelCounter<1){
            vowelCounter=1;
        }
        return vowelCounter;
    }

    /**
     * Checks english letter and returns true if it's vowel
     *
     * @param c english letter char
     * @return true if char is vowel and false if not
     */
    private boolean isVowel(char c) {
        for (char vowel : VOWELS) {
            if (c == vowel) {
                return true;
            }
        }
        return false;
    }
}