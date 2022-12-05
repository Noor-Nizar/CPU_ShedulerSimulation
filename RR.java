import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class RR {
    public Queue<Pair<Process, Integer>> run(ArrayList<Process> PD, int RRTQ, int ContextSwitch) {
        // create queue of pair
        Queue<Pair<Process, Integer>>  q2 = new LinkedList<Pair<Process, Integer>> ();
        Queue<Process> q = new LinkedList<Process>();

        for (int i = 0; i < PD.size(); i++) {
            q.add(PD.get(i));
            System.out.println("burst" + PD.get(i).getBurstTime());
        }
        int counter = 0;
        int cst = ContextSwitch;

        while (!q.isEmpty()) {
            System.out.println("counter " + counter);
            Process p = q.poll();
            if (p.getArrivalTime() > counter) {
                q.add(p);
                continue;
            }
            if (p.getBurstTime() > RRTQ) {
                p.setBurstTime(p.getBurstTime() - RRTQ);
                q.add(p);
                counter += RRTQ;
                Pair<Process, Integer> tmp = new Pair<Process, Integer>(p, counter);
                System.out.println("counter " + counter);
                q2.add(tmp);
            } else {
                counter += p.getBurstTime();
                Pair<Process, Integer> tmp = new Pair<Process, Integer>(p, counter);
                q2.add(tmp);
            }
            if (q.peek() != p) {
                counter += cst;
                Pair<Process, Integer> tmp = new Pair<Process, Integer>(new Process(-1, 0, 0, 0), counter);
                q2.add(tmp);
            }
        }
        return q2;

    }

    public int avgWaitTimeRR(Queue<Pair<Process, Integer>> l, int n) {
        int sum = 0;
        int lastCounter = 0;
        for (int j = 0; j < n; j++) {
            for (int i = 0; i < l.size(); i++) {
                if(l.poll().First().getNumber() == j){
                    lastCounter = j;
                }
            }
        }
        return 0;
    }
}
