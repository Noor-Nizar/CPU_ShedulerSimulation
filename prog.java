import java.util.ArrayList;
import java.util.Queue;
import java.util.Scanner;

public class prog {
    static ArrayList<Process> PD = new ArrayList<Process>();
    static int nProccesses;
    static int RRTQ;
    static int ContextSwitch;
    static SJF sjf = new SJF();
    static RR rr = new RR();
    static PS ps = new PS();
    
    static IOHandler io;

    public static void main(String[] args) {
        Integer[] argsP = new Integer[3];
        io = new IOHandler(argsP);
        io.getInput(PD);
        nProccesses = argsP[0];
        RRTQ = argsP[1];
        ContextSwitch = argsP[2];

        
        ArrayList<Pair<Process, Integer>> sjfP = ps.Sort(PD, ContextSwitch);
        // // Queue<Pair> sjfP = rr.run(PD, RRTQ, ContextSwitch);
        // System.out.println("SJF");
        for (int i = 0; i < sjfP.size(); i++) {
            System.out.println("Process Number: " + sjfP.get(i).First().getNumber() + " End Time: " + sjfP.get(i).Second());
        }
        // // while(!sjfP.isEmpty()){
        // //     Pair a =  sjfP.poll();
        // //     System.out.println("Process Number: " + a.getProcess().getNumber() + "End Time : " + a.getTime());
        // // }

    }

}
