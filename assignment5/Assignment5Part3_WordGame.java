package assignment5;

import com.shpp.cs.a.console.TextProgram;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 * This app reads dictionary file and allows user to play word game
 * where user inputs 3 letter and program returns word of this 3 letters found in the dictionary
 */

public class Assignment5Part3_WordGame extends TextProgram {
    //path to dictionary file
    public final static String FILE_PATH = "en-dictionary.txt";

    //size of output console width in symbols
    public final static int CONSOLE_WIDTH = 220;

    public void run() {
        ArrayList<String> dictionary = readFromFile();
        String word;
        if (dictionary == null) {
            println("cannot find the dictionary file");
            return;
        }

        ArrayList<String> words;
        while (true) {
            String userInput = readLine("Enter 3 letters: ");
            if (userInput.length() != 3) {
                continue;
            }
            words = findWords(userInput, dictionary);
            printWords(words);
        }

    }

    /**
     * Prints string arraylist in console considering it's size specified in CONSOLE_WIDTH constant
     *
     * @param words the string arraylist object that will be printed
     */
    private void printWords(ArrayList<String> words) {
        if (words.size() == 0) {
            println("Unfortunately, I can't find a word of these 3 letters in dictionary");
            return;
        }

        println("Generated words: ");
        int lineSize = 0; //size of current line
        for (int i = 0; i < words.size() - 1; i++) {
            String word = words.get(i);
            if (lineSize + word.length() > CONSOLE_WIDTH) { //if not enough space for next word, go to next line
                println();
                lineSize = 0;
            }
            print(word + ", ");
            lineSize += word.length() + 2;  //counting current line
        }
        //printing last element with dot(.) at the end
        if (lineSize + words.get(words.size() - 1).length() > CONSOLE_WIDTH) {
            println();
        }
        println(words.get(words.size() - 1) + ".");

        // printing quantity of words in arraylist
        println("Found " + words.size() + " words");
        println();
    }

    /**
     * finds words, that has 3 given letters in order as they are located, in dictionary
     *
     * @param input      string of 3 letters
     * @param dictionary arraylist of string words
     * @return the words in dictionary that has 3 given letters in order as they are located in input
     */
    private ArrayList<String> findWords(String input, ArrayList<String> dictionary) {
        input = input.toLowerCase();
        ArrayList<String> results = new ArrayList<>();
        int tempIndex;
        for (String word : dictionary) {
            if ((tempIndex = word.indexOf(input.charAt(0))) == -1) {    // first letter check
                continue;
            }
            if ((tempIndex = word.indexOf(input.charAt(1), tempIndex + 1)) == -1) {     //second letter check
                continue;
            }
            if ((word.indexOf(input.charAt(2), tempIndex + 1)) == -1) {     //third letter check
                continue;
            }
            results.add(word);
        }
        return results;
    }

    /**
     * reads file and writes each string as array item in return
     *
     * @return arrayList of strings of the file
     */
    private ArrayList<String> readFromFile() {
        try {
            ArrayList<String> result = new ArrayList<>();
            FileReader fileReader = new FileReader(FILE_PATH);
            BufferedReader buffReader = new BufferedReader(fileReader);
            String line;
            while ((line = buffReader.readLine()) != null) {
                result.add(line);
            }
            fileReader.close();
            return result;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}

