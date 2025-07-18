import java.util.List;
import java.util.Locale;

public class Main {
    public static void main(String[] args) {

        // Set the default locale to English
        Locale locale = new Locale("en", "EN");
        Locale.setDefault(locale);

        System.out.println("### CLUB FAIR SETUP START ###");
        ClubFairSetupPlanner planner = new ClubFairSetupPlanner();
        List<Project> projectList = planner.readXML(args[0]);
        planner.printSchedule(projectList);
        System.out.println("### CLUB FAIR SETUP END ###");

        System.out.println("### CAMPUS NAVIGATOR START ###");
        CampusNavigatorApp navigatorApp = new CampusNavigatorApp();
        CampusNavigatorNetwork network = navigatorApp.readCampusNavigatorNetwork(args[1]);
        List<RouteDirection> directions = navigatorApp.getFastestRouteDirections(network);
        navigatorApp.printRouteDirections(directions);
        System.out.println("### CAMPUS NAVIGATOR END ###");
    }
}
