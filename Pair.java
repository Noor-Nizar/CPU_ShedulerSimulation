public class Pair {
    
    private Process process;
    private int time;
    
    public Pair(Process process, int time) {
        this.process = process;
        this.time = time;
    }
    
    public Process getProcess() {
        return process;
    }
    
    public int getTime() {
        return time;
    }
    
    public void setProcess(Process process) {
        this.process = process;
    }
    
    public void setTime(int time) {
        this.time = time;
    }
    
}
