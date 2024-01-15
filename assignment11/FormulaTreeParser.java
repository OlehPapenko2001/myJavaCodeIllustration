package assignment11;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Set;

/**
 * This class has functions that prepare string formula to be parsed to FormulaTree,
 * has methods to parse string arguments to HashMap, and contains cached formulas map to avoid repeated parsing
 */
public class FormulaTreeParser {
    /**
     * If the length of string variable is less than this value won't be parsed
     */
    public static final int MIN_VARIABLE_STRING_LENGTH = 3;

    /**
     * This boolean constant enables replacing comma to dot in formula and arguments, when true
     */
    public static final boolean IS_REPLACING_COMMA_TO_DOT = true;

    /**
     * Contains parsed formulas key is string formula input, value is formula tree object
     */
    public static HashMap<String, FormulaTree> cachedFormulas = new HashMap<>();

    /**
     * Parses string formula to FormulaTree structure, that can be easy counted using different arguments.
     *
     * @param formula input string formula
     * @return formula in FormulaTree structure
     */
    public static FormulaTree parseFormula(String formula) {
        FormulaTree formulaTree;
        if ((formulaTree = cachedFormulas.get(formula)) != null) {
            return formulaTree;
        }
        String[] splitFormula = splitFormulaToStringArray(prepareFormulaForSplitting(formula));
        splitFormula = convertUnaryOperators(splitFormula);
        formulaTree = new FormulaTree(splitFormula);
        cachedFormulas.put(formula, formulaTree);
        return formulaTree;
    }

    /**
     * does complex of preparations of string before parsing:
     * 1. checks brackets correctness
     * 2. removes spaces
     * 3. removes redundant adding and subtracting operators
     * 4. replaces comas to dots if enabled
     *
     * @param formula string format formula
     * @return formula after complex of preparations
     */
    private static String prepareFormulaForSplitting(String formula) {
        checkBracketsCorrectness(formula);
        formula = removeSpacesIn(formula);
        formula = removeDoublePlusAndMinus(formula);
        return replaceComasToDots(formula);
    }


    /**
     * splits formula string to string array where each item is action, value or argument
     *
     * @param formula input formula
     * @return the splits formula string array
     */
    private static String[] splitFormulaToStringArray(String formula) {
        if (formula.length() == 0) {
            throw new IllegalArgumentException("Error in formula. Empty formula string");
        }
        String splittingChars = convertToRegexString(FormulaTree.OPERATORS_ACTIONS.keySet()) +
                FormulaTree.OPENING_BRACKET + FormulaTree.CLOSING_BRACKET;
        return formula.split("(?=[" + splittingChars + "])|(?<=[" + splittingChars + "])");
    }

    /**
     * converts Set of strings (operators) to regex string
     *
     * @param strings the set of strings (operators)
     * @return the regex string that contains each string of input array
     */
    private static String convertToRegexString(Set<String> strings) {
        StringBuilder result = new StringBuilder();
        for (String string : strings) {
            result.append("\\").append(string);
        }
        return result.toString();
    }

    /**
     * removes redundant pluses and minuses that doesn't change result in split formula arraylist
     *
     * @param formula split formula arraylist
     */
    private static String removeDoublePlusAndMinus(String formula) {
        String sub = FormulaTree.SUBTRACTION_OPERATOR;
        String add = FormulaTree.ADDING_OPERATOR;
        String regexSub = "\\" + sub;
        String regexAdd = "\\" + add;
        while (formula.contains(sub + sub) || formula.contains(add + add) ||
                formula.contains(add + sub) || formula.contains(sub + add)) {
            formula = formula.replaceAll(regexSub + regexSub, regexAdd);
            formula = formula.replaceAll(regexAdd + regexAdd, regexAdd);
            formula = formula.replaceAll(regexAdd + regexSub + "|" + regexSub + regexAdd, regexSub);
        }
        return formula;
    }

    /**
     * converts unary minuses ("-") before functions and open bracket to ("-1","*") and removes redundant addition
     * operators in split formula arraylist
     *
     * @param separatedFormula split formula arraylist
     */
    private static String[] convertUnaryOperators(String[] separatedFormula) {
        ArrayList<String> formulaList = new ArrayList<>(Arrays.asList(separatedFormula));
        String item, nextItem, prevItem;
        // variables name reduction to simplify readability
        String subtraction = FormulaTree.SUBTRACTION_OPERATOR;
        String multiplication = FormulaTree.MULTIPLICATION_OPERATOR;
        String openingBracket = FormulaTree.OPENING_BRACKET;
        String closingBracket = FormulaTree.CLOSING_BRACKET;
        String adding = FormulaTree.ADDING_OPERATOR;

        for (int i = 0; i < formulaList.size(); i++) {
            item = formulaList.get(i);
            //when item is subtraction operator
            if (item.equals(subtraction)) {
                if (i + 1 >= formulaList.size()) {
                    throw new IllegalArgumentException("Error in formula. There is nothing after last minus.");
                }
                nextItem = formulaList.get(i + 1);
                if (nextItem.equals(closingBracket)) {
                    throw new IllegalArgumentException("Error in formula. There is closing bracket after minus at "
                            + i + " position.");
                }
                if (i == 0) {   // when minus is at first position of separated formula
                    if (isArgumentOrValueOrClosingBracket(nextItem)) {
                        formulaList.remove(i);
                        formulaList.set(i, subtraction + nextItem);
                    }
                    if (FormulaTree.isFunction(nextItem)) {
                        formulaList.set(i, nextItem);
                        formulaList.add(i++, "-1");
                        formulaList.add(i++, multiplication);
                    }
                    continue;
                }
                prevItem = formulaList.get(i - 1);
                if (FormulaTree.isFunction(nextItem) && !isArgumentOrValueOrClosingBracket(prevItem)) {
                    formulaList.set(i, nextItem);
                    formulaList.add(i++, "-1");
                    formulaList.add(i++, multiplication);
                }
                if (FormulaTree.isOperator(prevItem) && nextItem.equals(openingBracket)) {
                    formulaList.set(i++, "-1");
                    formulaList.add(i++, multiplication);
                }
                if (FormulaTree.isOperator(prevItem) && isArgumentOrValueOrClosingBracket(nextItem)) {
                    formulaList.remove(i);
                    formulaList.set(i, subtraction + nextItem);
                }
                continue;
            }

            //when item is adding operator
            if (item.equals(adding)) {
                if (i == 0) {
                    formulaList.remove(i);
                    continue;
                }
                prevItem = formulaList.get(i - 1);
                //when prev item is function or operator or opening bracket
                if (FormulaTree.isFunction(prevItem) || FormulaTree.isOperator(prevItem) ||
                        prevItem.equals(openingBracket)) {
                    formulaList.remove(i);
                }
            }
        }
        String[] result = new String[formulaList.size()];
        formulaList.toArray(result);
        return result;
    }

    /**
     * checks brackets correctness in formula string and throws exceptions if something is wrong
     *
     * @param formula the formula string
     */
    private static void checkBracketsCorrectness(String formula) {
        int unclosedBrackets = 0;
        boolean isPrevOpenBracket = false;
        for (int i = 0; i < formula.length(); i++) {
            char currentChar = formula.charAt(i);
            if (currentChar == FormulaTree.OPENING_BRACKET.charAt(0)) {
                unclosedBrackets++;
                isPrevOpenBracket = true;
                continue;
            }
            if (currentChar == FormulaTree.CLOSING_BRACKET.charAt(0)) {
                if (--unclosedBrackets == -1) {
                    throw new IllegalArgumentException("Incorrect bracket at " + i + " position");
                }
                if (isPrevOpenBracket) {
                    throw new IllegalArgumentException("Empty brackets at " + i + " position");
                }
            }
            isPrevOpenBracket = false;
        }
        if (unclosedBrackets != 0) {
            throw new IllegalArgumentException("There are " + unclosedBrackets + " unclosed brackets in formula");
        }
    }

    /**
     * converts input string variables to hashmap where key is argument name; value is value
     *
     * @param input string array where first item is formula("a+1") and all the next is variables("a=1")
     * @return the hashmap where key is argument name; value is value
     */
    public static HashMap<String, Double> parseArguments(String[] input) {
        if (input.length < 2)   // when input doesn't contain arguments
            return null;
        HashMap<String, Double> variables = new HashMap<>(input.length - 1);
        String argumentItem;
        String[] separatedArgument;
        for (int i = 1; i < input.length; i++) {
            argumentItem = removeSpacesIn(input[i]);
            argumentItem = replaceComasToDots(argumentItem);
            if (argumentItem.length() < MIN_VARIABLE_STRING_LENGTH) {   // when argument string is too short
                throw new IllegalArgumentException("Error in arguments. Incorrect argument on " + i + " position");
            }
            separatedArgument = argumentItem.split("=");
            if (separatedArgument.length != 2) {
                throw new IllegalArgumentException("Error in arguments. Incorrect argument on " + i + " position");
            }
            String variableName = separatedArgument[0];
            if (variableName.equals("")) {
                throw new IllegalArgumentException("Error in arguments. Empty argument name at " + i + " position");
            }
            double variableValue;
            try {
                variableValue = Double.parseDouble(separatedArgument[1]);
            } catch (Exception e) {
                throw new IllegalArgumentException("Error in arguments. Incorrect value in argument at " + i + " position");
            }
            variables.put(variableName, variableValue);
        }
        return variables;
    }

    /**
     * checks separated formula item is argument, value or closing bracket
     *
     * @param item the separated formula item
     * @return true if item is argument, value or closing bracket
     */
    private static boolean isArgumentOrValueOrClosingBracket(String item) {
        return !(FormulaTree.isOperator(item) || FormulaTree.isFunction(item) ||
                item.equals(FormulaTree.OPENING_BRACKET));
    }

    /**
     * removes spaces in input string
     *
     * @param inputString the input string
     * @return the result
     */
    private static String removeSpacesIn(String inputString) {
        return inputString.replace(" ", "");
    }

    /**
     * replaces comma to dot in string if replacing is enabled in IS_REPLACING_COMMA_TO_DOT const
     *
     * @param input the input string
     * @return the string with replaced all commas to dots
     */
    private static String replaceComasToDots(String input) {
        if (IS_REPLACING_COMMA_TO_DOT) {
            return input.replace(',', '.');
        }
        return input;
    }
}
