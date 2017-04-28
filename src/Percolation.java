/**
 * Created by librah on 04/22/2017.
 */
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    // properties
    private static final char BLOCKED = 'b', EMPTY_OPEN = 'e', FULL_OPEN = 'o';

    private int siteEdge;
    private int siteSize;
    private int virtualTopSite, virtualBottomSite;
    private WeightedQuickUnionUF sitesUnionFind;

    private char[] siteStatus; // store each site's status: blocked, empty open, or full open
    private int openSites = 0;

    // functions
    public Percolation(int n) {                // create n-by-n grid, with all sites blocked
        if (n <= 0) {
            throw new IllegalArgumentException("n must be > 0");
        }

        siteEdge = n;
        siteSize = siteEdge * siteEdge + 2; // add 2 virtual top / bottom sites

        sitesUnionFind = new WeightedQuickUnionUF(siteSize);
        virtualTopSite = 0;
        virtualBottomSite = siteSize -1;

        // initialize the sites as blocked
        siteStatus = new char[siteSize];
        for (int i = 0; i < siteSize; i++) {
            siteStatus[i] = BLOCKED;
        }
        siteStatus[virtualTopSite] = FULL_OPEN;
    }

    public void open(int row, int col) {    // open site (row, col) if it is not open already
        int index = getIndex(row, col);
        if (siteStatus[index] == EMPTY_OPEN || siteStatus[index] == FULL_OPEN) {
            return; // do nothing
        }

        siteStatus[index] = EMPTY_OPEN; // first, change my status to open
        openSites++;

        // find out my neighbors, and connect to them if they're open
        if (row == 1) {
            this.union(index, virtualTopSite);
        }
        if (row > 1) { // find the neighbor above me
            int neighbor = getIndex(row-1, col);
            this.union(index, neighbor);
        }
        if (row < siteEdge) {
            int neighbor = getIndex(row+1, col);
            this.union(index, neighbor);
        }
        if (row == siteEdge) {
            this.union(virtualBottomSite, index);

        }
        if (col < siteEdge) {
            int neighbor = getIndex(row, col+1);
            this.union(index, neighbor);
        }
        if (col > 1) {
            int neighbor = getIndex(row, col-1);
            this.union(index, neighbor);
        }
    }

    public boolean isOpen(int row, int col) { // is site (row, col) open?
        return siteStatus[getIndex(row, col)] == EMPTY_OPEN || siteStatus[getIndex(row, col)] == FULL_OPEN;
    }

    public boolean isFull(int row, int col) { // is site (row, col) full?
        // return siteStatus[getIndex(row, col)] == FULL_OPEN;
        return sitesUnionFind.connected(getIndex(row, col), virtualTopSite);
    }

    public int numberOfOpenSites() {       // number of open sites
        return openSites;
    }

    public boolean percolates() {             // does the system percolate?
        return sitesUnionFind.connected(virtualTopSite, virtualBottomSite);
    }

    /**
     * By convention, the row and column indices are integers between 1 and n, where (1, 1) is the upper-left site:
     * Throw a java.lang.IndexOutOfBoundsException if any argument to open(), isOpen(), or isFull() is outside its
     * prescribed range. The constructor should throw a java.lang.IllegalArgumentException if n ≤ 0.
     * @param row
     * @param col
     * @return
     */
    private int getIndex(int row, int col) {
        if (!(row >= 1 && row <= siteEdge)) {
            throw new IndexOutOfBoundsException("row (value=" + row + ") is not between 1 ~ " + siteEdge);
        }
        if (!(col >= 1 && col <= siteEdge)) {
            throw new IndexOutOfBoundsException("col (value=" + col + ") is not between 1 ~ " + siteEdge);
        }

        return (row - 1) * siteEdge + col;
    }

    private void union(int from, int to) {
        if (siteStatus[to] == BLOCKED) {
            return;
        }

        if (siteStatus[from] == FULL_OPEN || siteStatus[to] == FULL_OPEN) {
            siteStatus[from] = FULL_OPEN;
            siteStatus[to] = FULL_OPEN;
        }
        sitesUnionFind.union(from, to);
    }

}
