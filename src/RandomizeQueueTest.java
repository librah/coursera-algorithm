import org.junit.Test;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Created by librah on 04/29/2017.
 */
public class RandomizeQueueTest {
    @Test
    public void test1Item() {
        RandomizedQueue<String> q = new RandomizedQueue();
        q.enqueue("a");

        assert q.size() == 1 : "Expect 1, actual: " + q.size();

        q.sample();
        assert q.size() == 1 : "Expect 1, actual: " + q.size();

        q.dequeue();
        assert q.size() == 0 : "Expect 0, actual: " + q.size();

        try {
            q.dequeue();
            assert false : "Expect NoSuchElementException ";
        } catch (NoSuchElementException e) {

        }

        try {
            q.sample();
            assert false : "Expect NoSuchElementException ";

        } catch (NoSuchElementException e) {

        }

    }

    @Test
    public void test2Items() {
        RandomizedQueue<String> q = new RandomizedQueue();
        q.enqueue("a");
        q.enqueue("b");

        assert q.size() == 2 : "Expect 2, actual: " + q.size();

        q.dequeue();
        assert q.size() == 1 : "Expect 1, actual: " + q.size();

        q.sample();
        assert q.size() == 1 : "Expect 1, actual: " + q.size();

        q.dequeue();
        assert q.size() == 0 : "Expect 0, actual: " + q.size();
        assert q.isEmpty();

        try {
            q.dequeue();
            assert false : "Expect NoSuchElementException ";
        } catch (NoSuchElementException e) {

        }

        try {
            q.sample();
            assert false : "Expect NoSuchElementException ";

        } catch (NoSuchElementException e) {

        }
    }

    @Test
    public void testIterator() {
        RandomizedQueue<String> q = new RandomizedQueue();
        q.enqueue("a");
        q.enqueue("b");
        q.enqueue("c");
        q.enqueue("d");

        int loopCount = 0;
        for (String s : q) {
            loopCount ++;
        }

        assert loopCount == 4 : "Expect 4, actual: " + loopCount;

        try {
            Iterator<String> it = q.iterator();
            for (int i=0; i<q.size()+1; i++) {
                it.next();
            }
            assert false : "Expect NoSuchElementException";
        } catch (NoSuchElementException e) {
            // ignore
        }
    }

    @Test
    public void testFailedCourseraTest() {
        RandomizedQueue<Integer> rq = new RandomizedQueue<>();

        assert rq.isEmpty();
        assert rq.size() == 0 : "actual value: " + rq.size();

        rq.enqueue(18);
        assert rq.size() == 1 : "actual value: " + rq.size();

        int value = rq.dequeue();
        assert value == 18 : "actual value: " + value;

        rq.enqueue(15);
        value = rq.dequeue();
        assert value == 15 : "actual value: " + value;
        assert rq.size() == 0 : "actual value: " + rq.size();

        rq.enqueue(10);
        assert rq.size() == 1 : "actual value: " + rq.size();

        rq.enqueue(18);
        assert rq.size() == 2 : "actual value: " + rq.size();


        value = rq.dequeue();
        assert value == 10 || value == 18 : "actual value: " + value;

        value += rq.dequeue();
        assert value == 28 : "actual value: " + value;
    }
}
