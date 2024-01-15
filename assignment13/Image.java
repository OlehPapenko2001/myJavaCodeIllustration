package assignment13;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;

public class Image {
    /**
     * the sum of 2 pixels rgb channels difference absolutes, if difference is more colors will be counted as different
     */
    public static final int PIXEL_IS_DIFFERENT_DELTA = 150;
    /**
     * contains buffered image got from jpg file
     */
    private final BufferedImage bufferedImage;

    /**
     * creates buffered image object of jpg image file
     *
     * @param fileName name of jpg image file
     * @throws IOException when there is problem with file reading
     */
    public Image(String fileName) throws IOException {
        bufferedImage = ImageIO.read(new File(fileName));
    }

    /**
     * @return the width of buffered image
     */
    public int getWidth() {
        return bufferedImage.getWidth();
    }

    /**
     * @return the height of buffered image
     */
    public int getHeight() {
        return bufferedImage.getHeight();
    }

    /**
     * @param x the x position of the pixel
     * @param y the y position of the pixel
     * @return rgb integer value of pixel at x,y position
     */
    private int getRGB(int x, int y) {
        return bufferedImage.getRGB(x, y);
    }

    /**
     * @param rgb integer value
     * @return red chanel of rgb integer
     */
    private int getRed(int rgb) {
        return (rgb >> 16) & 0xFF;
    }

    /**
     * @param rgb integer value
     * @return blue chanel of rgb integer
     */
    private int getBlue(int rgb) {
        return (rgb >> 8) & 0xFF;
    }

    /**
     * @param rgb integer value
     * @return green chanel of rgb integer
     */
    private int getGreen(int rgb) {
        return rgb & 0xFF;
    }

    /**
     * creates boolean 2-dimensional image array where background pixel is false, and other is true
     *
     * @return the boolean image array where background pixel is false, and other is true
     */
    public boolean[][] getBooleanImageObjectsArray() {
        int backgroundColor = getBackgroundColor();
        boolean[][] simplifiedImage = new boolean[getWidth()][getHeight()];
        for (int x = 0; x < getWidth(); x++) {
            for (int y = 0; y < getHeight(); y++) {
                simplifiedImage[x][y] = isPixelDifferent(getRGB(x, y), backgroundColor);
            }
        }
        return simplifiedImage;
    }

    /**
     * calculates background color of image
     *
     * @return background color of image in integer rgb type
     */
    public int getBackgroundColor() {
        int firstPixelInLine;
        int width = getWidth();
        int height = getHeight();
        boolean lineContainsOneColor = true;
        if (width < 2 || height < 2) {
            return getRGB(0, 0);
        }
        for (int x = 0; x < width; x++) {
            firstPixelInLine = getRGB(x, 0);
            for (int y = 1; y < height; y++) {
                if (isPixelDifferent(firstPixelInLine, getRGB(x, y))) {
                    lineContainsOneColor = false;
                    break;
                }
            }
            if (lineContainsOneColor) {
                return firstPixelInLine;
            }
            lineContainsOneColor = true;
        }
        for (int y = 0; y < height; y++) {
            firstPixelInLine = getRGB(0, y);
            for (int x = 1; x < width; x++) {
                if (isPixelDifferent(firstPixelInLine, getRGB(x, y))) {
                    lineContainsOneColor = false;
                    break;
                }
            }
            if (lineContainsOneColor) {
                return firstPixelInLine;
            }
            lineContainsOneColor = true;
        }
        return getBackgroundColorByBorderMajority();
    }

    /**
     * calculates background color by the most major border color
     *
     * @return background color of image in integer rgb type
     */
    private int getBackgroundColorByBorderMajority() {
        HashMap<Integer, Integer[]> colors = new HashMap<>();
        int maxX = getWidth() - 1;
        int maxY = getHeight() - 1;
        for (int i = 0; i <= maxY; i++) {
            addColorInHashMap(colors, 0, i);
            addColorInHashMap(colors, maxX, i);
        }
        for (int i = 0; i <= maxX; i++) {
            addColorInHashMap(colors, i, 0);
            addColorInHashMap(colors, i, maxY);
        }
        return findPrevailingColor(colors);
    }

    /**
     * finds color that has the most quantity of rgb color in colors HashMap
     *
     * @param colors the HashMap where key is rgb int color and value is integer array where first item is quantity
     *               of such pixels
     * @return rgb color that has the most quantity of rgb color in colors arraylist
     */
    private int findPrevailingColor(HashMap<Integer, Integer[]> colors) {
        int prevailingColor = 0;
        int prevailingColorQuantity = 0;
        for (Integer pixel : colors.keySet()) {
            if (colors.get(pixel)[0] > prevailingColorQuantity) {
                prevailingColor = pixel;
                prevailingColorQuantity = colors.get(pixel)[0];
            }
        }
        return prevailingColor;
    }

    /**
     * @param firstPixel  the rgb int color of first pixel
     * @param secondPixel the rgb int color of second pixel
     * @return true if pixels are different, false if not
     */
    private boolean isPixelDifferent(int firstPixel, int secondPixel) {
        //simple approach of computing the similarity of colors taken here https://www.baeldung.com/cs/compute-similarity-of-colours
        int redDelta = getRed(firstPixel) - getRed(secondPixel);
        int greenDelta = getGreen(firstPixel) - getGreen(secondPixel);
        int blueDelta = getBlue(firstPixel) - getBlue(secondPixel);

        return Math.sqrt(redDelta * redDelta + greenDelta * greenDelta + blueDelta * blueDelta) > PIXEL_IS_DIFFERENT_DELTA;
    }

    /**
     * adds rgb int pixel in pixels HashMap
     *
     * @param pixels the HashMap where key is rgb int color and value is integer array where first item is quantity
     *               of such pixels
     * @param x      the x position of the pixel
     * @param y      the y position of the pixel
     */
    private void addColorInHashMap(HashMap<Integer, Integer[]> pixels, int x, int y) {
        int currentColor;
        if (!pixels.containsKey(currentColor = getRGB(x, y))) {
            pixels.put(currentColor, new Integer[]{1});
        }
        pixels.get(currentColor)[0]++;
    }
}
