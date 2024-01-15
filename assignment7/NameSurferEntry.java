package assignment7;


import java.util.Arrays;

public class NameSurferEntry implements NameSurferConstants {


    private String name;
    private int[] ranks;

    /**
     * Creates a new NameSurferEntry from a data line as it appears
     * in the data file.  Each line begins with the name, which is
     * followed by integers giving the rank of that name for each
     * decade.
     */
    public NameSurferEntry(String line) {
        try {
            String[] items = line.split(FILE_FIELDS_SPLITTER);
            name = items[0];
            //creating and writing ranks arr
            ranks = new int[NDECADES];
            for (int i = 0; i < NDECADES; i++) {
                ranks[i] = Integer.parseInt(items[i + 1]);
            }
        } catch (Exception e) {
            throw new RuntimeException("Line is incorrect");
        }

    }


    /**
     * Returns the name associated with this entry.
     */
    public String getName() {
        return name;
    }


    /**
     * Returns the rank associated with an entry for a particular
     * decade.  The decade value is an integer indicating how many
     * decades have passed since the first year in the database,
     * which is given by the constant START_DECADE.  If a name does
     * not appear in a decade, the rank value is 0.
     */
    public int getRank(int decade) {
        if (decade >= 0 && decade < NDECADES)
            return ranks[decade];
        return 0;
    }


    /**
     * Returns a string that makes it easy to see the value of a
     * NameSurferEntry.
     */
    public String toString() {
        return name + " " + Arrays.toString(ranks);
    }
}

