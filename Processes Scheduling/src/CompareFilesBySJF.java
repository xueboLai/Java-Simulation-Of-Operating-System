import java.util.Comparator;
/**
 * This is the comparing two processes class based on SJF. If the result based on SJF is zero, then it would 
 * be based on the lab 2 tie-breaking rule. The class implements Comparator class.
 * 
 * @author: Xuebo Lai
 * @version:10/22/2017(last modification)
 * 
 * */	
public class CompareFilesBySJF implements Comparator<Process> {
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