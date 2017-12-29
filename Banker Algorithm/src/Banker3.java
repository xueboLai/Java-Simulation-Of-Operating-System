import java.util.*;
/**
 * This is the banker algorithm created for processing the data obtained through the main class in resourceAllocation.java
 * It has a constructor and method called process. The constructor is simply creating the instance and taking
 * in tasks information and resource's array through input argument.
 * 
 * @author: Xuebo Lai
 * @version:11/25/2017(last modification)
 * 
 * */	



public class Banker3 {
	private	ArrayList<Task> allTasks;
	private int[] resources;
	
	/**
	 *  This is the constructor of the class Banker3 which is used for simulating Banker Algorithm of Dijkstra.
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
	public Banker3(ArrayList<Task> allTasks,int[] resources){
		this.allTasks = allTasks;
		this.resources = resources;
	}
	/**
	 *  This is the actual method that implements/simulate the Banker Algorithm. It implements all the logics for the algorithm
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
		//looping through each element until all of them have been terminated or aborted
		boolean looping = true;
		int counter = 0;
		ArrayList<Task> blocked  = new ArrayList<Task>();
		ArrayList<String> blockedNum = new ArrayList<String>();
		while(looping){
			
			boolean changeCounterAlready = false;
			boolean Deadlock = true;
			int[] totalResourcesReleased = new int[resources.length];
			for(int ind = 0;ind<resources.length;ind++){
				totalResourcesReleased[ind]=0;
			}
			//Checking all elements in the blocked list to see whether they can be released from the blocking list
			ArrayList<Integer> realeaseBlock = new ArrayList<Integer>();
			for(int i = 0;i<blocked.size();i++){
				int[] quant = blocked.get(i).getActArray(blocked.get(i).getIndex());
				if(blocked.get(i).getTerminated()){
					blocked.remove(i);
					blockedNum.remove(i);
					i--;
				}else{
					blocked.get(i).incrementTotalRunningTime();
					int resourceType = quant[1];
					int numberReq = quant[2];
					if(resources[resourceType]<numberReq){
						continue;
					}else{
						//check whether the system can still be safe after blocked task's request have been requested.
						int resourcesLeft = resources[resourceType]-numberReq;
						boolean unsafe = true;
						for(int testingInside = 1;testingInside<allTasks.size();testingInside++){
							if(Integer.parseInt(blockedNum.get(i))==testingInside){
								int stillNeed = blocked.get(i).totalReqHold.get(resourceType)-numberReq;
								if(stillNeed<=resourcesLeft){
									unsafe = false;
									Enumeration allKeys = allTasks.get(testingInside).totalReqHold.keys();
									while(allKeys.hasMoreElements()){
										int type = (int) allKeys.nextElement();
										if(type!=resourceType){
											int need = allTasks.get(testingInside).totalReqHold.get(type);
											int leftInRes = resources[type];
											if(need>leftInRes){
												unsafe = true;
												break;
											}
										}
									}//end of while
									if(unsafe==false){
										//System.out.println("task "+i+" testing successful.");
										break;
									}	
								}
							}else{
							if(resourcesLeft>=blocked.get(i).totalReqHold.get(resourceType)){
								unsafe = false;
								Enumeration allKeys = allTasks.get(testingInside).totalReqHold.keys();
								while(allKeys.hasMoreElements()){
									int type = (int) allKeys.nextElement();
									if(type!=resourceType){
										int need = allTasks.get(testingInside).totalReqHold.get(type);
										int leftInRes = resources[type];
										if(need>leftInRes){
											unsafe = true;
											break;
										}
									}
								}
								if(unsafe==false){
									//System.out.println("task "+i+" testing successful.");
									break;
								}
									
							}
							}
						}
						if(unsafe){//blockedNum
							System.out.print("");
							//continue;
						}else{
							blocked.get(i).incrementActualWorkingTime();
							int left = blocked.get(i).totalReqHold.get(resourceType);
							blocked.get(i).totalReqHold.put(resourceType,(left-numberReq));
							if(blocked.get(i).possess.containsKey(resourceType)){
								int currentNum = (int) blocked.get(i).possess.get(resourceType);
								//System.out.println("resource")
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
							blocked.remove(i);
							i--;
						}
					}
				}
		}
			
			
			//checking the regular tasks.
			for(int i = 1;i<allTasks.size();i++){
			boolean inblockedList = false;
			for(int ab = 0;ab<blockedNum.size();ab++){
				if(i==Integer.parseInt(blockedNum.get(ab))){
					inblockedList = true;
				}
			}
			
			//checking whether the task has already been processed in the blocked list, if yes, avoid double count
			if(inblockedList!=true){
			if(!allTasks.get(i).getTerminated()){
				allTasks.get(i).incrementTotalRunningTime();
				String activity = allTasks.get(i).getActivity(allTasks.get(i).getIndex());
				int[] quant = allTasks.get(i).getActArray(allTasks.get(i).getIndex());
				int resourceType = quant[1];
				int numberReq = quant[2];
				//checking initiate activity to see wether the request is reasonable.
				if(activity.compareToIgnoreCase("initiate")==0){
					if(numberReq>resources[resourceType]){

						System.out.println("Banker aborts task "+i+" before run begins:\n"+"\t claim for resourse"+resourceType+" ("+numberReq+")"+" exceeds number of units present "+"("+resources[resourceType]+")");
						//  Banker aborts task 3 before run begins:
					    //claim for resourse 1 (5) exceeds number of units present (4)
						
						allTasks.get(i).setTerminated();
						allTasks.get(i).setAbort();
					
				}else
					{
						allTasks.get(i).incrementActualWorkingTime();
						Deadlock = false;
						allTasks.get(i).incrementIndex();	
						//continue;
					}
				//checking request to see whether the request can be granted.
				}else if(activity.compareToIgnoreCase("request")==0){
					
					if (resources[resourceType]<numberReq){
						blocked.add(allTasks.get(i));
						blockedNum.add(Integer.toString(i));
						//System.out.println("can't grant 196");
					}else{
						
						//the hashtable is used to see how many units of a resource type that the task have for now 
						
						//testing one: testing whether it exceed the initial claim:
						if(numberReq>(int)allTasks.get(i).totalReqHold.get(resourceType)){
							System.out.print("During cycle "+counter+"-"+(counter+1)+ " of Banker's algorithms\n"+"\tTask "+i+"'s "+"request exceeds its claim; aborted.");
							//release and inform how many resources are available next cycle. 
						Enumeration keys;
						keys = allTasks.get(i).possess.keys();
						while(keys.hasMoreElements()){
							int key = (int) keys.nextElement();
							totalResourcesReleased[key]+=allTasks.get(i).possess.get(key);
							System.out.println(" "+allTasks.get(i).possess.get(key)+" units of "+ "resource type "+key+" available next cycle. \n");
						}
							allTasks.get(i).setTerminated();
							allTasks.get(i).setAbort();
							continue;
						}
						
						//testing two: to see whether the state is safe
						int resourcesLeft = resources[resourceType]-numberReq;
						boolean unsafe = true;
						for(int testingInside = 1;testingInside<allTasks.size();testingInside++){
							if(i==testingInside){
								int stillNeed = allTasks.get(testingInside).totalReqHold.get(resourceType)-numberReq;
								if(stillNeed<=resourcesLeft){
									unsafe = false;
									Enumeration allKeys = allTasks.get(testingInside).totalReqHold.keys();
									while(allKeys.hasMoreElements()){
										int type = (int) allKeys.nextElement();
										if(type!=resourceType){
											int need = allTasks.get(testingInside).totalReqHold.get(type);
											int leftInRes = resources[type];
											if(need>leftInRes){
												unsafe = true;
												break;
											}
										}
									}
									if(unsafe ==false){
										break;
									}
								}
							}else{
							if(resourcesLeft>=allTasks.get(testingInside).totalReqHold.get(resourceType)){
								unsafe = false;
								Enumeration allKeys = allTasks.get(testingInside).totalReqHold.keys();
								while(allKeys.hasMoreElements()){
									int type = (int) allKeys.nextElement();
									if(type!=resourceType){
										int need = allTasks.get(testingInside).totalReqHold.get(type);
										int leftInRes = resources[type];
										if(need>leftInRes){
											unsafe = true;
											break;
										}
									}
								}
								if(unsafe==false){
									break;
								}
							}
							}
						}
						//dealing with the unsafe situation, add the task to the blocked list
						if(unsafe){
							blocked.add(allTasks.get(i));
							blockedNum.add(Integer.toString(i));
							continue;
						}else{
						//dealing with safe situation, execute the activity.
							allTasks.get(i).incrementActualWorkingTime();
							int left = allTasks.get(i).totalReqHold.get(resourceType);
							allTasks.get(i).totalReqHold.put(resourceType,(left-numberReq));
							if(allTasks.get(i).possess.containsKey(resourceType)){
								int currentNum = (int) allTasks.get(i).possess.get(resourceType);
								currentNum += numberReq;
								allTasks.get(i).possess.put(resourceType, currentNum);
							}else{
								allTasks.get(i).possess.put(resourceType, numberReq);
							}
							resources[resourceType] = resources[resourceType]-numberReq;
							
							allTasks.get(i).incrementIndex();
						}
					
					}
				}
				//release the task's resource and put those resources back to teh resrouces array.
				else if(activity.compareToIgnoreCase("release")==0){
					allTasks.get(i).incrementActualWorkingTime();
					int left = (int) allTasks.get(i).possess.get(resourceType)-numberReq;
					allTasks.get(i).possess.put(resourceType,left);
					totalResourcesReleased[resourceType]+=numberReq;
					Deadlock = false;
					int origin_value = allTasks.get(i).totalReqHold.get(resourceType);
					allTasks.get(i).totalReqHold.put(resourceType, (origin_value+numberReq));
					allTasks.get(i).incrementIndex();
					if(allTasks.get(i).getActivity(allTasks.get(i).getIndex()).compareToIgnoreCase("terminate")==0){
						allTasks.get(i).setTerminated();
					}
				//compute: basically suspend that task for a certain amount of time.
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
			//addign all the newly released resources to the resource array.
			for(int ind = 1;ind<resources.length;ind++){
				resources[ind]+=(totalResourcesReleased[ind]);
				/* only used for testing purpose
				 * if(totalResourcesReleased[ind]!=0){
					System.out.println("counter: "+counter+" type: "+ind+"; units:"+totalResourcesReleased[ind]);
					//System.out.println("bef counter resource: "+counter+" type: "+ind+"; units:"+resources[ind]);
					//System.out.println("counter: "+counter+" type: "+ind+"; units:"+resources[ind]);
				}*/
			}
			
			//adding all the newly unblocked tasks to the regular task list and remove them from the task list.
			for(int i = 0;i<realeaseBlock.size();i++){
				String removedObj = (blockedNum.get(realeaseBlock.get(i)));
				blockedNum.remove(removedObj);
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
		
		//printing out result
		System.out.println("\t\tBANKER's");
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
