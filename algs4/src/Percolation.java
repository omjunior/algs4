

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

/**
 * 
 * @author osvaldo
 */
public class Percolation {
    private WeightedQuickUnionUF uf, uf2;
    private boolean[][] grid;
    private int countOpen;
    private int size;

    public Percolation(int n) {

        if (n < 1)
            throw new java.lang.IllegalArgumentException();

        countOpen = 0;
        size = n;

        grid = new boolean[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                grid[i][j] = false;

        uf = new WeightedQuickUnionUF(n*n+2);
        for (int i = 0; i < n; i++) {
            // 0 and n^2+1 are the virtual nodes
            uf.union(0, i+1); // top row
            uf.union(n*n+1, n*(n-1)+i+1); // bottom row
        }

        // only for isFull
        uf2 = new WeightedQuickUnionUF(n*n+1);
        for (int i = 0; i < n; i++) {
            // 0 is the virtual node
            uf2.union(0, i+1); // top row
        }
    }

    public    void open(int row, int col) {
        if (!validate(row, col))
            throw new java.lang.IndexOutOfBoundsException();

        // open site
        if  (!(grid[row-1][col-1])) {
            grid[row-1][col-1] = true;
            countOpen++;
        }

        // connect UF
        if (validate(row-1, col))
            if (isOpen(row-1, col)) {
                uf.union(linearize(row, col), linearize(row-1, col));
                uf2.union(linearize(row, col), linearize(row-1, col));
            }
        if (validate(row+1, col))
            if (isOpen(row+1, col)) {
                uf.union(linearize(row, col), linearize(row+1, col));
                uf2.union(linearize(row, col), linearize(row+1, col));
            }
        if (validate(row, col-1))
            if (isOpen(row, col-1)) {
                uf.union(linearize(row, col), linearize(row, col-1));
                uf2.union(linearize(row, col), linearize(row, col-1));
            }
        if (validate(row, col+1))
            if (isOpen(row, col+1)) {
                uf.union(linearize(row, col), linearize(row, col+1));
                uf2.union(linearize(row, col), linearize(row, col+1));
            }

    }

    public boolean isOpen(int row, int col) {
        if (!validate(row, col))
            throw new java.lang.IndexOutOfBoundsException();

        return grid[row-1][col-1];
    }

    public boolean isFull(int row, int col) {
        if (!validate(row, col))
            throw new java.lang.IndexOutOfBoundsException();

        return isOpen(row, col) && uf2.connected(0, linearize(row, col));
    }

    public     int numberOfOpenSites() {
        return countOpen;
    }

    public boolean percolates() {
        if (size == 1)
            return isOpen(1, 1);
        else
            return uf.connected(0, size*size+1);
    }

    private boolean validate(int row, int col) {
        return !(row < 1 || row > size || col < 1 || col > size);
    }

    private int linearize(int row, int col) {
        return (row-1)*size +col;
    }

    public static void main(String[] args) {
        Percolation p = new Percolation(3);
        
        System.out.println(p.percolates());
        System.out.println("(Should be false)");

        p.open(1, 2);
        p.open(2, 2);

        System.out.println(p.percolates());
        System.out.println("(Should be false)");

        p.open(3, 2);

        System.out.println(p.percolates());
        System.out.println("(Should be true)");

    }
}
