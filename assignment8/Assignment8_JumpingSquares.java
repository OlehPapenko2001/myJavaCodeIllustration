package assignment8;

import acm.graphics.GObject;
import com.shpp.cs.a.graphics.WindowProgram;

import java.awt.event.MouseEvent;

/**
 * The screen is a grid of AxB squares, where the lowest row is filled with black squares.
 * When you click on any black square, it should start moving up (moving strictly along the grid)
 * until it hits the top of the window, and from there it should go down and stop at its original position.
 * <p>
 * A higher level - to make the square "bounce" according to the laws of gravity - bounces quickly,
 * but closer to the upper edge of the window it slows down.
 * <p>
 * Of course, during the flight of any square, you can click on another in the lower row, and it will also start moving.
 */
public class Assignment8_JumpingSquares extends WindowProgram implements Constants {
    Square[] squares;

    public void run() {
        getGCanvas().setAutoRepaintFlag(false); // disabling auto repaint at canvas
        drawSquares();
        addMouseListeners();
        while (true) {
            for (Square square : squares) {
                square.updateState();
            }
            getGCanvas().repaint();
            pause(PAUSE_TIME);
        }
    }

    /**
     * draws GRect squares in the canvas at last line and adds them to squares array
     */
    private void drawSquares() {
        double fieldSizeX = (double) getWidth() / GRID_SIZE_X;
        double fieldSizeY = (double) getHeight() / GRID_SIZE_Y;
        squares = new Square[GRID_SIZE_X];
        double lastRowY = fieldSizeY * (GRID_SIZE_Y - 1);
        for (int i = 0; i < GRID_SIZE_X; i++) {
            squares[i] = new Square(fieldSizeX * i, lastRowY, fieldSizeX, fieldSizeY);
            add(squares[i].getGObject());
        }
    }

    /**
     * handles clicks at squares and starts moving when square has been clicked
     *
     * @param e click MouseEvent
     */
    public void mouseClicked(MouseEvent e) {
        GObject clickedObject = getElementAt(e.getX(), e.getY());
        if (clickedObject == null) {
            return;
        }
        for (Square square : squares) {
            if (clickedObject == square.getGObject()) {
                square.startMoving();
                return;
            }
        }
    }

}