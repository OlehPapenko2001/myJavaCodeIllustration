package assignment11.FormulaTreeNodes;

import java.util.HashMap;

/**
 * this class is a part of formula tree structure and allows to contain double number value
 */
public class ValueNode implements Node {
    /**
     * value of the node in double type
     */
    double value;

    /**
     * creates value node that contains value in double type
     *
     * @param value the double type value that will be assigned to node
     */
    public ValueNode(double value) {
        this.value = value;
    }

    /**
     * @param arguments the hashmap where key is argument name; value is value (null if absent)
     * @return the value of node
     */
    public double getValue(HashMap<String, Double> arguments) {
        return value;
    }
}
