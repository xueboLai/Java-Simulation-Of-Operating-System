/**
 * This is the class that stores all the information about a word. The word does not have to
 * be contained in a class but I believe defining a word class can bring many convenience and
 * benefits for implementing the two-passes linkers.
 * 
 * @author: Xuebo Lai
 * @version:09/18/2017(last modification)
 * 
 * */	
public class Word {
	private String instruction;
	private String type;
	private String opcode;
	private boolean changeByUnDefValue;
	private String undefinedVariableName;
	private boolean treatedAsExternal;
	private boolean Used;

	//constructor for the Word class
	public Word(String instruction, String type,String opcode){
		this.instruction = instruction;
		this.type = type;
		this.opcode = opcode;
		this.undefinedVariableName = null;
		this.changeByUnDefValue = false;
		this.treatedAsExternal = false;
		this.Used = false;
	}
	//getter and setter for the data field above.
	public boolean getUsed(){
		return Used;
	}
	public void setUsed(boolean used){
		this.Used = used;
	}
	
	public void setTreatedAsExternal(boolean treatedAsExternal){
		this.treatedAsExternal = treatedAsExternal;
	}
	
	public boolean getTreatedAsExternal(){
		return treatedAsExternal;
	}
	
	public void changeByUndef(String unDefinedVariable){
		this.changeByUnDefValue = true;
		this.undefinedVariableName = unDefinedVariable;
		
	}
	public String undefinedVariable(){
		return this.undefinedVariableName;
	}
	public boolean getUndefStatus(){
		return changeByUnDefValue;
	}
	
	//a bunch of getter and setter
	public String getOpcode(){
		return opcode;
	}
	public String getInstruction(){
		return instruction;
	}
	public String getType(){
		return type;
	}
	//a bunch of setter
	public void setOpcode(String opcode){
		this.opcode=opcode;
		this.instruction = opcode+instruction.substring(1);
	}
	public void setInstruction(String instruction){
		this.instruction = instruction;
	}
	public void setType(String type){
		this.type = type;
	}
}
