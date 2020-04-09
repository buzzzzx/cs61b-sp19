package hw2;

import edu.princeton.cs.introcs.StdRandom;
import edu.princeton.cs.introcs.StdStats;

public class PercolationStats {
    private int T;
    private Percolation system;
    private int sizeLength;
    private int siteCount;
    private double[] results;

    /**
     * Constructor that perform T independent experiments on an N-by-N grid.
     */
    public PercolationStats(int N, int T, PercolationFactory pf) {
        if ((N <= 0) || (T <= 0)) {
            throw new IllegalArgumentException("N or T is equal or less than 0.");
        }

        this.T = T;
        this.sizeLength = N;
        this.system = pf.make(N);
        this.siteCount = N * N;
        this.results = new double[T];

        int i;
        for (i = 0; i < T; i += 1) {
            results[i] = experiment();
        }
    }

    /**
     * Experiment that choose a site to open randomly until system percolates.
     * Experiment result is the estimate of the percolation threshold.
     * Return the result;
     */
    private double experiment() {
        int row, col;
        while (!system.percolates()) {
            row = StdRandom.uniform(0, sizeLength);
            col = StdRandom.uniform(0, sizeLength);
            system.open(row, col);
        }
        return ((double) system.numberOfOpenSites()) / ((double) siteCount);
    }

    /**
     * Return sample mean of percolation threshold.
     */
    public double mean() {
        return StdStats.mean(results);
    }

    /**
     * Return sample standard deviation of percolation threshold.
     */
    public double stddev() {
        return StdStats.stddev(results);
    }

    /**
     * Return low endpoint of 95% confidence interval.
     */
    public double confidenceLow() {
        double mu = mean();
        double sigma = stddev();
        double res = mu - (1.96 * sigma) / Math.sqrt(T);
        return res;
    }

    /**
     * Return high endpoint of 95% confidence interval.
     */
    public double confidenceHigh() {
        double mu = mean();
        double sigma = stddev();
        double res = mu + (1.96 * sigma) / Math.sqrt(T);
        return res;
    }

    public static void main(String[] args) {
        PercolationFactory pf = new PercolationFactory();
        PercolationStats pS = new PercolationStats(20, 30, pf);

        System.out.println(pS.mean());
        System.out.println(pS.stddev());
        System.out.println(pS.confidenceLow());
        System.out.println(pS.confidenceHigh());
    }
}
