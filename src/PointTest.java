import org.junit.Test;

public class PointTest {
    @Test
    public void testTransitive() {
        Point p = new Point(262, 393);
        Point q = new Point(62, 184);
        Point r = new Point(335, 419);
        Point s = new Point(463, 292);

        assert p.slopeOrder().compare(q, r) == 0;
        assert p.slopeOrder().compare(r, s) == 0;
        assert p.slopeOrder().compare(q, s) == 0;

    }


}
