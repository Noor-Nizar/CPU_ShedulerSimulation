public class Process {
    private int Number;
    private int ArrivalTime;
    private int BurstTime;
    private int Priority;
    private int WaitingTime;
    private int Quantum;
    public Process(int Number, int ArrivalTime, int BurstTime, int Priority,int Quantum) {
        this.Number = Number;
        this.ArrivalTime = ArrivalTime;
        this.Priority = Priority;
        this.BurstTime = BurstTime;
        this.Quantum = Quantum;
    }
    public Process(int Number, int ArrivalTime, int BurstTime, int Priority) {
        this.Number = Number;
        this.ArrivalTime = ArrivalTime;
        this.Priority = Priority;
        this.BurstTime = BurstTime;
        }
    //getters
    public int getNumber() {
        return Number;
    }
    public int getArrivalTime() {
        return ArrivalTime;
    }
    public int getPriority() {
        return Priority;
    }
    public int getBurstTime() {
        return BurstTime;
    }
    
    public int getQuantum() {
        return Quantum;
    }
    //setters
    public void setNumber(int Number) {
        this.Number = Number;
    }
    public void setArrivalTime(int ArrivalTime) {
        this.ArrivalTime = ArrivalTime;
    }
    public void setPriority(int Priority) {
        this.Priority = Priority;
    }
    public void setBurstTime(int BurstTime) {
        this.BurstTime = BurstTime;
    }
    public void setQuantum(int Quantum) { 
        this.Quantum = Quantum;
    }
    // public void update(,int time){
    //     this.Quantum = this.Quantum -time ;
    // }
}
