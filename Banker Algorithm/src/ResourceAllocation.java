import java.io.File;
import java.util.*;
/**
 * The goal of this program is to allocate resources based on the method of either optimistic resource manager (Fifo)
 * or Banker's algorithm of Dijkstra(Banker3). The optimistic manager simply tries to run an activities (initiate/request/
 * release/compute). If the activities can be satified, the manger will run it, otherwise block it. For the banker algorithm,
 * 1, if a task requests more than its claim, we abort the task; 2, if after assigning resources to a task, the 
 * system is no longer safe, then we block the task. 3, checking both regular tasks and blocked tasks' request to see wehter
 * we can satisfy it, if we can, grant the permission; otherwise block it or leave the blocked task in the block list.
 * 
 * This is the main class that takes in input for the resource manager program. It takes in file path by argument line and 
 * reads input from a file. It sorts the input and passes them into helper classes to process. The answer will be generated
 * in console by other helper functions.
 * 
 * @author: Xuebo Lai
 * @version:11/25/2017(last modification)
 * 
 * */	

public class ResourceAllocation {

	public static void main(String[] args) {
		//checking whether the input argument is valid.
		if(args.length<1){
					System.err.println("Err:There should be file in the input field");
					System.exit(1);
		}
		File input = null;
		Scanner inputSc = null;
		Scanner inputSc2 = null;
		try{
			input = new File(args[0]);
			inputSc = new Scanner(input);
			inputSc2 = new Scanner(input);
		}catch(Exception e){
			System.err.println("error: "+e);
			System.exit(1);
		}
		//get the task number and the resource information from the input. 
		int taskNum = inputSc.nextInt();
		int resourceNum = inputSc.nextInt();
		int taskNum2 = inputSc2.nextInt();
		int resourceNum2 = inputSc2.nextInt();
		int[] resources = new int[resourceNum+1];
		int[] resources2 = new int[resourceNum+1];
		for (int i = 1;i<=resourceNum;i++){
			resources[i] = inputSc.nextInt();
			resources2[i] = inputSc2.nextInt();
			//resources2[i] = inputSc.nextInt();
		}

		
		
		//store all the information from the task into allTasks, which can make later calculation easier.
		ArrayList<Task> allTasks = new ArrayList<Task>(taskNum+1);
		ArrayList<Task> allTasks2 = new ArrayList<Task>(taskNum+1);
		for(int i = 0;i<taskNum+1;i++){
			allTasks.add(new Task(i));
			allTasks2.add(new Task(i));
		}
		int counter = 1;
		//Task task = new Task(1);
		while(inputSc.hasNext()){
			String status = inputSc.next();
			int[] num = new int[3];
			num[0] = inputSc.nextInt();
			num[1] = inputSc.nextInt();
			num[2] = inputSc.nextInt();
			allTasks.get(num[0]).addActivity(status, num);
			counter++;
		}
		while(inputSc2.hasNext()){
			String status = inputSc2.next();
			int[] num2 = new int[3];
			num2[0] = inputSc2.nextInt();
			num2[1] = inputSc2.nextInt();
			num2[2] = inputSc2.nextInt();
			allTasks2.get(num2[0]).addActivity(status, num2);
			counter++;
		}

		
		//using the helper function to generate the result to the console.
		new Fifo(allTasks,resources).process();
		new Banker3(allTasks2,resources2).process();
		
	}
	
}
