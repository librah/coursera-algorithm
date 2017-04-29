import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class RandomizedQueue<Item> implements Iterable<Item> {
    private Node first, last;
    private int size;

    private class Node {
        private Item item;
        private Node next;
    }

    // construct an empty randomized queue
    public RandomizedQueue() {
        first = null;
        last = null;
        size = 0;
    }

    // is the queue empty?
    public boolean isEmpty() {
        return size == 0;
    }

    // return the number of items on the queue
    public int size() {
        return size;
    }

    // add the item
    public void enqueue(Item item) {
        assertNotNull(item);
        Node oldLast = last;
        last = new Node();
        last.item = item;
        last.next = null;
        if (isEmpty()) {
            first = last;
        } else {
            oldLast.next = last;
        }
        size++;
    }

    // remove and return a random item
    public Item dequeue() {
        assertNotEmpty();
        int offset = StdRandom.uniform(0, size);

        Node cursor = first;
        Node previous = null;
        while (offset > 0) {
            previous = cursor;
            cursor = cursor.next;
            offset--;
        }

        if (previous != null) {
            previous.next = cursor.next;
        } else {
            first = cursor.next;
        }

        if (cursor.next == null) {
            last = previous;
        }

        size--;
        return cursor.item;
    }

    // return (but do not remove) a random item
    public Item sample() {
        assertNotEmpty();

        int offset = StdRandom.uniform(0, size);

        Node cursor = first;
        while (offset > 0) {
            cursor = cursor.next;
            offset--;
        }

        return cursor.item;
    }

    // return an independent iterator over items in random order
    @Override
    public Iterator<Item> iterator() {

        return new Iterator<Item>() {
            int cursor = 0;
            int indexes[] = new int[size];

            {
                for (int i = 0; i < indexes.length; i++) {
                    indexes[i] = i;
                }
                StdRandom.shuffle(indexes);
            }

            @Override
            public boolean hasNext() {
                return cursor < size;
            }

            @Override
            public Item next() {
                if (!hasNext()) {
                    throw new NoSuchElementException("No more next element");
                } else {
                    Node currentItem = first;
                    int count = indexes[cursor++];
                    while (count > 0) {
                        currentItem = currentItem.next;
                        count--;
                    }
                    return currentItem.item;
                }
            }

            @Override
            public void remove() {
                throw new UnsupportedOperationException();
            }
        };
    }

    private void assertNotNull(Item item) {
        if (item == null) {
            throw new NullPointerException("item is null");
        }
    }

    private void assertNotEmpty() {
        if (isEmpty()) {
            throw new NoSuchElementException("queue is empty");
        }
    }
}

