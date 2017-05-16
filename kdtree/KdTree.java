
import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdRandom;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author omjunior
 */
public class KdTree {

    private Node root;

    public KdTree() {
        root = null;
    }

    public boolean isEmpty() {
        return root == null;
    }

    public int size() {
        if (root == null) {
            return 0;
        } else {
            return root.size;
        }
        // return nodeSize(root);
    }

    /* private int nodeSize(Node n) {
        if (n == null) {
            return 0;
        }

        return 1 + nodeSize(n.left) + nodeSize(n.right);
    } */
    public void insert(Point2D p) {
        if (p == null) {
            throw new NullPointerException();
        }

        root = insertX(p, root, 0.0, 0.0, 1.0, 1.0);
    }

    private Node insertX(Point2D p, Node n,
            double xmin, double ymin, double xmax, double ymax) {
        if (n == null) {
            return new Node(p, new RectHV(xmin, ymin, xmax, ymax));
        }

        Point2D np = n.getPoint();
        if (p.x() < np.x()) {
            int oldSize = n.left == null ? 0 : n.left.size;
            n.left = insertY(p, n.left, xmin, ymin, np.x(), ymax);
            int newSize = n.left == null ? 0 : n.left.size;
            n.size = n.size - oldSize + newSize;
            return n;
        } else {
            if (p.equals(np)) {
                return n;
            }
            int oldSize = n.right == null ? 0 : n.right.size;
            n.right = insertY(p, n.right, np.x(), ymin, xmax, ymax);
            int newSize = n.right == null ? 0 : n.right.size;
            n.size = n.size - oldSize + newSize;
            return n;
        }
    }

    private Node insertY(Point2D p, Node n,
            double xmin, double ymin, double xmax, double ymax) {
        if (n == null) {
            return new Node(p, new RectHV(xmin, ymin, xmax, ymax));
        }
        Point2D np = n.getPoint();
        if (p.y() < np.y()) {
            int oldSize = n.left == null ? 0 : n.left.size;
            n.left = insertX(p, n.left, xmin, ymin, xmax, np.y());
            int newSize = n.left == null ? 0 : n.left.size;
            n.size = n.size - oldSize + newSize;
            return n;
        } else {
            if (p.equals(np)) {
                return n;
            }
            int oldSize = n.right == null ? 0 : n.right.size;
            n.right = insertX(p, n.right, xmin, np.y(), xmax, ymax);
            int newSize = n.right == null ? 0 : n.right.size;
            n.size = n.size - oldSize + newSize;
            return n;
        }
    }

    public boolean contains(Point2D p) {
        if (p == null) {
            throw new NullPointerException();
        }

        return containsX(p, root);
    }

    private boolean containsX(Point2D p, Node n) {
        if (n == null) {
            return false;
        }

        if (p.x() < n.getPoint().x()) {
            return containsY(p, n.left);
        } else {
            if (p.equals(n.getPoint())) {
                return true;
            }
            return containsY(p, n.right);
        }
    }

    private boolean containsY(Point2D p, Node n) {
        if (n == null) {
            return false;
        }

        if (p.y() < n.getPoint().y()) {
            return containsX(p, n.left);
        } else {
            if (p.equals(n.getPoint())) {
                return true;
            }
            return containsX(p, n.right);
        }
    }

    public void draw() {
        drawNodeX(root);
    }

    private void drawNodeX(Node n) {
        if (n == null) {
            return;
        }

        drawNodeY(n.right);
        drawNodeY(n.left);

        StdDraw.setPenColor(StdDraw.RED);
        StdDraw.setPenRadius();
        StdDraw.line(n.getPoint().x(), n.getRectanlge().ymin(),
                n.getPoint().x(), n.getRectanlge().ymax());

        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.setPenRadius(0.01);
        n.getPoint().draw();
    }

    private void drawNodeY(Node n) {
        if (n == null) {
            return;
        }

        drawNodeX(n.right);
        drawNodeX(n.left);

        StdDraw.setPenColor(StdDraw.BLUE);
        StdDraw.setPenRadius();
        StdDraw.line(n.getRectanlge().xmin(), n.getPoint().y(),
                n.getRectanlge().xmax(), n.getPoint().y());

        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.setPenRadius(0.01);
        n.getPoint().draw();
    }

    public Iterable<Point2D> range(RectHV rect) {
        if (rect == null) {
            throw new NullPointerException();
        }

        Queue<Point2D> points = new Queue<>();
        range(rect, root, points);
        return points;
    }

    private void range(RectHV rect, Node n, Queue<Point2D> points) {
        if (n == null) {
            return;
        }
        if (!rect.intersects(n.getRectanlge())) {
            return;
        }
        if (rect.contains(n.getPoint())) {
            points.enqueue(n.getPoint());
        }
        range(rect, n.left, points);
        range(rect, n.right, points);
    }

    public Point2D nearest(Point2D p) {
        if (p == null) {
            throw new NullPointerException();
        }

        return nearest(p, root, true, Double.POSITIVE_INFINITY);
    }

    private Point2D nearest(Point2D p, Node n, boolean vertical, double sqDistSoFar) {
        if (n == null) {
            return null;
        }
        if (n.getRectanlge().distanceSquaredTo(p) > sqDistSoFar) {
            return null;
        }
        Point2D best;
        double thisSqDist = p.distanceSquaredTo(n.getPoint());
        if (p.distanceSquaredTo(n.getPoint()) < sqDistSoFar) {
            sqDistSoFar = thisSqDist;
            best = n.getPoint();
        } else {
            best = null;
        }

        if ((vertical && p.x() < n.getPoint().x())
                || (!vertical && p.y() < n.getPoint().y())) {
            Point2D bestSubTree = nearest(p, n.left, !vertical, sqDistSoFar);
            if (bestSubTree != null) {
                double bestSubTreeSqDist = bestSubTree.distanceSquaredTo(p);
                if (bestSubTreeSqDist < sqDistSoFar) {
                    sqDistSoFar = bestSubTreeSqDist;
                    best = bestSubTree;
                }
            }
            bestSubTree = nearest(p, n.right, !vertical, sqDistSoFar);
            if (bestSubTree != null) {
                double bestSubTreeSqDist = bestSubTree.distanceSquaredTo(p);
                if (bestSubTreeSqDist < sqDistSoFar) {
                    // sqDistSoFar = bestSubTreeSqDist;
                    best = bestSubTree;
                }
            }
        } else {
            Point2D bestSubTree = nearest(p, n.right, !vertical, sqDistSoFar);
            if (bestSubTree != null) {
                double bestSubTreeSqDist = bestSubTree.distanceSquaredTo(p);
                if (bestSubTreeSqDist < sqDistSoFar) {
                    sqDistSoFar = bestSubTreeSqDist;
                    best = bestSubTree;
                }
            }
            bestSubTree = nearest(p, n.left, !vertical, sqDistSoFar);
            if (bestSubTree != null) {
                double bestSubTreeSqDist = bestSubTree.distanceSquaredTo(p);
                if (bestSubTreeSqDist < sqDistSoFar) {
                    // sqDistSoFar = bestSubTreeSqDist;
                    best = bestSubTree;
                }
            }
        }
        return best;
    }

    public static void main(String[] args) {
        KdTree kdt = new KdTree();
        System.out.println("Iniciando");
        for (int i = 0; i < 100000; i++) {
            Point2D p = new Point2D(StdRandom.uniform(100000) / 100000.0, StdRandom.uniform(100000) / 100000.0);
            kdt.insert(p);
            kdt.size();

            if (i % 1000 == 0) {
                System.out.println("Linha " + i + " size " + kdt.size());
            }
        }
        System.out.println("Fim");
    }

    private class Node {

        private Point2D p;
        private RectHV r;
        private Node right;
        private Node left;
        private int size;

        public Node(Point2D p, RectHV r) {
            this.p = p;
            this.r = r;
            this.right = null;
            this.left = null;
            this.size = 1;
        }

        public Point2D getPoint() {
            return p;
        }

        public RectHV getRectanlge() {
            return r;
        }

    }

}
