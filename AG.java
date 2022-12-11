import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Vector;
import java.lang.Math;
public class AG {
    public int time=0 ;
    
    public  ArrayList<Pair<Process, Integer>> run(ArrayList<Process> PD){
        Queue<Pair<Process, Integer>>  q2 = new LinkedList<Pair<Process, Integer>> ();
        Process cuProcess;
        Process prev;
        Queue<Process> q = new LinkedList<Process>();
        Vector<Process> tPD = new Vector<Process>();
        tPD = prog.DeepCopyV(PD);
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
        if(nxtTime==cuProcess.getBurstTime()){
            cuProcess.setQuantum(0);
        }
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
            nxtTime = Math.min((int)Math.ceil(cuProcess.getQuantum() *0.25), cuProcess.getBurstTime());
            time += nxtTime;
            if(nxtTime==cuProcess.getBurstTime()){
                cuProcess.setQuantum(0);
            }
            //time += (int)Math.ceil(cuProcess.getQuantum() *0.5- cuProcess.getQuantum()*0.25);
            cuProcess.setBurstTime(cuProcess.getBurstTime()-nxtTime);
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
                // int nextLeast = 0;
                // for (int j = 1; j < tPD.size(); j++) {
                //     int tdiffj = tPD.get(j).getArrivalTime() - time;
                //     int tdiffl = tPD.get(nextLeast).getArrivalTime() - time;
                //     if(tPD.get(j).getBurstTime() + tdiffj < tPD.get(nextLeast).getBurstTime() + tdiffl) {
                //         nextLeast = j;
                //     }
                // }
                // int WorkTime;
                // if (nextLeast!= 0) {
                //     WorkTime = Math.min(tPD.get(nextLeast).getArrivalTime() - time, cuProcess.getBurstTime());
                //     cuProcess.setBurstTime(cuProcess.getBurstTime() - WorkTime);
                //     // add process to end of..
                // }else{
                //     WorkTime = cuProcess.getBurstTime();
                //     cuProcess.setBurstTime(0);
                //     // remove process 
                // }
                // time += WorkTime;
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
                    cuProcess.setBurstTime((int)(WorkTime - Math.ceil(cuProcess.getQuantum()*0.5)));
                    // remove process 
                }
                time += WorkTime;
                cuProcess.setQuantum(cuProcess.getQuantum()*2);
            }
            else{
                prev.setQuantum((int)((2*prev.getQuantum())-Math.ceil( prev.getQuantum()*0.5)));
                continue;
            }
        }
        else {
            prev.setQuantum((int)Math.ceil((prev.getQuantum()- Math.ceil( prev.getQuantum()*0.25))/2));
            continue;
        }
        }
        
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

// import java.util.Queue;

// public class AGScheduling {
//     // The queue of processes to be executed
//     private Queue<Process> queue;

//     // The current process being executed
//     private Process currentProcess;

//     public AGScheduling() {
//         // Initialize the queue
//         queue = new LinkedList<Process>();
//     }

//     public void addProcess(Process p) {
//         // Add a process to the queue
//         queue.add(p);
//     }

//     public void run() {
//         // Run the scheduler until there are no more processes in the queue
//         while (!queue.isEmpty()) {
//             // Get the next process in the queue
//             currentProcess = queue.poll();

//             // Execute the process for its quantum time
//             currentProcess.execute(currentProcess.getQuantumTime());

//             // Check if the process used up its entire quantum time
//             if (currentProcess.isComplete()) {
//                 // The process is complete, so we can set its quantum time to zero
//                 currentProcess.setQuantumTime(0);
//             } else {
//                 // The process is not complete, so we need to add it back to the queue
//                 // and update its quantum time based on the scenario
//                 if (currentProcess.getSchedulingTechnique() == SchedulingTechnique.FCFS &&
//                     currentProcess.getElapsedTime() < 0.25 * currentProcess.getQuantumTime()) {
//                     // Scenario 1: The process was executed using FCFS and did not use up 25% of its quantum time
//                     // Add the process to the end of the queue and increase its quantum time by two
//                     queue.add(currentProcess);
//                     currentProcess.setQuantumTime(currentProcess.getQuantumTime() + 2);
//                 } else if (currentProcess.getSchedulingTechnique() == SchedulingTechnique.NON_PREEMPTIVE_PRIORITY &&
//                            currentProcess.getElapsedTime() < 0.25 * currentProcess.getQuantumTime()) {
//                     // Scenario 2: The process was executed using non-preemptive priority and did not use up 25% of its quantum time
//                     // Add the process to the end of the queue and increase its quantum time by half of the remaining quantum time
//                     queue.add(currentProcess);
//                     currentProcess.setQuantumTime(currentProcess.getQuantumTime() + 0.5 * (currentProcess.getQuantumTime() - currentProcess.getElapsedTime()));
//                 } else if (currentProcess.getSchedulingTechnique() == SchedulingTechnique.PREEMPTIVE_SJF &&
//                            currentProcess.getElapsedTime() < currentProcess.getQuantumTime()) {
//                     // Scenario 3: The process was executed using preemptive SJF and did not use up all of its quantum time
//                     // Add the process to the end of the queue and increase its quantum time by the remaining quantum time
//                     queue.add(currentProcess);
//                     currentProcess.setQuantumTime(currentProcess.getQuantumTime() + (currentProcess.getQuantumTime() - currentProcess.getElapsedTime()));
//                 } else {
