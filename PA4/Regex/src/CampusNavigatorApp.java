import java.io.Serializable;
import java.util.*;

public class CampusNavigatorApp implements Serializable {
    static final long serialVersionUID = 99L;

    public HashMap<Station, Station> predecessors = new HashMap<>();
    public HashMap<Set<Station>, Double> times = new HashMap<>();
    public HashMap<Station, Boolean> cart = new HashMap<>();

    public CampusNavigatorNetwork readCampusNavigatorNetwork(String filename) {
        CampusNavigatorNetwork network = new CampusNavigatorNetwork();
        network.readInput(filename);
        return network;
    }

    /**
     * Calculates the fastest route from the user's selected starting point to
     * the desired destination, using the campus golf cart network and walking
     * paths.
     * 
     * @return List of RouteDirection instances
     */
    public List<RouteDirection> getFastestRouteDirections(CampusNavigatorNetwork network) {
        List<RouteDirection> routeDirections = new ArrayList<>();

        PriorityQueue<StationNode> pq = new PriorityQueue<>();
        StationNode startNode = new StationNode(0, null, network.startPoint, false);
        pq.add(startNode);

        while (!pq.isEmpty()) {
            StationNode currentNode = pq.poll();
            Station currentStation = currentNode.getStation();
            if (predecessors.containsKey(currentStation)) {
                continue;
            }
            Station prevStation = currentNode.getPrevStation();

            predecessors.put(currentStation, prevStation);
            cart.put(currentStation, currentNode.isCart());

            HashSet<Station> edge = new HashSet<>();
            edge.add(prevStation);
            edge.add(currentStation);
            double speed = (currentNode.isCart()) ? network.averageCartSpeed : network.averageWalkingSpeed;
            double edgeCost = getEdgeCost(prevStation, currentStation, speed);
            times.put(edge, edgeCost);
            if (currentStation.equals(network.destinationPoint)) {
                break;
            }

            for (CartLine cartLine : network.lines) {
                List<Station> stations = cartLine.cartLineStations;
                for (int i = 0; i < stations.size(); i++) {
                    Station tmp = stations.get(i);
                    if (tmp.equals(currentStation)) {
                        if (i > 0) {
                            Station neighbor = stations.get(i - 1);
                            if (!predecessors.containsKey(neighbor)) {
                                edgeCost = getEdgeCost(tmp, neighbor, network.averageCartSpeed);
                                double remainingDistance = getDistance(neighbor, network.destinationPoint);
                                double estimation = estimateRemainingTime(remainingDistance, network.averageCartSpeed);
                                double cost = calculatePathCostOfStation(currentNode.getPathCost(), edgeCost,
                                        estimation);
                                pq.add(new StationNode(cost, currentStation, neighbor, true));
                            }
                        }
                        if (i < stations.size() - 1) {
                            Station neighbor = stations.get(i + 1);
                            if (!predecessors.containsKey(neighbor)) {
                                edgeCost = getEdgeCost(tmp, neighbor, network.averageCartSpeed);
                                double remainingDistance = getDistance(neighbor, network.destinationPoint);
                                double estimation = estimateRemainingTime(remainingDistance, network.averageCartSpeed);
                                double cost = calculatePathCostOfStation(currentNode.getPathCost(), edgeCost,
                                        estimation);
                                pq.add(new StationNode(cost, currentStation, neighbor, true));
                            }
                        }
                    } else {
                        if (!predecessors.containsKey(tmp)) {
                            edgeCost = getEdgeCost(tmp, currentStation, network.averageWalkingSpeed);
                            double remainingDistance = getDistance(tmp, network.destinationPoint);
                            double estimation = estimateRemainingTime(remainingDistance, network.averageCartSpeed);
                            double cost = calculatePathCostOfStation(currentNode.getPathCost(), edgeCost,
                                    estimation);
                            pq.add(new StationNode(cost, currentStation, tmp, false));
                        }
                    }
                }
            }
            if (!predecessors.containsKey(network.destinationPoint)) {
                edgeCost = getEdgeCost(network.destinationPoint, currentStation, network.averageWalkingSpeed);
                double remainingDistance = getDistance(network.destinationPoint, network.destinationPoint);
                double estimation = estimateRemainingTime(remainingDistance, network.averageCartSpeed);
                double cost = calculatePathCostOfStation(currentNode.getPathCost(), edgeCost,
                        estimation);
                pq.add(new StationNode(cost, currentStation, network.destinationPoint, false));
            }
        }

        Station tmp = network.destinationPoint;
        while (tmp != null && predecessors.containsKey(tmp)) {
            Station current = tmp;
            Station prev = predecessors.get(tmp);
            if (prev == null) {
                break;
            }
            HashSet<Station> set = new HashSet<>();
            set.add(prev);
            set.add(tmp);
            double edgeCost = times.get(set);
            routeDirections.add(0, new RouteDirection(prev.toString(), current.toString(), edgeCost, cart.get(tmp)));
            tmp = prev;
        }

        return routeDirections;
    }

    private static double getDistance(Station s1, Station s2) {
        // return Math.abs(s1.coordinates.x - s2.coordinates.x)
        // + Math.abs(s1.coordinates.y - s2.coordinates.y);
        return Math.sqrt(Math.pow((s1.coordinates.x - s2.coordinates.x), 2)
                + Math.pow((s1.coordinates.y - s2.coordinates.y), 2));
    }

    private static double getEdgeCost(Station s1, Station s2, double speed) {
        if (s1 == null || s2 == null) {
            return 0d;
        }
        return getDistance(s1, s2) / speed;
    }

    private static double estimateRemainingTime(double remainingDistance, double averageCartSpeed) {
        return remainingDistance / averageCartSpeed;
    }

    private static double calculatePathCostOfStation(double prevCost, double edgeCost, double estimation) {
        return prevCost + edgeCost + estimation;

    }

    /**
     * Function to print the route directions to STDOUT
     */
    public void printRouteDirections(List<RouteDirection> directions) {

        // TODO: Your code goes here

        double sum = 0;
        for (RouteDirection routeDirection : directions) {
            sum += routeDirection.duration;
        }

        System.out.printf("The fastest route takes %d minute(s).\nDirections\n----------\n", (int) Math.round(sum));

        int count = 1;
        for (RouteDirection routeDirection : directions) {
            System.out.print(count++ + ". ");
            System.out.println(routeDirection);
        }
    }
}