import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {
    private Chunk firstChunk, lastChunk;

    private class Chunk {
        private Item[] items;
        private Chunk previousChunk = null;
        private Chunk nextChunk = null;

        // head: points to the first slot with data
        // tail: points to the first empty slot (w/o data)
        // when this chunk is empty, head == tail
        // when this chunk is full, head == 0 and tail == items.length
        private int head = 0;
        private int tail = 0;


        public Chunk(int capacity, Chunk previous, Chunk next, Item itemToAdd) {
            if (previous != null && next != null) {
                throw new IllegalArgumentException("does not support insert chunks in the middle");
            }

            if (itemToAdd == null) {
                throw new NullPointerException("itemToAdd can not be null");
            }


            items = (Item[]) new Object[capacity];

            if (previous != null) {
                // This chunk is appended to a list of chunks
                if (previous.getNextChunk() != null) {
                    throw new RuntimeException("This chunk should be the last one in the link list");
                }

                previous.nextChunk = this;
                this.previousChunk = previous;
                this.addLast(itemToAdd);
            } else if (next != null) {
                // This chunk is inserted in the beginning of a list of chunks
                if (next.getPreviousChunk() != null) {
                    throw new RuntimeException("This chunk should be the first one in the link list");
                }

                next.previousChunk = this;
                this.nextChunk = next;
                this.items[items.length - 1] = itemToAdd;
                this.head = this.items.length - 1;
                this.tail = this.items.length;
            } else {
                // This chunk is the very first chunk in the link list
                this.addLast(itemToAdd);
            }
        }

        public Chunk getPreviousChunk() {
            return previousChunk;
        }

        public Chunk getNextChunk() {
            return nextChunk;
        }

        public int getCapacity() {
            return items.length;
        }

        public int size() {
            return tail - head;
        }

        public boolean isEmpty() {
            return size() == 0;
        }

        public boolean isFull() {
            return size() == items.length;
        }

        public boolean allowAddLast() {
            return tail < items.length;
        }

        public boolean allowAddFirst() {
            return head > 0;
        }

        public void addLast(Item i) {
            assertNotNull(i);
            assertAllowAddLast();

            items[tail++] = i;
        }

        public void addFirst(Item i) {
            assertNotNull(i);
            assertAllowAddFirst();
            items[--head] = i;
        }

        public Item removeLast() {
            assertNotEmpty();
            Item result = items[--tail];
            items[tail] = null;
            return result;
        }

        public Item removeFirst() {
            assertNotEmpty();
            Item result = items[head];
            items[head++] = null;
            return result;
        }

        // no need to compact
        /*
        private void compact() {
            // shift items to the left of the arrays
            int newCursor = 0;
            int oldCursor = head;
            while (newCursor < items.length) {
                if (oldCursor < tail) {
                    items[newCursor++] = items[oldCursor++];
                } else {
                    items[newCursor++] = null;
                }
                newCursor ++;
            }

            // calibrate the head and tail value
            tail = size();
            head = 0;
        }
        */

        private void assertNotNull(Item item) {
            if (item == null) {
                throw new NullPointerException("item can not be null");
            }
        }

        private void assertNotEmpty() {
            if (isEmpty()) {
                throw new NoSuchElementException("Chunk is empty");
            }
        }

        private void assertAllowAddLast() {
            if (!allowAddLast()) {
                throw new RuntimeException("Can not add last");
            }
        }

        private void assertAllowAddFirst() {
            if (!allowAddFirst()) {
                throw new RuntimeException("Can not add first");
            }
        }
    }

    public Deque() {
        firstChunk = null;
        lastChunk = null;
    }

    public boolean isEmpty() {
        Chunk cursor = firstChunk;
        while (cursor != null) {
            if (!cursor.isEmpty()) {
                return false;
            } else {
                cursor = cursor.getNextChunk();
            }
        }
        return true;
    }

    public int size() {
        int totalSize = 0;
        Chunk cursor = firstChunk;
        while (cursor != null) {
            totalSize += cursor.size();
            cursor = cursor.getNextChunk();
        }
        return totalSize;
    }

    public void addFirst(Item item) {
        if (firstChunk == null) {
            firstChunk = new Chunk(1, null, null, item);
            lastChunk = firstChunk;
        } else {
            if (!firstChunk.allowAddFirst()) {
                firstChunk = new Chunk(firstChunk.getCapacity()*2, null, firstChunk, item);
            } else {
                firstChunk.addFirst(item);
            }
        }
    }

    public void addLast(Item item) {
        if (lastChunk == null) {
            firstChunk = new Chunk(1, null, null, item);
            lastChunk = firstChunk;
        } else {
            if (!lastChunk.allowAddLast()) {
                lastChunk = new Chunk(lastChunk.getCapacity()*2, lastChunk, null, item);
            } else {
                lastChunk.addLast(item);
            }
        }
    }

    public Item removeFirst() {
        assertNotEmpty();

        Item result = firstChunk.removeFirst();

        // remove the chunk if it's no longer in use
        if (firstChunk.isEmpty()) {
            Chunk oldFirstChunk = firstChunk;

            firstChunk = oldFirstChunk.getNextChunk();
            if (firstChunk == null) {
                lastChunk = null;
            } else {
                firstChunk.previousChunk = null;
            }

            oldFirstChunk.previousChunk = null;
            oldFirstChunk.nextChunk = null;
            oldFirstChunk.items = null;
        }

        return result;
    }

    public Item removeLast() {
        assertNotEmpty();

        Item result = lastChunk.removeLast();
        if (lastChunk.isEmpty()) {
            Chunk oldLastChunk = lastChunk;
            lastChunk = oldLastChunk.getPreviousChunk();

            if (lastChunk == null) {
                firstChunk = null;
            } else {
                lastChunk.nextChunk = null;
            }

            oldLastChunk.previousChunk = null;
            oldLastChunk.nextChunk = null;
            oldLastChunk.items = null;
        }
        return result;
    }

    @Override
    public Iterator<Item> iterator() {
        Iterator<Item> it = new Iterator<Item>() {
            private Chunk currentChunk = firstChunk;
            private int cursor = (currentChunk == null) ? 0 : currentChunk.head;

            @Override
            public boolean hasNext() {
                return currentChunk != null && cursor < currentChunk.tail;
            }

            @Override
            public Item next() {
                if (!hasNext()) {
                    throw new NoSuchElementException("No more next element");
                } else {
                    Item result = currentChunk.items[cursor++];

                    if (cursor == currentChunk.tail) {
                        currentChunk = currentChunk.getNextChunk();
                        cursor = (currentChunk == null) ? 0 : currentChunk.head;
                    }

                    return result;
                }
            }

            @Override
            public void remove() {
                throw new UnsupportedOperationException();
            }
        };

        return it;
    }

    private void assertNotEmpty() {
        if (isEmpty()) {
            throw new NoSuchElementException("Dequeue is empty");
        }
    }
}
