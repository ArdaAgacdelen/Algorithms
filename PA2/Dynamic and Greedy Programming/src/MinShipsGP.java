import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;

public class MinShipsGP {
    private final ArrayList<Integer> artifactsFound = new ArrayList<>();
    public ArrayList<Integer> getArtifactsFound() {
        return artifactsFound;
    }

    MinShipsGP(ArrayList<Integer> artifactsFound) {
        this.artifactsFound.addAll(artifactsFound);
    }

    public OptimalShipSolution optimalArtifactCarryingAlgorithm() throws FileNotFoundException {
        // Implement your greedy programming algorithm using the equation 2
        // provided in the assignment file.

        ArrayList<Integer> artifactsSorted = new ArrayList<>(artifactsFound);
        artifactsSorted.sort(Collections.reverseOrder());
        ArrayList<Integer> shipRemainingCapacities = new ArrayList<>();

        for (Integer artifact : artifactsSorted) {
            boolean loaded = false;

            for (int i = 0; i < shipRemainingCapacities.size(); i++) {
                if (shipRemainingCapacities.get(i) >= artifact) {
                    shipRemainingCapacities.set(i, shipRemainingCapacities.get(i) - artifact);
                    loaded = true;
                    break;
                }
            }

            if (!loaded) {
                int newShipCapacity = 100 - artifact;
                shipRemainingCapacities.add(newShipCapacity);
            }
        }

        return new OptimalShipSolution(artifactsFound, shipRemainingCapacities.size());
    }
}
