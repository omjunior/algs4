
import java.util.Iterator;

/**
 * Class Deque
 * Implements a Deque of type Item
 * @author osvaldo
 * @param <Item>
 */
public class Deque<Item> implements Iterable<Item> {

    private class Node<Item> {

        private Item data;
        private Node<Item> prev, next;
    }

    private Node<Item> first, last;
    private int size;

    /**
     * Constructor
     */
    public Deque() {
        first = null;
        last = null;
        size = 0;
    }

    /**
     * Test if Deque is empty
     * @return
     */
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * Return the number of elements currently on the Deque
     * @return
     */
    public int size() {
        return size;
    }

    /**
     * Add element at the begining
     * @param item 
     * The item to be added
     */
    public void addFirst(Item item) {

        if (item == null) {
            throw new java.lang.NullPointerException();
        }

        Node<Item> n = new Node<>();
        n.data = item;
        n.prev = null;
        n.next = first;
        first = n;
        if (size == 0) {
            last = n;
        } else {
            n.next.prev = n;
        }
        size++;
    }

    /**
     * Add element at the end
     * @param item
     * The item to be added
     */
    public void addLast(Item item) {

        if (item == null) {
            throw new java.lang.NullPointerException();
        }

        Node<Item> n = new Node<>();
        n.data = item;
        n.prev = last;
        n.next = null;
        last = n;
        if (size == 0) {
            first = n;
        } else {
            n.prev.next = n;
        }
        size++;
    }

    /**
     * Remove the first element
     * @return The former first element
     */
    public Item removeFirst() {

        if (size == 0) {
            throw new java.util.NoSuchElementException();
        }

        Node<Item> n = first;
        first = n.next;
        size--;
        if (size == 0) {
            last = null;
        } else {
            first.prev = null;
        }

        return n.data;
    }

    /**
     * Remove the least element
     * @return The former last element
     */
    public Item removeLast() {

        if (size == 0) {
            throw new java.util.NoSuchElementException();
        }

        Node<Item> n = last;
        last = n.prev;
        size--;
        if (size == 0) {
            first = null;
        } else {
            last.next = null;
        }

        return n.data;
    }

    @Override
    public Iterator<Item> iterator() {
        return new DequeIterator();

    }

    private class DequeIterator implements Iterator<Item> {

        private Node<Item> itNext;

        public DequeIterator() {
            itNext = first;
        }

        @Override
        public boolean hasNext() {
            return itNext != null;
        }

        @Override
        public Item next() {
            if (!hasNext()) {
                throw new java.util.NoSuchElementException();
            }
            Item data = itNext.data;
            itNext = itNext.next;
            return data;
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }

    }

    /**
     *
     * @param args
     */
    public static void main(String[] args) {
    }

}
