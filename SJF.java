import java.util.ArrayList;

public class SJF {

    public ArrayList<Pair<Process, Integer>> Sort(ArrayList<Process> PD, int ContextSwitch) {
        // deep copy PD into tPD
        ArrayList<Pair<Process, Integer>> retP = new ArrayList<Pair<Process, Integer>>();
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
            // Debugging
            // System.out.println("counter: " + counter);
            // System.out.println("First: " + First);
            // System.out.println("jBest: " + jBest);
            // System.out.println("--------------------");
            // prog.PDprinter(tPD);
            // System.out.println("--------------------");
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
        
        for (int i = 0; i < PD.size(); i++) {
            tPD.add(PD.get(i));
        }
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
                System.out.println(proc.get(i).Second());
                System.out.println( tPD.get(proc.get(i).First().getNumber()-1).getBurstTime());
                waitTime[proc.get(i).First().getNumber()-1] = proc.get(i).Second() - tPD.get(proc.get(i).First().getNumber()-1).getBurstTime();
                m[proc.get(i).First().getNumber()-1] = 0;
                
            }
              
        }
        for(int i = 0; i < waitTime.length; i++){
            System.out.println("Process no."+tPD.get(i).getNumber()+" waitTime = " + waitTime[i]);
        }
    }
    // Method to find the waiting time for all
    // processes
    // static void findWaitingTime(ArrayList<Process> proc, int n,
    //                                  int wt[])
    // {
    //     int rt[] =new int[n];  
          
    //     // Copy the burst time into rt[]
    //     for (int i = 0; i < n; i++){
    //       rt[i]=proc.get(i).getBurstTime();    
    //     }
       
    //     int complete = 0, t = 0, minm = Integer.MAX_VALUE;
    //     int shortest = 0, finish_time;
    //     boolean check = false;
       
    //     // Process until all processes gets
    //     // completed
    //     while (complete != n) {
       
    //         // Find process with minimum
    //         // remaining time among the
    //         // processes that arrives till the
    //         // current time`
    //         for (int j = 0; j < n; j++) 
    //         {
    //             if ((proc.get(j).getArrivalTime() <= t) &&
    //               (rt[j] < minm) && rt[j] > 0) {
    //                 minm = rt[j];
    //                 shortest = j;
    //                 check = true;
    //             }
    //         }
       
    //         if (check == false) {
    //             t++;
    //             continue;
    //         }
       
    //         // Reduce remaining time by one
    //         rt[shortest]--;
       
    //         // Update minimum
    //         minm = rt[shortest];
    //         if (minm == 0)
    //             minm = Integer.MAX_VALUE;
       
    //         // If a process gets completely
    //         // executed
    //         if (rt[shortest] == 0) {
       
    //             // Increment complete
    //             complete++;
    //             check = false;
       
    //             // Find finish time of current
    //             // process
    //             finish_time = t + 1;
       
    //             // Calculate waiting time
    //             wt[shortest] = finish_time -
    //                          proc.get(shortest).getBurstTime() -
    //                          proc.get(shortest).getArrivalTime();
       
    //             if (wt[shortest] < 0)
    //                 wt[shortest] = 0;
    //         }
    //         // Increment time
    //         t++;
    //     }
    // }
    // static void findTurnAroundTime(ArrayList<Process> proc, int n,
    //                         int wt[], int tat[])
    // {
    //     // calculating turnaround time by adding
    //     // burstTime[i] + waittime[i]
    //     for (int i = 0; i < n; i++)
    //         tat[i] = proc.get(i).getBurstTime() + wt[i];
    // }
       
    // // Method to calculate average time
    // static void findavgTime( ArrayList<Process> proc, int n)
    // {
    //     int wt[] = new int[n], tat[] = new int[n];
    //     int  total_wt = 0, total_tat = 0;
       
    //     // Function to find waiting time of all
    //     // processes
    //     findWaitingTime(proc, n, wt);
       
    //     // Function to find turn around time for
    //     // all processes
    //     findTurnAroundTime(proc, n, wt, tat);
       
    //     // Display processes along with all
    //     // details
    //     System.out.println("Processes " +
    //                        " Burst time " +
    //                        " Waiting time " +
    //                        " Turn around time");
       
    //     // Calculate total waiting time and
    //     // total turnaround time
    //     for (int i = 0; i < n; i++) {
    //         total_wt = total_wt + wt[i];
    //         total_tat = total_tat + tat[i];
    //         System.out.println(" " + proc.get(i).getNumber() + "\t\t"
    //                          + proc.get(i).getBurstTime() + "\t\t " + wt[i]
    //                          + "\t\t" + tat[i]);
    //     }
       
    //     System.out.println("Average waiting time = " +
    //                       (float)total_wt / (float)n);
    //     System.out.println("Average turn around time = " +
    //                        (float)total_tat / (float)n);
    // }
}
