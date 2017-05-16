
import edu.princeton.cs.algs4.StdRandom;
import java.util.Iterator;

/**
 * A queue that dequeues in random order
 *
 * @author osvaldo
 * @param <Item>
 */
public class RandomizedQueue<Item> implements Iterable<Item> {

    private Item[] queue;
    private int size;

    public RandomizedQueue() {
        queue = (Item[]) new Object[2];
        size = 0;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return size;
    }

    private void resize(int newSize) {
        Item[] newQueue = (Item[]) new Object[newSize];
        System.arraycopy(queue, 0, newQueue, 0, size);
        queue = newQueue;
    }

    public void enqueue(Item item) {

        if (item == null) {
            throw new java.lang.NullPointerException();
        }

        // resize if needed
        if (size == queue.length) {
            resize(2 * queue.length);
        }

        queue[size] = item;
        size++;
    }

    public Item dequeue() {

        if (size == 0) {
            throw new java.util.NoSuchElementException();
        }

        // resize if needed
        if (size == queue.length / 4) {
            resize(queue.length / 2);
        }

        int itemIndex = StdRandom.uniform(size);
        Item item = queue[itemIndex];

        // swap removed item with last item
        size--;
        queue[itemIndex] = queue[size];
        queue[size] = null;

        return item;
    }

    public Item sample() {
        if (size == 0) {
            throw new java.util.NoSuchElementException();
        }
        return queue[StdRandom.uniform(size)];
    }

    @Override
    public Iterator<Item> iterator() {
        return new RandomizedQueueIterator();
    }

    private class RandomizedQueueIterator implements Iterator<Item> {

        private final int[] idxQueue;
        private int nextIdx;

        public RandomizedQueueIterator() {
            idxQueue = new int[size];
            for (int i = 0; i < idxQueue.length; i++) {
                idxQueue[i] = i;
            }
            StdRandom.shuffle(idxQueue);
            nextIdx = 0;
        }

        @Override
        public boolean hasNext() {
            return nextIdx < idxQueue.length;
        }

        @Override
        public Item next() {
            if (!hasNext()) {
                throw new java.util.NoSuchElementException();
            }
            return queue[idxQueue[nextIdx++]];
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    public static void main(String[] args) {
    }
}
