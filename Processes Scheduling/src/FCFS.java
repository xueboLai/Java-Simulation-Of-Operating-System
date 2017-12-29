import java.io.File;
import java.util.*;
/**
 * This is the easiest scheduling algorithm for operating system: First comes first serve. It is a non-preemptive 
 * multi-programming algorithm. It aims to provide scheduling coordination for operating system.
 * 
 * 
 * @author: Xuebo Lai
 * @version:10/22/2017(last modification)
 * 
 * */	
public class FCFS {
	private ArrayList<Process> inputArr;
	private ArrayList<Integer> randomNumber;
	private int indexOfRan = 0;
	private int finish;
	private int cpuUti;
	private int IOUtil;
	private int throughput;
	private int totalTurnAround;
	private int totalWaitingTime;
	private int counter;
	private String verbose;
	

	//constructor for the class First comes, first serves
	public FCFS(ArrayList<Process> raw,ArrayList<Integer> random,String verbose){
		this.inputArr = raw;
		this.randomNumber = random;
		this.indexOfRan = 0;
		this.finish = 0;
		this.cpuUti = 0;
		this.IOUtil = 0;
		this.throughput =0 ;
		this.totalTurnAround = 0;
		this.totalWaitingTime = 0;
		this.counter = 0;
		this.verbose = verbose;
	}
	
	public ArrayList<Process> getArr(){
		return inputArr;
	}
	/**
	 *  this is the function that does the major part of first comes first serves. It prints out all the answer to the console.
	 * 
	 * @param If there's no optional flag, then args[0] is input file name. If there's optional flag, then args[0] is the optional flag and args[1] is the file name.
	 * 
	 * @return nothing but prints out all the answer in the console
	 * 
	 * @throws no exception
	 * 
	 * */
	public void process(){
		System.out.println("First come first serves:\n");
		ArrayList<Process> init = new ArrayList<Process>();
		for(int i = 0;i<inputArr.size();i++){
			init.add(inputArr.get(i));
		}
		ArrayList<Process> ready = new ArrayList<Process>();
		ArrayList<Process> running = new ArrayList<Process>();
		ArrayList<Process> blocked = new ArrayList<Process>();

		counter=0;


		do{
			String stringBuilder = "";

			if(verbose!=null){
			stringBuilder += "Before Cycle:\t"+String.format("%5d", counter);
			}

			if(verbose!=null){
			for(int a = 0; a<inputArr.size();a++){
					if(inputArr.get(a).getStatus().compareTo("running")==0){
						stringBuilder+=String.format("%12s",inputArr.get(a).getStatus())+String.format("%3d",inputArr.get(a).getCpuBurst()+1);}
					else if(inputArr.get(a).getStatus().compareTo("blocked")==0){
						stringBuilder+=String.format("%12s",inputArr.get(a).getStatus())+String.format("%3d",inputArr.get(a).getSingleIO()+1);
					}else{
						stringBuilder+=String.format("%12s",inputArr.get(a).getStatus())+String.format("%3d",0);
					}
				}
			
			stringBuilder+="\n";
			System.out.print(stringBuilder);
			}
			//}
			//finish printing out information about each process
			//This is the real part
			
			boolean emptyEle = false;
			for(int d = 0;d<blocked.size();d++){
				if(blocked.get(d).getSingleIO()==0){
					emptyEle = true;
				}
			}
			
			if(!emptyEle){

			for(int b = 0;b<init.size();b++){

				if(init.get(b).getA()==counter){
					//set up the incoming element
					int[] randomNumber2 = this.randomOS(init.get(b));
					//System.out.println("The random number is "+randomNumber2[0]+"; the gnerated number is "+randomNumber2[1]);
					init.get(b).setStatus("ready");
					init.get(b).setSingleIO(init.get(b).getM()*init.get(b).getCpuBurst());
					init.get(b).setStart(true);
					ready.add(init.get(b));
				}
			}
			}
			
			


			if(running.size()!=0){
				if(running.get(0).getC()==0){
					running.get(0).setDone(true);
					running.get(0).setStatus("terminated");
					running.get(0).setFinishTime(counter);
					this.finish = counter;
					running.remove(0);
				}
				else if(running.get(0).getCpuBurst()==0){
					running.get(0).setStatus("blocked");
					blocked.add(running.get(0));
					running.remove(0);
				}
				else{
					//cpuUti++;
					running.get(0).decrementCpuBurst();
					running.get(0).decrementC();
					//System.out.println("decpuburst+decre: "+running.get(0).getCpuBurst()+" "+running.get(0).getC());
					running.get(0).setStatus("running"); 
				}

			}


			
			if(running.size()==0){
				//System.out.println("The ready size is "+ready.size());
				if(ready.size()!=0){
				//cpuUti++;
				//ready.get(0).decrementWaitingTime();
				running.add(ready.get(0));
				ready.remove(0);
				running.get(0).setStatus("running");
				running.get(0).decrementC();
				running.get(0).decrementCpuBurst();
				}
			}
			
			//dealing with blocked:
			ArrayList<Process> temp = null;
			temp = new ArrayList<Process>();

			for(int d = 0;d<blocked.size();d++){
				if(blocked.get(d).getSingleIO()==0){
					temp.add(blocked.get(d));
					blocked.remove(d);
					d--;
				}
				else{
					blocked.get(d).decrementSingleIO();
					blocked.get(d).incrementIO();
					//blocked.get(d).incrementWaitingTime();
				}
			}
			//System.out.println("The temp size is "+temp.size());
			if(ready.size()==0&&running.size()==0&&temp.size()!=0){
				if(running.size()==0){
				Collections.sort(temp);
				temp.get(0).setStatus("running");
				//temp.get(0).randomOS();
				int[] randomNumber1 = this.randomOS(temp.get(0));
				//System.out.println("The random number is "+randomNumber1[0]+"; the gnerated number is "+randomNumber1[1]);
				temp.get(0).setSingleIO(temp.get(0).getM()*temp.get(0).getCpuBurst());
				running.add(temp.get(0));
				//very very weird: processing running here
				running.get(0).decrementCpuBurst();
				running.get(0).decrementC();
				//cpuUti++;
				//very very weird
				temp.remove(0);
				for(int e = 0;e<temp.size();e++){
					//temp.get(e).randomOS();
					int[] randomNumberInt = this.randomOS(temp.get(e));
					//System.out.println("The random number is "+randomNumberInt[0]+"; the gnerated number is "+randomNumberInt[1]);
					temp.get(e).setStatus("ready");
					temp.get(e).setSingleIO(temp.get(e).getM()*temp.get(e).getCpuBurst());
					ready.add(temp.get(e));
				}
				}
			}
			else if(temp.size()!=0){
				Collections.sort(temp);
				for(int e = 0;e<temp.size();e++){
					//temp.get(e).randomOS();
					int[] randomNumber2 = this.randomOS(temp.get(e));
					//System.out.println("The random number is "+randomNumber2[0]+"; the gnerated number is "+randomNumber2[1]);
					temp.get(e).setStatus("ready");
					temp.get(e).setSingleIO(temp.get(e).getM()*temp.get(e).getCpuBurst());
					//temp.get(e).incrementWaitingTime();
					ready.add(temp.get(e));

				}
			}
			
			// ////
			if(emptyEle){
			//adding the element in the beginning to the ready list
			for(int b = 0;b<init.size();b++){
				if(init.get(b).getA()==counter){
					//set up the incoming element
					//init.get(b).randomOS();
					int[] randomNumber2 = this.randomOS(init.get(b));
					//System.out.println("The random number is "+randomNumber2[0]+"; the gnerated number is "+randomNumber2[1]);
					init.get(b).setStatus("ready");
					init.get(b).setSingleIO(init.get(b).getM()*init.get(b).getCpuBurst());
					init.get(b).setStart(true);
					//init.get(b).incrementWaitingTime();
					ready.add(init.get(b));
				} 
			}
			}
			counter++;
			//test run
			if(running.size()!=0){
				cpuUti++;
			}
			//test block
			if(blocked.size()!=0){
				IOUtil++;
			}
			
			
			
			//increment the wait time for all elements inside ready list
			for (int c = 0;c<ready.size();c++){
				ready.get(c).incrementWaitingTime();
			}
			}while((ready.size()!=0)||(running.size()!=0)||(blocked.size()!=0));
			
		System.out.println("The scheduling algorithm used was First Come First Served\n");
		//printing out final output:
		for (int m = 0;m<init.size();m++){
			System.out.println("Process "+m+":");
			Process m1 = init.get(m);
			System.out.println("\t(A,B,C,M) = ("+m1.getA()+","+m1.getB()+","+m1.getSaveC()+","+m1.getM()+")");
			System.out.println("\tFinishing time: "+m1.getFinishTime());
			System.out.println("\tTurnaround time: "+(m1.getFinishTime()-m1.getA()));
			totalTurnAround+=(m1.getFinishTime()-m1.getA());
			System.out.println("\tI/O time: "+m1.getIO());
			System.out.println("\tWaiting Time: "+m1.getWaitingTime());
			this.totalWaitingTime+=m1.getWaitingTime();
			System.out.println();
			
			}
		System.out.println("Summary Data:");
		System.out.println("Finishing Time: "+this.finish);
		System.out.println("CPU Utilization: "+String.format("%.6f",(double)this.cpuUti/(double)(this.counter-1)));
		System.out.println("I/O Utilization: "+String.format("%.6f",(double)this.IOUtil/(double)(this.counter-1)));
		System.out.println("Throughput: "+String.format("%.6f",((double)this.inputArr.size()/(double)(this.counter-1))*100)+" processes per hundred cycles.");
		System.out.println("Average turnaround time: "+String.format("%.6f", ((double)this.totalTurnAround/(double)this.inputArr.size())));
		System.out.println("Average waiting time: "+String.format("%.6f",((double)this.totalWaitingTime/(double)this.inputArr.size())));
		System.out.println("\n\n\n\n");
		
		
		
		
		
		}
	
	public int[] randomOS(Process pro){
		int cpuBurst = 1+(randomNumber.get(indexOfRan)%pro.getB());
		this.indexOfRan++;
		pro.setCpuBurst(cpuBurst);
		int[] arr1= new int[2];
		arr1[0] = randomNumber.get((indexOfRan-1));
		arr1[1] = pro.getCpuBurst();
		return arr1;
	}
	
/*
 * 	public int randomOS(){
	this.indexOfRan++;
	this.cpuBurst =  1+(randomArr.get(indexOfRan)%B);
	return cpuBurst;
}
 */
	
	
	
}



