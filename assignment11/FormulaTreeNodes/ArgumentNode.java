package assignment11.FormulaTreeNodes;

import assignment11.FormulaTree;

import java.util.HashMap;

/**
 * this class is a part of formula tree structure and allows to contain argument
 */
public class ArgumentNode implements Node {

    /**
     * is argument negative or positive
     */
    private boolean isNegative = false;

    /**
     * the name of argument
     */
    public String argumentName;

    /**
     * creates argument node that contains argument name and information is it negative or positive
     *
     * @param argumentName the string argument including it's unary minus if present
     */
    public ArgumentNode(String argumentName) {
        if (argumentName.charAt(0) == FormulaTree.SUBTRACTION_OPERATOR.charAt(0)) {
            isNegative = true;
            argumentName = argumentName.substring(1);
        }
        this.argumentName = argumentName;
    }

    /**
     * substitutes the value to argument and returns it
     *
     * @param arguments the hashmap where key is argument name; value is value (null if absent)
     * @return the value of argument
     */
    public double getValue(HashMap<String, Double> arguments) {
        if (arguments == null || arguments.get(argumentName) == null) {
            throw new IllegalArgumentException("Error in arguments. Argument " + argumentName + " is undefined");
        }
        if (isNegative) {
            return -arguments.get(argumentName);
        }
        return arguments.get(argumentName);
    }
}
