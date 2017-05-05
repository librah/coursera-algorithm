import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;
import org.junit.Test;

/**
 * Created by librah on 05/04/2017.
 */
public class FastCollinearPointsTest {
    public static Point[] getPoints(String filename) {
        // read the n points from a file
        In in = new In(filename);
        int n = in.readInt();
        Point[] points = new Point[n];
        for (int i = 0; i < n; i++) {
            int x = in.readInt();
            int y = in.readInt();
            points[i] = new Point(x, y);
        }

        return points;
    }

    @Test
    public void test1() {
        Point[] tmpPoints = new Point[4];
        tmpPoints[0] = new Point(2776, 3140);
        tmpPoints[1] = new Point(4208, 3140);
        tmpPoints[2] = new Point(9042, 3140);
        tmpPoints[3] = new Point(15492, 3140);

        FastCollinearPoints p = new FastCollinearPoints(tmpPoints);

        assert p.numberOfSegments() == 1;
    }

    @Test
    public void testInput8() {
        FastCollinearPoints p = new FastCollinearPoints(getPoints("src/collinear/input8.txt"));

        assert p.numberOfSegments() == 2;
    }

    @Test
    public void testInput20() {
        FastCollinearPoints p = new FastCollinearPoints(getPoints("src/collinear/input20.txt"));
    }

    @Test
    public void testInput40() {
        FastCollinearPoints p = new FastCollinearPoints(getPoints("src/collinear/input40.txt"));
        assert p.numberOfSegments() == 4;
    }

    @Test
    public void testInput48() {
        FastCollinearPoints p = new FastCollinearPoints(getPoints("src/collinear/input48.txt"));
        assert p.numberOfSegments() == 6;
    }

    @Test
    public void testEquidistant() {
        FastCollinearPoints p = new FastCollinearPoints(getPoints("src/collinear/equidistant.txt"));

        assert p.numberOfSegments() == 4;
    }
}
