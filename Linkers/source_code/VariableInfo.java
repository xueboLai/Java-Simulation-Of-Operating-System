/**
 * This is the class that stores all the information about a symbol, including symbol's name
 * whether it has been defined multiple times, whether it has been used, its relative address
 * and its absolute address, etc.
 * 
 * @author: Xuebo Lai
 * @version:09/18/2017(last modification)
 * 
 * */	
public class VariableInfo {
	private String name = null;
	private int definedTime = 0;
	private int address;
	private boolean used = false;
	private boolean defined = true;
	private int definedModule;
	private boolean multipleDefined = false;
	private boolean notDefined = false;
	private boolean outside = false;
	private int outsideModule;
	private int relativeAddress;
	
	
	public VariableInfo(String name,int address,boolean defined,boolean used,int definedModule,int relativeAddress){
		this.name = name;
		this.address = address;
		this.used = used;
		this.definedTime=0;
		this.defined = defined;
		this.definedModule = definedModule;
		this.multipleDefined = false;
		this.notDefined = false;
		this.outside = false;
		this.outsideModule = -1;
		this.relativeAddress = relativeAddress;
	}
	//getter and setter for the data field above
	public int getRelativeAddress(){
		return relativeAddress;
	}
	public void setRelativeAddress(int relativeAddress){
		this.relativeAddress = relativeAddress;
	}
	public void setDefinedModule(int definedModule){
		this.definedModule = definedModule;
	}
	
	public int getOutsideModule(){
		return this.outsideModule;
	}
	public void setOutsideModule(int outsideModule){
		this.outsideModule = outsideModule;
	}
	public boolean getOutside(){
		return outside;
	}
	public void setOutside(boolean outside){
		this.outside = outside;
	}
	public void setNotDefined(boolean notDefined){
		this.notDefined = notDefined;
	}
	public boolean getNotDefined(){
		return notDefined;
	}
	public boolean getMultipleDefined(){
		return multipleDefined;
	}
	
	public void setMultipleDefined(boolean multipleDefined){
		this.multipleDefined = multipleDefined;
	}
	
	public int getModule(){
		return definedModule;
	}
	public String getName(){
		return name;
		
	}
	public void setName(String name){
		this.name = name;
	}
	
	public void incrementDefinedTime(){
		definedTime++;
	}
	public int getDefinedTime(){
		return definedTime;
	}
	public int getAddress(){
		return address;
	}
	public void setAddress(int address){
		this.address = address;
	}
	public void setUsed (boolean used){
		this.used = used; 
	}
	public boolean getUsed(){
		return used;
	}
	public int returnDefinedTime(){
		return definedTime;
	}
	public boolean getDefined(){
		return defined;
	}
	public void setDefined(boolean defined){
		this.defined = defined;
	}
	
	
}
