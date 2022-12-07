import java.util.ArrayList; // import the ArrayList class from the Java standard library

public class SJFAnnotated { // define the SJF class

    public ArrayList<Pair<Process, Integer>> Sort(ArrayList<Process> PD, int ContextSwitch) { // define the Sort method that takes in a list of Process objects and the context switch time as input
        // deep copy PD into tPD
        ArrayList<Pair<Process, Integer>> retP = new ArrayList<Pair<Process, Integer>>(); // create an empty list of Pair objects to store the results
        ArrayList<Process> tPD = new ArrayList<Process>(); // create a new list of Process objects to store the deep copy of the input list
        for (int i = 0; i < PD.size(); i++) { // iterate over the input list of Process objects
            tPD.add(PD.get(i)); // add each Process object to the deep copy list
        }
        // Sorting on Burst Time
        for (int i = 0; i < tPD.size(); i++) { // nested loop to sort the deep copy list by burst time
            for (int j = 0; j < tPD.size(); j++) {
                if (tPD.get(i).getBurstTime() < tPD.get(j).getBurstTime()) { // if the burst time of the ith Process object is less than the burst time of the jth Process object
                    Process tmp = tPD.get(i); // store the ith Process object in a temporary variable
                    tPD.set(i, tPD.get(j)); // replace the ith Process object with the jth Process object
                    tPD.set(j, tmp); // replace the jth Process object with the temporary variable
               
                }
            }
        }
        // sorting on Arrival Time
        for (int i = 0; i < tPD.size(); i++) { // nested loop to sort the deep copy list by arrival time
            for (int j = 0; j < tPD.size(); j++) {
                if (tPD.get(i).getArrivalTime() < tPD.get(j).getArrivalTime()) { // if the arrival time of the ith Process object is less than the arrival time of the jth Process object
                    Process tmp = tPD.get(i); // store the ith Process object in a temporary variable
                    tPD.set(i, tPD.get(j)); // replace the ith Process object with the jth Process object
                    tPD.set(j, tmp); // replace the jth Process object with the temporary variable
                }
            }
        }
        int counter = 0; // represents Time // initialize a counter variable to keep track of the current time
        int dbg = 5; // debug counter // initialize a debug counter variable
        while (true) { // enter an infinite loop
            int jBest = -1; // the best process to run that has arrived so far // initialize a variable to store the index of the best process to run so far
            int jnout = -1; // the process tht is arriving next // initialize a variable to store the index of the process that is arriving next
            int First = -1; // if not -1 then there are still processes to run // initialize a variable to keep track of whether there are still processes to run
            for (int j = 0; j < tPD.size(); j++) { // iterate over the deep copy list of Process objects
                if (tPD.get(j).getBurstTime() == 0) { // if the burst time of the jth Process object is 0
                    continue; // skip this iteration and continue to the next one
                } else {
                    if (First == -1) { // if the First variable is not set
                        First = j; // set the First variable to the index of the jth Process object
                    }
                }
                if (tPD.get(j).getArrivalTime() <= counter) { // if the arrival time of the jth Process object is less than or equal to the current time
                    if (jBest == -1) { // if the jBest variable is not set
                        jBest = j; // set the jBest variable to the index of the jth Process object
                    } else {
                        if (tPD.get(j).getBurstTime() < tPD.get(jBest).getBurstTime()) { // if the burst time of the jth Process object is less than the burst time of the process at the jBest index
                            jBest = j; // set the jBest variable to the index of the jth Process object
                        }
                    }
                } else { // if the arrival time of the jth Process object is greater than the current time
                    if (jnout == -1) { // if the jnout variable is not set
                        jnout = j; // set the jnout variable to the index of the jth Process object
                    }
                }
            }
            if (First == -1) { // if there are no more processes to run
