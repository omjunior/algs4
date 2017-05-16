
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;
import java.util.Arrays;
import java.util.Comparator;
import java.util.LinkedList;

/**
 *
 * @author osvaldo
 */
public class BruteCollinearPoints {

    private LineSegment[] segments;

    public BruteCollinearPoints(Point[] points) {

        if (points == null) {
            throw new java.lang.NullPointerException();
        }

        points = Arrays.copyOf(points, points.length); // decouple input argument
        Arrays.sort(points);

        Point last = null;
        for (Point point : points) {
            if (point == null) {
                throw new java.lang.NullPointerException();
            }
            if (last != null && last.compareTo(point) == 0) {
                throw new java.lang.IllegalArgumentException("Repeated Points");
            }
            last = point;
        }

        LinkedList<LineSegment> segll = new LinkedList<>();

        for (int i = 0; i < points.length; i++) {
            Point pi = points[i];
            for (int j = i + 1; j < points.length; j++) {
                Point pj = points[j];
                for (int k = j + 1; k < points.length; k++) {
                    Point pk = points[k];
                    Comparator<Point> comp = pi.slopeOrder();
                    if (comp.compare(pj, pk) == 0) { // all 3 collinear so far
                        for (int l = k + 1; l < points.length; l++) {
                            Point pl = points[l];
                            if (comp.compare(pj, pl) == 0) {
                                segll.add(new LineSegment(pi, pl));
                            }
                        }
                    }
                }
            }
        }

        segments = segll.toArray(new LineSegment[0]);

    }

    public int numberOfSegments() {
        return segments.length;
    }

    public LineSegment[] segments() {
        return Arrays.copyOf(segments, segments.length);
    }

    public static void main(String[] args) {
        // read the n points from a file
        In in = new In(args[0]);
        int n = in.readInt();
        Point[] points = new Point[n];
        for (int i = 0; i < n; i++) {
            int x = in.readInt();
            int y = in.readInt();
            points[i] = new Point(x, y);
        }

        // draw the points
        StdDraw.enableDoubleBuffering();
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        for (Point p : points) {
            p.draw();
        }
        StdDraw.show();

        // print and draw the line segments
        BruteCollinearPoints collinear = new BruteCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }

}
