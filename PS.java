import java.util.ArrayList;

public class PS {
    public ArrayList<Pair<Process, Integer>> run(ArrayList<Process> PD, int ContextSwitch) {
        // deep copy PD into tPD
        ArrayList<Pair<Process, Integer>> retP = new ArrayList<Pair<Process, Integer>>();
        ArrayList<Process> tPD = new ArrayList<Process>();
        for (int i = 0; i < PD.size(); i++) {
            tPD.add(PD.get(i));
        }
        // Sorting on Priority
        for (int i = 0; i < tPD.size(); i++) {
            for (int j = 0; j < tPD.size(); j++) {
                if (tPD.get(i).getPriority() < tPD.get(j).getPriority()) {
                    Process tmp = tPD.get(i);
                    tPD.set(i, tPD.get(j));
                    tPD.set(j, tmp);
                }
            }
        }
        // sorting on Arrival Time
        for (int i = 0; i < tPD.size(); i++) {
            for (int j = 0; j < tPD.size(); j++) {
                if (tPD.get(i).getArrivalTime() < tPD.get(j).getArrivalTime()) {
                    Process tmp = tPD.get(i);
                    tPD.set(i, tPD.get(j));
                    tPD.set(j, tmp);
                }
            }
        }
        int counter = 0; // represents Time
        int dbg = 5; // debug counter
        while (true) {
            int jBest = -1; // the best process to run that has arrived so far
            int jnout = -1; // the process tht is arriving next
            int First = -1; // if not -1 then there are still processes to run
            for (int j = 0; j < tPD.size(); j++) {
                if (tPD.get(j).getBurstTime() == 0) {
                    continue;
                } else {
                    if (First == -1) {
                        First = j;
                    }
                }

                if (tPD.get(j).getArrivalTime() <= counter) {
                    if (jBest == -1) {
                        jBest = j;
                    } else {
                        if (tPD.get(j).getBurstTime() < tPD.get(jBest).getBurstTime()) {
                            jBest = j;
                        }
                    }
                } else {
                    if (jnout == -1) {
                        jnout = j;
                    }
                }

            }
            if (First == -1) {
                break;
            }
            if (jBest == -1) {
                retP.add(new Pair<Process, Integer>(new Process(-1, 0, 0, 0), tPD.get(jnout).getArrivalTime()));
                counter = tPD.get(First).getArrivalTime();
                continue;
            }
            int maxgw;
            if (jnout != -1) {
                maxgw = min(tPD.get(jBest).getBurstTime(),
                        tPD.get(jnout).getArrivalTime() - counter);
            } else {
                maxgw = tPD.get(jBest).getBurstTime();
            }
            Pair<Process, Integer> tmp = new Pair<Process, Integer>(tPD.get(jBest), counter + maxgw);                                                      // start time
            retP.add(tmp);
            tPD.get(jBest).setBurstTime(tPD.get(jBest).getBurstTime() - maxgw);
            counter += maxgw;
            // context switching
            if (ContextSwitch > 0) {
                retP.add(new Pair<Process, Integer>(new Process(-1, 0, 0, 0), counter + ContextSwitch));
                counter += ContextSwitch;
            }
        }    
        return retP;

    }
    private int min(int burstTime, int burstTime2) {
        if (burstTime < burstTime2) {
            return burstTime;
        } else {
            return burstTime2;
        }
    }
}
/* P0       AT       BT      P                
   P1       0        7       2
   P2       2        4       1
   P3       4        2       4
   P4       6        3       3

P1 / P2 / P1 / P4 / P3
0  /2   /6   /11   /14  /16




*/
