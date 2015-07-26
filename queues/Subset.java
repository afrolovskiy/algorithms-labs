import java.util.Iterator;

public class Subset {
    public static void main(String[] args) {
        if (args.length < 1) {
            throw new IllegalArgumentException();
        }
        int k = Integer.parseInt(args[0]);

        RandomizedQueue<String> queue = new RandomizedQueue<String>();
        int count = 0;
        while (count < k && !StdIn.isEmpty()) {
            queue.enqueue(StdIn.readString());
            count++;
        }

        Iterator<String> iter = queue.iterator();
        while (iter.hasNext()) {
            StdOut.printf(iter.next() + "\n");
        }
    }
}