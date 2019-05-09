package bearmaps.hw4;

import bearmaps.proj2ab.DoubleMapPQ;
import edu.princeton.cs.algs4.Stopwatch;

import java.util.ArrayList;
import java.util.List;

public class AStarSolver<Vertex> implements ShortestPathsSolver<Vertex> {

    private SolverOutcome outcome;
    private double solutionWeight;
    private List<Vertex> solution;
    private double timeSpent;
    public int numStatesExplored;



    public AStarSolver(AStarGraph<Vertex> input, Vertex
            start, Vertex end, double timeout) {

        Stopwatch sw = new Stopwatch();

        solution = new ArrayList<>();
        solutionWeight = 0.0;
        numStatesExplored = 0;
        DoubleMapPQ<Vertex> PQ = new DoubleMapPQ<>();
        PQ.add(start, input.estimatedDistanceToGoal(start, end));


        while (PQ.size() > 0 ) {
            Vertex p = PQ.removeSmallest();
            numStatesExplored += 1;

            if (sw.elapsedTime() > timeout) {
                outcome = SolverOutcome.UNSOLVABLE;
                solution.clear();
                timeSpent =  sw.elapsedTime();
                return;
            }

            if (p.equals(end)) {
                solution.add(p);
                outcome = SolverOutcome.SOLVED;
                timeSpent =  sw.elapsedTime();
                return;
            }

            solution.add(p);

            // Relax edges
            for (WeightedEdge<Vertex> e : input.neighbors(p)) {

                if (PQ.contains(e.to())) {
//                    PQ.changePriority(e.to(), e.weight() + input.estimatedDistanceToGoal(e.to(), end));
                } else {
                    PQ.add(e.to(), e.weight() + input.estimatedDistanceToGoal(e.to(), end));
                }
            }
        }
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
