import java.util.ArrayList;

public class SJF {

    public ArrayList<Pair> Sort(ArrayList<Process> PD) {
        // deep copy PD into tPD
        ArrayList<Pair> retP = new ArrayList<Pair>();
        ArrayList<Process> tPD = new ArrayList<Process>();
        for (int i = 0; i < PD.size(); i++) {
            tPD.add(PD.get(i));
        }
        // Sorting on Burst Time
        for (int i = 0; i < tPD.size(); i++) {
            for (int j = 0; j < tPD.size(); j++) {
                if (tPD.get(i).getBurstTime() < tPD.get(j).getBurstTime()) {
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
        int counter = 0;    //represents Time
        int jBest = -1;     //the best process to run that has arrived so far
        int jnout = -1;     //the process tht is arriving next
        boolean work = true;    //loop exit condition
        int First = -1;         //if not -1 then there are still processes to run
        int dbg = 5;        //debug counter
        while (work) {
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
                work = false;
                break;
            }
            if (jBest == -1) {
                counter = tPD.get(First).getArrivalTime();
                continue;
            }

            if (jnout != -1) {
                int maxgw = min(tPD.get(jBest).getBurstTime(),
                        tPD.get(jnout).getArrivalTime() - counter);
                Pair tmp = new Pair(tPD.get(jBest), counter + maxgw); // meed to account for wait time start time
                retP.add(tmp);
                tPD.get(jBest).setBurstTime(tPD.get(jBest).getBurstTime() - maxgw);
                counter += maxgw;
            } else {
                int maxgw = tPD.get(jBest).getBurstTime();
                Pair tmp = new Pair(tPD.get(jBest), counter + maxgw); // meed to account for wait time start time
                retP.add(tmp);
                tPD.get(jBest).setBurstTime(tPD.get(jBest).getBurstTime() - maxgw);
                counter += maxgw;
            }
            // }
            // System.out.println("counter: " + counter);
            // System.out.println("First: " + First);
            // System.out.println("jBest: " + jBest);
            // System.out.println("--------------------");
            // prog.PDprinter(tPD);
            // System.out.println("--------------------");
            jBest = -1;
            First = -1;
            jnout = -1;

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
