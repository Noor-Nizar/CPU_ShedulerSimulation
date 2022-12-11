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

        Integer[] argsP = new Integer[4];
        io = new IOHandler(argsP);
        io.getInput(PD);
        // System.out.println(PD);
        nProccesses = argsP[0];
        RRTQ = argsP[1];
        ContextSwitch = argsP[2];

        ArrayList<Pair<Process, Integer>> output;
        if (argsP[3] == 0) {
            output = sjf.Sort(PD, ContextSwitch);
        }
        else if (argsP[3] == 1) {
            output = rr.Sort(PD, RRTQ, ContextSwitch);
        }
        else if (argsP[3] == 2) {
            output = ps.Sort(PD, ContextSwitch, AgeTime);
        }
        else if (argsP[3] == 3) {
            output = Squinch(ag.Sort(PD));
        }else{
            System.out.println("Invalid Algorithm");
            output = sjf.Sort(PD, ContextSwitch);
        }

        IOHandler.PrintTimeInfo(output, PD);
        IOHandler.PEOprinter(output);


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
    public static ArrayList<Pair<Process, Integer>> Squinch(ArrayList<Pair<Process, Integer>> peo){
        ArrayList<Pair<Process, Integer>> tmp = new ArrayList<Pair<Process, Integer>>();
        for (int i = 0; i < peo.size()-1; i++) {
            if(peo.get(i).First().getNumber() == peo.get(i+1).First().getNumber()){
                continue;
            }else{
                tmp.add(peo.get(i));
            }
        }
        tmp.add(peo.get(peo.size()-1));
        return tmp;
    }
}