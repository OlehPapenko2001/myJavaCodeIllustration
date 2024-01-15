package assignment7;

/*
 * File: NameSurferConstants.java
 * ------------------------------
 * This file declares several constants that are shared by the
 * different modules in the NameSurfer application.  Any class
 * that implements this interface can use these constants.
 */

import java.awt.*;

public interface NameSurferConstants {

    /**
     * The width of the application window
     */
    public static final int APPLICATION_WIDTH = 800;

    /**
     * The height of the application window
     */
    public static final int APPLICATION_HEIGHT = 600;

    /**
     * The name of the file containing the data
     */
    public static final String NAMES_DATA_FILE = "names-data.txt";

    /**
     * The first decade in the database
     */
    public static final int START_DECADE = 1900;

    /**
     * The number of decades
     */
    public static final int NDECADES = 12;

    /**
     * The maximum rank in the database
     */
    public static final int MAX_RANK = 1000;

    /**
     * The number of pixels to reserve at the top and bottom
     */
    public static final int GRAPH_MARGIN_SIZE = 20;

    /**
     * Font of bottom years labels
     */
    public static final String GRAPH_BOT_LABELS_FONT = "TimesNewRoman-18";

    /**
     * Font of entries labels in graph
     */
    public static final String GRAPH_ENTRIES_LABELS_FONT = "TimesNewRoman-12";

    /**
     * array of colors that will be used in graph drawing
     */
    public static final Color[] GRAPH_COLORS = {Color.BLUE, Color.RED, Color.MAGENTA, Color.BLACK};

    /**
     * Width of the JTextField intended for input
     */
    public static final int NAME_FIELD_WIDTH = 20;

    /**
     * Symbol that replaces 0 in graph rank label
     */
    public static final String SYMBOL_FOR_ZERO_RANK = "*";

    /**
     * The size of decade
     */
    public static final int DECADE_SIZE = 10;

    /**
     * The splitter of fields in database lines
     */
    public static final String FILE_FIELDS_SPLITTER = " ";

    /**
     * The color of graph grid
     */
    public static final Color GRID_COLOR = Color.BLACK;
}
