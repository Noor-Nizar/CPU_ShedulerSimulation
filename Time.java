import java.util.ArrayList;
import java.util.Queue;

public class Time {
    
    static ArrayList<Integer> findWaitingTime(ArrayList<Pair<Process, Integer>> proc,ArrayList<Process> PD){    
        ArrayList<Process> tPD = new ArrayList<Process>();
        ArrayList<Integer> waitingTime = new ArrayList<Integer>();
        tPD = prog.DeepCopy(PD);
        // for(int i=0; i<proc.size(); i++){
        //     proc.get(i).setFirst(p.get(i).First());
        //     proc.get(i).setSecond(p.get(i).Second());

        // }
        
        int waitTime[]=new int[tPD.size()];
        int m[]=new int[tPD.size()];
        for(int i=0; i<tPD.size(); i++){
           m[i]=-1;
          
        }
        for(int i = proc.size()-1; i > 0 ; i--){
           
            if(m[proc.get(i).First().getNumber()-1]!= 0){
                waitTime[proc.get(i).First().getNumber()-1] = proc.get(i).Second() - PD.get(proc.get(i).First().getNumber()-1).getBurstTime()-PD.get(proc.get(i).First().getNumber()-1).getArrivalTime();
                m[proc.get(i).First().getNumber()-1] = 0;
                
            }
              
        }
        for(int i = 0; i < waitTime.length; i++){
            waitingTime.add(waitTime[i]);
        }
        return waitingTime;
    }
    static ArrayList<Integer> TAT(ArrayList<Pair<Process, Integer>> proc,ArrayList<Process> PD){    
        ArrayList<Process> tPD = new ArrayList<Process>();
        ArrayList<Integer> tat = new ArrayList<Integer>();
        tPD = prog.DeepCopy(PD);
        // for(int i=0; i<proc.size(); i++){
        //     proc.get(i).setFirst(p.get(i).First());
        //     proc.get(i).setSecond(p.get(i).Second());

        // }
        
        int Time[]=new int[tPD.size()];
        int m[]=new int[tPD.size()];
        for(int i=0; i<tPD.size(); i++){
           m[i]=-1;
          
        }
        for(int i = proc.size()-1; i > 0 ; i--){
           
            if(m[proc.get(i).First().getNumber()-1]!= 0){
                Time[proc.get(i).First().getNumber()-1] = proc.get(i).Second() - PD.get(proc.get(i).First().getNumber()-1).getArrivalTime();
                m[proc.get(i).First().getNumber()-1] = 0;
                
            }
              
        }
        for(int i = 0; i < Time.length; i++){
            tat.add(Time[i]);
        }
        return tat;
    }
