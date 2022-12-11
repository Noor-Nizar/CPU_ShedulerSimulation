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
        /*----------------------------------------------------------------
         * Once a process is executed for given time period, it’s called FCFS till the

finishing of (ceil(52%)) of its Quantum time then it’s converted to non-
preemptive Priority till the finishing of the next (ceil(52%)), after that it’s

converted to preemptive Shortest- Job First (SJF).
         * 
         */
        int dbg = tPD.size();
        cuProcess = tPD.get(0);
        // ArrayList<Process> tPD_Next = prog.DeepCopy(tPD);
        while (tPD.size() > 0) {
            int Q = cuProcess.getQuantum();
            int Q25 = (int) Math.ceil(Q * 0.25);    // Q25 + Q25nd = Q()
            int Q25nd = (int) Math.ceil(Q*0.5)-Q25;
            if(Q25 == Q){
                Q25nd = 0;
            }
            int RQ = Q - Q25 - Q25nd;
            // int Q = cuProcess.getQuantum();
            // int Q25 = (int) Math.ceil(Q * 0.25);
            // int Q25nd = (int) Math.ceil((Q * 0.25));
            // int RQ = Q - Q25nd;

            for (int i = 0; i < tPD.size(); i++) {
                for (int j = 0; j < tPD.size(); j++) {
                    if (tPD.get(i).getArrivalTime() < tPD.get(j).getArrivalTime()) {
                        Process tmp = tPD.get(i);
                        tPD.set(i, tPD.get(j));
                        tPD.set(j, tmp);
                    }
                }
            }
            // for (int i = 0; i < tPD.size(); i++) {
            //     System.out.println("Quantum Time : " + tPD.get(i).getQuantum() + " Pnum :" + tPD.get(i).getNumber() + "\n");
            // }
            // cuProcess = tPD.get(0);
            int nxtTime = Math.min(Q25, cuProcess.getBurstTime());
            time += nxtTime;
            cuProcess.setBurstTime(cuProcess.getBurstTime() - nxtTime);
            pExecOrder.add(new Pair<Process, Integer>(cuProcess, time));
            if (cuProcess.getBurstTime() == 0) {
                tPD.remove(cuProcess);
                if (tPD.size() == 0) {
                    break;
                }
                cuProcess = tPD.get(0);
                continue;
            }

            Process jBest = cuProcess;
            for (int j = 0; j < tPD.size(); j++) {

                if (tPD.get(j).getArrivalTime() <= time) {
                    if (jBest.getPriority() > tPD.get(j).getPriority()) {
                        jBest = tPD.get(j);
                        // break;
                    }
                }
            }

            int avwt = 0;
            if (jBest == cuProcess) {
                avwt = cuProcess.getBurstTime();
            } else {
                avwt = Math.max(jBest.getArrivalTime() - time, 0);
            }
            
            int workTimeNPP = Math.min(Q25nd, avwt);
            workTimeNPP = Math.min(workTimeNPP, cuProcess.getBurstTime());
            time += workTimeNPP;
            pExecOrder.add(new Pair<Process, Integer>(cuProcess, time));
            
            if (workTimeNPP == cuProcess.getBurstTime()) {
                tPD.remove(cuProcess);
                if (tPD.size() == 0) {
                    break;
                }
                // (int)Math.ceil((prev.getQuantum()- Math.ceil( prev.getQuantum()*0.25))/2)
                cuProcess = tPD.get(0);
                continue;
            }
            cuProcess.setBurstTime(cuProcess.getBurstTime() - workTimeNPP);
            if (workTimeNPP == avwt) {
                Process tmp = new Process(cuProcess.getNumber(), cuProcess.getArrivalTime() + 0,
                        cuProcess.getBurstTime(), cuProcess.getPriority(),
                        cuProcess.getQuantum() + (int) Math.ceil((double)(  Q-Q25-workTimeNPP ) / 2));
                tPD.remove(cuProcess);
                cuProcess = jBest;
                tPD.add(tmp);
                
                continue;
            }

            // cuProcess.setBurstTime(cuProcess.getBurstTime() - workTimeNPP);
            // time += workTimeNPP;

            Process nextLeastInTime = cuProcess;
            for (int j = 0; j < tPD.size(); j++) {
                // int tdiffj = tPD.get(j).getArrivalTime() - time;
                if (tPD.get(j).getArrivalTime() <= time) {
                    if (nextLeastInTime.getBurstTime() > tPD.get(j).getBurstTime()) {
                        nextLeastInTime = tPD.get(j);
                    }
                }
            }
            Process nextLeastOutTime = cuProcess;
            if (nextLeastInTime == cuProcess) {
                for (int j = 0; j < tPD.size(); j++) {
                    // int tdiffj = tPD.get(j).getArrivalTime() - time;
                    int tdiff = Math.max(tPD.get(j).getArrivalTime() - time, 0);
                    if (cuProcess.getBurstTime() > tPD.get(j).getBurstTime() + tdiff) {
                        nextLeastOutTime = tPD.get(j);
                        break;
                    }
                }
            }
            Process nextProc;
            if(nextLeastInTime == cuProcess){
                nextProc = nextLeastOutTime;
            }else{
                nextProc = nextLeastInTime;
            }
            int avwt2 = 0;
            if (nextProc != cuProcess) {
                avwt2 = Math.max(nextProc.getArrivalTime() - time, 0);
            } else {
                avwt2 = cuProcess.getBurstTime();
            }
            int remQuantum = RQ;
            int WorkTime = Math.min(avwt2, remQuantum);
            time += WorkTime;
            cuProcess.setBurstTime(cuProcess.getBurstTime() - WorkTime);
            cuProcess.setArrivalTime(cuProcess.getArrivalTime() + 0);

            pExecOrder.add(new Pair<Process, Integer>(cuProcess, time));
            if (cuProcess.getBurstTime() == 0) {
                tPD.remove(cuProcess);
                if (tPD.size() == 0) {
                    break;
                }
                cuProcess = tPD.get(0);
                continue;
            }
            if (WorkTime == remQuantum) {
                noQuantum(tPD, cuProcess);
                cuProcess = tPD.get(0);
                continue;
            }
            if (WorkTime == avwt2) {
                Process tmp = new Process(cuProcess.getNumber(), cuProcess.getArrivalTime(),
                        cuProcess.getBurstTime(), cuProcess.getPriority(),
                        cuProcess.getQuantum() + (int) Math.ceil(RQ - WorkTime));
                tPD.remove(cuProcess);
                tPD.add(tmp);
                cuProcess = nextProc;
                continue;
            }
        }
        return pExecOrder;
    }

    void noQuantum(ArrayList<Process> tPD, Process cuProcess) {
        Process tmp = new Process(tPD.get(0).getNumber(), tPD.get(0).getArrivalTime(), tPD.get(0).getBurstTime(),
                tPD.get(0).getPriority(), tPD.get(0).getQuantum() + 2);
        tPD.remove(cuProcess);
        tPD.add(tmp);
    }

}