package assignment7;

/**
 * This class keeps track of the complete database of names.
 * The constructor reads in the database from a file, and
 * the only public method makes it possible to look up a
 * name and get back the corresponding NameSurferEntry.
 * Names are matched independent of case, so that "Eric"
 * and "ERIC" are the same names.
 */

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

public class NameSurferDataBase implements NameSurferConstants {

    private HashMap<String, NameSurferEntry> namesMap;

    /**
     * Creates a new NameSurferDataBase and initializes it using the
     * data in the specified file.  The constructor throws an error
     * exception if the requested file does not exist or if an error
     * occurs as the file is being read.
     */
    public NameSurferDataBase(String filename) {
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(filename))) {
            namesMap = new HashMap<>();
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                try {
                    NameSurferEntry entry = new NameSurferEntry(line);
                    namesMap.put(entry.getName().toLowerCase(), entry);
                } catch (RuntimeException e) {// exception when file has wrong line
                    System.out.println(e.getMessage());
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("There is problem with file");
        }
    }


    /**
     * Returns the NameSurferEntry associated with this name, if one
     * exists.  If the name does not appear in the database, this
     * method returns null.
     */
    public NameSurferEntry findEntry(String name) {
        return namesMap.get(name.toLowerCase());
    }
}

