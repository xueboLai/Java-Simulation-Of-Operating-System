import java.util.*;
/**
 * This is the process class. It contains all the information for a process that we need to use for scheduling algorithm,
 * All the field's details has been commented below. 
 * 
 * 
 * @author: Xuebo Lai
 * @version:10/22/2017(last modification)
 * 
 * */	
public class Process implements Comparable{
	private int A;//arrival time;
	private int B;//cpu burst time
	private int C;//total cpu time needed
	private int M;//multiplier
	private int count;//arriving sequence
	private int IO=0;//total io time
	private int waitingTime = 0;//total waiting time
	private int singleIO = 0;//io burst time
	private int finishTime = 0;//when the program finishes
	private String status;//what's the current status of the process: unstarted, running, blocked, ready
	private boolean start = false;//whether the process has been initialized
	private int cpuBurst = 0;//cpu burst time
	private ArrayList<Integer> randomArr;//the arraylist that has all the random numbers.
	private int indexOfRan = 0;//index of the random number that we pick
	private boolean done = false;//see whether the program is finished
	private int saveC;//the original value of c
	private int totalTime1;//the total that the program runs
	private int runningTime;// the running time of th program.

	
	
	//constructor
	public Process(int arrival, int cBurst, int cNeed, int multiplier,int count,ArrayList<Integer> randomArr){
		this.A = arrival;
		this.B = cBurst;
		this.C = cNeed;
		this.M = multiplier;
		this.count = count;
		this.IO = 0;
		this.waitingTime=0;
		this.singleIO = 0;
		this.finishTime = 0;
		this.status = "unstarted";
		this.start = false;
		this.indexOfRan = -1;
		this.randomArr = randomArr;
		this.done = false;
		this.saveC = this.C;
		this.totalTime1=0;
		this.runningTime = 0;
	}
	
	//	
	/*public int randomOS(){
	this.indexOfRan++;
	this.cpuBurst =  1+(randomArr.get(indexOfRan)%B);
	return cpuBurst;
}*/
	
	
	
	
	
	//getter
	public int getRunningTime(){
		return this.runningTime;
	}
	public int getTotalTime(){
		return this.totalTime1;
	}
	public int getSaveC(){
		return saveC;
	}
	public boolean getDone(){
		return this.done;
	}
	public int getSingleIO(){
		return singleIO;
	}
	public int getCpuBurst(){
		return this.cpuBurst;
	}
	public boolean getStart(){
		return start;
	}
	public String getStatus(){
		if(!start){
			return "unstarted";
		}
		return status;
	}
	public int getSingleTime(){
		return this.singleIO;
	}
	public int getFinishTime(){
		return this.finishTime;
	}
	public int getIO(){
		return IO;
	}
	public int getWaitingTime(){
		return this.waitingTime;
	}
	public int getA(){
		return A;
	}
	public int getB(){
		return B;
	}
	public int getC(){
		return C;
	}
	public int getM(){
		return M;
	}
	public int getCount(){
		return count;
	}
	//setter
	public void incrementRunningTime(){
		this.runningTime++;
	}
	public void incrementTotalTime(){
		this.totalTime1++;
	}
	public void setCpuBurst(int cpuBurstTime){
		this.cpuBurst = cpuBurstTime;
	}
	public void setDone(boolean done){
		this.done = done;
	}
	public void decrementWaitingTime(){
		this.waitingTime--;
	}
	
	public void decrementSingleIO(){
		this.singleIO--;
	}
	public void decrementC(){
		this.C--;
	}
	public void decrementCpuBurst(){
		this.cpuBurst--;
	}
	public void setStart(boolean start){
		this.start = start;
	}
	public void setStatus(String currentStatus){
		this.status = currentStatus;
	}
	public void setA(int a){
		this.A = a;
	}
	public void setB(int b){
		this.B = b;
	}
	public void setC(int c){
		this.C = c;
	}
	public void setM(int m){
		this.M = m;
	}
	//increment
	public void incrementIO(){
		this.IO++;
	}
	public void incrementWaitingTime(){
		this.waitingTime++;
	}
	/*		this.singleIO = 0;
		this.finishTime = 0;
	 * */
	public void setSingleIO(int singleIO){
		this.singleIO = singleIO;
	}
	public void setFinishTime(int finishTime){
		this.finishTime = finishTime;
	}

	@Override
	public int compareTo(Object o) {
		Process another = (Process)o;
		if(this.A>another.getA()){
			return 1;
		}else if(this.A<another.getA()){
			return -1;
		}else{
			if(this.count<another.getCount()){
				return -1;
			}else{
				return 1;
			}
		}
	}


	static class CompareFilesByFCFS implements Comparator<Process> {
		@Override
		public int compare(Process o1, Process o2) {
			if(o1.getA()>o2.getA()){
				return 1;
			}else if(o1.getA()<o2.getA()){
				return -1;
			}else{
				if(o1.getCount()<=o2.getCount()){
					return -1;
				}else{
					return 1;
				}
			}
			
		}
	}
	static class CompareFilesBySJF implements Comparator<Process> {
		@Override
		public int compare(Process o1, Process o2) {
			if(o1.getC()>o2.getC()){
				return 1;
			}else if(o1.getC()<o2.getC()){
				return -1;
			}else{
				return o1.compareTo(o2);
			}
			
		}
	}
	
	
	
	/*static class CompareFilesByHPRN implements Comparator<Process> {
		@Override
		public int compare(Process o1, Process o2) {
			if(o1.getC()>o2.getC()){
				return 1;
			}else if(o1.getC()<o2.getC()){
				return -1;
			}else{
				return o1.compareTo(o2);
			}
			
		}
	}*/
	
	
	
	
	
	

}


