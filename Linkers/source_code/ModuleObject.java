import java.util.ArrayList;
/**
 * This is the class that stores all the information about a module, including the words,
 * the baseAddress and size of a module.
 * 
 * @author: Xuebo Lai
 * @version:09/18/2017(last modification)
 * 
 * */	
public class ModuleObject {
	private int baseAddress;
	private int size;
	//private Word addresses[];
	private ArrayList<Word> addresses;

	//constructor of the ModuleObject class that is used for storing information for module
	public ModuleObject(int baseAddress,int size, ArrayList<Word> inputArray){
		this.baseAddress = baseAddress;
		this.size = size;
		this.addresses = inputArray;
		
	}
	
	//These are the getter for the variables in the data field.
	public int getBaseAddress(){
		return baseAddress;
	}
	
	public int getSize(){
		return size;
	}
	
	public ArrayList<Word> getAddresses(){
		return addresses;
	}
	//These are the setter for all the variables in the data field.
	public void setBaseAddress(int baseAddress){
		this.baseAddress = baseAddress;
	}
	public void setSize(int size){
		this.size = size;
	}
	public void setAddresses(ArrayList<Word> addresses){
		this.addresses = addresses;
	}
	public void changeAddress(int position, Word item){
		addresses.set(position, item);
	}
	public void getAnAddress(int position){
		addresses.get(position);
	}
}
