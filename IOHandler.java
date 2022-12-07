import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class IOHandler {
    String filename;
    public IOHandler(String filename){
        this.filename = filename;
    }
    public void FromFile(ArrayList<Process> retP, Integer[] args) throws FileNotFoundException{
        Scanner sc = new Scanner(new File(filename));
        System.out.println("Enter the number of processes: ");
        args[0] = sc.nextInt();
        System.out.println("Enter the Round Robin Time Quantum: ");
        args[1] = sc.nextInt();
        System.out.println("Enter the Context Switch Time: ");
        args[2] = sc.nextInt();
        while(sc.hasNextLine()){
            String line = sc.nextLine();
            String[] tokens = line.split(" ");
            int Number = Integer.parseInt(tokens[0]);
            int ArrivalTime = Integer.parseInt(tokens[1]);
            int BurstTime = Integer.parseInt(tokens[2]);
            int Priority = Integer.parseInt(tokens[3]);
            Process tmp = new Process(Number, ArrivalTime, BurstTime, Priority);
            retP.add(tmp);
        }
        sc.close();
    }
    public void DataFromTerminal(ArrayList<Process> PD, Integer[] args){
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter the number of processes: ");
        args[0] = sc.nextInt();
        System.out.println("Enter the Round Robin Time Quantum: ");
        args[1] = sc.nextInt();
        System.out.println("Enter the Context Switch Time: ");
        args[2] = sc.nextInt();
        for (int i = 0; i < args[0]; i++) {
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
    }
    public static void PDprinter(ArrayList<Process> PD) {
        for (int i = 0; i < PD.size(); i++) {
            System.out.println("Process Number: " + PD.get(i).getNumber() + " Arrival Time: " + PD.get(i).getArrivalTime() + " Burst Time: " + PD.get(i).getBurstTime() + " Priority: " + PD.get(i).getPriority());
        }
    }
}