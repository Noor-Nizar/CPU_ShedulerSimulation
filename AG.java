import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.lang.Math;

public class AG {
    public int time = 0;

    public ArrayList<Pair<Process, Integer>> Sort(ArrayList<Process> PD) {

        ArrayList<Pair<Process, Integer>> pExecOrder = new ArrayList<Pair<Process, Integer>>();
        Process cuProcess;
        // Process prev;
        ArrayList<Process> tPD = prog.DeepCopy(PD);
        for (int i = 0; i < tPD.size(); i++) {
            for (int j = 0; j < tPD.size(); j++) {
                if (tPD.get(i).getArrivalTime() < tPD.get(j).getArrivalTime()) {
                    Process tmp = tPD.get(i);
                    tPD.set(i, tPD.get(j));
                    tPD.set(j, tmp);
                }
            }
        }
        int dbg = tPD.size();
        // ArrayList<Process> tPD_Next = prog.DeepCopy(tPD);
        while (tPD.size() > 0) {
            
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
            int nxtTime = Math.min((int) Math.ceil(cuProcess.getQuantum() * 0.25), cuProcess.getBurstTime());
            time += nxtTime;
            cuProcess.setBurstTime(cuProcess.getBurstTime() - nxtTime);
            pExecOrder.add(new Pair<Process, Integer>(cuProcess, time));
            if (cuProcess.getBurstTime() == 0) {
                tPD.remove(0);
                continue;
            }

            int jBest = 0; 
            for (int j = 1; j < tPD.size(); j++) {

                if (tPD.get(j).getArrivalTime() <= time) {
                    if (cuProcess.getPriority() > tPD.get(j).getPriority()) {
                        jBest = j;
                        break;
                    }
                }
            }
            int workTimeNPP = 0;
            if (jBest == 0) {
                workTimeNPP = Math.min((int) Math.ceil(cuProcess.getQuantum() * 0.25), cuProcess.getBurstTime());
                pExecOrder.add(new Pair<Process, Integer>(cuProcess, time + workTimeNPP));
                if (workTimeNPP == cuProcess.getBurstTime()) {
                    tPD.remove(0);
                    continue;
                }
            } else {
                int ttmp = Math.min(tPD.get(jBest).getArrivalTime() - time, cuProcess.getBurstTime());
                workTimeNPP = Math.min((int) Math.ceil(cuProcess.getQuantum() * 0.25), ttmp);

                if (ttmp == cuProcess.getBurstTime()) {
                    pExecOrder.add(new Pair<Process, Integer>(cuProcess, time + cuProcess.getBurstTime()));
                    tPD.remove(0);
                    continue;
                }
                pExecOrder.add(new Pair<Process, Integer>(cuProcess, time + workTimeNPP));
                if (workTimeNPP == ttmp) {
                    Process tmp = new Process(cuProcess.getNumber(), cuProcess.getArrivalTime() + time + workTimeNPP,
                            cuProcess.getBurstTime() - workTimeNPP, cuProcess.getPriority(),
                            cuProcess.getQuantum() + (int) Math.ceil(cuProcess.getQuantum()*0.75 / 2 - ttmp / 2));
                    tPD.add(tmp);
                    tPD.remove(0);
                    continue;
                }
            }






            
            cuProcess.setBurstTime(cuProcess.getBurstTime() - workTimeNPP);
            time += workTimeNPP;
            int nextLeast = 0;
            for (int j = 1; j < tPD.size(); j++) {
                // int tdiffj = tPD.get(j).getArrivalTime() - time;
                int tdiff = Math.max(tPD.get(j).getArrivalTime() - time, 0);
                if (cuProcess.getBurstTime() > tPD.get(j).getBurstTime() + tdiff) {
                    nextLeast = j;
                }
            }
            int WorkTime;
            if (nextLeast != 0) {
                int ttnxt = Math.max(tPD.get(nextLeast).getArrivalTime() - time,0);
                int ttmp = Math.min(ttnxt, cuProcess.getBurstTime());
                if (ttmp == cuProcess.getBurstTime()) {
                    pExecOrder.add(new Pair<Process, Integer>(cuProcess, time + ttmp));
                    tPD.remove(0);
                    continue;
                }
                WorkTime = Math.min((int) Math.ceil(cuProcess.getQuantum() * 0.5), ttmp);
                pExecOrder.add(new Pair<Process, Integer>(cuProcess, time + WorkTime));
                
                cuProcess.setBurstTime(cuProcess.getBurstTime() - WorkTime);
                cuProcess.setArrivalTime(cuProcess.getArrivalTime() + WorkTime + time);
                if (WorkTime == ttmp) {
                    Process tmp = new Process(cuProcess.getNumber(), cuProcess.getArrivalTime(),
                            cuProcess.getBurstTime(), cuProcess.getPriority(),
                            cuProcess.getQuantum() + (int) Math.ceil(cuProcess.getQuantum()*0.5 - WorkTime));
                    tPD.add(tmp);
                    tPD.remove(0);
                    continue;
                } else {
                    noQuantum(tPD);
                }
            } else {
                WorkTime = Math.min(cuProcess.getBurstTime(), (int) Math.ceil(cuProcess.getQuantum() * 0.5));
                pExecOrder.add(new Pair<Process, Integer>(cuProcess, time + WorkTime));
                if(WorkTime == cuProcess.getBurstTime()){
                    // pExecOrder.add(new Pair<Process, Integer>(cuProcess, time + WorkTime));
                    tPD.remove(0);
                    continue;
                }else{
                    cuProcess.setBurstTime(cuProcess.getBurstTime() - WorkTime);
                    cuProcess.setArrivalTime(cuProcess.getArrivalTime() + WorkTime + time);
                    noQuantum(tPD);
                }
            }
            time += WorkTime;
        }
        return pExecOrder;
    }

    void noQuantum(ArrayList<Process> tPD) {
        Process tmp = new Process(tPD.get(0).getNumber(), tPD.get(0).getArrivalTime(), tPD.get(0).getBurstTime(),
                tPD.get(0).getPriority(), tPD.get(0).getQuantum() + 2);
        tPD.add(tmp);
        tPD.remove(0);
    }

}
// 1-Check arrivial process
// 2-if no process is run (Start of program or process has done job) set current
// process to be head of queue
// 3-run current process 25% of its quantum time
// 4-Check arrivial process
// 5-check if current process has highest priority if yes continue if no set
// current process to be higest priority process then back to step 1
// 6-run current process 25% of its quantum time (correct formual is ceil(50% of
// it quantum time - 25% of its quantum time) to get value like 10 right)
// 7-Check arrivial process
// 8-check if current process has lowest burst time if yes continue if no set
// current process to be shortest job then back to step 1
// 9-run current process 50% of its quantum time (remeber it is preemptive so
// step 8 must be repeated)

// public static void runAGScheduling(List<Process> processes) {
// // Sort the list of processes in ascending order of their arrival time
// Collections.sort(processes);

// // Run the processes in the list
// while (!processes.isEmpty()) {
// Process currProcess = processes.remove(0);
// int quantum = currProcess.getQuantum();

// // Execute the process as FCFS until 25% of its quantum time has been reached
// int timeElapsed = 0;
// while (timeElapsed < quantum * 0.25) {
// // Execute the process for 1 time unit
// currProcess.execute();
// timeElapsed++;

// // If the process has completed, move on to the next process
// if (currProcess.isCompleted()) {
// break;
// }
// }

// // If the process has not completed, add it to the end of the list and
// increase its quantum time by 2
// if (!currProcess.isCompleted()) {
// currProcess.setQuantum(quantum + 2);
// processes.add(currProcess);

// // Execute the process as non-preemptive priority until 50% of its quantum
// time has been reached
// while (timeElapsed < quantum * 0.5) {
// // Execute the process for 1 time unit
// currProcess.execute();
// timeElapsed++;

// // If the process has completed, move on to the next process
// if (currProcess.isCompleted()) {
// break;
// }
// }
// }

// // If the process has not completed, add it to the end of the list and
// increase its quantum time by 50% of the remaining quantum time
// if (!currProcess.isCompleted()) {
// currProcess.setQuantum((int) Math.ceil((quantum - timeElapsed) / 2.0));
// processes.add(currProcess);

// // Execute the process as preemptive shortest-job first until it completes
// while (!currProcess.isCompleted()) {
// // Execute the process for 1 time unit
// currProcess.execute();
// }
// }
// }
// }
