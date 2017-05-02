import java.util.Comparator;

public class Point implements Comparable<Point> {
    private int x, y;

    // constructs the point (x, y)
    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    // draws this point
    public void draw() {

    }

    // draws the line segment from this point to that point
    public void drawTo(Point that) {

    }

    // string representation
    public String toString() {
        return "x=" + x + ", y=" + y;
    }

    // compare two points by y-coordinates, breaking ties by x-coordinates
    public int compareTo(Point that) {
        // The compareTo() method should compare points by their y-coordinates, breaking ties by their x-coordinates.
        // Formally, the invoking point (x0, y0) is less than the argument point (x1, y1) if and only if
        // either y0 < y1 or if y0 = y1 and x0 < x1.

        if (this.y != that.y) {
            return this.y - that.y;
        } else {
            return this.x - that.x;
        }
    }

    // the slope between this point and that point
    public double slopeTo(Point that) {
        // The slopeTo() method should return the slope between the invoking point (x0, y0) and
        // the argument point (x1, y1), which is given by the formula (y1 − y0) / (x1 − x0).
        // Treat the slope of a horizontal line segment as positive zero; treat the slope of a
        // vertical line segment as positive infinity; treat the slope of a degenerate line segment
        // (between a point and itself) as negative infinity.

        int height = that.y - this.y;
        int width = that.x - this.x;

        if (height == 0 && width == 0) {
            return Double.NEGATIVE_INFINITY;
        } else if (width == 0) {
            return Double.POSITIVE_INFINITY;
        } else {
            return ((double) height) / (double) width;
        }
    }

    // compare two points by slopes they make with this point
    public Comparator<Point> slopeOrder() {
        // The slopeOrder() method should return a comparator that compares its two argument points
        // by the slopes they make with the invoking point (x0, y0). Formally, the point (x1, y1) is
        // less than the point (x2, y2) if and only if the slope (y1 − y0) / (x1 − x0) is less than
        // the slope (y2 − y0) / (x2 − x0). Treat horizontal, vertical, and degenerate line segments
        // as in the slopeTo() method.
        return (p1, p2) -> {
            double s1 = slopeTo(p1);
            double s2 = slopeTo(p2);
            if (s1 == s2) {
                return 0;
            } else {
                return (int) (s1 - s2);
            }

        };
    }
}