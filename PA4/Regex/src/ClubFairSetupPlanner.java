import java.io.File;
import java.io.Serializable;
import java.util.*;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

public class ClubFairSetupPlanner implements Serializable {
    static final long serialVersionUID = 88L;

    /**
     * Given a list of Project objects, prints the schedule of each of them.
     * Uses getEarliestSchedule() and printSchedule() methods of the current project
     * to print its schedule.
     * 
     * @param projectList a list of Project objects
     */
    public void printSchedule(List<Project> projectList) {
        // TODO: YOUR CODE HERE
        for (Project project : projectList) {
            project.printSchedule(project.getEarliestSchedule());
        }
    }

    /**
     * TODO: Parse the input XML file and return a list of Project objects
     *
     * @param filename the input XML file
     * @return a list of Project objects
     */
    public List<Project> readXML(String filename) {
        List<Project> projectList = new ArrayList<>();
        // TODO: YOUR CODE HERE

        try {
            File xmlFile = new File(filename);
            DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
            Document doc = docBuilder.parse(xmlFile);
            doc.getDocumentElement().normalize();

            NodeList projectNodes = doc.getElementsByTagName("Project");
            for (int i = 0; i < projectNodes.getLength(); i++) {
                Element projectElement = (Element) projectNodes.item(i);
                String name = projectElement.getElementsByTagName("Name").item(0).getTextContent();

                List<Task> tasks = new ArrayList<>();
                NodeList taskNodes = projectElement.getElementsByTagName("Task");

                for (int j = 0; j < taskNodes.getLength(); j++) {
                    Element taskElement = (Element) taskNodes.item(j);
                    int taskID = Integer.parseInt(taskElement.getElementsByTagName("TaskID").item(0).getTextContent());
                    String description = taskElement.getElementsByTagName("Description").item(0).getTextContent();
                    int duration = Integer
                            .parseInt(taskElement.getElementsByTagName("Duration").item(0).getTextContent());

                    List<Integer> dependencies = new ArrayList<>();
                    NodeList dependencyNodes = taskElement.getElementsByTagName("DependsOnTaskID");
                    for (int k = 0; k < dependencyNodes.getLength(); k++) {
                        dependencies.add(Integer.valueOf(dependencyNodes.item(k).getTextContent()));
                    }

                    tasks.add(new Task(taskID, description, duration, dependencies));
                }

                projectList.add(new Project(name, tasks));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return projectList;
    }

}