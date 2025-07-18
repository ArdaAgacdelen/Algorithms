import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) throws IOException {

        /** Safe-lock Opening Algorithm Below **/

        System.out.println("##Initiate Operation Safe-lock##");
        // TODO: Your code goes here
        // You are expected to read the file given as the first command-line argument to read
        // safes arriving each minute. Then, use this data to instantiate a
        // OptimalScrollSolution object. You need to call optimalSafeOpeningAlgorithm() method
        // of your MaxScrollsDP object to get the solution, and finally print it using
        // printSolution() method of OptimalScrollSolution object.

        ArrayList<ArrayList<Integer>> safes = new ArrayList<>();
        readSafesDiscovered(safes, args[0]);
        MaxScrollsDP maxScrollsDP = new MaxScrollsDP(safes);
        OptimalScrollSolution optimalScrollSolution = maxScrollsDP.optimalSafeOpeningAlgorithm();
        optimalScrollSolution.printSolution(optimalScrollSolution);

        System.out.println("##Operation Safe-lock Completed##");




        /** Operation Artifact Algorithm Below **/

        System.out.println("##Initiate Operation Artifact##");
        // TODO: Your code goes here
        // You are expected to read the file given as the second command-line argument to read
        // each artifact. Then, use this data to instantiate an OptimalShipSolution object.
        // You need to call optimalArtifactCarryingAlgorithm() method
        // of your MinShipsGP object to get the solution, and finally print it using
        // printSolution() method of OptimalShipSolution object.

        ArrayList<Integer> artifactsFound = new ArrayList<>();
        readArtifactsFound(artifactsFound, args[1]);
        MinShipsGP minShipsGP = new MinShipsGP(artifactsFound);
        OptimalShipSolution optimalShipSolution = minShipsGP.optimalArtifactCarryingAlgorithm();
        optimalShipSolution.printSolution(optimalShipSolution);

        System.out.println("##Operation Artifact Completed##");

    }

    public static void readSafesDiscovered(ArrayList<ArrayList<Integer>> safes, String filePath) throws FileNotFoundException {
        Scanner scanner = new Scanner(new File(filePath));

        try{
            int numberOfSafes = Integer.parseInt(scanner.nextLine().trim());

            for (int i = 0; i < numberOfSafes; i++) {
                String line = scanner.nextLine().trim();
                line = line.replace("[", "").replace("]", "");
                String[] parts = line.split(",");
                int requiredKnowledge = Integer.parseInt(parts[0]);
                int scrolls = Integer.parseInt(parts[1]);

                ArrayList<Integer> safe = new ArrayList<>();
                safe.add(requiredKnowledge);
                safe.add(scrolls);

                safes.add(safe);
            }
        }catch (NumberFormatException e) {
            scanner = new Scanner(new File(filePath));
            String line = scanner.nextLine().trim();
            String[] tokens = line.split(",");

            for (int i = 0; i < tokens.length - 1; i += 2) {
                int requiredKnowledge = Integer.parseInt(tokens[i].trim());
                int scrolls = Integer.parseInt(tokens[i + 1].trim());

                ArrayList<Integer> safe = new ArrayList<>();
                safe.add(requiredKnowledge);
                safe.add(scrolls);
                safes.add(safe);
            }
        }
    }

    public static void readArtifactsFound(ArrayList<Integer> artifactsFound, String filePath) throws FileNotFoundException {
        Scanner scanner = new Scanner(new File(filePath));

        String line = scanner.nextLine().trim();
        line = line.replace("[", "").replace("]", "");
        String[] parts = line.split(",");

        for (String part : parts){
            artifactsFound.add(Integer.parseInt(part));
        }
    }
}