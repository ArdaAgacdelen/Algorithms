import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;

public class MaxScrollsDP {
    private ArrayList<ArrayList<Integer>> safesDiscovered = new ArrayList<>();

    public MaxScrollsDP(ArrayList<ArrayList<Integer>> safesDiscovered) {
        this.safesDiscovered = safesDiscovered;
    }

    public ArrayList<ArrayList<Integer>> getSafesDiscovered() {
        return safesDiscovered;
    }

    public OptimalScrollSolution optimalSafeOpeningAlgorithm() throws FileNotFoundException {
        // Implement your dynamic programming algorithm using the equation 1
        // provided in the assignment file.

        int time = safesDiscovered.size();
        int[][] table = new int[time + 1][time + 1];
        for (int[] row : table) {
            Arrays.fill(row, -1);
        }
        table[0][0] = 0;
        for (int i = 0; i < table.length; i++) {
            table[i][i] = 0;
        }

        for (int i = 1; i < table.length; i++) {
            for (int j = 0; j < i; j++) {
                int v1 = 0, v2 = 0, v3;
                int k = 5 * j;
                if (k - 5 >= 0) v1 = table[i - 1][j-1];
                ArrayList<Integer> safe = safesDiscovered.get(i - 1);
                if ((k + safe.get(0)) / 5 <= i - 1) {
                    int x = table[i - 1][(k + safe.get(0)) / 5];
                    v2 = safe.get(1) + x;
                }
                v3 = table[i - 1][j];
                table[i][j] = Math.max(v1, Math.max(v2, v3));
            }
        }

        int max = 0;
        for (int val : table[table.length-1]){
            max = Math.max(max, val);
        }

        return new OptimalScrollSolution(safesDiscovered, max);
    }
}
