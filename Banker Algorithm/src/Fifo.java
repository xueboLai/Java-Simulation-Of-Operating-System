import java.util.*;
/**
 *  This class implments the First in first out optimistic resource manager. This method (fifo) will try to 
 *  run all the activities unless the activities' requiremnet can't be satisfied. The task whose activity can't be
 *  run will be put into the blocked list, and every time we will check the blocked list first, before we move on to the
 *  regular task
 * 
 * 
 * @author: Xuebo Lai
 * @version:11/25/2017(last modification)
 * 
 * */	
public class Fifo {
	private	ArrayList<Task> allTasks;
	private int[] resources;
	/**
	 *  This is the constructor of the class Fifo which is used for simulating alogrithm Fifo.
	 * 
	 * @param args[0]: ArrayList<Task> allTasks; args[1]: array of resources info 
	 * 
	 * @return nothing;
	 * 
	 * @throws no exception
	 * 
	 * 
	 * 
	 * */

	public Fifo(ArrayList<Task> allTasks,int[] resources){
		this.allTasks = allTasks;
		this.resources = resources;
	}
	
	/**
	 *  This is the actual method that runs the algorithm of fifo. It implements all the logic for the algorithm
	 *  and behaviors of different activities (request/release/initiate/compute). 
	 *  
	 * 
	 * @param none (All the parameters can be accessed in the class properties)
	 * 
	 * @return (print out) for each task, how long it runs, how long it waits and what's the percentage of the wasiting time.
	 * 
	 * @throws no exception
	 * */
	public void process(){
		//setting up the loop: when all the tasks have been terminated, the loop ends.
		boolean looping = true;
		int counter = 0;
		ArrayList<Task> blocked  = new ArrayList<Task>();//the task that has been blocked
		ArrayList<String> blockedNum = new ArrayList<String>();
		while(looping){
			boolean changeCounterAlready = false;
			boolean Deadlock = true;
			//process request
			int[] totalResourcesReleased = new int[resources.length];
			for(int ind = 0;ind<resources.length;ind++){
				totalResourcesReleased[ind]=0;
			}

			//checking all tasks that have been blocked before checking the regular tasks
			ArrayList<Integer> realeaseBlock = new ArrayList<Integer>(); 
			for(int i = 0;i<blocked.size();i++){
				//if the tasks have been aborted/terminated, delete it from the blocked list.
				if(blocked.get(i).getTerminated()){
					blocked.remove(i);
					blockedNum.remove(i);
					i--;
				}else{
					//check whether blocked tasks can be run now.
					blocked.get(i).incrementTotalRunningTime();
					int[] quant = blocked.get(i).getActArray(blocked.get(i).getIndex());
					int resourceType = quant[1];
					int numberReq = quant[2];
					//If there's not enough resources to run the task, continue the loop and skip the rest of th checking;
					if(resources[resourceType]<numberReq){
						continue;
					//inside else: if the task can be unblocked, we will execute the request, update the resources 
					//that the task holds and in the resource's array.
					}else{
						
						//System.out.println("Counter: "+counter+" In block list, task "+blocked.get(i).getTaskNum()+ " Resource "+resourceType+": request granted");
						blocked.get(i).incrementActualWorkingTime();
						if(blocked.get(i).possess.containsKey(resourceType)){
							int currentNum = (int) blocked.get(i).possess.get(resourceType);
							currentNum += numberReq;
							blocked.get(i).possess.put(resourceType, currentNum);
						}else{
							blocked.get(i).possess.put(resourceType, numberReq);
						}
						resources[resourceType] = resources[resourceType]-numberReq;
						Deadlock = false;
						blocked.get(i).incrementIndex();
						realeaseBlock.add(i);
						changeCounterAlready = true;
						//counter++;
						blocked.remove(i);
						i--;
					}
				}
			}
			
			
			//start dealing with the regular tasks.
			for(int i = 1;i<allTasks.size();i++){
			boolean inblockedList = false;
			//System.out.println("testing: (to see whether it will get into the loop) blocked size "+blocked.size()+"; blockedNum size: "+":"+blockedNum.size());
			for(int ab = 0;ab<blockedNum.size();ab++){
				if(i==Integer.parseInt(blockedNum.get(ab))){
					inblockedList = true;
				}
			}
			//avoid double counting the tasks inside
			if(inblockedList!=true){
			if(!allTasks.get(i).getTerminated()){
				allTasks.get(i).incrementTotalRunningTime();
				String activity = allTasks.get(i).getActivity(allTasks.get(i).getIndex());
				int[] quant = allTasks.get(i).getActArray(allTasks.get(i).getIndex());
				int resourceType = quant[1];
				int numberReq = quant[2];
				//System.out.println("counter "+counter+": task "+allTasks.get(i).getTaskNum()+": "+activity);
				//dealing with the activity initiate
				if(activity.compareToIgnoreCase("initiate")==0){
					allTasks.get(i).incrementActualWorkingTime();
					Deadlock = false;
					allTasks.get(i).incrementIndex();	
					continue;
				//dealing with the activity request
				}else if(activity.compareToIgnoreCase("request")==0){
					if (resources[resourceType]<numberReq){
						//System.out.println("before adding into blocked; blocked size "+blocked.size()+"; blockedNum size: "+":"+blockedNum.size());
						blocked.add(allTasks.get(i));
						blockedNum.add(Integer.toString(i));
						//System.out.println("INside adding into blocked; blocked size "+blocked.size()+"; blockedNum size: "+":"+blockedNum.size());
						//allTasks.remove(i);
						//i--;
						//System.out.println("can't grant");
					}else{
						allTasks.get(i).incrementActualWorkingTime();
						//the hashtable is used to see how many units of a resource type that the task have for now 
						//
						if(allTasks.get(i).possess.containsKey(resourceType)){
							int currentNum = (int) allTasks.get(i).possess.get(resourceType);
							//System.out.println("resource is "+resourceType);
							//System.out.println("request is "+numberReq);
							//System.out.println("available:"+resources[resourceType]);
							//System.out.println("resource")
							currentNum += numberReq;
							allTasks.get(i).possess.put(resourceType, currentNum);
						}else{
							allTasks.get(i).possess.put(resourceType, numberReq);
						}
						resources[resourceType] = resources[resourceType]-numberReq;
						Deadlock = false;
						allTasks.get(i).incrementIndex();	
					}
				}//end of request
				//dealing with the activity release
				else if(activity.compareToIgnoreCase("release")==0){
					allTasks.get(i).incrementActualWorkingTime();
					int left = (int) allTasks.get(i).possess.get(resourceType)-numberReq;
					allTasks.get(i).possess.put(resourceType,left);
					totalResourcesReleased[resourceType]+=numberReq;
					Deadlock = false;
					allTasks.get(i).incrementIndex();
					if(allTasks.get(i).getActivity(allTasks.get(i).getIndex()).compareToIgnoreCase("terminate")==0){
						allTasks.get(i).setTerminated();
					}
				//dealing with the activity release
				}else if(activity.compareToIgnoreCase("compute")==0){
					allTasks.get(i).incrementActualWorkingTime();
					Deadlock = false;
					if(allTasks.get(i).getSettingRunning()){
						allTasks.get(i).decrementRunningTime();
					}else{
						allTasks.get(i).setRunning(resourceType);
						allTasks.get(i).decrementRunningTime();
					}
					if(!allTasks.get(i).stillRunning()){
						allTasks.get(i).incrementIndex();
					}
					if(allTasks.get(i).getActivity(allTasks.get(i).getIndex()).compareToIgnoreCase("terminate")==0){
						allTasks.get(i).setTerminated();
					}
				}
			}
			}
			}
			//System.out.println(counter + "!!!!!!!!!!!"+resources[1]);
			//adding all released value to the origin value.
			for(int ind = 1;ind<resources.length;ind++){
				resources[ind]+=(totalResourcesReleased[ind]);
			}

			
			//adding the unblocked tasks back to the regular task and remove the unblock task from the removed list.
			for(int i = 0;i<realeaseBlock.size();i++){
				String removedObj = (blockedNum.get(realeaseBlock.get(i)));
				blockedNum.remove(removedObj);

			}

			//dealing with the deadlock situation: keep aborting a task from the lowest numbered task until
			//the deadlock has been solved.
			if(Deadlock){
				for(int i = 1;i<allTasks.size();i++){
					if(!allTasks.get(i).getTerminated()){
						allTasks.get(i).setAbort();
						allTasks.get(i).setTerminated();
						Enumeration allKeys = allTasks.get(i).possess.keys();
						while(allKeys.hasMoreElements()){
							int type = (int)allKeys.nextElement();
							int value = allTasks.get(i).possess.get(type);
							resources[type] = resources[type]+value;
						}
						//test deadlock
						boolean checkDeadLockAgain = false;
						int[] totalResourcesRequired = new int[resources.length];
						for(int ind = 0;ind<totalResourcesRequired.length;ind++){
							totalResourcesRequired[ind]=0;
						}
						for(int k = 1;k<allTasks.size();k++){
							if(!allTasks.get(k).getTerminated()){
								String activity = allTasks.get(k).getActivity(allTasks.get(k).getIndex());
								int[] quant = allTasks.get(k).getActArray(allTasks.get(k).getIndex());
								int resourceType = quant[1];
								int numberReq = quant[2];
								totalResourcesRequired[resourceType] += numberReq;
							}
						}
						for(int k = 1;k<resources.length;k++){
							if(totalResourcesRequired[k]>resources[k]){
								//some place gotta go wrong
								checkDeadLockAgain = true;
							}
						}
						
						if(checkDeadLockAgain){
							continue;
						}else{
						//test deadlock
						break;
						}
					}
				}
			}
			
			//check whether all the process has been terminated to decide the value for looping.
			
			boolean terminateLoop =true;
			for(int j = 1;j<allTasks.size();j++){

				if((allTasks.get(j).getTerminated()==false)){
					 terminateLoop= false;
				}
			}
			if(terminateLoop){
				looping = false;
			}
			counter++;
		}
		
		//generate the ouput to the console.
		System.out.println("\t\tFifo");
		for(int i = 1;i<allTasks.size();i++){
		System.out.print("    Task "+String.format("%-3d", i));
		if(allTasks.get(i).getAbort()){
			System.out.println("      aborted");
		}else{
			System.out.print("      ");
			System.out.print(String.format("%-5d", allTasks.get(i).getTotolRunningTime()));
			System.out.print(String.format("%-5d", (allTasks.get(i).getTotolRunningTime()-allTasks.get(i).getActualWorkingTime())));
			
			double waitingTime = (double)(allTasks.get(i).getTotolRunningTime()-allTasks.get(i).getActualWorkingTime())/(double)allTasks.get(i).getTotolRunningTime();
			double percentage = waitingTime*100;
			int outputPercentage = (int)percentage;
			//String.format("%3d",inputArr.get(a).getCpuBurst()+1)
			//String.format("%.6f",(double)this.cpuUti/(double)(this.counter-1))
			System.out.print(outputPercentage+"%");
			System.out.println();
		}
		
		}
		int totalTime = 0;
		int totalWaitingTime = 0;
		
		for(int i = 1;i<allTasks.size();i++){
			if(!allTasks.get(i).getAbort()){
				totalTime+=allTasks.get(i).getTotolRunningTime();
				totalWaitingTime += (double)(allTasks.get(i).getTotolRunningTime()-allTasks.get(i).getActualWorkingTime());
			}
		}
		int totalWaitingPercentage = (int)((double)totalWaitingTime/(double)totalTime*100);
		System.out.print("    total"+"   ");
		System.out.print("      ");
		System.out.print(String.format("%-5d", totalTime));
		System.out.print(String.format("%-5d",totalWaitingTime));
		System.out.print(totalWaitingPercentage+"%");
		System.out.println();
		System.out.println();
	}

	
	
}
