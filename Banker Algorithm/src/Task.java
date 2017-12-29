import java.util.*;
/**
 *  The class created for storing the information for each task. It contains all the detailed information for
 *  each tasks. Most of the methods in this class are the getter and setter for the private properties field.
 * 
 * 
 * @author: Xuebo Lai
 * @version:11/25/2017(last modification)
 * 
 * */	
public class Task {
	private int index = 0;
	private ArrayList<String> activities;
	protected ArrayList<int[]> quantity;
	boolean Terminated = false;
	boolean abort = false;
	protected Hashtable<Integer,Integer> possess;
	private int running = 0;
	private boolean setRunning = false;
	private int totalRunningTime = 0;
	private int actualWorkingTime = 0;
	private int taskNumber;
	protected Hashtable<Integer,Integer> totalReqHold;
	//protected int stillNeed;
	/**
	 *  This is the constructor of the class. We call on it every time we want a new instance of task.
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
	public Task(int taskNumber){
		this.activities = new ArrayList<String>();
		this.quantity = new ArrayList<int[]>();
		this.Terminated = false;
		this.abort = false;
		this.index = 0;
		possess = new Hashtable<Integer,Integer>();
		this.running = 0;
		this.setRunning = false;
		this.totalRunningTime = 0;
		this.actualWorkingTime = 0;
		this.taskNumber = taskNumber;
		this.totalReqHold = new Hashtable<Integer,Integer>();
		//this.stillNeed = 
	}
	public boolean addActivity(String activity,int[] actArr){
		if(activity.compareToIgnoreCase("initiate")==0){
			this.totalReqHold.put(actArr[1],actArr[2]);
			//allTasks.get(i).possess.put(resourceType, currentNum);
		}
		
		try{
			activities.add(activity);
			quantity.add(actArr);
			return true;
		}catch (Exception e){
			System.err.println("failure writing data into task");
			return false;
		}
		
	}
	//getter for all private fields shown above: the name of function is the best explanation (all getters are 
	//created based on the structure of get + name of the property field.)
	public int getTaskNum(){
		return taskNumber;
	}
	public int getActualWorkingTime(){
		return actualWorkingTime;
	}
	public int getTotolRunningTime(){
		return this.totalRunningTime;
	}
	public boolean getEnd(){
		return (Terminated||abort);
	}
	public int getIndex(){
		return index;
	}
	public boolean getTerminated(){
		return Terminated;
	}
	public String getActivity(int i){
		return activities.get(i);
	}
	public int[] getActArray(int i){
		return quantity.get(i);
	}
	public boolean getAbort(){
		return abort;
	}
	public boolean stillRunning(){
		if(running>0){
			return true;
		}else{
			setRunning = false;
			return false;
		}
	}
	public boolean getSettingRunning(){
		return setRunning;
	}
	
	//setter for all private fields shown above: the name of function is the best explanation (all setters are 
	//created based on the structure of set + name of the property field.)
	//the expception will be increment. In that case, the setter will just be the "increment"+property's name.
	public void incrementActualWorkingTime(){
		this.actualWorkingTime++;
	}
	public void incrementTotalRunningTime()
	{
		this.totalRunningTime++;
	}	
	public void decrementRunningTime(){
		this.running--;
	}
	public void setRunning(int time){
		this.setRunning = true;
		this.running = time;
	}
		
	public void setTerminated(){
		Terminated = true;
	}
	public void incrementIndex(){
		this.index++;
	}
	public void setAbort(){
		abort = true;
	}
	
	
	
}
