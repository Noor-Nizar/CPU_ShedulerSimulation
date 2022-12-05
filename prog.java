import java.util.ArrayList;
import java.util.Queue;
import java.util.Scanner;

public class prog {
    static ArrayList<Process> PD = new ArrayList<Process>();
    static int nProccesses;
    static int RRTQ;
    static int ContextSwitch;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.println("Enter the number of processes: ");
        nProccesses = sc.nextInt();
        System.out.println("Enter the Round Robin Time Quantum: ");
        RRTQ = sc.nextInt();
        System.out.println("Enter the Context Switch Time: ");
        ContextSwitch = sc.nextInt();
        for (int i = 0; i < nProccesses; i++) {
            System.out.println("Enter the Process Number: ");
            int Number = sc.nextInt();
            System.out.println("Enter the Process Arrival Time: ");
            int ArrivalTime = sc.nextInt();
            System.out.println("Enter the Process Burst Time: ");
            int BurstTime = sc.nextInt();
            System.out.println("Enter the Process Priority: ");
            int Priority = sc.nextInt();
            Process tmp = new Process(Number, ArrivalTime, BurstTime, Priority);
            PD.add(tmp);
        }
        sc.close();

        ///SJF
        SJF sjf = new SJF();
        RR rr = new RR();
        ArrayList<Pair<Process, Integer>> sjfP = sjf.Sort(PD, ContextSwitch);
        // Queue<Pair> sjfP = rr.run(PD, RRTQ, ContextSwitch);
        System.out.println("SJF");
        for (int i = 0; i < sjfP.size(); i++) {
            System.out.println("Process Number: " + sjfP.get(i).First().getNumber() + " End Time: " + sjfP.get(i).Second());
        }
        // while(!sjfP.isEmpty()){
        //     Pair a =  sjfP.poll();
        //     System.out.println("Process Number: " + a.getProcess().getNumber() + "End Time : " + a.getTime());
        // }

    }
    

    public static void PDprinter(ArrayList<Process> PD) {
        for (int i = 0; i < PD.size(); i++) {
            System.out.println("Process Number: " + PD.get(i).getNumber() + " Arrival Time: " + PD.get(i).getArrivalTime() + " Burst Time: " + PD.get(i).getBurstTime() + " Priority: " + PD.get(i).getPriority());
        }
    }

}
