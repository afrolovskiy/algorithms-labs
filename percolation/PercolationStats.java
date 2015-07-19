public class PercolationStats {
    private double[] thresholds;
    private int size, retries, siteCount;

    // perform T independent experiments on an N-by-N grid
    public PercolationStats(int N, int T) {
        if (N <= 0 || T <= 0) {
            throw new IllegalArgumentException();
        }

        size = N;
        retries = T;
        siteCount = size * size;
 
        thresholds = new double[retries];
        for (int i = 0; i < retries; i++) {
            thresholds[i] = calcThreshold();
        }
    }
    
    private double calcThreshold() {
        int count = 0;
        Percolation percolation = new Percolation(size);
        while (!percolation.percolates()) {
            count++;
            openRandomSite(percolation);
        }
        return ((double) count) / siteCount;
    };

    private void openRandomSite(Percolation percolation) {
        int i, j;
        do {
            i = StdRandom.uniform(size) + 1;
            j = StdRandom.uniform(size) + 1;
        } while (percolation.isOpen(i, j));
        percolation.open(i, j);
    };

    public double mean() {
        return StdStats.mean(thresholds);
    }

    public double stddev() {
        return StdStats.stddev(thresholds);
    }

    // low  endpoint of 95% confidence interval
    public double confidenceLo() {
        return mean() - (1.96 * Math.sqrt(stddev()) / Math.sqrt(retries));
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi() {
         return mean() + (1.96 * Math.sqrt(stddev()) / Math.sqrt(retries));
    }
    
    public static void main(String[] args) {
        if (args.length < 2) {
            throw new IllegalArgumentException();
        }

        int N = Integer.parseInt(args[0]);
        int T = Integer.parseInt(args[1]);

        PercolationStats ps = new PercolationStats(N, T);

        StdOut.printf("mean                    = " + ps.mean() + "\n");
        StdOut.printf("stddev                  = " + ps.stddev() + "\n");
        StdOut.printf("95%% confidence interval = " + ps.confidenceLo() + " "
                      + ps.confidenceHi() + "\n");
    };
};