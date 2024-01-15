package assignment13;


import java.util.*;


/**
 * this class counts silhouettes in given simple color image, it filters small objects by 2 criteria
 * 1st is if object less than max object square at (max object square) * UNCOUNTABLE_SQUARE_MULTIPLIER
 * 2nd is if object square is less than (image square) * SMALL_OBJECT_SQUARE_MULTIPLIER.
 */
public class Assignment13Part1_SilhouettesCounter {
    /**
     * Controls strength of refining object edges, the higher number, the stronger refining
     * formula: Math.round(Math.min(imageHeight, imageWidth) * REFINE_EDGES_MULTIPLIER
     */
    public static final double REFINE_EDGES_MULTIPLIER = 0.005;
    /**
     * the default file name that will be used if args array is empty
     */
    public static String DEFAULT_FILE_NAME = "test.jpg";
    /**
     * if object square is less than (max object square) * (this const) it wont be counted
     */
    public static final double UNCOUNTABLE_SQUARE_MULTIPLIER = 0.04;
    /**
     * if object square is less than (image square) * (this const) it wont be counted
     */
    public static final double SMALL_OBJECT_SQUARE_MULTIPLIER = 0.0005;

    /**
     * this method counts silhouettes in jpg file, which name gets at args[0]
     *
     * @param args the input of program, the first element in this array will be maintained as filename
     */
    public static void main(String[] args) {
        String fileName = getFileName(args);
        Image image;
        try {
            image = new Image(fileName);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return;
        }
        boolean[][] booleanImage = image.getBooleanImageObjectsArray();
        refineTheEdgesOfObjects(booleanImage);
//        printImage(booleanImage);
        System.out.println(findSilhouettes(booleanImage));
    }


    /**
     * calculates square of object at x,y position and replace it to background
     *
     * @param x            width coordinate of pixel
     * @param y            height coordinate of pixel
     * @param booleanImage the boolean 2-dimensional array, where background pixel is false and object pixel is true
     * @return square of object
     */
    public static int getObjectSquareAndRemoveIt(int x, int y, boolean[][] booleanImage) {
        int squareCounter = 1;
        Integer[] point;
        booleanImage[x][y] = false;
        ArrayDeque<Integer[]> pointsQueue = new ArrayDeque<>();
        pointsQueue.add(new Integer[]{x, y});
        while (pointsQueue.size() > 0) {
            point = pointsQueue.pop();
            x = point[0];
            y = point[1];
            if (x + 1 < booleanImage.length && booleanImage[x + 1][y]) {
                pointsQueue.add(new Integer[]{x + 1, y});
                booleanImage[x + 1][y] = false;
                squareCounter++;
            }
            if (y - 1 >= 0 && booleanImage[x][y - 1]) {
                pointsQueue.add(new Integer[]{x, y - 1});
                booleanImage[x][y - 1] = false;
                squareCounter++;
            }
            if (x - 1 >= 0 && booleanImage[x - 1][y]) {
                pointsQueue.add(new Integer[]{x - 1, y});
                booleanImage[x - 1][y] = false;
                squareCounter++;
            }
            if (y + 1 < booleanImage[0].length && booleanImage[x][y + 1]) {
                pointsQueue.add(new Integer[]{x, y + 1});
                booleanImage[x][y + 1] = false;
                squareCounter++;
            }
        }
        return squareCounter;
    }

    /**
     * counts square of all silhouettes on the image and filter small objects by its square, then return the quantity of
     * big objects
     *
     * @param booleanImage the boolean 2-dimensional array, where background pixel is false and object pixel is true
     * @return the quantity of silhouettes
     */
    public static int findSilhouettes(boolean[][] booleanImage) {
        ArrayList<Integer> objectsSquare = new ArrayList<>();
        int width = booleanImage.length;
        int height = booleanImage[0].length;
        double minObjSquare = width * height * SMALL_OBJECT_SQUARE_MULTIPLIER;
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                if (booleanImage[x][y]) {
                    objectsSquare.add(getObjectSquareAndRemoveIt(x, y, booleanImage));
                }
            }
        }
        return countObjectsBySquare(objectsSquare, minObjSquare);
    }

    /**
     * counts quantity of big objects by square if object square is less than minObjSquare or less than
     * maxSquare * UNCOUNTABLE_SQUARE_MULTIPLIER, it won't be counted
     *
     * @param objectsSquare array with squares of each object
     * @param minObjSquare  the minimal square that object should have to be counted
     * @return the quantity of big objects
     */
    private static int countObjectsBySquare(ArrayList<Integer> objectsSquare, double minObjSquare) {
        if (objectsSquare.size() < 2) {
            return objectsSquare.size();
        }
        int maxSquare = 0;
        for (Integer objectSquare : objectsSquare) {
            if (maxSquare < objectSquare) {
                maxSquare = objectSquare;
            }
        }
        int objectsCounter = 0;
        for (Integer objectSquare : objectsSquare) {
            if (objectSquare > maxSquare * UNCOUNTABLE_SQUARE_MULTIPLIER && objectSquare > minObjSquare) {
                objectsCounter++;
            }
        }
        return objectsCounter;
    }

    /**
     * @param args the input of program
     * @return file name if args[0] isn't empty, and default file name if is.
     */
    private static String getFileName(String[] args) {
        if (args.length == 0 || args[0].equals("")) {
            return DEFAULT_FILE_NAME;
        } else {
            return args[0];
        }
    }

    /**
     * Change edge pixels of objects to background, to split sticky object. The strength of refining is specified by
     * REFINE_EDGES_MULTIPLIER
     *
     * @param booleanImage the boolean 2-dimensional array, where background pixel is false and object pixel is true
     */
    public static void refineTheEdgesOfObjects(boolean[][] booleanImage) {
        long refiningDepth = Math.round(Math.min(booleanImage[0].length, booleanImage.length) * REFINE_EDGES_MULTIPLIER);

        int x, y;
        LinkedList<Integer[]> perimeterPixels;
        for (int i = 0; i < refiningDepth; i++) {
            perimeterPixels = getPerimeterLineOfObjects(booleanImage);
            for (Integer[] perimeterPixel : perimeterPixels) {
                x = perimeterPixel[0];
                y = perimeterPixel[1];
                booleanImage[x][y] = false;
            }
        }

    }

    /**
     * Finds edge points of objects that adjoin background
     *
     * @param booleanImage the boolean 2-dimensional array, where background pixel is false and object pixel is true
     * @return LinkedList of perimeter points represented as array where [0] is x and [1] is y
     */
    private static LinkedList<Integer[]> getPerimeterLineOfObjects(boolean[][] booleanImage) {
        LinkedList<Integer[]> perimeterLine = new LinkedList<>();
        for (int x = 0; x < booleanImage.length; x++) {
            for (int y = 0; y < booleanImage[0].length; y++) {
                if (booleanImage[x][y] && isNeighbourPixelBackground(x, y, booleanImage)) {
                    perimeterLine.add(new Integer[]{x, y});
                }
            }
        }
        return perimeterLine;
    }

    /**
     * @param x            the x coordinate pixel
     * @param y            the y coordinate pixel
     * @param booleanImage the boolean 2-dimensional array, where background pixel is false and object pixel is true
     * @return true if any adjoining pixel is background
     */
    private static boolean isNeighbourPixelBackground(int x, int y, boolean[][] booleanImage) {
        return x + 1 < booleanImage.length && !booleanImage[x + 1][y] ||
                y - 1 >= 0 && !booleanImage[x][y - 1] ||
                x - 1 >= 0 && !booleanImage[x - 1][y] ||
                y + 1 < booleanImage[0].length && !booleanImage[x][y + 1];
    }

//    private static void printImage(boolean[][] booleanImage) {
//        for (boolean[] booleans : booleanImage) {
//            for (boolean aBoolean : booleans) {
//                System.out.print(aBoolean ? "X" : " ");
//            }
//            System.out.println();
//        }
//    }
//    /**
//     * Copies 2-dimensional boolean array
//     *
//     * @param src the source array, that will be copied
//     * @return the copy of source array
//     */
//    private static boolean[][] cloneArray(boolean[][] src) {
//        boolean[][] copy = new boolean[src.length][];
//        for (int i = 0; i < src.length; i++) {
//            copy[i] = Arrays.copyOf(src[i], src[i].length);
//        }
//        return copy;
//    }
}