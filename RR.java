import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class RR {
    public Queue<Pair> run(ArrayList<Process> PD, int RRTQ, int ContextSwitch) {
        // create queue of pair
        Queue<Pair> q2 = new LinkedList<Pair>();
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
                Pair tmp = new Pair(p, counter);
                System.out.println("counter " + counter);
                q2.add(tmp);
            } else {
                counter += p.getBurstTime();
                Pair tmp = new Pair(p, counter);
                q2.add(tmp);
            }
            if (q.peek() != p) {
                counter += cst;
                Pair tmp = new Pair(new Process(-1, 0, 0, 0), counter);
                q2.add(tmp);
            }
        }
        return q2;

    }

    public int avgWaitTimeRR(Queue<Pair> l, int n) {
        int sum = 0;
        int lastCounter = 0;
        for (int j = 0; j < n; j++) {
            for (int i = 0; i < l.size(); i++) {
                if(l.poll().getProcess().getNumber() == j){
                    lastCounter = j;
                }
            }
        }
        return 0;
    }
}
