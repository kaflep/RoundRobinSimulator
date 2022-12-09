import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class MainEngine {
    //reads a text file and uses it to dynamically call scheduler() with the appropriate args
    public static void Initializer(String fileName) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(fileName));
        String line = br.readLine();
        String[] args = line.split(", ");//returns a string array containing the data fields we want

        //initializes array with first int value (amount of processes) as it's argument
        Process[] processes = new Process[Integer.parseInt(args[0])];
        int quantum = Integer.parseInt(args[1]); //sets quantum

        //used to keep track of the next empty cell in processes array
        int index = 0;
        line = br.readLine();//moves to the first process line
        Process process;

        //loop until we reach the end of the '.txt' file
        while(line != null){
            //Splits line into substrings where there is a comma and a space
            args = line.split(", ");

            //gets all of our arguments
            int id = Integer.parseInt(args[0]);
            int arrivalTime = Integer.parseInt(args[1]);
            int executionTime = Integer.parseInt(args[2]);
            int priority = Integer.parseInt(args[3]);

            // make a new process object using the overloaded constructor with our
            // arguments we got from the txt file
            process = new Process(id, arrivalTime, executionTime, priority);

            //places our new process reference at the proper place in our array
            processes[index] = process;

            index++;
            line = br.readLine();
        }

        //calls the scheduler method with the processes array and our appropriate quantum time
        scheduler(processes, quantum);
    }

    //prints the appropriate process schedule to the console using the Round Robin algorithm
    public static void scheduler(Process[] processes, int quantum){
        LinkedList<Process> schedule = new LinkedList<>();//que to represent processes in ready queue
        LinkedList<Process> toArrive = new LinkedList<>();//queue to allow for dynamic arrival
        int time = 0; //"global" counter for quantum time

        ArrayList<Process> unsorted = new ArrayList<>();

        for (int i = processes.length - 1; i >= 0; i--){
            unsorted.add(processes[i]);
        }

        while (!unsorted.isEmpty()){
            Process queueNext = unsorted.get(0);
            for (int i = 1; i < unsorted.size(); i++){
                Process currentProcess = unsorted.get(i); //selects the first object arbitrarily for comparison

                //if process arrives sooner, then we should queue that process instead
                if (queueNext.getArrivalTime() > currentProcess.getArrivalTime())
                {
                    queueNext = currentProcess;
                }
                //if process arrives at the same time and the id is smaller, then we should queue that process instead
                else if ((queueNext.getArrivalTime() == currentProcess.getArrivalTime()) && (queueNext.getId() > currentProcess.getId()))
                {
                    queueNext = currentProcess;
                }
            }
            unsorted.remove(queueNext);//remove selected process from unsorted list
            toArrive.addLast(queueNext);//add selected process to queue
        }

        int numRounds = 1;

        while(!(toArrive.isEmpty() && schedule.isEmpty())){
            System.out.println("===========Beginning Round Number " + numRounds+ "=========");

            //adds any processes that arrive to the end of the ready queue
            while (!toArrive.isEmpty() && toArrive.get(0).getArrivalTime() <= time){
                schedule.addLast(toArrive.removeFirst());
            }
            System.out.println("Current Time: "+ time + "  Quantum: " + quantum);

            //ready queue is current
            System.out.print("Ready Queue<< ");

            for (int i = 0; i < schedule.size() -1 ; i++){
                System.out.print(schedule.get(i).getId() + ", ");
            }

            System.out.println(schedule.getLast().getId() + " >>\n");


            Process current = schedule.removeFirst();

            System.out.println("Current Time: "+ time + "  Quantum: " + quantum);
            System.out.println("Next Process is as follows\n" + current + "\n");

            current.setExecutionTime(current.getExecutionTime() - quantum);//runs for set amount of time

            if (current.getExecutionTime() > 0){
                schedule.addLast(current);
                time += quantum;

                System.out.println("Current Time: "+ time + "  Quantum: " + quantum);
                System.out.println(current);
                System.out.println("Process " + current.getId() + " is not done yet, enqueueing...\n");
            } else{
                time += quantum + current.getExecutionTime(); // only runs for remaining time and is not enqueued because it is done!

                System.out.println("Current Time: "+ time + "  Quantum: " + quantum);
                System.out.println("Process " + current.getId() + " is finished\n");
            }

            numRounds++;
        }

        System.out.println("============ALL PROCESSES COMPLETE============");
    }

    public static void main(String[] args) {
        Scanner s = new Scanner(System.in);
        System.out.print("Please enter the name of the '.txt' file you'd like to use: ");
        String fileName = s.nextLine();

        fileName = (fileName.split(".txt"))[0]; //so the user can enter just the name or the name and extension

        try{
            Initializer(fileName + ".txt");
        } catch (IOException e){
            System.out.println("Wrong Filename Given: " + fileName + ".txt");
            System.out.println(e.getMessage());
        }
    }
}