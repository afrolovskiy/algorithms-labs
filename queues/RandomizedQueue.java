import java.lang.Integer;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class RandomizedQueue<Item> implements Iterable<Item> {
    private Node first = null;
    private int count = 0;

    private class Node {
        private Item item;
        private Node next;
        private Node prev;
    }

    public RandomizedQueue() { }

    public boolean isEmpty() {
        return first == null;
    }

    public int size() {
        return count;
    }

    public void enqueue(Item item) {
        if (item == null) {
            throw new NullPointerException();
        }

        Node newFirst = new Node();
        newFirst.next = first;
        newFirst.item = item;

        if (first != null) {
            first.prev = newFirst;
        }

        first = newFirst;
        count++;
    }

    private void validateSize() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
    }

    public Item dequeue() {
        validateSize();

        int num = StdRandom.uniform(size());
        Node current = first;
        for (int i = 0; i < num; i++) {
            current = current.next;
        }

        Node prevNode = current.prev;
        Node nextNode = current.next;
        if (prevNode == null) {
            first = nextNode;
        } else {
            prevNode.next = nextNode;
        }
        if (nextNode != null) {
            nextNode.prev = prevNode;
        }

        current.next = null;
        current.prev = null;
        count--;
        return current.item;
    }

    public Item sample() {
        validateSize();

        int num = StdRandom.uniform(size());
        Node current = first;
        for (int i = 0; i < num; i++) {
            current = current.next;
        }

        return current.item;
    }

    private class RandomizedQueueIterator implements Iterator<Item> {
        private Item[] items;
        private int currentIdx = 0;

        public RandomizedQueueIterator() {
            items = (Item[]) new Object[size()];

            // fill items
            Node current = first;
            int idx = 0;
            while (current != null) {
                items[idx] = current.item;
                current = current.next;
                idx++;
            }

            // shuffle items
            for (int i = 0; i < size(); i++) {
                int rndIdx = StdRandom.uniform(size());
                Item tmp = items[i];
                items[i] = items[rndIdx];
                items[rndIdx] = tmp;

            }
        }

        public boolean hasNext() {
            return (currentIdx < items.length);
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }

        public Item next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }

            Item item = items[currentIdx];
            currentIdx++;
            return item;
        }
    }

    public Iterator<Item> iterator() {
        return new RandomizedQueueIterator();
    }

    public static void main(String[] args) {
        RandomizedQueue<Integer> queue = new RandomizedQueue<Integer>();

        assert queue.isEmpty();
        assert queue.size() == 0;

        queue.enqueue(1);
        assert !queue.isEmpty();
        assert queue.size() == 1;

        assert queue.sample() == 1;
        assert !queue.isEmpty();
        assert queue.size() == 1;

        assert queue.dequeue() == 1;
        assert queue.isEmpty();
        assert queue.size() == 0;

        queue.enqueue(1);
        queue.enqueue(2);
        assert !queue.isEmpty();
        assert queue.size() == 2;

        int sample = queue.sample();
        assert (sample == 1) || (sample == 2);
        assert !queue.isEmpty();
        assert queue.size() == 2;

        int item = queue.dequeue();
        assert (item == 1) || (item == 2);
        assert !queue.isEmpty();
        assert queue.size() == 1;

        item = queue.dequeue();
        assert (item == 1) || (item == 2);
        assert queue.isEmpty();
        assert queue.size() == 0;

        queue.enqueue(1);
        queue.enqueue(2);
        Iterator<Integer> iter = queue.iterator();
        int iterated = 0;
        while (iter.hasNext()) {
            iter.next();
            iterated++;
        }
        assert iterated == 2;
        assert queue.size() == 2;

        assert false;
    }
}