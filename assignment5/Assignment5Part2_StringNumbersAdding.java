package assignment5;

import com.shpp.cs.a.console.TextProgram;

/**
 * This app sums 2 String numbers of any length and returns result in console
 */
public class Assignment5Part2_StringNumbersAdding extends TextProgram {
    public void run() {
        /* Sit in a loop, reading numbers and adding them. */
        while (true) {
            String n1 = readLine("Enter first number:  ");
            String n2 = readLine("Enter second number: ");
            println(n1 + " + " + n2 + " = " + addNumericStrings(n1, n2));
            println();
        }
    }

    /**
     * Given two string representations of nonnegative integers, adds the
     * numbers represented by those strings and returns the result.
     *
     * @param n1 The first number.
     * @param n2 The second number.
     * @return A String representation of n1 + n2
     */
    private String addNumericStrings(String n1, String n2) {
        int indexOfLast;
        String result = "";
        int carrier = 0;
        int sumOf2;
        int num1, num2;
        indexOfLast = Math.max(n1.length(), n2.length());
        for (int i = 1; i <= indexOfLast; i++) {
            num1 = getNumberByIndex(n1, n1.length() - i);
            num2 = getNumberByIndex(n2, n2.length() - i);
            sumOf2 = num1 + num2 + carrier;
            if (sumOf2 >= 10) {
                carrier = 1;
                sumOf2 = sumOf2 % 10;
            } else {
                carrier = 0;
            }
            result = sumOf2 + result;
        }
        if (carrier == 0) {
            return result;
        }
        return carrier + result;
    }

    /**
     * returns int number at specified by index place
     *
     * @param stringNumber sting where char will be found
     * @param index        the index of char in the string
     * @return int number at specified by index place
     */
    private int getNumberByIndex(String stringNumber, int index) {
        if (index < 0) {
            return 0;
        }
        return stringNumber.charAt(index) - '0';
    }
}

