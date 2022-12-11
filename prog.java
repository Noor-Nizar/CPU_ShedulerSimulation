import java.util.ArrayList;
import java.util.Queue;
import java.util.Scanner;

public class prog {
    static int nProccesses;
    static int RRTQ;
    static int ContextSwitch;
    static SJF sjf = new SJF();
    static RR rr = new RR();
    static PS ps = new PS();
    static AG ag = new AG();

    static IOHandler io;

    static int AgeTime = 1; // << !!!!!!!!!

    public static void main(String[] args) {
        ArrayList<Process> PD = new ArrayList<Process>();

        Integer[] argsP = new Integer[3];
        io = new IOHandler(argsP);
        io.getInput(PD);
        nProccesses = argsP[0];
        RRTQ = argsP[1];
        ContextSwitch = argsP[2];

        // for(int i = 0; i < PD.size(); i++){

        // System.out.println("getnumber: "+mPD.get(i).getNumber());
        // System.out.println("BurstTime: "+mPD.get(i).getBurstTime());
        // System.out.println("ArrivalTime: "+mPD.get(i).getArrivalTime());
        // System.out.println("--------------------");
        // }
        // ArrayList<Pair<Process, Integer>> sjfP = sjf.Sort(PD, ContextSwitch);
        // ArrayList<Pair<Process, Integer>> rrP = rr.Sort(DeepCopy(PD), RRTQ, ContextSwitch);
        // ArrayList<Pair<Process, Integer>> psP = ps.Sort(DeepCopy(PD), ContextSwitch, AgeTime);
        ArrayList<Pair<Process, Integer>> sjfP = sjf.Sort(PD, ContextSwitch);
        
        Time.findWaitingTime(sjfP,PD);

        // // Queue<Pair> sjfP = rr.run(PD, RRTQ, ContextSwitch);
        // System.out.println("SJF");
        for (int i = 0; i < sjfP.size(); i++) {
            System.out.println("Process Number: " + sjfP.get(i).First().getNumber() + " End Time: " + sjfP.get(i).Second());
        }

        ArrayList<Integer>  a = Time.findWaitingTime(sjfP,PD);
        for(int i = 0; i < a.size(); i++){
            System.out.println("Process Number: " + (i+1)+"Wait Time: " + a.get(i));
        }
        System.out.println("--------------------------------");
        System.out.println(Time.findWaitingTime(sjfP,PD));
        System.out.println("AvgWaitTime: " + Time.AvgTime(Time.findWaitingTime(sjfP,PD)));
        ArrayList<Pair<Process, Integer>> agP = ag.Sort(PD);

        // Time.findWaitingTime(sjfP, PD);

        IOHandler.PEOprinter(agP);

        // System.out.println("Wait Times: " + Time.findWaitingTime(agP, PD));
        // System.out.println("AvgWaitTime: " + Time.AvgWaitTime(Time.findWaitingTime(agP, PD)));

    }

    public static ArrayList<Process> DeepCopy(ArrayList<Process> PD) {
        ArrayList<Process> mPD = new ArrayList<Process>();
        for (int i = 0; i < PD.size(); i++) {
            Process p = new Process(PD.get(i).getNumber(), PD.get(i).getArrivalTime(), PD.get(i).getBurstTime(),
                    PD.get(i).getPriority(), PD.get(i).getQuantum());
            mPD.add(p);
        }
        return mPD;
    }
}