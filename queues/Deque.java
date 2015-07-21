import java.util.Iterator;

public class Deque<Item> implements Iterable<Item> {
    private Node first = null;
    private Node last = null;
    private int count = 0;

    private class Node {
        private Item item;
        private Node next;
        private Node prev;
    }

    public Deque() { }

    public boolean isEmpty() {
        return first == null;
    }

    public int size() {
        return count;
    }

    public void addFirst(Item item) {
        validateItem(item);

        Node newFirst = new Node();
        newFirst.item = item;
        newFirst.next = first;
        newFirst.prev = null;

        if (first == null) {
            last = newFirst;
        } else {
            first.prev = newFirst;
        }

        first = newFirst;
        count++;
    }

    public void addLast(Item item) {
        validateItem(item);

        Node newLast = new Node();
        newLast.item = item;
        newLast.next = null;
        newLast.prev = last;

        if (last == null) {
            first = newLast;
        } else {
            last.next = newLast;
        }

        last = newLast;
        count++;
    }

    private void validateItem(Item item) {
        if (item == null) {
            throw new NullPointerException();
        }
    }

    public Item removeFirst() {
        validateSize();

        Node nextItem = first.next;
        if (nextItem == null) {
            last = null;
        } else {
            nextItem.prev = null;
        }

        Node oldFirst = first;
        oldFirst.next = null;

        first = nextItem;
        count--;

        return oldFirst.item;
    }

    public Item removeLast() {
        validateSize();

        Node prevItem = last.prev;
        if (prevItem == null) {
            first = null;
        } else  {
            prevItem.next = null;
        }

        Node oldLast = last;
        oldLast.prev = null;

        last = prevItem;
        count--;

        return oldLast.item;
    }

    private void validateSize() {
        if (isEmpty()) {
            throw new java.util.NoSuchElementException();
        }
    }

    private class DequeIterator implements Iterator<Item> {
        public boolean hasNext() {
            return !isEmpty();
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }

        public Item next() {
            return removeFirst();
        }
    }

    public Iterator<Item> iterator() {
        return new DequeIterator();
    }

    public static void main(String[] args) {
        Deque<Integer> deque = new Deque<Integer>();

        assert deque.isEmpty();

        deque.addFirst(1);
        assert !deque.isEmpty();

        assert deque.removeFirst() == 1;
        assert deque.isEmpty();

        deque.addFirst(2);
        assert !deque.isEmpty();

        assert deque.removeLast() == 2;
        assert deque.isEmpty();

        deque.addLast(3);
        assert !deque.isEmpty();

        assert deque.removeFirst() == 3;
        assert deque.isEmpty();

        deque.addLast(4);
        assert !deque.isEmpty();

        assert deque.removeLast() == 4;
        assert deque.isEmpty();

        deque.addFirst(2);
        deque.addFirst(1);
        deque.addLast(3);
        deque.addLast(4);

        assert deque.removeFirst() == 1;
        assert deque.removeFirst() == 2;
        assert deque.removeFirst() == 3;
        assert deque.removeFirst() == 4;

        deque.addLast(3);
        deque.addLast(4);
        deque.addFirst(2);
        deque.addFirst(1);

        assert deque.removeLast() == 4;
        assert deque.removeLast() == 3;
        assert deque.removeLast() == 2;
        assert deque.removeLast() == 1;
    }
}
