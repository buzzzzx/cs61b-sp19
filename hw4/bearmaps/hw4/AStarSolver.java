package bearmaps.hw4;

import bearmaps.proj2ab.ArrayHeapMinPQ;
import edu.princeton.cs.algs4.Stopwatch;

import java.util.*;

public class AStarSolver<Vertex> implements ShortestPathsSolver {

    private Vertex end;
    private SolverOutcome outcome;
    private LinkedList<Vertex> solution;
    private int numStatesExplored;
    private double timeSpent;

    private ArrayHeapMinPQ<Vertex> PQ;
    private Map<Vertex, Double> distTo;
    private Map<Vertex, Vertex> edgeTo;

    private final double INF = Double.POSITIVE_INFINITY;

    /**
     * Constructor which finds the solution
     * computing everything necessary for all other methods
     * to return their results in constant time.
     * @param timeout: seconds.
     */
    public AStarSolver(AStarGraph<Vertex> input, Vertex start, Vertex end, double timeout) {

        this.end = end;
        solution = new LinkedList<>();  // according to edgeTo to find best path.
        numStatesExplored = 0;
        timeSpent = 0;
        distTo = new HashMap<>();
        edgeTo = new HashMap<>();
        PQ = new ArrayHeapMinPQ<>();

        Stopwatch sw = new Stopwatch();
        // initial distTo
        distTo.put(start, 0.0);
        PQ.add(start, distTo.get(start) + input.estimatedDistanceToGoal(start, end));

        while (!PQ.isEmpty()) {

            // check if reach the goal.
            if (PQ.getSmallest().equals(end)) {
                outcome = SolverOutcome.SOLVED;

                Vertex currVertex = PQ.getSmallest();  // which is end.
                solution.addFirst(currVertex);
                while (!currVertex.equals(start)) {
                    solution.addFirst(edgeTo.get(currVertex));
                    currVertex = edgeTo.get(currVertex);
                }

                return;
            }

            Vertex p = PQ.removeSmallest();
            numStatesExplored += 1;

            // check time spent.
            timeSpent = sw.elapsedTime();
            if (timeout < timeSpent) {
                outcome = SolverOutcome.TIMEOUT;

                return;
            }

            // relax all edges outgoing from p.
            for (WeightedEdge<Vertex> edge : input.neighbors(p)) {
                Vertex q = edge.to();
                if (!distTo.containsKey(q)) {
                    distTo.put(q, INF);
                }

                double weight = edge.weight();

                if (distTo.get(p) + weight < distTo.get(q)) {
                    distTo.put(q, distTo.get(p) + weight);
                    edgeTo.put(q, p);  // update.

                    if (PQ.contains(q)) {
                        PQ.changePriority(q, distTo.get(q) + input.estimatedDistanceToGoal(q, end));
                    } else {
                        PQ.add(q, distTo.get(q) + input.estimatedDistanceToGoal(q, end));
                    }
                }
            }
        }

        outcome = SolverOutcome.UNSOLVABLE;
        timeSpent = sw.elapsedTime();

    }

    public SolverOutcome outcome() {
        return outcome;
    }

    public List<Vertex> solution() {
        return solution;
    }

    public double solutionWeight() {
        if (outcome == SolverOutcome.UNSOLVABLE || outcome == SolverOutcome.TIMEOUT) {
            return 0;
        }
        return distTo.get(end);
    }

    public int numStatesExplored() {
        return numStatesExplored;
    }

    public double explorationTime() {
        return timeSpent;
    }
}
