package assignment11;

import java.util.HashMap;

public class Assignment11Part1_FormulaCalculator {
    public static void main(String[] args) {
        try {
            printResult(calculate(args));
        } catch (IllegalArgumentException exception) {
            System.out.println(exception.getMessage());
        }
    }

    /**
     * if value can be represented in integer type it will be printed in console as int else as double
     *
     * @param value value in double type
     */
    private static void printResult(double value) {
        if (value  == (int)value) {
            System.out.println((int) value);
            return;
        }
        System.out.println(value);
    }
    /**
     * Calculates formula assigned as string array where first item is formula("a+1")
     * and all the next is variables("a=1"). And returns its result as double.
     *
     * @param userInput string array where first item is formula("a+1") and all the next is variables("a=1")
     * @return double result of formula
     */
    public static double calculate(String[] userInput) {
        if (userInput.length < 1) {
            throw new IllegalArgumentException("Error. Empty input");
        }
        FormulaTree formulaTree = FormulaTreeParser.parseFormula(userInput[0]);
        HashMap<String,Double> arguments = FormulaTreeParser.parseArguments(userInput);
        return formulaTree.calculateTree(arguments);
    }

    /**
     * Calculates formula got in FormulaTree structure, using arguments from arguments hashmap.
     * And returns its result as double.
     *
     * @param formulaTree      parsed formula in FormulaTree structure
     * @param arguments the hashmap where key is variable name; value is value
     * @return double result of formula
     */
    public static double calculate(FormulaTree formulaTree, HashMap<String, Double> arguments) {
        return formulaTree.calculateTree(arguments);
    }
}