import java.io.File;

public class Main {
    public static void main(String[] args) {
        // TODO: Create an instance of AlienFlora and run readGenomes. After
        // that run commands for reading and evaluating evolution and
        // adaptation pairs. File name is given in first argument.

        AlienFlora alienFlora = new AlienFlora(new File(args[0]));
        alienFlora.readGenomes();
        alienFlora.evaluateEvolutions();
        alienFlora.evaluateAdaptations();
    }
}
