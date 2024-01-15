package assignment6_MyCodeIsOnlyInLogicFiles.histogramEqualization.teest.gui;

import acm.gui.TablePanel;

import java.awt.Dimension;
import javax.swing.JTextArea;

import assignment6_MyCodeIsOnlyInLogicFiles.histogramEqualization.teest.TeestCase;
import assignment6_MyCodeIsOnlyInLogicFiles.histogramEqualization.teest.TeestResult;

public class TeestCaseRow extends TablePanel {
    private ResultBox resultBox = new ResultBox();
    private JTextArea text;

    public TeestCaseRow(TeestCase test) {
        super(1, 2);
        this.text = new JTextArea(test.getName());
        this.text.setEditable(false);
        this.text.setLineWrap(true);
        this.text.setWrapStyleWord(true);
        this.text.setPreferredSize(new Dimension(150, 50));
        this.add(this.resultBox);
        this.add(this.text);
    }

    public void setResult(TeestResult result) {
        this.resultBox.setResult(result);
    }
}
