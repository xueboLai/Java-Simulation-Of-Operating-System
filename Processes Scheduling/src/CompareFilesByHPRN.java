import java.util.Comparator;
/**
 * This is the comparing two processes class based on HPRN. If the result based on HPRN is zero, then it would 
 * be based on the lab 2 tie-breaking rule. The class implements Comparator class.
 * 
 * @author: Xuebo Lai
 * @version:10/22/2017(last modification)
 * 
 * */	
public class CompareFilesByHPRN implements Comparator<Process> {
	@Override
	public int compare(Process o1, Process o2) {
		if(((double)o1.getTotalTime()/(double)Math.max(o1.getRunningTime(),1))>((double)o2.getTotalTime()/(double)Math.max(o2.getRunningTime(),1))){
			return -1;
		}else if(((double)o1.getTotalTime()/(double)Math.max(o1.getRunningTime(),1))<((double)o2.getTotalTime()/(double)Math.max(o2.getRunningTime(),1))){
			return 1;
		}else{
			return o1.compareTo(o2);
		}
		
	}
}


