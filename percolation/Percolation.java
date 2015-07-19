public class Percolation {
    private int size;
    private boolean[][] opennedSites;
    private WeightedQuickUnionUF uf;
    private int virtTopSite, virtBottomSite;

    public Percolation(int N) {
        if (N <= 0) {
            throw new IllegalArgumentException();
        }

        size = N;

        // all sites blocked by default
        opennedSites = new boolean[size][size];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                opennedSites[i][j] = false;
            }
        }

        int siteCount = size * size;
        uf = new WeightedQuickUnionUF(siteCount + 2);  // + 2 virtial sites
        virtTopSite = siteCount;
        virtBottomSite = siteCount + 1;
    };

    private int getLinearIndex(int i, int j) {
        return (i - 1) * size + j - 1;
    };

    private void validate(int i) {
        if (i < 1 || i > size) {
            throw new IndexOutOfBoundsException();
        }
    };

    public void open(int i, int j) {
        if (isOpen(i, j)) {
            return;
        }

        opennedSites[i-1][j-1] = true;
 
        int idx = getLinearIndex(i, j);
 
        if (i == 1) {
            uf.union(idx, virtTopSite);
        }

        if (i == size) {
            uf.union(idx, virtBottomSite);
        }
        
        if (i > 1 && isOpen(i - 1, j)) {
            uf.union(getLinearIndex(i - 1, j), idx);
        }
        
        if (i < size && isOpen(i + 1, j)) {
            uf.union(getLinearIndex(i + 1, j), idx);
        }
        
        if (j > 1 && isOpen(i, j - 1)) {
            uf.union(getLinearIndex(i, j - 1), idx);
        }
        
        if (j < size && isOpen(i, j + 1)) {
            uf.union(getLinearIndex(i, j + 1), idx);
        }
    }
 
    public boolean isOpen(int i, int j) {
        validate(i);
        validate(j);
        return opennedSites[i-1][j-1];
    }

    public boolean isFull(int i, int j) {
        return isOpen(i, j) && uf.connected(getLinearIndex(i, j), virtTopSite);
    }

    public boolean percolates() {
        return uf.connected(virtTopSite, virtBottomSite);
    }
};