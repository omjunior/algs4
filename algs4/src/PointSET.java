
import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.SET;

/**
 *
 * @author omjunior
 */
public class PointSET {

    private final SET<Point2D> rbt;

    public PointSET() {
        rbt = new SET<>();
    }

    public boolean isEmpty() {
        return rbt.isEmpty();
    }

    public int size() {
        return rbt.size();
    }

    public void insert(Point2D p) {
        rbt.add(p);
    }

    public boolean contains(Point2D p) {
        return rbt.contains(p);
    }

    public void draw() {
        for (Point2D p : rbt) {
            p.draw();
        }
    }

    public Iterable<Point2D> range(RectHV rect) {
        if (rect == null) {
            throw new NullPointerException();
        }

        Queue<Point2D> points = new Queue<>();
        for (Point2D p : rbt) {
            if (rect.contains(p)) {
                points.enqueue(p);
            }
        }
        return points;
    }

    public Point2D nearest(Point2D p) {
        if (p == null) {
            throw new NullPointerException();
        }

        double minDist = Double.POSITIVE_INFINITY;
        Point2D minP = null;
        for (Point2D point : rbt) {
            double dist = point.distanceSquaredTo(p); // no need to sqrt
            if (dist < minDist) {
                minDist = dist;
                minP = point;
            }
        }
        return minP;
    }

    public static void main(String[] args) {
    }
}
