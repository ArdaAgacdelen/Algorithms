import org.w3c.dom.*;
import javax.xml.parsers.*;
import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

public class AlienFlora {
    private File xmlFile;
    private Vector<GenomeCluster> clusters = new Vector<>();
    private HashMap<String, Genome> genomes = new HashMap<>();
    Document doc;

    public AlienFlora(File xmlFile) {
        this.xmlFile = xmlFile;

        try {
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = dbf.newDocumentBuilder();
            this.doc = docBuilder.parse(xmlFile);
            doc.getDocumentElement().normalize();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void readGenomes() {
        // TODO:
        // - Parse XML
        // - Read genomes and links
        // - Create clusters
        // - Print number of clusters and their genome IDs
        NodeList genomeEls = doc.getElementsByTagName("genome");

        for (int i = 0; i < genomeEls.getLength(); i++) {

            Element elt = (Element) genomeEls.item(i);
            String id = elt.getElementsByTagName("id").item(0).getTextContent();
            int evolutionFactor = Integer.parseInt(elt.getElementsByTagName("evolutionFactor").item(0).getTextContent());

            Genome genome;
            if (!genomes.containsKey(id)) {
                genome = new Genome(id, evolutionFactor);
                genomes.put(id, genome);
            } else {
                genome = genomes.get(id);
                genome.evolutionFactor = evolutionFactor;
            }

            NodeList links = elt.getElementsByTagName("link");
            for (int j = 0; j < links.getLength(); j++) {

                Element link = (Element) links.item(j);
                String target = link.getElementsByTagName("target").item(0).getTextContent();
                int adaptationFactor = Integer.parseInt(link.getElementsByTagName("adaptationFactor").item(0).getTextContent());

                genome.addLink(target, adaptationFactor);

                if (!genomes.containsKey(target)) {
                    Genome neighbor = new Genome(target);
                    neighbor.addLink(id, adaptationFactor);
                    genomes.put(target, neighbor);
                }
            }
        }


        clusterGenomes();
        printClusters();
    }

    private void clusterGenomes() {
        HashMap<String, Boolean> visited = new HashMap<>();
        for (String genome : genomes.keySet()) {
            visited.put(genome, false);
        }

        for (String genome : genomes.keySet()) {
            if (!visited.get(genome)) {
                GenomeCluster cluster = new GenomeCluster();
                dfs(genome, visited, cluster);
                clusters.add(cluster);
            }
        }
    }

    private void dfs(String id, Map<String, Boolean> visited, GenomeCluster cluster) {
        Genome genome = genomes.get(id);
        visited.put(id, true);
        cluster.addGenome(genome);

        for (Genome.Link link : genome.links) {
            String neighbor = link.target;
            if (!visited.get(neighbor)) {
                dfs(neighbor, visited, cluster);
            }
        }
    }

    private void printClusters() {
        System.out.printf("##Start Reading Flora Genomes##\n" +
                        "Number of Genome Clusters: %d\n" +
                        "For the Genomes: %s\n" +
                        "##Reading Flora Genomes Completed##\n",
                clusters.size(), clusters);
    }

    public void evaluateEvolutions() {
        // TODO:
        // - Parse and process possibleEvolutionPairs
        // - Find min evolution genome in each cluster
        // - Calculate and print evolution factors
        int possibleEvolutions = 0;
        int certifiedEvolutions = 0;
        Vector<Float> evolutionFactors = new Vector<>();

        NodeList pairList = doc.getElementsByTagName("possibleEvolutionPairs").item(0).getChildNodes();
        for (int i = 0; i < pairList.getLength(); i++) {

            if (pairList.item(i).getNodeType() == Node.ELEMENT_NODE) {
                Element pairElement = (Element) pairList.item(i);

                String firstId = pairElement.getElementsByTagName("firstId").item(0).getTextContent();
                String secondId = pairElement.getElementsByTagName("secondId").item(0).getTextContent();

                possibleEvolutions++;

                for (GenomeCluster cluster0 : clusters) {
                    if (!cluster0.contains(firstId)) continue;
                    if (cluster0.contains(secondId)) {
                        evolutionFactors.add(-1f);
                        break;
                    }
                    for (GenomeCluster cluster1 : clusters) {
                        if (!cluster1.contains(secondId)) continue;
                        Genome min0 = cluster0.getMinEvolutionGenome();
                        Genome min1 = cluster1.getMinEvolutionGenome();
                        evolutionFactors.add((float) (min0.evolutionFactor + min1.evolutionFactor) / 2);
                        certifiedEvolutions++;
                    }
                }
            }
        }
        printEvolutions(possibleEvolutions, certifiedEvolutions, evolutionFactors);
    }

    private void printEvolutions(int possibleEvolutions, int certifiedEvolutions, Vector<Float> evolutionFactors) {
        System.out.printf("##Start Evaluating Possible Evolutions##\n" +
                        "Number of Possible Evolutions: %d\n" +
                        "Number of Certified Evolution: %d\n" +
                        "Evolution Factor for Each Evolution Pair: %s\n" +
                        "##Evaluated Possible Evolutions##\n",
                possibleEvolutions, certifiedEvolutions, evolutionFactors);
    }

    public void evaluateAdaptations() {
        // TODO:
        // - Parse and process possibleAdaptationPairs
        // - If genomes in same cluster, use Dijkstra to calculate min path
        // - Print adaptation factors
        int possibleAdaptations = 0;
        int certifiedAdaptations = 0;
        Vector<Integer> adaptationFactors = new Vector<>();

        NodeList pairList = doc.getElementsByTagName("possibleAdaptationPairs").item(0).getChildNodes();
        for (int i = 0; i < pairList.getLength(); i++) {

            if (pairList.item(i).getNodeType() == Node.ELEMENT_NODE) {
                Element pairElement = (Element) pairList.item(i);

                String firstId = pairElement.getElementsByTagName("firstId").item(0).getTextContent();
                String secondId = pairElement.getElementsByTagName("secondId").item(0).getTextContent();

                possibleAdaptations++;

                for (GenomeCluster cluster : clusters){
                    if (cluster.contains(firstId)){
                        if (!cluster.contains(secondId)) {
                            adaptationFactors.add(-1);
                            break;
                        }
                        certifiedAdaptations++;
                        adaptationFactors.add(cluster.dijkstra(firstId, secondId));
                    }
                }
            }
        }
        printAdaptations(possibleAdaptations, certifiedAdaptations, adaptationFactors);
    }
    private void printAdaptations(int possibleAdaptations, int certifiedAdaptations, Vector<Integer> evolutionAdaptations) {
        System.out.printf("##Start Evaluating Possible Adaptations##\n" +
                        "Number of Possible Adaptations: %d\n" +
                        "Number of Certified Adaptations: %d\n" +
                        "Adaptation Factor for Each Adaptation Pair: %s\n" +
                        "##Evaluated Possible Adaptations##",
                possibleAdaptations, certifiedAdaptations, evolutionAdaptations);
    }
}
