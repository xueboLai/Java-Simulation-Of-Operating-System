/**
 * The goal of this program is to simulate demand paging. Through simulation, we can 
 * see the relation among process size, page size, program size, job mix, locality etc. 
 * in the context of using multiprogramming and demand paging. The basic idea is to have
 * a driver to create reference and then have the paging simulator to see whether the reference 
 * is a hit or a page fault. If it is a page fault, adapt certain replacement strategy accordingly.
 * here's the explanation of M,P,S,J,N,R shown below:
 * M the mahicne size in words;
 * P the page size in words;
 * S the size of each process
 * J job mix (used for generating probability to generate reference)
 * N: number of references
 * R:the replacement algorithm specified by user
 * Replacement Algorithm available for the algorithm:
 * LIFO (Last In First Out)
 * Random: randomly choosing replacing frame
 * LRU: Least Recently Used=>replace the frame that is least recently used.
 * 
 * 
 * @author: Xuebo Lai
 * @version:12/8/2017(last modification)
 * 
 * */	
import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
public class Paging {
	/**
	 * This is the main function for the program. It reads input and sorts them into the 
	 * format that our program needs. Based on user request, it can use different replacement algorithm to run
	 * the simulation.
	 * 
	 * @param args, argument list
	 * 
	 * @return nothing;
	 *	it prints all the result to console.
	 * 
	 * @throws no exception
	 * 
	 * */	


	public static void main(String[] args) {
		if(args.length<6){
			System.err.println("The number of arguments is not correct");
			
		}
		//read in value for M,P,S, J, N
		int M = Integer.parseInt(args[0]);
		int P = Integer.parseInt(args[1]);
		int S = Integer.parseInt(args[2]);
		int J = Integer.parseInt(args[3]);
		int N = Integer.parseInt(args[4]);
		System.out.println("The machine size is "+M);
		System.out.println("The page size is "+P);
		System.out.println("The process size is "+S);
		System.out.println("The job mix number is "+J);
		System.out.println("The number of references per process is "+N);
		String R = args[5];
		System.out.println("The replacement algorithm is "+R+"\n");
		//System.out.println("page numbers is "+(M/P)+"\n");
		//set up A,B,C,D and process number
		int A,B,C,D,processNum;
		Process[] processArr;
		if(J==1){
			processNum = 1;
			processArr = new Process[processNum];
			processArr[0] = new Process(1,0,0,N,P,(S/P));
		}else if(J==2){
			processNum = 4;
			processArr = new Process[processNum];
			for (int i = 0;i<4;i++){
				processArr[i] = new Process(1,0,0,N,P,(S/P));
			}
		}else if(J==3){
			processNum = 4;
			processArr = new Process[processNum];
			for (int i = 0;i<4;i++){
				processArr[i] = new Process(1,0,0,N,P,(S/P));
			}
		}else{
			processNum = 4;
			processArr = new Process[processNum];
			processArr[0] = new Process(0.75,0.25,0,N,P,(S/P));
			processArr[1] = new Process(0.75,0,0.25,N,P,(S/P));
			processArr[2] = new Process(0.75,0.125,0.125,N,P,(S/P));
			processArr[3] = new Process(0.5,0.125,0.125,N,P,(S/P));
		}
		Scanner randomFile = null;
		try {
			randomFile = new Scanner(new File("./random.txt"));
		} catch (FileNotFoundException e) {
			System.err.println("fail to read in the random file");
			e.printStackTrace();
		}
		int frameNumber = M/P;
		if(R.compareToIgnoreCase("lru")==0){
			new LRU(frameNumber,processArr,S,randomFile);
		}else if(R.compareToIgnoreCase("lifo")==0){
			new LIFO(frameNumber,processArr,S,randomFile);
		}else{
			//let's assume it's random
			new RandomRep(frameNumber,processArr,S,randomFile);
		}
		

	}

}
