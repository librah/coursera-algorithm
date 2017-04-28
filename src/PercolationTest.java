import org.junit.Test;

/**
 * Created by librah on 04/26/2017.
 */
public class PercolationTest {
    @Test
    public void test1() {
        Percolation p = new Percolation(6);
        p.open(1,6);
        assert(p.isOpen(1, 6));
        assert(p.isFull(1, 6));
        assert(!p.isFull(1, 1));
    }

    @Test
    public void test3() {
        Percolation p = new Percolation(3);
        p.open(1, 3);
        assert(p.isFull(1, 3));

        p = new Percolation(2);
        p.open(1, 1);
        p.open(2, 2);
        p.open(1,2);

        assert(p.isFull(2,2));
    }

    @Test
    public void test1_input6() {
        Percolation p = new Percolation(6);
        p.open(1, 6);
        p.open(2, 6);
        p.open(3, 6);
        p.open(4, 6);
        p.open(5, 6);
        p.open(5, 5);
        p.open(4, 4);
        p.open(3, 4);
        p.open(2, 4);
        p.open(2, 3);
        p.open(2, 2);
        p.open(2, 1);
        p.open(3, 1);
        p.open(4, 1);
        p.open(5, 1);
        p.open(5, 2);
        p.open(6, 2);
        p.open(5, 4);
        assert(p.isFull(2, 1));
    }
}
