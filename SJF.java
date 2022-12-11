import java.util.ArrayList;

public class SJF {

    public ArrayList<Pair<Process, Integer>> Sort(ArrayList<Process> PD, int ContextSwitch) {
        // deep copy PD into tPD
        ArrayList<Pair<Process, Integer>> retP = new ArrayList<Pair<Process, Integer>>();
        ArrayList<Process> tPD = new ArrayList<Process>();
        tPD = prog.DeepCopy(PD); 
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
                if(tPD.get(jBest).getBurstTime() == 0){
                    retP.add(new Pair<Process, Integer>(new Process(-1, 0, 0, 0), counter + ContextSwitch));
                    counter += ContextSwitch;
                }
                else if (jnout != -1) {
                    if (tPD.get(jBest).getBurstTime() > tPD.get(jnout).getBurstTime()) {
                        retP.add(new Pair<Process, Integer>(new Process(-1, 0, 0, 0), counter + ContextSwitch));
                        counter += ContextSwitch;
                    }
                }
            }
            // Debugging
            // System.out.println("counter: " + counter);
            // System.out.println("First: " + First);
            // System.out.println("jBest: " + jBest);
            // System.out.println("--------------------");
            // prog.PDprinter(tPD);
            // System.out.println("--------------------");
        }
        if(ContextSwitch > 0){
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

static void findWaitingTime(ArrayList<Pair<Process, Integer>> proc,ArrayList<Process> PD){
        
        ArrayList<Process> tPD = new ArrayList<Process>();
        
        tPD = prog.DeepCopy(PD);
        // for(int i=0; i<proc.size(); i++){
        //     proc.get(i).setFirst(p.get(i).First());
        //     proc.get(i).setSecond(p.get(i).Second());

        // }
        
        int waitTime[]=new int[tPD.size()];
        int m[]=new int[tPD.size()];
        for(int i=0; i<tPD.size(); i++){
           m[i]=-1;
          
        }
        for(int i = proc.size()-1; i > 0 ; i--){
           
            if(m[proc.get(i).First().getNumber()-1]!= 0){
                waitTime[proc.get(i).First().getNumber()-1] = proc.get(i).Second() - PD.get(proc.get(i).First().getNumber()-1).getBurstTime()-PD.get(proc.get(i).First().getNumber()-1).getArrivalTime();
                m[proc.get(i).First().getNumber()-1] = 0;
                
            }
              
        }
        for(int i = 0; i < waitTime.length; i++){
            System.out.println("Process no."+tPD.get(i).getNumber()+" waitTime = " + waitTime[i]);
        }
    }
}
    