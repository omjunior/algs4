
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import java.util.NoSuchElementException;


/**
 *
 * @author osvaldo
 */
public class Permutation {
    public static void main(String[] args) {
        RandomizedQueue<String> rq = new RandomizedQueue<>();
        
        // readString until there are no more Strings
        while (true) {
            try {
                rq.enqueue(StdIn.readString());
            } catch (NoSuchElementException e) {
                break;
            }
        }
        
        for (int i = 0; i < Integer.parseInt(args[0]); i++) {
            StdOut.println(rq.dequeue());
        }
        
    }
}
