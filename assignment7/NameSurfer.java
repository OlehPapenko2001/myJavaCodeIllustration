package assignment7;

/*
 * File: NameSurfer.java
 * ---------------------
 * When it is finished, this program will implements the viewer for
 * the baby-name database described in the assignment handout.
 */

import com.shpp.cs.a.simple.SimpleProgram;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class NameSurfer extends SimpleProgram implements NameSurferConstants {

    private JTextField nameField;
    private JButton createGraphButton, clearButton;
    private JLabel messageLabel;
    private NameSurferGraph graph;
    private NameSurferDataBase dataBase;


    /**
     * This method has the responsibility for reading in the database
     * and initializing the interactors at the top of the window.
     */
    public void init() {
        try {// try to read file
            dataBase = new NameSurferDataBase(NAMES_DATA_FILE);
        } catch (Exception e) {
            //error message when problem with file
            JOptionPane.showMessageDialog(null, "There is a problem with a file:");
            exit();
        }

        //adding Name input text field
        add(new JLabel("Name"), NORTH);
        nameField = new JTextField(NAME_FIELD_WIDTH);
        add(nameField, NORTH);

        //adding buttons
        createGraphButton = new JButton("Graph");
        add(createGraphButton, NORTH);
        clearButton = new JButton("Clear");
        add(clearButton, NORTH);

        //adding error text label
        messageLabel = new JLabel(" ");
        add(messageLabel, SOUTH);

        //adding action listeners
        nameField.addActionListener(this);
        addActionListeners();

        //adding graph
        graph = new NameSurferGraph();
        add(graph);

    }

    /**
     * This class is responsible for detecting when the buttons are
     * clicked, so you will have to define a method to respond to
     * button actions.
     */
    public void actionPerformed(ActionEvent e) {
        messageLabel.setText(" "); //clear error label
        //maintaining commands
        if (e.getSource() == nameField || e.getSource() == createGraphButton) {
            NameSurferEntry entry = dataBase.findEntry(nameField.getText());
            //errors maintaining
            if (entry == null) {
                messageLabel.setText("There is no such name in database");
                return;
            }
            //default
            graph.addEntry(entry);
            graph.update();
            nameField.setText("");
        } else if (e.getSource() == clearButton) {
            graph.clear();
            graph.update();
        }

    }
}
