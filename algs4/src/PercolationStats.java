

import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

/**
 *
 * @author osvaldo
 */
public class PercolationStats {

    private double[] results;
    private int trials;

    public PercolationStats(int n, int trials) {
        if (n < 1 || trials < 1) {
            throw new java.lang.IllegalArgumentException();
        }

        results = new double[trials];
        this.trials = trials;

        for (int trial = 0; trial < trials; trial++) {
            Percolation p = new Percolation(n);
            while (!p.percolates()) {
                int rrow, rcol;
                do {
                    rrow = StdRandom.uniform(0, n) + 1;
                    rcol = StdRandom.uniform(0, n) + 1;
                } while (p.isOpen(rrow, rcol));
                p.open(rrow, rcol);
            }
            results[trial] = ((double) p.numberOfOpenSites()) / (n * n);
        }

    }

    public double mean() {
        return StdStats.mean(results);
    }

    public double stddev() {
        return StdStats.stddev(results);
    }

    public double confidenceLo() {
        return this.mean() - 1.96 * this.stddev() / Math.sqrt(trials);
    }

    public double confidenceHi() {
        return this.mean() + 1.96 * this.stddev() / Math.sqrt(trials);
    }

    public static void main(String[] args) {
        int n = 0, t = 0;
        if (args.length == 2) {
            try {
                n = Integer.parseInt(args[0]);
                t = Integer.parseInt(args[1]);
            } catch (NumberFormatException e) {
                System.err.println("Arguments must be integers.");
                System.exit(1);
            }

            PercolationStats ps = new PercolationStats(n, t);
            System.out.println("mean                    = " + ps.mean());
            System.out.println("stddev                  = " + ps.stddev());
            System.out.println("95% confidence interval = [" + ps.confidenceLo() + ", " + ps.confidenceHi() + "]");
        } else {
            System.err.println("run:");
            System.err.println("java PercolationStats [n] [t]");
        }
    }
}
