import java.util.ArrayList;
import java.util.Queue;

public class Time {

    static ArrayList<Integer> findWaitingTime(ArrayList<Pair<Process, Integer>> proc, ArrayList<Process> PD) {
        ArrayList<Process> tPD = new ArrayList<Process>();
        ArrayList<Integer> waitingTime = new ArrayList<Integer>();
        tPD = prog.DeepCopy(PD);
        System.out.println("tPD size " + tPD.size());
        int waitTime[] = new int[tPD.size()];
        int m[] = new int[tPD.size()];
        for (int i = 0; i < tPD.size(); i++) {
            m[i] = -1;

        }
        for (int i = 0; i < PD.size(); i++) {
            int last = 0;
            for (int j = 0; j < proc.size(); j++) {
                if (proc.get(j).First().getNumber() == PD.get(i).getNumber()) {
                    last = j;
                }
            }
            waitTime[i] = proc.get(last).Second() - PD.get(i).getBurstTime() - PD.get(i).getArrivalTime();
        }

        for (int i = 0; i < waitTime.length; i++) {
            waitingTime.add(waitTime[i]);
        }
        return waitingTime;
    }

    static ArrayList<Integer> TAT(ArrayList<Pair<Process, Integer>> proc, ArrayList<Process> PD) {
        ArrayList<Process> tPD = new ArrayList<Process>();
        ArrayList<Integer> tat = new ArrayList<Integer>();
        tPD = prog.DeepCopy(PD);

        int Time[] = new int[tPD.size()];
        int m[] = new int[tPD.size()];
        for (int i = 0; i < tPD.size(); i++) {
            m[i] = -1;

        }
        for (int i = proc.size() - 1; i > 0; i--) {

            if (m[proc.get(i).First().getNumber() - 1] != 0) {
                Time[proc.get(i).First().getNumber() - 1] = proc.get(i).Second()
                        - PD.get(proc.get(i).First().getNumber() - 1).getArrivalTime();
                m[proc.get(i).First().getNumber() - 1] = 0;

            }

        }
        for (int i = 0; i < Time.length; i++) {
            tat.add(Time[i]);
        }
        return tat;
    }

    public static Double AvgTime(ArrayList<Integer> Times) {
        Double avg = 0.0;
        for (int i = 0; i < Times.size(); i++) {
            avg += Times.get(i);
        }
        avg = avg / Times.size();
        return avg;
    }

}