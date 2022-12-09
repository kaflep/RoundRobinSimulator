import java.util.Date;

public class Process {
    private static int id = 0;
    private int processId, arrivalTime, executionTime, priority;

    public Process(){
        processId = id;
        arrivalTime = 0;
        executionTime = 0;
        priority = 0;
        id++;
    }

    public Process(int processId, int arrivalTime, int executionTime, int priority){
        this.processId = processId;
        this.arrivalTime = arrivalTime;
        this.executionTime = executionTime;
        this.priority = priority;
    }

    public int getId() {
        return processId;
    }

    public void setId(int id) {
        this.processId = id;
    }

    public int getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(int arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public int getExecutionTime() {
        return executionTime;
    }

    public void setExecutionTime(int executionTime) {
        this.executionTime = executionTime;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    @Override
    public String toString(){
        String s = "ID: ";
        s += processId;
        s += ", ArrivalTime: " + arrivalTime;
        s += ", ExecutionTime: " + executionTime;
        s += ", Priority: " + priority;
        return s;
    }
}
