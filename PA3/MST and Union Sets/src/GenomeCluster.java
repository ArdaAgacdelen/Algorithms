import java.util.*;

public class GenomeCluster {
    public Map<String, Genome> genomeMap = new HashMap<>();

    public void addGenome(Genome genome) {
        // TODO: Add genome to the cluster
        genomeMap.put(genome.id, genome);
    }

    public boolean contains(String genomeId) {
        // TODO: Return true if the genome is in the cluster
        return genomeMap.containsKey(genomeId);
    }

    public Genome getMinEvolutionGenome() {
        // TODO: Return the genome with minimum evolutionFactor
        Genome min = null;
        for (Genome genome: genomeMap.values()){
            if (min != null){
                if (min.evolutionFactor > genome.evolutionFactor) min = genome;
            }else min = genome;

        }
        return min;
    }

    public int dijkstra(String startId, String endId) {
        // TODO: Implement Dijkstra's algorithm to return shortest path
        if (startId.equals(endId)) return 0;

        PriorityQueue<Node> priorityQueue = new PriorityQueue<>();
        priorityQueue.add(new Node(startId, 0));
        HashMap<String, Boolean> visited = new HashMap<>();
        for (String genome: genomeMap.keySet()) visited.put(genome, false);

        while (!priorityQueue.isEmpty()){
            Node top = priorityQueue.poll();
            if (visited.get(top.genome)) continue;
            visited.put(top.genome, true);
            if (top.genome.equals(endId)) return top.cost;

            for (Genome.Link link : genomeMap.get(top.genome).links){
                if (!visited.get(link.target)) priorityQueue.add(new Node(link.target, link.adaptationFactor + top.cost));
            }
        }
        return -1;
    }

    @Override
    public String toString() {
        return genomeMap.keySet().toString();
    }

    class Node implements Comparable<Node>{
        private String genome;
        private int cost;

        public Node(String genome, int cost) {
            this.genome = genome;
            this.cost = cost;
        }

        @Override
        public int compareTo(Node o) {
            return Integer.compare(this.cost, o.cost);
        }
    }
}
