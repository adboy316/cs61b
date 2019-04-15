package hw2;

import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {

    private int N;
    private int T;
    private PercolationFactory pf;
    private Percolation p;
    private double totalSites;

    // perform T independent experiments on an N-by-N grid
    public PercolationStats(int N, int T, PercolationFactory pf) {
        if(N <= 0 || T <= 0) throw new IllegalArgumentException();

        this.N = N;
        this.totalSites = N*N;
        this.T = T;
        this.pf = pf;

    }

    // sample mean of percolation threshold
    public double mean(){
        return StdStats.mean(getFractions());
    }

    // sample standard deviation of percolation threshold
    public double stddev(){
        return StdStats.stddev(getFractions());
    }

    // low endpoint of 95% confidence interval
    public double confidenceLow(){

        return mean() - stddev();
    }

    // high endpoint of 95% confidence interval
    public double confidenceHigh() {
        return mean() + stddev();
    }

    // Returns a list of xT Perlocation fractions.
    private double[] getFractions(){
        double[] fractions = new double[T];
        for (int i = 0; i < T; i++) {
            p = pf.make(N);
            fractions[i] = calculateFraction(p);
        }
        return  fractions;
    }

    // Calculation fraction of single Perlocation.
    private double calculateFraction(Percolation p) {
        while (!p.percolates()) {
            p.open(StdRandom.uniform(0, N), StdRandom.uniform(0, N));
        }
        return p.numberOfOpenSites()/totalSites;
    }

    // For testing

    private Percolation getP() {
        return p;
    }

}
