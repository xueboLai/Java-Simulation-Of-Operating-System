import java.io.File;
import java.io.IOException;
import java.util.Scanner;
import java.util.*;
/**
 * This is the class that has main method. It coordinates all parts of the program to work. It has the following function:
 * 1, read in random number's file
 * 2, read in processes information's file
 * 3, calls all the scheduling algorithm to print out all the necessary information to the console
 * 4, the result will be displayed as FCFS, RR, SJF, HPRN.
 * 
 * 
 * @author: Xuebo Lai
 * @version:10/22/2017(last modification)
 * 
 * */	
public class Scheduling {

	public static void main(String[] args) throws IOException {
		if(args.length<1){
			System.err.println("There should be at least one input");
			System.exit(1);
		}
		//begin of generating random number
		ArrayList<Integer> randomNum = new ArrayList<Integer>();
		File randomNumInput = null;
		Scanner randomSc = null;
		try{
			randomNumInput = new File("random.txt");
			randomSc = new Scanner(randomNumInput);
		}catch (Exception e){
			System.err.print(e);
			System.exit(1);
		}
		while(randomSc.hasNext()){
			int nextRan = randomSc.nextInt();
			randomNum.add(nextRan);
			//System.out.println(nextRan);
		}
		String verbose = null;
		String readFileName = null;
		if(args.length>1){
			verbose = args[0];
			readFileName = args[1];
		}else{
			readFileName = args[0];
		}

		//end of reading generating randomNum list
		File input = null;
		Scanner sc = null;
		try{
			input = new File(readFileName);
			sc = new Scanner(input);
		}catch (Exception e){
			System.err.println("read file failed1");
			System.exit(1);
		}
		
		int numberOfProcesses = sc.nextInt();
		ArrayList<Process> allProcesses = new ArrayList<Process>();
		ArrayList<Process> allProcessFcfs = new ArrayList<Process>();
		ArrayList<Process> allProcessRR = new ArrayList<Process>();
		ArrayList<Process> allProcessSJF = new ArrayList<Process>();
		ArrayList<Process> allProcessHPRN = new ArrayList<Process>();
		System.out.print("The original input was: "+numberOfProcesses+" ");
		for (int i = 0;i<numberOfProcesses;i++){
			int a= sc.nextInt();
			int b = sc.nextInt();
			int c = sc.nextInt();
			int m = sc.nextInt();
			allProcesses.add(new Process(a,b,c,m,i,randomNum));
			allProcessFcfs.add(new Process(a,b,c,m,i,randomNum));
			allProcessRR.add(new Process(a,b,c,m,i,randomNum));
			allProcessSJF.add(new Process(a,b,c,m,i,randomNum));
			allProcessHPRN.add(new Process(a,b,c,m,i,randomNum));
			System.out.print("("+a+" "+b+" "+c+" "+m+") ");
		}
		System.out.println();
		Collections.sort(allProcesses);
		Collections.sort(allProcessFcfs);
		Collections.sort(allProcessRR);
		Collections.sort(allProcessSJF);
		Collections.sort(allProcessHPRN);
		System.out.print("The (sorted) input is: "+numberOfProcesses+" ");
		for (int i = 0;i<numberOfProcesses;i++){
			Process temp = allProcesses.get(i);
			System.out.print("("+temp.getA()+" "+temp.getB()+" "+temp.getC()+" "+temp.getM()+") ");
		}
		System.out.println();
		System.out.println();
		
		
		
		//for (int item = 0;item<allProcesses)
		
		FCFS fcfs = new FCFS(allProcessFcfs,randomNum,verbose);
		fcfs.process();
		
		RR rr = new RR(allProcessRR,randomNum,verbose);
		rr.process();
		
		SJF sjf = new SJF(allProcessSJF,randomNum,verbose);
		sjf.process();
		
		HPRN hprn = new HPRN(allProcessHPRN,randomNum,verbose);
		hprn.process();
		
		
		
		
		
		//FC
		
		
		
		
	}

}
