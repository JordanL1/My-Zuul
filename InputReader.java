import java.util.HashSet;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * InputReader reads typed text input from the standard text terminal. 
 * The text typed by a user is then chopped into words, and a set of words 
 * is provided.
 * 
 * @author     Tony Beaumont, (modified from Michael Kolling and David J. Barnes)
 * @version    1.0
 */
public class InputReader
{
    private Scanner reader;

    /**
     * Create a new InputReader that reads text from the file with the given name.
     */
    public InputReader(String fname) {
        try {
            reader = new Scanner(new File(fname));
        } catch (FileNotFoundException ex) {
            Logger.getLogger(InputReader.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Read a line of text from the input (a CSV file),
     * and return it as an array of String data values.
     *
     * @return  An array of Strings, where each String is one of the 
     *          fields in the input.
     */
    public String[] getInput() 
    {
        String[] input = reader.nextLine().trim().split(",");
        return input;
    }
}
