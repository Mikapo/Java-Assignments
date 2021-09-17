/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

/**
 *
 * @author Mika
 */
public class PercolationStats {

    // perform independent trials on an n-by-n grid
    public PercolationStats(int n, int trials) {
        m_thresholds = new double[trials];
        T = trials;
        for (int i = 0; i < trials; i++) {
            Percolation test = new Percolation(n);
            int[] rows = new int[n * n];
            int[] col = new int[n * n];

            for (int a = 0; a < n; a++) {
                for (int b = 0; b < n; b++) {
                    rows[a + n * b] = b;
                    col[a + n * b] = a;
                }
            }
            int[] random = StdRandom.permutation(n * n);

            int tries = 0;
            while (!test.percolates()) {
                test.open(rows[random[tries]] + 1, col[random[tries]] + 1);
                ++tries;
            }
            m_thresholds[i] = (double) tries / ((double) n * (double) n);
        }
    }

    // sample mean of percolation threshold
    public double mean() {
        return StdStats.mean(m_thresholds);
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        return StdStats.stddev(m_thresholds);
    }

    // low endpoint of 95% confidence interval
    public double confidenceLo() {
        return mean() - ((1.96 * stddev()) / Math.sqrt(T));

    }

    // high endpoint of 95% confidence interval
    public double confidenceHi() {
        return mean() + ((1.96 * stddev()) / Math.sqrt(T));
    }

    private final double[] m_thresholds;
    private double T;

    // test client (see below)
    public static void main(String[] args) {
        PercolationStats test_stats = new PercolationStats(Integer.parseInt(args[0]), Integer.parseInt(args[1]));
        System.out.println("mean                    = " + test_stats.mean());
        System.out.println("stddev                  = " + test_stats.stddev());
        System.out.println("95% confidence interval = [" + test_stats.confidenceLo() + "], "+  test_stats.confidenceHi()+ "]");
    }
}
