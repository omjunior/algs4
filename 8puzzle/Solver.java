
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdOut;
import java.util.Comparator;

/**
 *
 * @author omjunior
 */
public class Solver {

    private MinPQ<Node> pq;
    private Stack<Board> solution;

    private final class Node {

        private final Board board;
        private final Node parent;
        private final int moves;

        public Node(Board board, Node parent, int moves) {
            this.board = board;
            this.parent = parent;
            this.moves = moves;
        }

        public Board getBoard() {
            return board;
        }

        public Node getParent() {
            return parent;
        }

        public int getMoves() {
            return moves;
        }

        @Override
        public String toString() {
            StringBuilder s = new StringBuilder();
            s.append(" --- Board\n");
            s.append(board);
            s.append("\n");
            s.append(" --- Parent\n");
            s.append((parent == null) ? "null\n" : parent.getBoard());
            s.append(" ---\n");
            s.append("moves ").append(moves).append("\n");
            return s.toString();
        }

    }

    private class ComparatorBoardHamming implements Comparator<Node> {

        @Override
        public int compare(Node b1, Node b2) {
            int ham = (b1.getBoard().hamming() + b1.getMoves()) - (b2.getBoard().hamming() + b2.getMoves());
            if (ham == 0) {
                return (b1.getBoard().manhattan() + b1.getMoves()) - (b2.getBoard().manhattan() + b2.getMoves());
            }
            return ham;
        }

    }

    private class ComparatorBoardManhattan implements Comparator<Node> {

        @Override
        public int compare(Node b1, Node b2) {
            // manhattan braking ties with hamming
            int man = (b1.getBoard().manhattan() + b1.getMoves()) - (b2.getBoard().manhattan() + b2.getMoves());
            if (man == 0) {
                return (b1.getBoard().hamming() + b1.getMoves()) - (b2.getBoard().hamming() + b2.getMoves());
            }
            return man;
        }

    }

    public Solver(Board initial) {
        Comparator<Node> comparator = new ComparatorBoardManhattan();
        pq = new MinPQ<>(comparator);
        pq.insert(new Node(initial, null, 0));
        pq.insert(new Node(initial.twin(), null, 0));
        solution = new Stack<>();

        do {
            Node min = pq.delMin();
            if (min.getBoard().isGoal()) {
                do {
                    solution.push(min.getBoard());
                    min = min.getParent();
                } while (min != null);
                break;
            }

            for (Board b : min.getBoard().neighbors()) {
                if (!(min.getParent() != null && b.equals(min.getParent().getBoard()))) {
                    pq.insert(new Node(b, min, min.getMoves() + 1));
                }
            }
        } while (true);

        if (!solution.peek().equals(initial)) {
            solution = null;
        }
    }

    public boolean isSolvable() {
        return solution != null;
    }

    public int moves() {
        if (isSolvable()) {
            return solution.size() - 1;
        } else {
            return -1;
        }
    }

    public Iterable<Board> solution() {
        return solution;
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

        // solve the puzzle
        Solver solver = new Solver(initial);

        // print solution to standard output
        if (!solver.isSolvable()) {
            StdOut.println("No solution possible");
        } else {
            StdOut.println("Minimum number of moves = " + solver.moves());
            for (Board board : solver.solution()) {
                StdOut.println(board);
            }
        }
    }
}
