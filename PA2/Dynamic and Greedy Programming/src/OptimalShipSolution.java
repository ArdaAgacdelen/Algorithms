import java.util.ArrayList;

public class OptimalShipSolution {
    private final ArrayList<Integer> artifactSet;
    private final int solution;

    OptimalShipSolution(ArrayList<Integer> artifactSet, int solution) {
        this.artifactSet = artifactSet;
        this.solution = solution;
    }

    public int getSolution() {
        return solution;
    }

    public ArrayList<Integer> getArtifactSet() {
        return artifactSet;
    }

    public void printSolution(OptimalShipSolution solution) {
        System.out.printf("Minimum spaceships required: %d\n", solution.solution);
        System.out.print("For the artifact set of :" + artifactSet + "\n");

    }
}
