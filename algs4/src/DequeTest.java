
import edu.princeton.cs.algs4.StdRandom;
import java.util.LinkedList;

/**
 *
 * @author osvaldo
 */
public class DequeTest {

    public static void main(String[] args) {

        int nbr = 1000;

        LinkedList<Integer> list = new LinkedList();
        for (int i = 0; i < nbr; i++) {
            list.add(StdRandom.uniform(1000));
        }

        // test add and remove
        Deque<Integer> d = new Deque();
        for (Integer i : list) {
            d.addLast(i);
        }
        for (Integer i : list) {
            if (!i.equals(d.removeFirst())) {
                throw new RuntimeException("Wrong number on removeFirst");
            }
        }

        System.out.println(".");

        // test add and iterator
        for (Integer i : list) {
            d.addLast(i);
        }
        LinkedList<Integer> list2 = new LinkedList();
        for (Integer i : d) {
            list2.add(i);
        }
        if (!list.equals(list2)) {
            throw new RuntimeException("Wrong number on iterator");
        }

        System.out.println("END of test");
    }
}
