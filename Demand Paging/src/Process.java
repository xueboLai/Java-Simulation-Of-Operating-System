/**
 *This is the class for modeling process. It contains all the properties for processes that we need for the programs.
 * 
 * @author: Xuebo Lai
 * @version:12/8/2017(last modification)
 * 
 * */	
public class Process {
	protected double A;
	protected double B;
	protected double C;
	protected boolean start;
	private double[] originalValue;
	protected boolean finished = false;
	protected int currentWord;
	protected int countingFault;
	protected int currentFrame = -1;
	protected int numberOfRef = -1;
	protected int originNumOfRef = -1;
	protected int pageSize = -1;
	protected int lastCall = -1;
	protected int residency = 0;
	protected int residencySum = 0;
	protected int numOfEvict = 0;
	protected Page[] pages;
	protected int pageNum = 0;
	protected int lastIn = 0;
	
	
	
	public Process(double A,double B,double C,int numberOfRef,int pageSize,int pageNum){
		this.A = A;
		this.B = B;
		this.C = C;
		originalValue = new double[3];
		originalValue[0] = A;
		originalValue[1] = A;
		originalValue[2] = A;
		this.start = false;
		this.finished = false;
		this.currentWord = -1;
		this.countingFault = 0;
		this.currentFrame = -1;
		this.numberOfRef = numberOfRef;
		this.originNumOfRef = numberOfRef;
		this.pageSize = pageSize;
		this.lastCall = -1;
		this.residency = 0;
		this.residencySum = 0;
		this.numOfEvict = 0;
		this.pageNum = pageNum;
		pages = new Page[pageNum];
		for(int i = 0;i<pageNum;i++){
			pages[i] = new Page();
		}
		this.lastIn = -1;
	}
	
	
}
