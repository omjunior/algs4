
/**
 *
 * @author osvaldo
 */
public class RandomizedQueueTest {
    public static void main(String[] args) {
        
        int num = 10;        
        
        RandomizedQueue<Integer> rq = new RandomizedQueue();
        
        for (int i = 0; i < num; i++) {
            rq.enqueue(i);            
        }
        
        for (int i = 0; i < num; i++) {
            System.out.print(rq.dequeue() + " ");
        }
        System.out.println();


        for (int i = 0; i < num; i++) {
            rq.enqueue(i);            
        }
        
        for (Integer i : rq) {
            System.out.print(i + " ");
        }
        System.out.println();
                
        
        
    }
}
