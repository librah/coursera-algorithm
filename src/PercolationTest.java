import org.junit.Test;

/**
 * Created by librah on 04/26/2017.
 */
public class PercolationTest {
    @Test
    public void test1() {
        Percolation p = new Percolation(6);
        p.open(1,6);
        assert(p.isFull(1, 6));
        assert(!p.isFull(1, 1));
    }
}
