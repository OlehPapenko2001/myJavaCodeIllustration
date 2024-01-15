package assignment11.FormulaTreeNodes;

import java.util.HashMap;

/**
 * this interface shows what functions should contain formula tree node
 */
public interface Node {
    /**
     * returns the value of the node
     * @param arguments the hashmap where key is argument name; value is value
     * @return the value of the node
     */
    double getValue(HashMap<String, Double> arguments);
}
