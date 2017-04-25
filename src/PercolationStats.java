/**
 * Created by librah on 04/25/2017.
 */
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;


public class PercolationStats {
    // properties
    private double mean;
    private double stddev;
    private double confidenceLo;
    private double confidenceHi;

    public PercolationStats(int n, int trials) {   // perform trials independent experiments on an n-by-n grid
        if (n <= 0) {
            throw new IllegalArgumentException("n <= 0");
        }

        if (trials <= 0) {
            throw new IllegalArgumentException("trials <= 0");
        }

        double[] openFractions = new double[trials];

        for (int i=0; i<trials; i++) {
            int loopCount = 0;

            Percolation p = new Percolation(n);
            while (!p.percolates()) {
                int x = StdRandom.uniform(1, n + 1);
                int y = StdRandom.uniform(1, n+1);
                if (p.isFull(x, y) || p.isOpen(x, y)) {
                    continue;
                } else {
                    p.open(x, y);
                    loopCount ++;
                }
            }

            openFractions[i] = (double) loopCount / (n*n);
        } // end for loop

        this.mean = StdStats.mean(openFractions);
        this.stddev = StdStats.stddev(openFractions);
        this.confidenceLo = this.mean -  (1.96 * this.stddev / Math.sqrt(trials));
        this.confidenceHi = this.mean +  (1.96 * this.stddev / Math.sqrt(trials));
    }

    public double mean() {                         // sample mean of percolation threshold
        return this.mean;
    }

    public double stddev() {                       // sample standard deviation of percolation threshold
        return this.stddev;
    }

    public double confidenceLo() {                 // low  endpoint of 95% confidence interval
        return this.confidenceLo;
    }

    public double confidenceHi() {                // high endpoint of 95% confidence interval
        return this.confidenceHi;
    }

    public static void main(String[] args) {       // test client (described below)
        if (args.length != 2) {
            System.out.println("Usage: java Percolation <n> <T>");
            System.out.println(" <n>: size of sites");
            System.out.println(" <T>: rounds of testing");
            System.exit(-1);
        }

        int n = Integer.parseInt(args[0]);
        int trials = Integer.parseInt(args[1]);

        PercolationStats s = new PercolationStats(n, trials);
        System.out.println("mean                    = " + s.mean());
        System.out.println("stddev                  = " + s.stddev());
        System.out.println("95% confidence interval = [" + s.confidenceLo() + ", " + s.confidenceHi() + "]");
    }
}
