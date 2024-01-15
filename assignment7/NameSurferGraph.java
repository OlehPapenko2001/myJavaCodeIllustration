package assignment7;

/**
 * This class represents the canvas on which the graph of
 * names is drawn. This class is responsible for updating
 * (redrawing) the graphs whenever the list of entries changes
 * or the window is resized.
 */

import acm.graphics.*;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class NameSurferGraph extends GCanvas
        implements NameSurferConstants, ComponentListener {


    private ArrayList<NameSurferEntry> entries;

    /**
     * Creates a new NameSurferGraph object that displays the data.
     */
    public NameSurferGraph() {
        addComponentListener(this);
        entries = new ArrayList<>();
    }


    /**
     * Clears the list of name surfer entries stored inside this class.
     */
    public void clear() {
        entries.clear();
    }


    /**
     * Adds a new NameSurferEntry to the list of entries on the display.
     * Note that this method does not actually draw the graph, but
     * simply stores the entry; the graph is drawn by calling update.
     */
    public void addEntry(NameSurferEntry entry) {
        if (entry != null && !entries.contains(entry))
            entries.add(entry);
    }


    /**
     * Updates the display image by deleting all the graphical objects
     * from the canvas and then reassembling the display according to
     * the list of entries. Your application must call update after
     * calling either clear or addEntry; update is also called whenever
     * the size of the canvas changes.
     */
    public void update() {
        removeAll();
        drawGrid();
        drawNamesEntries();
    }

    /**
     * draws rating of the ArrayList entries in the graph
     */
    private void drawNamesEntries() {
        double verticalDistBetweenLines = (double) getWidth() / NDECADES;
        Color entryColor;
        double prevX = 0.0, prevY = 0.0;
        for (int i = 0; i < entries.size(); i++) {
            NameSurferEntry entry = entries.get(i);
            entryColor = GRAPH_COLORS[i % GRAPH_COLORS.length];
            for (int j = 0; j < NDECADES; j++) {
                //counting the position
                double curX = verticalDistBetweenLines * j;
                double curY = calcYForEntryRank(entry.getRank(j));
                drawEntryLabel(curX, curY, entry.getName(), entry.getRank(j), entryColor);
                if (j > 0) {
                    drawLine(prevX, prevY, curX, curY, entryColor);
                }
                prevX = curX;
                prevY = curY;
            }
        }
    }

    /**
     * Draws line between current and previous entry points of specified color
     *
     * @param prevX     position of previous point in graph
     * @param prevY     position of previous point in graph
     * @param curX      position of current point in graph
     * @param curY      position of current point in graph
     * @param lineColor the color of line
     */
    private void drawLine(double prevX, double prevY, double curX, double curY, Color lineColor) {
        GLine line = new GLine(prevX, prevY, curX, curY);
        line.setColor(lineColor);
        add(line);
    }

    /**
     * draws entry label of specified name rank and color in x,y position
     *
     * @param x          position of start of the label
     * @param y          baseline coordinate of the label
     * @param name       the name
     * @param rank       the rank of the name in the decade
     * @param entryColor the color of the label
     */
    private void drawEntryLabel(double x, double y, String name, int rank, Color entryColor) {
        GLabel nameLabel = new GLabel(name + " " + (rank == 0 ? SYMBOL_FOR_ZERO_RANK : rank));
        nameLabel.setFont(GRAPH_ENTRIES_LABELS_FONT);
        nameLabel.setColor(entryColor);
        nameLabel.setLocation(x, y - nameLabel.getDescent());
        add(nameLabel);
    }

    /**
     * calculates y position, in the graph where item with specified rank must be drawn
     *
     * @param entryRank the rank of the item
     * @return y position, in the graph where item with specified rank must be drawn
     */
    private double calcYForEntryRank(int entryRank) {
        if (entryRank == 0) {
            return getHeight() - GRAPH_MARGIN_SIZE;
        }
        return GRAPH_MARGIN_SIZE + entryRank / (double) MAX_RANK * (getHeight() - GRAPH_MARGIN_SIZE * 2);
    }

    /**
     * draws raw grid with years in bottom for names ranking
     */
    private void drawGrid() {
        add(new GLine(0, GRAPH_MARGIN_SIZE, getWidth(), GRAPH_MARGIN_SIZE));
        add(new GLine(0, getHeight() - GRAPH_MARGIN_SIZE, getWidth(), getHeight() - GRAPH_MARGIN_SIZE));
        double verticalLinesMargin = (double) getWidth() / NDECADES;
        for (int i = 0; i < NDECADES; i++) {
            //drawing lines
            drawLine(verticalLinesMargin * i, 0, verticalLinesMargin * i, getHeight(), GRID_COLOR);
            //drawing dates at bottom
            GLabel dateLabel = new GLabel(Integer.toString(START_DECADE + i * DECADE_SIZE));
            dateLabel.setFont(GRAPH_BOT_LABELS_FONT);
            dateLabel.setLocation(verticalLinesMargin * i, getHeight() - dateLabel.getDescent());
            add(dateLabel);
        }
    }


    /* Implementation of the ComponentListener interface */
    public void componentHidden(ComponentEvent e) {
    }

    public void componentMoved(ComponentEvent e) {
    }

    public void componentResized(ComponentEvent e) {
        update();
    }

    public void componentShown(ComponentEvent e) {
    }
}
