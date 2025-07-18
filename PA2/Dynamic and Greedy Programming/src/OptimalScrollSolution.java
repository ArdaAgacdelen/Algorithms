import java.util.ArrayList;

public class OptimalScrollSolution {
    private final ArrayList<ArrayList<Integer>> safeSet;
    private final int solution;

    OptimalScrollSolution(ArrayList<ArrayList<Integer>> safeSet, int solution) {
        this.safeSet = safeSet;
        this.solution = solution;
    }

    public int getSolution() {
        return solution;
    }

    public ArrayList<ArrayList<Integer>> getSafeSet() {
        return safeSet;
    }

    public void printSolution(OptimalScrollSolution solution) {
        // Print your OptimalScrollSolution object in the format provided in the assignment pdf.
        System.out.printf("Maximum scrolls acquired: %d\n", solution.solution);
        System.out.print("For the safe set of :" + solution.safeSet + "\n");
    }
}
