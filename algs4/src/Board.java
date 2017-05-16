
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.StdOut;
import static java.lang.Math.abs;
import java.util.Arrays;

/**
 *
 * @author omjunior
 */
public class Board {

    private int[] blocks;
    private int n;
    private int hamming;
    private int manhattan;

    public Board(int[][] blocks) {
        this.n = blocks.length;
        this.blocks = new int[n * n];
        this.hamming = -1;
        this.manhattan = -1;

        if (n < 2 || n > 127) {
            throw new IllegalArgumentException("The size should be between 2x2"
                    + " and 127x127");
        }
        for (int i = 0; i < blocks.length; i++) {
            if (blocks[i].length != n) {
                throw new IllegalArgumentException("blocks should be a square"
                        + " matrix");
            }
            System.arraycopy(blocks[i], 0, this.blocks, n * i, n);
        }
    }

    private Board(int[] linearblocks, int n) {
        this.n = n;
        this.blocks = linearblocks; // ! does not copy
        this.hamming = -1;
        this.manhattan = -1;
    }

    public int dimension() {
        return n;
    }

    public int hamming() {
        if (hamming != -1) {
            return hamming;
        }

        hamming = 0;
        for (int i = 0; i < blocks.length; i++) {
            if (blocks[i] == 0) {
                continue;
            }
            if (blocks[i] != i + 1) {
                hamming++;
            }
        }
        return hamming;
    }

    public int manhattan() {
        if (manhattan != -1) {
            return manhattan;
        }

        manhattan = 0;
        for (int i = 0; i < blocks.length; i++) {
            if (blocks[i] != 0) {
                // sum abs diff of row and col
                manhattan += abs(((blocks[i] - 1) % n) - (i % n))
                        + abs(((blocks[i] - 1) / n) - (i / n));
            }
        }
        return manhattan;
    }

    public boolean isGoal() {
        return manhattan() == 0;
    }

    private void swap(int[] blocks, int posA, int posB) {
        int a = blocks[posA];
        blocks[posA] = blocks[posB];
        blocks[posB] = a;
    }

    public Board twin() {
        // copy Board
        int[] newBlocks = new int[n * n];
        System.arraycopy(blocks, 0, newBlocks, 0, n * n);

        // make a change (skipping 0)
        if (newBlocks[0] != 0 && newBlocks[1] != 0) {
            swap(newBlocks, 0, 1);
        } else {
            swap(newBlocks, n, n + 1);
        }

        return new Board(newBlocks, n);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (other == null) {
            return false;
        }
        if (other.getClass() != this.getClass()) {
            return false;
        }
        Board that = (Board) other;
        return (this.n == that.n) && (Arrays.equals(this.blocks, that.blocks));
    }

    public Iterable<Board> neighbors() {
        int zero;
        for (zero = 0; zero < blocks.length; zero++) {
            if (blocks[zero] == 0) {
                break;
            }
        }
        int zeroRow = zero / n;
        int zeroCol = zero % n;

        Queue<Board> queue = new Queue<>();

        if (zeroRow != 0) {
            int[] newBlocks = new int[n * n];
            System.arraycopy(blocks, 0, newBlocks, 0, n * n);
            swap(newBlocks, zeroRow * n + zeroCol, (zeroRow - 1) * n + zeroCol);
            queue.enqueue(new Board(newBlocks, n));
        }
        if (zeroRow != n - 1) {
            int[] newBlocks = new int[n * n];
            System.arraycopy(blocks, 0, newBlocks, 0, n * n);
            swap(newBlocks, zeroRow * n + zeroCol, (zeroRow + 1) * n + zeroCol);
            queue.enqueue(new Board(newBlocks, n));
        }
        if (zeroCol != 0) {
            int[] newBlocks = new int[n * n];
            System.arraycopy(blocks, 0, newBlocks, 0, n * n);
            swap(newBlocks, zeroRow * n + zeroCol, zeroRow * n + zeroCol - 1);
            queue.enqueue(new Board(newBlocks, n));
        }
        if (zeroCol != n - 1) {
            int[] newBlocks = new int[n * n];
            System.arraycopy(blocks, 0, newBlocks, 0, n * n);
            swap(newBlocks, zeroRow * n + zeroCol, zeroRow * n + zeroCol + 1);
            queue.enqueue(new Board(newBlocks, n));
        }

        return queue;
    }

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append(n).append("\n");
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                s.append(String.format("%2d ", blocks[n * i + j]));
            }
            s.append("\n");
        }
        //s.append("hamming   ").append(this.hamming()).append("\n");
        //s.append("manhattan ").append(this.manhattan()).append("\n");
        return s.toString();
    }

    public static void main(String[] args) {
        // create initial board from file
        In in = new In(args[0]);
        int n = in.readInt();
        int[][] blocks = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                blocks[i][j] = in.readInt();
            }
        }
        Board initial = new Board(blocks);

        // xxx
        StdOut.println(initial);
        StdOut.println("hamming:   " + initial.hamming());;
        StdOut.println("manhattan: " + initial.manhattan());

        for (Board neigh : initial.neighbors()) {
            StdOut.println(neigh);
        }

    }
}
