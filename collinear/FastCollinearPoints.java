
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;

/**
 *
 * @author osvaldo
 */
public class FastCollinearPoints {

    private LineSegment[] segments;

    public FastCollinearPoints(Point[] points) {
        if (points == null) {
            throw new java.lang.NullPointerException();
        }

        Point[] points2 = Arrays.copyOf(points, points.length); // decouple input argument
        Arrays.sort(points2);

        {
            Point last = null;
            for (Point point : points2) {
                if (point == null) {
                    throw new java.lang.NullPointerException();
                }
                if (last != null && last.compareTo(point) == 0) {
                    throw new java.lang.IllegalArgumentException("Repeated Points");
                }
                last = point;
            }
        }

        LinkedList<Point[]> segll = new LinkedList<>();

        for (Point p1 : points) {
            Arrays.sort(points2, p1.slopeOrder());

            double lastSlope = Double.NaN;
            LinkedList<Point> equalSlopes = new LinkedList<>();
            equalSlopes.add(p1);

            for (int i = 1; i < points2.length; i++) { // skip first
                Point p2 = points2[i];
                double slope = p1.slopeTo(p2);
                if (slope != lastSlope) {
                    // see if last one was good
                    treatPoints(equalSlopes, segll);
                    // reset equalSlopes
                    equalSlopes = new LinkedList<>();
                    equalSlopes.add(p1);
                }
                equalSlopes.add(p2);
                lastSlope = slope;
            }
            treatPoints(equalSlopes, segll);
        }

        segments = new LineSegment[segll.size()];
        int i = 0;
        for (Point[] ls : segll) {
            segments[i] = new LineSegment(ls[0], ls[1]);
            i++;
        }
    }

    private void treatPoints(LinkedList<Point> equalSlopes, LinkedList<Point[]> segll) {
        if (equalSlopes.size() >= 4) {
            Collections.sort(equalSlopes);
            Point[] ls = new Point[]{equalSlopes.getFirst(), equalSlopes.getLast()};
            boolean repeated = false;
            for (Point[] pp : segll) {
                if (pp[0] == ls[0] && pp[1] == ls[1]) {
                    repeated = true;
                }
            }
            if (!repeated) {
                segll.add(ls);
            }
        }
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
        FastCollinearPoints collinear = new FastCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }
}
