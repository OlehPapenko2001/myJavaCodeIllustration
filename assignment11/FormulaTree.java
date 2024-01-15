package assignment11;

import assignment11.FormulaTreeNodes.*;

import java.util.Arrays;
import java.util.HashMap;

/**
 * this class contains root node of FormulaTree and has functions to build this tree of prepared in FormulaTreeParser
 * separated formula string array, also has method that calculates formula tree
 */
public class FormulaTree {
    /**
     * division operator symbol
     */
    public static final String DIVISION_OPERATOR = "/";
    /**
     * multiplication operator symbol
     */
    public static final String MULTIPLICATION_OPERATOR = "*";
    /**
     * adding operator symbol
     */
    public static final String ADDING_OPERATOR = "+";
    /**
     * subtraction operator symbol
     */
    public static final String SUBTRACTION_OPERATOR = "-";
    /**
     * exponentiation operator symbol
     */
    public static final String EXPONENTIATION_OPERATOR = "^";

    /**
     * opening bracket operator symbol
     */
    public static final String OPENING_BRACKET = "(";
    /**
     * closing bracket operator symbol
     */
    public static final String CLOSING_BRACKET = ")";


    /**
     * Hashmap that contains priorities of operators(Character). The lower value(Integer) will have higher priority
     */

    public static final HashMap<String, Integer> ARITHMETIC_OPERATORS_PRIORITY =
            new HashMap<>() {
                {
                    put(EXPONENTIATION_OPERATOR, 1);
                    put(DIVISION_OPERATOR, 2);
                    put(MULTIPLICATION_OPERATOR, 2);
                    put(ADDING_OPERATOR, 3);
                    put(SUBTRACTION_OPERATOR, 3);
                }
            };

    /**
     * priority of function during calculations
     */
    public static final int FUNCTION_PRIORITY = 1;

    /**
     * hashmap that contains methods for operators actions
     */
    public static final HashMap<String, Action> OPERATORS_ACTIONS =
            new HashMap<>() {
                {
                    put(EXPONENTIATION_OPERATOR, Math::pow);
                    put(DIVISION_OPERATOR, (value1, value2) -> value1 / value2);
                    put(MULTIPLICATION_OPERATOR, ((value1, value2) -> value1 * value2));
                    put(ADDING_OPERATOR, ((value1, value2) -> value1 + value2));
                    put(SUBTRACTION_OPERATOR, ((value1, value2) -> value1 - value2));
                }
            };

    /**
     * hashmap that contains methods for functions actions
     */
    public static final HashMap<String, Function> FUNCTIONS_ACTIONS =
            new HashMap<>() {
                {
                    put("sin", Math::sin);
                    put("cos", Math::cos);
                    put("tan", Math::tan);
                    put("atan", Math::atan);
                    put("log10", Math::log10);
                    put("log2", (value1 -> Math.log(value1) / Math.log(2)));
                    put("sqrt", Math::sqrt);
                }
            };

    /**
     * contains root node of formula tree
     */
    private final Node rootNode;

    /**
     * creates formula tree of separated formula string array, where each item is operator, value or argument
     *
     * @param separatedFormula the string array, where each item is operator, value or argument
     */
    public FormulaTree(String[] separatedFormula) {
        rootNode = formulaToTree(separatedFormula);
    }

    /**
     * Parses formula from string array to tree structure that has 4 types of nodes: argument, function, operator and
     * value.
     *
     * @param splitFormula string formula array that wil be parsed
     * @return generated formula tree object
     */
    private Node formulaToTree(String[] splitFormula) {
        int actionId = getLastAction(splitFormula);
        String item = splitFormula[actionId];
        if (isOperator(item)) {//maintaining operators
            if (actionId == 0 && item.equals(SUBTRACTION_OPERATOR)) {//when minus is at the start of split formula
                String[] rightSide = cutEdgeBrackets(splitFormula, actionId + 1, splitFormula.length - 1);
                return new OperatorNode(OPERATORS_ACTIONS.get(MULTIPLICATION_OPERATOR),
                        new ValueNode(-1), formulaToTree(rightSide));
            }
            // getting formula parts without brackets
            String[] leftSide = cutEdgeBrackets(splitFormula, 0, actionId - 1);
            String[] rightSide = cutEdgeBrackets(splitFormula, actionId + 1, splitFormula.length - 1);
            return new OperatorNode(OPERATORS_ACTIONS.get(item), formulaToTree(leftSide), formulaToTree(rightSide));
        }
        if (isFunction(item)) {//maintaining functions
            String[] functionBody = cutEdgeBrackets(splitFormula, actionId + 1, splitFormula.length - 1);
            return new FunctionNode(FUNCTIONS_ACTIONS.get(item), formulaToTree(functionBody));
        }
        try {//if parsing to double successful creating value node, if not - argument
            double value = Double.parseDouble(item);
            return new ValueNode(value);
        } catch (Exception exception) {
            return new ArgumentNode(item);
        }
    }

    /**
     * returns the result of formula tree calculation using given arguments hashmap
     *
     * @param arguments the hashmap where key is argument name; value is value
     * @return result of formula tree calculation
     */
    public double calculateTree(HashMap<String, Double> arguments) {
        return rootNode.getValue(arguments);
    }

    /**
     * returns last action operator id in split formula string array
     *
     * @param splitFormula split formula string array
     * @return last action operator id
     */
    private int getLastAction(String[] splitFormula) {
        if (splitFormula.length == 1) {
            return 0;
        }
        int bracketsLevel = 0;
        int actionBracketsLevel = Integer.MAX_VALUE;
        int actionPriority = 0;
        int lowestPriorityActionId = -1;
        String item;
        for (int i = splitFormula.length - 1; i >= 0; i--) {
            item = splitFormula[i];
            // work with chars
            if (item.equals(OPENING_BRACKET)) {
                bracketsLevel--;
                continue;
            }
            if (item.equals(CLOSING_BRACKET)) {
                bracketsLevel++;
                continue;
            }
            //operator priority check
            if (isOperator(item) &&
                    (actionBracketsLevel >= bracketsLevel &&
                            ARITHMETIC_OPERATORS_PRIORITY.get(item) > actionPriority ||
                            actionBracketsLevel > bracketsLevel)) {
                lowestPriorityActionId = i;
                actionPriority = ARITHMETIC_OPERATORS_PRIORITY.get(item);
                actionBracketsLevel = bracketsLevel;
                continue;
            }
            // current function priority check
            if (isFunction(item) && (actionBracketsLevel == bracketsLevel && FUNCTION_PRIORITY > actionPriority
                    || actionBracketsLevel > bracketsLevel)) {
                lowestPriorityActionId = i;
                actionPriority = FUNCTION_PRIORITY;
                actionBracketsLevel = bracketsLevel;
            }
        }
        if (lowestPriorityActionId == -1) {
            throw new IllegalArgumentException("Error in formula. Cannot find action in \""
                    + convertSeparatedFormulaToString(splitFormula) + "\"");
        }
        if (splitFormula[lowestPriorityActionId].length() == 1 &&
                splitFormula[lowestPriorityActionId].equals(EXPONENTIATION_OPERATOR))// if highest priority is ascension
            return getLastAscension(splitFormula);
        return lowestPriorityActionId;
    }

    /**
     * returns last ascension action operator id in split formula string array
     *
     * @param splitFormula split formula string array
     * @return last ascension action id
     */
    private int getLastAscension(String[] splitFormula) {
        int bracketsLevel = 0;
        int actionBracketsLevel = Integer.MAX_VALUE;
        int lowestPriorityAscensionId = -1;
        for (int i = 0; i < splitFormula.length; i++) {
            if (splitFormula[i].equals(OPENING_BRACKET)) {
                bracketsLevel++;
                continue;
            }
            if (splitFormula[i].equals(CLOSING_BRACKET)) {
                bracketsLevel--;
                continue;
            }
            if (splitFormula[i].equals(EXPONENTIATION_OPERATOR) &&
                    actionBracketsLevel > bracketsLevel) {
                lowestPriorityAscensionId = i;
                actionBracketsLevel = bracketsLevel;
            }
        }
        return lowestPriorityAscensionId;
    }


    /**
     * Removes brackets at start and in the end in specified range of split formula array and returns this part without
     * brackets as new string array
     *
     * @param splitFormula the input string array with formula
     * @param startIndex   index of first element of formula array where edge brackets will be cut
     * @param endIndex     index of last element of formula array where edge brackets will be cut
     * @return string array without brackets at start and at the end
     */
    private String[] cutEdgeBrackets(String[] splitFormula, int startIndex, int endIndex) {
        if (startIndex >= splitFormula.length) {
            throw new IllegalArgumentException("Error in formula. There is nothing after "
                    + convertSeparatedFormulaToString(splitFormula));
        }
        while (splitFormula[startIndex].equals(OPENING_BRACKET) &&
                splitFormula[endIndex].equals(CLOSING_BRACKET)) {
            startIndex++;
            endIndex--;
        }
        return Arrays.copyOfRange(splitFormula, startIndex, endIndex + 1);
    }

    /**
     * Converts string array to string without splitters
     *
     * @param array input string array
     * @return array converted to string without splitters
     */
    private String convertSeparatedFormulaToString(String[] array) {
        StringBuilder stringBuilder = new StringBuilder();
        for (String item : array) {
            stringBuilder.append(item);
        }
        return stringBuilder.toString();
    }

    /**
     * Checks is character an operator
     *
     * @param value the char value
     * @return true if character is an operator and false if not
     */
    public static boolean isOperator(String value) {
        return ARITHMETIC_OPERATORS_PRIORITY.containsKey(value);
    }

    /**
     * Checks is String a function
     *
     * @param item the String value
     * @return true if String is a function and false if not
     */
    public static boolean isFunction(String item) {
        return FUNCTIONS_ACTIONS.containsKey(item);
    }

}
