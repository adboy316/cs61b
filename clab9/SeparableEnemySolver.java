import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class SeparableEnemySolver {

    Graph g;

    /**
     * Creates a SeparableEnemySolver for a file with name filename. Enemy
     * relationships are biderectional (if A is an enemy of B, B is an enemy of A).
     */
    SeparableEnemySolver(String filename) throws java.io.FileNotFoundException {
        this.g = graphFromFile(filename);
    }

    /** Alterntive constructor that requires a Graph object. */
    SeparableEnemySolver(Graph g) {
        this.g = g;
    }

    /**
     * Returns true if input is separable, false otherwise.
     */
    public boolean isSeparable() {

        ArrayList<Integer> degrees = new ArrayList();
        Set<Integer> degreesSet = new HashSet<>();

        for (String node: g.labels()){
            degrees.add(g.neighbors(node).size());
            degreesSet.add(g.neighbors(node).size());
        }

        if (degreesSet.size() == 1) {
            return true;
        }

        int numberOfOddDegrees = 0;
        int numberOfEvenDegrees = 0;
        for (Integer n: degrees) {

            if (n % 2 > 0){
                numberOfOddDegrees += 1;
            } else {
                numberOfEvenDegrees += 1;
            }
        }

        if (numberOfOddDegrees == 2 && numberOfEvenDegrees > 2 && isConnected(g.labels().iterator().next()) ) {
            return true;
        }
        return false;
    }



    Set<String> aleadyVisited = new HashSet<>();
    int counter = 0;
    public boolean isConnected(String s) {
       aleadyVisited.add(s);
        counter += 1;

       for (String n: g.neighbors(s)) {
           if (!aleadyVisited.contains(n)) {
               isConnected(n);
           }
       }
        return counter >= g.labels().size();
    }



    /* HELPERS FOR READING IN CSV FILES. */

    /**
     * Creates graph from filename. File should be comma-separated. The first line
     * contains comma-separated names of all people. Subsequent lines each have two
     * comma-separated names of enemy pairs.
     */
    private Graph graphFromFile(String filename) throws FileNotFoundException {
        List<List<String>> lines = readCSV(filename);
        Graph input = new Graph();
        for (int i = 0; i < lines.size(); i++) {
            if (i == 0) {
                for (String name : lines.get(i)) {
                    input.addNode(name);
                }
                continue;
            }
            assert(lines.get(i).size() == 2);
            input.connect(lines.get(i).get(0), lines.get(i).get(1));
        }
        return input;
    }

    /**
     * Reads an entire CSV and returns a List of Lists. Each inner
     * List represents a line of the CSV with each comma-seperated
     * value as an entry. Assumes CSV file does not contain commas
     * except as separators.
     * Returns null if invalid filename.
     *
     * @source https://www.baeldung.com/java-csv-file-array
     */
    private List<List<String>> readCSV(String filename) throws java.io.FileNotFoundException {
        List<List<String>> records = new ArrayList<>();
        Scanner scanner = new Scanner(new File(filename));
        while (scanner.hasNextLine()) {
            records.add(getRecordFromLine(scanner.nextLine()));
        }
        return records;
    }

    /**
     * Reads one line of a CSV.
     *
     * @source https://www.baeldung.com/java-csv-file-array
     */
    private List<String> getRecordFromLine(String line) {
        List<String> values = new ArrayList<String>();
        Scanner rowScanner = new Scanner(line);
        rowScanner.useDelimiter(",");
        while (rowScanner.hasNext()) {
            values.add(rowScanner.next().trim());
        }
        return values;
    }

    /* END HELPERS  FOR READING IN CSV FILES. */

}
