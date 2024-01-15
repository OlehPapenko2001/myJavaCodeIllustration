package assignment6_MyCodeIsOnlyInLogicFiles.stenganography;

import acm.graphics.*;

public class SteganographyLogic {
    /**
     * Given a GImage containing a hidden message, finds the hidden message
     * contained within it and returns a boolean array containing that message.
     * <p/>
     * A message has been hidden in the input image as follows.  For each pixel
     * in the image, if that pixel has a red component that is an even number,
     * the message value at that pixel is false.  If the red component is an odd
     * number, the message value at that pixel is true.
     *
     * @param source The image containing the hidden message.
     * @return The hidden message, expressed as a boolean array.
     */
    public static boolean[][] findMessage(GImage source) {
        int red;
        int[][] pixelsArr = source.getPixelArray();
        boolean[][] result = new boolean[pixelsArr.length][pixelsArr[0].length];
        for (int i = 0; i < pixelsArr.length; i++) {
            int[] pixelRow = pixelsArr[i];
            for (int j = 0; j < pixelRow.length; j++) {
                int pixel = pixelRow[j];
                red = GImage.getRed(pixel);

                result[i][j] = red % 2 == 1;  //decrypting
            }
        }
        return result;
    }


    /**
     * Hides the given message inside the specified image.
     * <p/>
     * The image will be given to you as a GImage of some size, and the message will
     * be specified as a boolean array of pixels, where each white pixel is denoted
     * false and each black pixel is denoted true.
     * <p/>
     * The message should be hidden in the image by adjusting the red channel of all
     * the pixels in the original image.  For each pixel in the original image, you
     * should make the red channel an even number if the message color is white at
     * that position, and odd otherwise.
     * <p/>
     *
     * @param message The message to hide.
     * @param source  The source image.
     * @return A GImage whose pixels have the message hidden within it.
     */
    public static GImage hideMessage(boolean[][] message, GImage source) {
        int red, green, blue;
        int[][] pixelsArr = source.getPixelArray();
        for (int i = 0; i < pixelsArr.length; i++) {
            for (int j = 0; j < pixelsArr[i].length; j++) {
                red = GImage.getRed(pixelsArr[i][j]);
                green = GImage.getGreen(pixelsArr[i][j]);
                blue = GImage.getBlue(pixelsArr[i][j]);

                red = encodeComponent(red, message[i][j]);  //hiding secret pixel in red component
                pixelsArr[i][j] = GImage.createRGBPixel(red, green, blue);//overriding regular pixel with encoded
            }
        }
        return new GImage(pixelsArr);
    }

    /**
     * Hides pixel in given color component if hidden pixel is black colorComponent will be odd
     * else it will be odd
     *
     * @param colorComponent colorComponent component, where pixel will be hidden
     * @param hiddenPixel    black/white pixel that will be hidden
     * @return component of colorComponent with hidden pixel
     */
    private static int encodeComponent(int colorComponent, boolean hiddenPixel) {
        //     black    &&     odd
        if (hiddenPixel && colorComponent % 2 != 1) {
            colorComponent++;
            //      white       &&      even
        } else if (!hiddenPixel && colorComponent % 2 != 0) {
            colorComponent--;
        }
        return colorComponent;
    }
}
