package assignment6_MyCodeIsOnlyInLogicFiles.toneMatrix;

public class ToneMatrixLogic {
    /**
     * Given the contents of the tone matrix, returns a string of notes that should be played
     * to represent that matrix.
     *
     * @param toneMatrix The contents of the tone matrix.
     * @param column     The column number that is currently being played.
     * @param samples    The sound samples associated with each row.
     * @return A sound sample corresponding to all notes currently being played.
     */
    public static double[] matrixToMusic(boolean[][] toneMatrix, int column, double[][] samples) {
        double[] result = new double[ToneMatrixConstants.sampleSize()];
        boolean normalizationNeeded=false;
        for (int i = 0; i < toneMatrix.length; i++) {
            if (toneMatrix[i][column]) {
                for (int j = 0; j < samples[i].length; j++) {
                    result[j] += samples[i][j];
                }
                normalizationNeeded = true;
            }
        }
        if(normalizationNeeded){
            normalizeSoundArr(result);
        }
        return result;
    }

    /**
     * Normalizes sound array, in edges {-1;1} using dividing on highest item in array
     * @param soundArray the array that should be normalized
     */
    private static void normalizeSoundArr(double[] soundArray) {
        double maxSound = findMaxSound(soundArray);
        for (int i = 0; i < soundArray.length; i++) {
            soundArray[i]= soundArray[i]/maxSound;
        }
    }

    /**
     * Finds the highest sound in the sound array
     * @param soundArr Array with the sounds
     * @return the highest sound
     */
    private static double findMaxSound(double[] soundArr) {
        double max = soundArr[0];
        for (double sound : soundArr) {
            if (Math.abs(max) < Math.abs(sound)) {
                max = sound;
            }
        }
        return max;
    }

}
