package bearmaps.hw4;

import bearmaps.proj2ab.DoubleMapPQ;
import edu.princeton.cs.algs4.Stopwatch;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class AStarSolver<Vertex> implements ShortestPathsSolver<Vertex> {

    private SolverOutcome outcome;

    private List<Vertex> solution;
    private List<Vertex> alreadyVisited;

    private double solutionWeight;
    private double timeSpent;

    private int numStatesExplored;


    HashMap<Vertex, Double> distTo = new HashMap<>();
    HashMap<Vertex, Vertex> edgeTo = new HashMap<>();



    public AStarSolver(AStarGraph<Vertex> input, Vertex
            start, Vertex end, double timeout) {

        Stopwatch sw = new Stopwatch();

        solution = new ArrayList<>();
        alreadyVisited = new ArrayList<>();
        solutionWeight = 0.0;
        numStatesExplored = 0;

        DoubleMapPQ<Vertex> PQ = new DoubleMapPQ<>();
        PQ.add(start, 0);
        distTo.put(start, 0.0);


        while (PQ.size() != 0 ) {
            Vertex p = PQ.removeSmallest();

            numStatesExplored += 1;
            alreadyVisited.add(p);

            if (runOutOfTime(timeout, sw)) return;
            if (goalReached(end, start, sw, p)) return;
            relaxEdges(input, end, PQ, p);


        }
        solution.clear();
        solutionWeight = 0.0;
        outcome = SolverOutcome.UNSOLVABLE;
    }

    private void relaxEdges(AStarGraph<Vertex> input, Vertex end, DoubleMapPQ<Vertex> PQ, Vertex p) {
        for (WeightedEdge<Vertex> e : input.neighbors(p)) {

            if (!distTo.containsKey(e.to())) {
                distTo.put(e.to(), Double.POSITIVE_INFINITY);
                edgeTo.put(e.to(), e.from());

            }

            Vertex q = e.to();
            Double h = input.estimatedDistanceToGoal(q, end);
            Double w = e.weight();

            if (distTo.get(p)+ w < distTo.get(q)) {

                distTo.replace(q, distTo.get(p)+ w);

                if (PQ.contains(q)) {
                    PQ.changePriority(q, distTo.get(p) + h);
                }
                if (!PQ.contains(q)) {
                    PQ.add(q, distTo.get(q) + h);
                    distTo.put(e.to(), e.weight() + distTo.get(p));
                }
            }

        }
    }

    private boolean goalReached(Vertex end, Vertex start, Stopwatch sw, Vertex p) {
        if (p.equals(end)) {
            solution = findPath(p, start, edgeTo);
            outcome = SolverOutcome.SOLVED;
            solutionWeight = distTo.get(p);
            timeSpent =  sw.elapsedTime();
            return true;
        }
        return false;
    }

    private List<Vertex> findPath(Vertex p, Vertex start, HashMap<Vertex, Vertex> edgeTo) {

        if (p.equals(start)) {
            solution.add(start);
            Collections.reverse(solution);
            return solution;
        }

        solution.add(p);
        findPath(edgeTo.get(p), start, edgeTo);
        return solution;
    }

    private boolean runOutOfTime(double timeout, Stopwatch sw) {
        if (sw.elapsedTime() > timeout) {
            outcome = SolverOutcome.UNSOLVABLE;
            solution.clear();
            solutionWeight = 0.0;
            timeSpent =  sw.elapsedTime();
            return true;
        }
        return false;
    }


    @Override
    public SolverOutcome outcome() {
        return outcome;
    }

    @Override
    public List<Vertex> solution() {
        return solution;
    }

    @Override
    public double solutionWeight() {
        return solutionWeight;
    }

    @Override
    public int numStatesExplored() {
        return numStatesExplored;
    }

    @Override
    public double explorationTime() {
        return timeSpent;
    }
}