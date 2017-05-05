import java.util.Arrays;

public class BruteCollinearPoints {
    private LineSegment lineSegments[];
    private int size;


    // finds all line segments containing 4 points
    public BruteCollinearPoints(Point[] points) {
        if (points == null) {
            throw new NullPointerException("points is null");
        }

        for (int i = 0; i < points.length; i++) {
            if (points[i] == null) {
                throw new NullPointerException("point is null");
            }
        }

        LineSegment[] tmp = new LineSegment[points.length];
        size = 0;

        for (int i = 0; i < points.length; i++) {
            Point p = points[i];
            for (int j = i + 1; j < points.length; j++) {
                Point q = points[j];
                if (p.compareTo(q) == 0) {
                    throw new IllegalArgumentException("duplicate points");
                }
                double pqSlope = p.slopeTo(q);
                for (int k = j + 1; k < points.length; k++) {
                    Point r = points[k];
                    double prSlope = p.slopeTo(r);
                    for (int l = k + 1; l < points.length; l++) {
                        Point s = points[l];
                        double psSlope = p.slopeTo(s);
                        if (pqSlope == prSlope && pqSlope == psSlope) {
                            // found line segment
                            // Store the min => max point
                            Point[] result = {
                                    p, q, r, s
                            };

                            java.util.Arrays.sort(result);

                            tmp[size++] = new LineSegment(result[0], result[3]);
                        }
                    }
                }
            }
        }

        lineSegments = new LineSegment[size];
        for (int i = 0; i < size; i++) {
            lineSegments[i] = tmp[i];
        }

    }

    // the number of line segments
    public int numberOfSegments() {
        return size;

    }

    // the line segments
    public LineSegment[] segments() {
        return lineSegments;
    }
}
