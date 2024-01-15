package assignment5;

import com.shpp.cs.a.console.TextProgram;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 * this app generates ArrayList<String> of csv table column, specified by columnIndex
 */
public class Assignment5Part4_csvConverter extends TextProgram {
    public final static String FILE_PATH = "";

    public void run() {
        println(extractColumn("color_srgb.csv", 0));
    }

    /**
     * converts csv row in the ArrayList<String>
     *
     * @param line the line that should be converted in ArrayList<String>
     * @return ArrayList<String> of csv row items
     */
    private ArrayList<String> fieldsIn(String line) {
        StringBuilder strBuilder = new StringBuilder();
        ArrayList<String> result = new ArrayList<>();
        int dQuotesCounter = 0;
        boolean prevDQuotes = false;
        char curChar;
        for (int i = 0; i < line.length(); i++) {
            curChar = line.charAt(i);

            if (curChar == '"') {
                dQuotesCounter++;
                if (prevDQuotes&&strBuilder.length()>1) {  // skipping dQuote if prev is also dQuote
                    prevDQuotes = false;
                    continue;
                }
                prevDQuotes = true;
            } else {
                prevDQuotes = false;
            }

            if (curChar == ',') {
                if (dQuotesCounter == 0) {    // the end of non-escaped cell
                    result.add(strBuilder.toString());
                    strBuilder.setLength(0);    //clear strBuilder
                    continue;
                }
                if (dQuotesCounter % 2 == 0) {  // the end of escaped cell
                    // adding strBuilder without dQuotes on edges
                    result.add(strBuilder.substring(1, strBuilder.length() - 1));
                    dQuotesCounter = 0;
                    strBuilder.setLength(0);    //clear strBuilder
                    continue;
                }
            }

            // for regular chars
            strBuilder.append(curChar);

            // for the end of the line
            if (i == line.length() - 1) {
                if (dQuotesCounter == 0) {    // if line is non-escaped
                    result.add(strBuilder.toString());
                } else {                       // if line is escaped
                    // adding strBuilder without dQuotes on edges
                    result.add(strBuilder.substring(1, strBuilder.length() - 1));
                }
            }
        }
        return result;
    }


    /**
     * generate ArrayList<String> of csv table column, specified by columnIndex
     *
     * @param filename    the name of scv table file
     * @param columnIndex the index of column, that will be writed in the arraylist
     * @return ArrayList<String> of csv table column, specified by columnIndex
     */
    private ArrayList<String> extractColumn(String filename, int columnIndex) {
        try {
            ArrayList<String> result = new ArrayList<>();
            //initializing buffer reader
            FileReader fileReader = new FileReader(FILE_PATH + filename);
            BufferedReader buffReader = new BufferedReader(fileReader);

            String line;
            while ((line = buffReader.readLine()) != null) {
                ArrayList<String> lineArr = fieldsIn(line);//saving list with line items
                if (columnIndex < lineArr.size()) {     //checking index correctness
                    result.add(lineArr.get(columnIndex));
                } else {
                    result.add("");
                }
            }
            fileReader.close();
            return result;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}

