import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.lang.Math;
public class AG {
    public int time=0 ;
    
    public  ArrayList<Pair<Process, Integer>> run(ArrayList<Process> PD){
        Queue<Pair<Process, Integer>>  q2 = new LinkedList<Pair<Process, Integer>> ();
        Process cuProcess;
        Process prev;
        Queue<Process> q = new LinkedList<Process>();
        ArrayList<Process> tPD = new ArrayList<Process>();
        for (int i = 0; i < PD.size(); i++) {
            tPD.add(PD.get(i));
        }
        for (int i = 0; i < tPD.size(); i++) {
            for (int j = 0; j < tPD.size(); j++) {
                if (tPD.get(i).getArrivalTime() < tPD.get(j).getArrivalTime()) {
                    Process tmp = tPD.get(i);
                    tPD.set(i, tPD.get(j));
                    tPD.set(j, tmp);
                }
            }
        }   
        cuProcess = tPD.get(0);

        while (true) {
            for (int i = 0; i < tPD.size(); i++) {
            for (int j = 0; j < tPD.size(); j++) {
                if (tPD.get(i).getArrivalTime() < tPD.get(j).getArrivalTime()) {
                    Process tmp = tPD.get(i);
                    tPD.set(i, tPD.get(j));
                    tPD.set(j, tmp);
                }
            }
            }

        int nxtTime = Math.min((int)Math.ceil( cuProcess.getQuantum()*0.25), cuProcess.getBurstTime());
        time += nxtTime;
        cuProcess.setBurstTime(cuProcess.getBurstTime()-nxtTime);
        
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

            if (tPD.get(j).getArrivalTime() <= time) {
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
        prev = cuProcess;
        cuProcess = tPD.get(jBest);
        if(prev.getNumber() == cuProcess.getNumber()){
            time += (int)Math.ceil(cuProcess.getQuantum() *0.5- cuProcess.getQuantum()*0.25);
            cuProcess.setBurstTime(cuProcess.getBurstTime()-(int)Math.ceil( cuProcess.getQuantum()*0.25));
            jBest = -1; // the best process to run that has arrived so far
            jnout = -1; // the process tht is arriving next
            First = -1;
            for (int j = 0; j < tPD.size(); j++) {
                if (tPD.get(j).getBurstTime() == 0) {
                    continue;
                } else {
                    if (First == -1) {
                        First = j;
                    }
                }

                if (tPD.get(j).getArrivalTime() <= time) {
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
            prev = cuProcess; 
            cuProcess = tPD.get(jBest);
            if (prev.getNumber() == cuProcess.getNumber()) {

                // time+= (int)Math.ceil( cuProcess.getQuantum() *0.5);
                // int t = cuProcess.getBurstTime();
                // cuProcess.setBurstTime(0);
                // jBest = -1; // index process we are working on
                int nextLeast = 0;
                for (int j = 1; j < tPD.size(); j++) {
                    int tdiffj = tPD.get(j).getArrivalTime() - time;
                    int tdiffl = tPD.get(nextLeast).getArrivalTime() - time;
                    if(tPD.get(j).getBurstTime() + tdiffj < tPD.get(nextLeast).getBurstTime() + tdiffl) {
                        nextLeast = j;
                    }
                }
                int WorkTime;
                if (nextLeast!= 0) {
                    WorkTime = Math.min(tPD.get(nextLeast).getArrivalTime() - time, cuProcess.getBurstTime());
                    cuProcess.setBurstTime(cuProcess.getBurstTime() - WorkTime);
                    // add process to end of..
                }else{
                    WorkTime = cuProcess.getBurstTime();
                    cuProcess.setBurstTime(0);
                    // remove process 
                }
                time += WorkTime;
            }
            else{
                continue;
            }
        }
        else {
            continue;
        }
        }
        return null;
    }
}
// 1-Check arrivial process
// 2-if no process is run (Start of program or process has done job) set current process to be head of queue
// 3-run current process 25% of its quantum time
// 4-Check arrivial process
// 5-check if current process has highest priority if yes continue if no set current process to be higest priority process then back to step 1
// 6-run current process 25% of its quantum time (correct formual is ceil(50% of it quantum time - 25% of its quantum time) to get value like 10 right)
// 7-Check arrivial process
// 8-check if current process has lowest burst time if yes continue if no set current process to be shortest job then back to step 1
// 9-run current process 50% of its quantum time (remeber it is preemptive so step 8 must be repeated)


// public static void runAGScheduling(List<Process> processes) {
//     // Sort the list of processes in ascending order of their arrival time
//     Collections.sort(processes);

//     // Run the processes in the list
//     while (!processes.isEmpty()) {
//         Process currProcess = processes.remove(0);
//         int quantum = currProcess.getQuantum();

//         // Execute the process as FCFS until 25% of its quantum time has been reached
//         int timeElapsed = 0;
//         while (timeElapsed < quantum * 0.25) {
//             // Execute the process for 1 time unit
//             currProcess.execute();
//             timeElapsed++;

//             // If the process has completed, move on to the next process
//             if (currProcess.isCompleted()) {
//                 break;
//             }
//         }

//         // If the process has not completed, add it to the end of the list and increase its quantum time by 2
//         if (!currProcess.isCompleted()) {
//             currProcess.setQuantum(quantum + 2);
//             processes.add(currProcess);

//             // Execute the process as non-preemptive priority until 50% of its quantum time has been reached
//             while (timeElapsed < quantum * 0.5) {
//                 // Execute the process for 1 time unit
//                 currProcess.execute();
//                 timeElapsed++;

//                 // If the process has completed, move on to the next process
//                 if (currProcess.isCompleted()) {
//                     break;
//                 }
//             }
//         }

//         // If the process has not completed, add it to the end of the list and increase its quantum time by 50% of the remaining quantum time
//         if (!currProcess.isCompleted()) {
//             currProcess.setQuantum((int) Math.ceil((quantum - timeElapsed) / 2.0));
//             processes.add(currProcess);

//             // Execute the process as preemptive shortest-job first until it completes
//             while (!currProcess.isCompleted()) {
//                 // Execute the process for 1 time unit
//                 currProcess.execute();
//             }
//         }
//     }
// }
