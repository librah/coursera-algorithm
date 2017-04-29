import org.junit.Test;

/**
 * Created by librah on 04/29/2017.
 */
public class DequeTest {
    @Test
    public void mytest() {
        Deque<String> d = new Deque();

        assert (d.isEmpty());
        assert (d.size() == 0);

        d.addLast("a");
        d.addLast("b");
        d.addLast("c");
        d.addFirst("3");
        d.addFirst("2");
        d.addFirst("1");

        assert d.size() == 6 : "d.size() should be 6, actual value: " + d.size();
        assert (!d.isEmpty());

        String result = "";
        while (!d.isEmpty()) {
            result += (String) d.removeFirst();
        }
        assert (result.equals("123abc"));
        assert (d.isEmpty());
        assert (d.size() == 0);
    }

    @Test
    public void myFailedTest1() {
        Deque<Integer> deque = new Deque();

        deque.addFirst(0);
        deque.removeLast();
        assert (deque.isEmpty());
    }

    @Test
    public void testIterator() {
        Deque<String> d = new Deque();

        d.addLast("a");
        d.addLast("b");
        d.addLast("c");
        d.addFirst("3");
        d.addFirst("2");
        d.addFirst("1");

        String result = "";
        for (String i : d) {
            result += i;
        }
        assert (result.equals("123abc"));
        assert (!d.isEmpty());
        assert (d.size() == 6);
    }

    @Test
    public void testNullItem() {
        Deque<String> d = new Deque();
        try {
            d.addFirst(null);
        } catch (NullPointerException e) {
            // do nothing
        }

        try {
            d.addLast(null);
        } catch (NullPointerException e) {

        }

    }
}
