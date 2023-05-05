import java.io.File;
import java.io.PrintWriter;

/**
 * Result write class for back tracking algorithm
 */
public class ResultWriter {
    PrintWriter pw;

    /**
     * Constructor
     * 
     * @param nameIn
     */
    public ResultWriter(String nameIn) {
        try {
            pw = new PrintWriter(new File(nameIn));
        } catch (Exception e) {
            System.err.println("Could not find entries file");
            System.exit(-1);
        }
    }

    /**
     * Write to the file
     * 
     * @param s
     */
    public void write(String s) {
        pw.println(s);
    }

    /**
     * Close the writer
     */
    public void close() {
        pw.close();
    }
}
