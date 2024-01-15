package assignment11.FormulaTreeNodes;

import assignment11.Action;

import java.util.HashMap;

/**
 * this class is a part of formula tree structure and allows to calculate operator action for 2 children of formula tree
 * class
 */
public class OperatorNode implements Node {

    /**
     * the function of operator action
     */
    private final Action nodeAction;

    /**
     * the left part before operator
     */
    private final Node firstChild;

    /**
     * the right part after operator
     */
    private final Node secondChild;

    /**
     * creates operator node that contains function of operator action and links on children that are left and right
     * part of operator
     *
     * @param nodeAction  the function of operator action
     * @param firstChild  the left part before operator
     * @param secondChild the right part after operator
     */
    public OperatorNode(Action nodeAction, Node firstChild, Node secondChild) {
        this.nodeAction = nodeAction;
        this.firstChild = firstChild;
        this.secondChild = secondChild;
    }

    /**
     * calculates the result of action executed on children of this action node
     *
     * @param arguments the hashmap where key is argument name; value is value (null if absent)
     * @return the result of calculations
     */
    public double getValue(HashMap<String, Double> arguments) {
        return nodeAction.calculate(firstChild.getValue(arguments), secondChild.getValue(arguments));
    }
}
