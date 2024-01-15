package assignment8;

import acm.graphics.GRect;

public class Square implements Constants {
    /**
     * The graphic object, that is handled by this class
     */
    GRect square;

    boolean isMoving = false;
    /**
     * The y position of square in grid
     */
    private double currentHeight = GRID_SIZE_Y - 1;
    /**
     * The height of one field in the grid
     */
    double gridFieldHeight;
    /**
     * The velocity of square on current frame
     */
    double movingDelta = INITIAL_MOVING_SPEED;

    /**
     * Creates square class that handles jump moving of the GRect object in the grid
     *
     * @param gridPosX    The x position of upper left corner of grid field
     * @param gridPosY    The y position of upper left corner of grid field
     * @param fieldWidth  The width of grid field
     * @param fieldHeight The height of grid field
     */
    public Square(double gridPosX, double gridPosY, double fieldWidth, double fieldHeight) {
        double squareSize = Math.min(fieldWidth, fieldHeight) * SQUARE_SIZE_MULTIPLIER;
        double xPos = gridPosX + (fieldWidth - squareSize) / 2;
        double yPos = gridPosY + (fieldHeight - squareSize) / 2;
        gridFieldHeight = fieldHeight;
        square = new GRect(xPos, yPos, squareSize, squareSize);
        square.setFilled(true);
    }

    /**
     * @return The graphic object, that is handled by this class
     */
    public GRect getGObject() {
        return square;
    }

    /**
     * Runs moving of the square
     */
    public void startMoving() {
        isMoving = true;
        movingDelta = INITIAL_MOVING_SPEED;
    }

    /**
     * updates state of square at current frame
     */
    public void updateState() {
        if (isMoving) {
            move();
        }
    }

    /**
     * moves the square at current frame
     */
    public void move() {
        currentHeight -= movingDelta;
        movingDelta -= GRAVITY;
        if (currentHeight <= 0) {   // when square reaches top screen border
            currentHeight = 0;
            movingDelta = movingDelta > 0 ? -movingDelta : movingDelta;
        } else if (currentHeight >= GRID_SIZE_Y - 1) {  // when square reaches bottom screen border
            currentHeight = GRID_SIZE_Y - 1;
        }

        square.setLocation(square.getX(),
                (Math.round(currentHeight) * gridFieldHeight) + (gridFieldHeight - square.getWidth()) / 2);
    }

}
