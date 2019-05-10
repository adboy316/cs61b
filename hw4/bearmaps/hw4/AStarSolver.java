package bearmaps.hw4;

import bearmaps.proj2ab.DoubleMapPQ;
import edu.princeton.cs.algs4.Stopwatch;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class AStarSolver<Vertex> implements ShortestPathsSolver<Vertex> {

    private SolverOutcome outcome;

    private List<Vertex> solution;
    private List<Vertex> alreadyVisited;

    private double solutionWeight;
    private double timeSpent;

    private int numStatesExplored;

    private double estimate = 0;

    HashMap<Vertex, Double> distTo = new HashMap<>();



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
            //solutionWeight += estimate;

            if (runOutOfTime(timeout, sw)) return;
            if (goalReached(end, sw, p)) return;

            solution.add(p);

            //estimate = Double.POSITIVE_INFINITY;
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
            }

            Double h = input.estimatedDistanceToGoal(e.to(), end);
            Vertex q = e.to();
            Vertex p2 = e.from();
            Double w = e.weight();
            Double distToP = distTo.get(p);
            Double distToQ = distTo.get(q);

            if (distTo.get(p)+ w < distTo.get(q)) {
                //distToQ = distTo.get(p)+ w;
                distTo.replace(q, distToQ, distTo.get(p)+ w);

                if (PQ.contains(q)) {
                    PQ.changePriority(q, distTo.get(p) + h);
                } else {
                    PQ.add(q, distTo.get(q) + h);
                    alreadyVisited.add(e.to());
                    distTo.put(e.to(), e.weight() + distTo.get(e.from()) );
                }
            }

        }
    }

    private boolean goalReached(Vertex end, Stopwatch sw, Vertex p) {
        if (p.equals(end)) {
            solution.add(p);
            outcome = SolverOutcome.SOLVED;
            solutionWeight = distTo.get(p);
            timeSpent =  sw.elapsedTime();
            return true;
        }
        return false;
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