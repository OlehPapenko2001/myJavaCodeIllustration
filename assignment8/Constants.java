package assignment8;

/*
 * File: NameSurferConstants.java
 * ------------------------------
 * This file declares several constants that are shared by the
 * different modules in the NameSurfer application.  Any class
 * that implements this interface can use these constants.
 */

public interface Constants {
    /**
     * Width and height of application window in pixels
     */
    public static final int APPLICATION_WIDTH = 500;
    public static final int APPLICATION_HEIGHT = 600;
    /**
     * The render grid dimension
     */
    public static final int GRID_SIZE_X = 15;
    public static final int GRID_SIZE_Y = 15;
    /**
     * The multiplier of square size(shows how big the square is relative to the grid box)
     */
    public static final double SQUARE_SIZE_MULTIPLIER = 0.7;
    /**
     * The pause time between render frames
     */
    public static final int PAUSE_TIME = 100;
    /**
     * The initial speed of square at the start of moving
     */
    public static final double INITIAL_MOVING_SPEED = 1;
    /**
     * The strength of gravity(velocity of square during flight)
     */
    public static final double GRAVITY = 0.06;

}
