/**
 * LIFO (Last In First Out)
 * This is the detailed implementation of the LIFO replacement algorithm. It will pick the most recent loaded frame
 * to pop and relace with the requested process's page. 
 * 
 * 
 * @author: Xuebo Lai
 * @version:12/8/2017(last modification)
 * 
 * */	
import java.util.Scanner;
public class LIFO {
	protected Process[] processArr;
	protected int frameNum;
	

	public LIFO(int frameNum,Process[] processArr,int S,Scanner Random){
		this.frameNum = frameNum;
		//create frame table:
		Frame[] frameTable = new Frame[frameNum];
		for(int i = 0;i<frameNum;i++){
			frameTable[i] = new Frame(i);
		}
		this.processArr = processArr;
		boolean looping = true;
		int timeCounter = 0;
		//the loop will end when all processes in teh loop don't have references left.
		while(looping){
			for(int i = 0;i<processArr.length;i++){
				if(processArr[i].finished==false){
					//quantum is 3
					for(int ref = 0;ref<3;ref++){
						int currentFrame = -1;
						timeCounter++;
						//first time
						if(processArr[i].start==false){
							processArr[i].currentWord = 111*(i+1)%S;
							processArr[i].start = true;
						}
						//System.out.print((i+1)+" references word ");
						//System.out.print(processArr[i].currentWord);
						//System.out.print("(page"+"");
						boolean found = false;
						
						int pageNumber = processArr[i].currentWord/(processArr[i].pageSize);
						//System.out.print("(page"+pageNumber+") ");
						//System.out.print(" at time "+timeCounter+": ");
						//check whether the page has already been loaded.
						int processNumber = i;
						for(int j = 0;j<frameNum;j++){
							if(pageNumber==frameTable[j].pageNum&&processNumber==frameTable[j].processNum){
								found = true;
								currentFrame = j;
								frameTable[j].lastAccess = timeCounter;
								//didn't miss anything
								//frameTable[j].lastIn = timeCounter;
								break;
							}
						}
						if(found){
							//System.out.print("Hit in frame "+currentFrame+"\n");
							System.out.print("");
						//if the page we requested hasn't been loaded, look for empty spot to put our page.
						}else{
							processArr[i].countingFault++;
							boolean empty = false;
							int emptyFrame = -1;
							
							//change happens
							//System.out.println("\nframe [0]"+frameTable[0].set+" time: "+timeCounter);
							for(int z = frameNum-1;z>=0;z--){
								if(frameTable[z].set==false){
									empty = true;
									emptyFrame = z;
									frameTable[z].lastAccess = timeCounter;
									break;
								}
							}
							
							if(empty){
							frameTable[emptyFrame].lastIn=timeCounter;
							frameTable[emptyFrame].pageNum = pageNumber;
							frameTable[emptyFrame].processNum = i;
							frameTable[emptyFrame].lastAccess = timeCounter;
							frameTable[emptyFrame].set = true;
							//System.out.println("pages's size is "+processArr[i].pages.length);
							processArr[i].pages[pageNumber].start = timeCounter;
							//System.out.println("Fault, using free frame "+emptyFrame);
							//if there' no available empty spot, then we use fifo replacement strategy to find a page to 
							//pop and replace it with our request process page.
							}else{
								//start lifo
								//find the oldest to evict
								int largest = frameTable[0].lastIn;
								int largest_index = 0;
								for (int index = 0;index<frameTable.length;index++){
									if(frameTable[index].lastIn>largest){
										largest = frameTable[index].lastIn;
										largest_index = index;
									}
								}
								//System.out.println("Fault, evicting page "+frameTable[largest_index].pageNum+" of "+(frameTable[largest_index].processNum+1)+" from frame "+largest_index);
								
								processArr[frameTable[largest_index].processNum].residencySum += (timeCounter-processArr[frameTable[largest_index].processNum].pages[frameTable[largest_index].pageNum].start);
								processArr[frameTable[largest_index].processNum].numOfEvict++;
								frameTable[largest_index].lastIn = timeCounter;
								frameTable[largest_index].pageNum = pageNumber;
								frameTable[largest_index].processNum = i;
								frameTable[largest_index].lastAccess = timeCounter;
								processArr[i].pages[pageNumber].start = timeCounter;
								
							}
							
						}
						processArr[i].numberOfRef--;

						
						
						//calculate each process's next word
						int randomNum = Random.nextInt();
						//System.out.println("The random number is "+randomNum);
						double y = (double)randomNum/(double)(Integer.MAX_VALUE+1d);
						int nextRef = -1;
						if(y<processArr[i].A){
							nextRef = (processArr[i].currentWord+1+S)%S;
						}else if(y<(processArr[i].A+processArr[i].B)){
							nextRef = (processArr[i].currentWord-5+S)%S;
						}else if(y<(processArr[i].A+processArr[i].B+processArr[i].C)){
							nextRef = (processArr[i].currentWord+4+S)%S;
						}else{
							nextRef = Random.nextInt()%S;
						}
						processArr[i].currentWord = nextRef;
						if(processArr[i].numberOfRef==0){
							processArr[i].finished = true;
							break;
						}
						
						
						
					}

				}
			//end for looping for each processes
			}
			
			for(int i = 0;i<frameNum;i++){
				if(frameTable[i].set==true){
					if(processArr[frameTable[i].processNum].finished==false){
						processArr[frameTable[i].processNum].residency++;
					}
				}
			}
			
			//testing whether all the process is finished
			looping = false;
			for(int i = 0;i<processArr.length;i++){
				if(processArr[i].finished==false){
					looping = true;
					break;
				}
			}
		
		
		
		}
		int totalSum = 0;
		int totalEvict = 0;
		int totalFault = 0;
		//System.out.println("");
		//print out results.
		for(int each = 0;each<processArr.length;each++){
			if(processArr[each].numOfEvict==0){
				
				System.out.println("Process "+(each+1)+" had "+processArr[each].countingFault+" faults.");
				System.out.println("\tWith no eviction, the average residence is undefined.");
				totalEvict += processArr[each].numOfEvict;
				totalSum += processArr[each].residencySum; 
				totalFault +=processArr[each].countingFault;
			}else{
				//System.out.println("sum: "+processArr[each].residencySum+"; "+"evict time: "+processArr[each].numOfEvict);
			double avgRes = (double)processArr[each].residencySum/(double)processArr[each].numOfEvict;
			System.out.println("Process "+(each+1)+" had "+processArr[each].countingFault+" faults and " + avgRes +" average residence");
			totalEvict += processArr[each].numOfEvict;
			totalSum += processArr[each].residencySum; 
			totalFault +=processArr[each].countingFault;
			}
		}
		
		System.out.println("");
		//System.out.println("total sum: "+totalSum);
		//System.out.println("totalEvict: "+totalEvict);
		double overallres = (double)totalSum/(double)totalEvict;
		System.out.print("The total number of faults is "+totalFault+"");
		if(totalEvict!=0){
		System.out.println(" and the overall average residence is "+overallres);
		
		}else{
			System.out.println("\n\tWith no evictions, the overall average residence is undefined.");
		}
		
		
		
		
	}
}
