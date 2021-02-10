package main;


import java.util.*;

public class Dijkstra {

//    private final List<Airport> nodes;
    private final List<AirportConnection> edges;
    private Set<Airport> settledNodes;
    private Set<Airport> unSettledNodes;
    private Map<Airport, Airport> predecessors;
    private Map<Airport, Integer> distance;

    public Dijkstra(ArrayList<AirportConnection> edges) {
        // create a copy of the array so that we can operate on this array
//        this.nodes = new ArrayList<Airport>(map.getCivilianAirports().getElements() + map.getMilitaryAirports().getElements());
        this.edges = new ArrayList<AirportConnection>(edges);
    }

    public void execute(Airport source) {
        settledNodes = new HashSet<Airport>();
        unSettledNodes = new HashSet<Airport>();
        distance = new HashMap<Airport, Integer>();
        predecessors = new HashMap<Airport, Airport>();
        distance.put(source, 0);
        unSettledNodes.add(source);
        while (unSettledNodes.size() > 0) {
            Airport node = getMinimum(unSettledNodes);
            settledNodes.add(node);
            unSettledNodes.remove(node);
            findMinimalDistances(node);
        }
    }

    private void findMinimalDistances(Airport node) {
        List<Airport> adjacentNodes = getNeighbors(node);
        for (Airport target : adjacentNodes) {
            if (getShortestDistance(target) > getShortestDistance(node)
                    + getDistance(node, target)) {
                distance.put(target, getShortestDistance(node)
                        + getDistance(node, target));
                predecessors.put(target, node);
                unSettledNodes.add(target);
            }
        }

    }

    private int getDistance(Airport node, Airport target) {
        for (AirportConnection edge : edges) {
            if (edge.getSource().equals(node)
                    && edge.getDestination().equals(target)) {
                return (int)edge.getLength();
            }
        }
        throw new RuntimeException("Should not happen");
    }

    private List<Airport> getNeighbors(Airport node) {
        List<Airport> neighbors = new ArrayList<Airport>();
        for (AirportConnection edge : edges) {
            if (edge.getSource().equals(node)
                    && !isSettled(edge.getDestination())) {
                neighbors.add(edge.getDestination());
            }
        }
        return neighbors;
    }

    private Airport getMinimum(Set<Airport> vertexes) {
        Airport minimum = null;
        for (Airport vertex : vertexes) {
            if (minimum == null) {
                minimum = vertex;
            } else {
                if (getShortestDistance(vertex) < getShortestDistance(minimum)) {
                    minimum = vertex;
                }
            }
        }
        return minimum;
    }

    private boolean isSettled(Airport vertex) {
        return settledNodes.contains(vertex);
    }

    private int getShortestDistance(Airport destination) {
        Integer d = distance.get(destination);
        return Objects.requireNonNullElse(d, Integer.MAX_VALUE);
    }

    /*
     * This method returns the path from the source to the selected target and
     * NULL if no path exists
     */
    public LinkedList<Airport> getPath(Airport target) {
        LinkedList<Airport> path = new LinkedList<Airport>();
        Airport step = target;
        // check if a path exists
        if (predecessors.get(step) == null) {
            return null;
        }
        path.add(step);
        while (predecessors.get(step) != null) {
            step = predecessors.get(step);
            path.add(step);
        }
        // Put it into the correct order
        Collections.reverse(path);
        return path;
    }

}