import java.util.ArrayList;
import java.math.*;

public class PS {

    public ArrayList<Pair<Process, Integer>> Sort(ArrayList<Process> PD, int ContextSwitch, int AgeTime) {
        // deep copy PD into tPD
        ArrayList<Pair<Process, Integer>> retP = new ArrayList<Pair<Process, Integer>>();
        ArrayList<Process> tPD = new ArrayList<Process>();

        ArrayList<Integer> initialPriority = new ArrayList<Integer>();
        for (int i = 0; i < PD.size(); i++) {
            tPD.add(PD.get(i));
        }
        // Sorting on Burst Time
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
        for (int i = 0; i < tPD.size(); i++) {
            initialPriority.add(tPD.get(i).getPriority());
        }
        int counter = 0; // represents Time
        int dbg = 5; // debug counter
        int retpCounter = -1;
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
                    // aging
                    int tdiff = Math.max(counter - tPD.get(j).getArrivalTime(), 0);
                    if (tPD.get(j).getPriority() > 0) {
                        tPD.get(j).setPriority(Math.max(initialPriority.get(j)
                                - (int) Math.floor(tdiff / AgeTime)
                                , 0));
                    }
                    //+ (int) Math.floor(Math.max(tdiff - 1, 0)/ AgeTime)
                    if (jBest == -1) {
                        jBest = j;
                    } else {
                        if (tPD.get(j).getPriority() < tPD.get(jBest).getPriority()) {
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

            Pair<Process, Integer> tmp = new Pair<Process, Integer>(tPD.get(jBest), counter + maxgw); // start time
            retP.add(tmp);
            tPD.get(jBest).setBurstTime(tPD.get(jBest).getBurstTime() - maxgw);
            counter += maxgw;

            if (ContextSwitch > 0) {
                if (tPD.get(jBest).getBurstTime() == 0) {
                    retP.add(new Pair<Process, Integer>(new Process(-1, 0, 0, 0), counter + ContextSwitch));
                    counter += ContextSwitch;
                } else if (jnout != -1) {
                    if (tPD.get(jBest).getPriority() > tPD.get(jnout).getPriority()) {
                        retP.add(new Pair<Process, Integer>(new Process(-1, 0, 0, 0), counter + ContextSwitch));
                        counter += ContextSwitch;
                    }
                }
            }
            // Debugging
            System.out.println("counter: " + counter);
            // System.out.println("First: " + First);
            // System.out.println("jBest: " + jBest);
            // System.out.println("--------------------");
            IOHandler.PDprinter(tPD);
            // System.out.println("--------------------");
        }

        if (ContextSwitch > 0) {
            retP.remove(retP.size() - 1);
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