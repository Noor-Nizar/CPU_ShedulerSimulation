import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class IOHandler {
    String filename;
    int mode = 0;
    Integer args[];
    Scanner sci = new Scanner(System.in);
    public IOHandler(Integer[] args){
        this.args = args;
        System.out.println("Enter the number of processes: ");
        args[0] = sci.nextInt();
        System.out.println("Enter the Round Robin Time Quantum: ");
        args[1] = sci.nextInt();
        System.out.println("Enter the Context Switch Time: ");
        args[2] = sci.nextInt();
        sci.nextLine();
        System.out.println("Input From File or Console? (F/C)");
        String input = sci.nextLine();
        if(input.equals("F")){
            System.out.println("Enter the filename (or enter for default): ");
            filename = sci.nextLine();
            if(filename.equals(""))
                filename = "input.txt"; // default filename
            mode = 1;
        }
        else{
            mode = 2;
        }
    }
    void FromFile(ArrayList<Process> retP, Integer[] args) throws FileNotFoundException{
        Scanner sc = new Scanner(new File(filename));
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
    void DataFromTerminal(ArrayList<Process> PD, Integer[] args){
        for (int i = 0; i < args[0]; i++) {
            System.out.println("Enter the Process Number:");
            int Number = sci.nextInt();
            System.out.println("Enter the Process Arrival Time: ");
            int ArrivalTime = sci.nextInt();
            System.out.println("Enter the Process Burst Time: ");
            int BurstTime = sci.nextInt();
            System.out.println("Enter the Process Priority: ");
            int Priority = sci.nextInt();
            Process tmp = new Process(Number, ArrivalTime, BurstTime, Priority);
            PD.add(tmp);
        }
        sci.close();
    }

    public void getInput(ArrayList<Process> PD){
        if(mode == 1){
            try {
                FromFile(PD, args);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        else if(mode == 2){
            DataFromTerminal(PD, args);
        }
    }

    public static void PDprinter(ArrayList<Process> PD) {
        for (int i = 0; i < PD.size(); i++) {
            System.out.println("Process Number: " + PD.get(i).getNumber() + " Arrival Time: " + PD.get(i).getArrivalTime() + " Burst Time: " + PD.get(i).getBurstTime() + " Priority: " + PD.get(i).getPriority());
        }
    }
}