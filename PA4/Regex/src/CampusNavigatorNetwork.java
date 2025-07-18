import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CampusNavigatorNetwork implements Serializable {
    static final long serialVersionUID = 11L;
    public double averageCartSpeed;
    public final double averageWalkingSpeed = 1000 / 6.0;
    public int numCartLines;
    public Station startPoint;
    public Station destinationPoint;
    public List<CartLine> lines;

    /**
     * Write the necessary Regular Expression to extract string constants from the
     * fileContent
     * 
     * @return the result as String
     */
    public String getStringVar(String varName, String fileContent) {
        // TODO: Your code goes here
        Pattern p = Pattern.compile("[\\t ]*" + varName + "[\\t ]*=[\\t ]*\"(.*)\"");
        Matcher m = p.matcher(fileContent);
        m.find();
        return m.group(1);
    }

    /**
     * Write the necessary Regular Expression to extract floating point numbers from
     * the fileContent
     * Your regular expression should support floating point numbers with an
     * arbitrary number of
     * decimals or without any (e.g. 5, 5.2, 5.02, 5.0002, etc.).
     * 
     * @return the result as Double
     */
    public Double getDoubleVar(String varName, String fileContent) {
        // TODO: Your code goes here
        Pattern p = Pattern.compile("[\\t ]*" + varName + "[\\t ]*=[\\t ]*([-+]?[0-9]+(\\.[0-9]+)?)");
        Matcher m = p.matcher(fileContent);
        m.find();
        return Double.parseDouble(m.group(1));
    }

    public int getIntVar(String varName, String fileContent) {
        Pattern p = Pattern.compile("[\\t ]*" + varName + "[\\t ]*=[\\t ]*([0-9]+)");
        Matcher m = p.matcher(fileContent);
        m.find();
        return Integer.parseInt(m.group(1));
    }

    /**
     * Write the necessary Regular Expression to extract a Point object from the
     * fileContent
     * points are given as an x and y coordinate pair surrounded by parentheses and
     * separated by a comma
     * 
     * @return the result as a Point object
     */
    public Point getPointVar(String varName, String fileContent) {
        // TODO: Your code goes here
        Pattern p = Pattern
                .compile("[\\t ]*" + varName + "[\\t ]*=[\\t ]*\\([\\t ]*([0-9]+)[\\t ]*,[\\t ]*([0-9]+)[\\t ]*\\)");
        Matcher m = p.matcher(fileContent);
        m.find();
        int x = Integer.parseInt(m.group(1));
        int y = Integer.parseInt(m.group(2));
        return new Point(x, y);
    }

    /**
     * Function to extract the cart lines from the fileContent by reading train line
     * names and their
     * respective stations.
     * 
     * @return List of CartLine instances
     */
    public List<CartLine> getCartLines(String fileContent) {

        // TODO: Your code goes here
        Pattern p1 = Pattern.compile("[\\t ]*cart_line_name[\\t ]*=[\\t ]*\"([^\"]+)\"[\\t ]*\\n"
                + "[\\t ]*cart_line_stations[\\t ]*=(([\\t ]*\\([\\t ]*[0-9]+[\\t ]*,[\\t ]*[0-9]+[\\t ]*\\))+)");
        Matcher m1 = p1.matcher(fileContent);
        List<CartLine> cartLines = new ArrayList<>();
        while (m1.find()) {
            String name = m1.group(1);
            String stationsString = m1.group(2);
            Pattern p2 = Pattern.compile("\\([\\t ]*([0-9]+)[\\t ]*,[\\t ]*([0-9]+)[\\t ]*\\)");
            Matcher m2 = p2.matcher(stationsString);
            ArrayList<Station> stations = new ArrayList<>();
            int count = 1;
            while (m2.find()) {
                int x = Integer.parseInt(m2.group(1));
                int y = Integer.parseInt(m2.group(2));
                Point p = new Point(x, y);
                stations.add(new Station(p, name + " Station " +count++));
            }
            cartLines.add(new CartLine(name, stations));
        }

        return cartLines;
    }

    /**
     * Function to populate the given instance variables of this class by calling
     * the functions above.
     */
    public void readInput(String filename) {

        // TODO: Your code goes here
        try {
            String fileContent = new String(Files.readAllBytes(Paths.get(filename)));
            this.averageCartSpeed = getDoubleVar("average_cart_speed", fileContent) * 100.0 / 6.0;
            this.numCartLines = getIntVar("num_cart_lines", fileContent);
            this.startPoint = new Station(getPointVar("starting_point", fileContent), "Starting Point");
            this.destinationPoint = new Station(getPointVar("destination_point", fileContent), "Final Destination");
            this.lines = getCartLines(fileContent);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
