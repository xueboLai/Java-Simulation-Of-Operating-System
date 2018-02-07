/**
 * The goal of this program is to simulate demand paging. Through simulation, we can 
 * see the relation among process size, page size, program size, job mix, locality etc. 
 * in the context of using multiprogramming and demand paging. The basic idea is to have
 * a driver to create reference and then have the paging simulator to see whether the reference 
 * is a hit or a page fault. If it is a page fault, adopt certain replacement strategy accordingly.
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
public class Frame {
	protected int frameNum;
	protected int processNum;
	protected int pageNum;
	protected boolean set;
	protected int value;
	protected int lastAccess;
	protected int lastIn;


public Frame(int frameNum){
	this.frameNum = frameNum;
	this.processNum = -1;
	this.set = false;
	value = -1;
	pageNum = -1;
	lastAccess = -1;
	lastIn = -1;
}





}
