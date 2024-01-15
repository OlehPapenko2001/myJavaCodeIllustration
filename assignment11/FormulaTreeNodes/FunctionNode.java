package assignment11.FormulaTreeNodes;

import assignment11.Function;

import java.util.HashMap;

/**
 * this class is a part of formula tree structure and allows to calculate function action for one child of formula tree
 * class
 */
public class FunctionNode implements Node {

    /**
     * the function of function operator
     */
    private final Function nodeAction;

    /**
     * the function body
     */
    private final Node child;

    /**
     * creates function node that contains function of function operator and link on child that is function body
     *
     * @param nodeAction - the function of function operator
     * @param child      - the function body
     */
    public FunctionNode(Function nodeAction, Node child) {
        this.nodeAction = nodeAction;
        this.child = child;
    }

    /**
     * calculates the result of action executed on child of this function node
     *
     * @param arguments the hashmap where key is argument name; value is value (null if absent)
     * @return the result of calculations
     */
    public double getValue(HashMap<String, Double> arguments) {
        return nodeAction.calculate(child.getValue(arguments));
    }
}
