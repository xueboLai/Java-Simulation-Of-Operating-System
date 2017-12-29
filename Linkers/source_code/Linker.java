import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
/**
 * This is the main class that takes in input for Linker Project. It has all the error checking.
 * What's more, the major parts, two passes through the linkers are also included in this class.
 * The details of Linker's function can be found in homework specification.
 * The error checking has been merged into the main method.
 * 
 * @author: Xuebo Lai
 * @version:09/18/2017(last modification)
 * 
 * */	
public class Linker {
	/**
	 *  The main method for Linker's class read in a file name and then read through the 
	 *  linkers in the file twice to process them and output the Symbol Table and Memory Map
	 *  for those linkers. 
	 *  The main method also has error checking inside to detect errors among linkers.
	 * 
	 * @param args[0] input file name
	 * 
	 * @return (print out) the Symbol Table and Memory Map for the linkers in the input file
	 * 
	 * @throws no exception
	 * 
	 * 
	 * 
	 * */
	public static void main(String[] args){
		if(args.length<1){
			System.err.println("There should be at least one input");
		}
	       //System.out.println("Working Directory = " +
	         //      System.getProperty("user.dir"));
	 
		//reading File input
		File inputFile = null;
		Scanner sc=null;
		try{
			inputFile= new File(args[0]);
			sc = new Scanner(inputFile);
		}catch(Exception e){
			e.printStackTrace();
			System.err.println("The file cannot be found");
		}
		int moduleNumebr = sc.nextInt();
		//checked
		//System.out.println("The module number is "+moduleNumebr);
		ArrayList<VariableInfo> symbolsTable = new ArrayList<VariableInfo>();
		ArrayList<ModuleObject> modules = new ArrayList<ModuleObject>();
		for(int i=0;i<moduleNumebr;i++){
			ArrayList<VariableInfo> forThisModule = new ArrayList<VariableInfo>();
			//System.out.println(i);
			//incorrect
			//public ModuleObject(int baseAddress,int size, ArrayList<Word> inputArray){
			int baseAddress = 0;
			
			if(i!=0){
				baseAddress=modules.get(i-1).getBaseAddress()+modules.get(i-1).getSize();
			}/*
			
			ModuleObject(baseAddress,0,);*/
			//System.out.println(i);
			//System.out.println(baseAddress);
			//dealing with definition list
			int defVarNum = sc.nextInt();
			for (int j=0;j<defVarNum;j++){
				boolean existAlready = false;
				String var = sc.next();
				int value = sc.nextInt();
				//if already existed, take in the information
				VariableInfo ptrToElement = null;
				for(int z = 0;z<symbolsTable.size();z++){
					if(var.compareTo(symbolsTable.get(z).getName())==0){
						existAlready = true;
						ptrToElement = symbolsTable.get(z);
						ptrToElement.setDefinedModule(i);
						if(ptrToElement.getDefined()==false){
							ptrToElement.setAddress(baseAddress+value);
							ptrToElement.setDefined(true);
							ptrToElement.setRelativeAddress(value);
						}else{
							ptrToElement.setMultipleDefined(true);
							ptrToElement.incrementDefinedTime();
							continue;
						}
					}
				}
				if(!existAlready){
				//VariableInfo(String name,int address,boolean defined,boolean used)
				VariableInfo symbol= new VariableInfo(var,value+baseAddress,true,false,i,value);
				symbolsTable.add(symbol);
				}/*else{

					ptrToElement.setDefined(true);
					ptrToElement.incrementDefinedTime();
					ptrToElement.setAddress((baseAddress+value));
					
					
				}*/
			}
			//dealing with declaration
			int useVarNum = sc.nextInt();
			for(int j=0; j<useVarNum;j++){
				String varName = sc.next();
				int address = sc.nextInt();
				//VariableInfo tempPointer = null;
				boolean alreadyExist = false;
				for(int counterForSymbol=0;counterForSymbol<symbolsTable.size();counterForSymbol++){
					if(symbolsTable.get(counterForSymbol).getName().compareTo(varName)==0){
						alreadyExist = true;
						symbolsTable.get(counterForSymbol).setUsed(true);
						continue;
					}
				}
				if(alreadyExist){
					continue;
				}else{
					//VariableInfo(String name,int address,boolean defined,boolean used)
					//public ModuleObject(int baseAddress,int size, ArrayList<Word> inputArray){
					VariableInfo aVariable=new VariableInfo(varName,0,false,true,-1,-1);
					//System.out.println("Variable: "+aVariable.getName()+" defined"+aVariable.getDefined());
					symbolsTable.add(aVariable);
				}
				
			}
			
			
			
			//dealing with words
			int sizeOfWords = sc.nextInt();
			//System.out.println("Hello, I'm here");
			for (int countSymTable = 0;countSymTable<symbolsTable.size();countSymTable++){
				VariableInfo temporary = symbolsTable.get(countSymTable);
				//System.out.println("Hello, I'm here, sizeof SymbolsTable is "+symbolsTable.size());
				//System.out.println("getvariableName, relative address, module, sizeofwords "+temporary.getName()+" "+temporary.getRelativeAddress()+" "+temporary.getModule()+" "+sizeOfWords);
				//System.out.println("current i: "+i);
				if(temporary.getModule()==i){
					if(temporary.getRelativeAddress()>=sizeOfWords){
						//System.out.println("Hello, I'm here");
						temporary.setOutside(true);
						temporary.setOutsideModule(i);
						if(i==0){
							temporary.setAddress(0);
						}else{
							temporary.setAddress(baseAddress);
						}
					}						
				}
			}
			//System.out.println("The size of words is: "+sizeOfWords);
			ArrayList<Word> addresses = new ArrayList<Word>();
			for(int wordIndex = 0;wordIndex<sizeOfWords;wordIndex++){
				String word = sc.next();
				//System.out.println("The word is "+word);
				String type = word.substring(word.length()-1);
				String opcode = word.substring(0,1);
				String instruction = word.substring(0,word.length()-1);
				Word aWord = new Word(instruction,type,opcode);
				addresses.add(aWord);
			}
			ModuleObject aModule = new ModuleObject(baseAddress,sizeOfWords,addresses);
			modules.add(aModule);
			
			
		}
		


		Scanner sc_2 = null;
		try{
			sc_2 = new Scanner(inputFile);
		}catch(Exception e){
			e.printStackTrace();
			System.err.print("The file can't be found.");
		}
		moduleNumebr = sc_2.nextInt();
		for(int i=0;i<moduleNumebr;i++){
			//definition list
			int defSymNum = sc_2.nextInt();
			ArrayList<VariableInfo> aListOfVariable = new ArrayList<VariableInfo>();
			for(int i1=0;i1<defSymNum;i1++){
				String symName = sc_2.next();
				int goToAddress = sc_2.nextInt();
				//doing nothing here, just read through the array
				VariableInfo sym = null;
				for (int counterSym = 0;counterSym<symbolsTable.size();counterSym++){
					if(symbolsTable.get(counterSym).getName().compareTo(symName)==0){
						aListOfVariable.add(symbolsTable.get(counterSym));
					}
				}
			}
			//declaration list
			int declarSymNum = sc_2.nextInt();
			ArrayList<VariableInfo> temp3 = null;
			/*for (int goingThroughListOfVar = 0;goingThroughListOfVar<aListOfVariable.size();goingThroughListOfVar++){
				if(aListOfVariable.get(goingThroughListOfVar).getAddress()+1-modules.get(i).getBaseAddress()>modules.get(i).getSize()){
					aListOfVariable.get(goingThroughListOfVar).setOutside(true);
					aListOfVariable.get(goingThroughListOfVar).setOutsideModule(i);
					System.out.println("name: "+aListOfVariable.get(goingThroughListOfVar).getName()+"; address: "+aListOfVariable.get(goingThroughListOfVar).getAddress()+"; Module_size: "+modules.get(i).getSize()+"; Module_number: "+i);
					for (int j2 = 0;j2<symbolsTable.size();j2++){
						if(aListOfVariable.get(goingThroughListOfVar).getName().compareTo(symbolsTable.get(j2).getName())==0){
							symbolsTable.get(j2).setAddress(modules.get(i).getBaseAddress());
							System.out.println("1,Symbol table name: "+symbolsTable.get(j2).getName()+"; address: "+symbolsTable.get(j2).getAddress()+"; Module_size: "+modules.get(i).getSize()+"; Module_number: "+i+" soemthing more"+j2);
						}
					}
					//aListOfVariable.get(goingThroughListOfVar).setAddress(modules.get(i).getBaseAddress());
				}
			}*/
			//System.out.println("2,Symbol2 table name: "+symbolsTable.get(0).getName()+"; address: "+symbolsTable.get(0).getAddress()+"; Module_size: "+modules.get(i).getSize()+"; Module_number: "+i);
		
			for(int i2 = 0; i2<declarSymNum;i2++){
				String symName = sc_2.next();
				//System.out.println(("symName is "+symName));
				VariableInfo temp2 = null;
				int indexNum = sc_2.nextInt();
				int value = 0;
				boolean undefined = false;
				//find the symbol
				for(int i3 = 0;i3<symbolsTable.size();i3++){
					if(symbolsTable.get(i3).getName().compareTo(symName)==0){
						//System.out.println("i3 is "+i3);
						temp2 = symbolsTable.get(i3);
						value = temp2.getAddress();
						undefined = (!(temp2.getDefined()));
						//System.out.println("3,Symbol2 table name: "+symbolsTable.get(0).getName()+"; address: "+symbolsTable.get(0).getAddress()+"; Module_size: "+modules.get(i).getSize()+"; Module_number: "+i);
						//System.out.println("The name and value is gonna be "+temp2.getName()+" "+temp2.getAddress());
						continue;
					}
				}
				ModuleObject theModule = modules.get(i);
				//change address
				int nextPosition = indexNum;
				do{	
					int currentPosition = nextPosition;
					
					//System.out.println("previous: "+currentPosition);
					
					
					ArrayList<Word>	allAddresses=theModule.getAddresses();
					
					/*if (currentPosition > allAddresses.size()){
						currentPosition = 0;
						temp.setOutside(true);
						//System.out.print("I'm outside, my name is "temp.getName());
					}*/
					
					if(allAddresses.get(currentPosition).getInstruction().substring(1).compareTo("777")!=0){
						nextPosition = Integer.parseInt(allAddresses.get(currentPosition).getInstruction().substring(1));
					}else{
						nextPosition = -1;
					}
					//System.out.println("after: "+currentPosition);
					
					Word theWord = allAddresses.get(currentPosition);
					if(theWord.getType().compareTo("1")==0){
						theWord.setTreatedAsExternal(true);
					}
					theWord.setUsed(true);
					theWord.setInstruction(theWord.getOpcode()+String.format("%03d",value));
					//System.out.println("opcode+003: "+theWord.getOpcode()+String.format("%03d",value));
					//String.format("|%020d|", 93);
					//try to get rid of this line
					//theModule.changeAddress(currentPosition, theWord);
				//add type checking	
					if(undefined){
						theWord.changeByUndef(temp2.getName());
					}
				}while(nextPosition!=-1);
				
			}//end of declaration
			ModuleObject theModule = modules.get(i);
			ArrayList<Word> allAddresses = theModule.getAddresses();
			for(int i5=0;i5<allAddresses.size();i5++){
				if(allAddresses.get(i5).getType().compareTo("3")==0){
					Word temp = allAddresses.get(i5);
					String instruction = allAddresses.get(i5).getInstruction();
					//reset type, instruction
					int newInstruction = (Integer.parseInt(instruction)+theModule.getBaseAddress());
					temp.setInstruction(Integer.toString(newInstruction));
				}
			}
			
			//going through the word list without any further action:
			int numberOfWord = sc_2.nextInt();
			//clear up all number
			for (int i4 = 0;i4<numberOfWord;i4++){
				sc_2.nextInt();
			}
		}//going through each module 137
		System.out.println("Symbol Table");
		for(int i =0;i<symbolsTable.size();i++){
			//System.out.println("i'm here!2");
			VariableInfo temp = symbolsTable.get(i);
			if(!temp.getDefined()){
				continue;
			}
			System.out.print(temp.getName()+"="+temp.getAddress());
	
			if(temp.getMultipleDefined()){
				System.out.println(" Error: This variable is multiply defined; first value used.");
				if(temp.getOutside()){
					System.out.println("   Error: The definition of "+temp.getName()+" is outside module "+temp.getOutsideModule()+"; zero (relative) used.");
				}
			}
			else{
				if(temp.getOutside()){
					System.out.print("   Error: The definition of "+temp.getName()+" is outside module 1; zero (relative) used.");
				}
			
				//comeback
				System.out.println();
			}
		}
		int countLine = 0;
		System.out.println();
		System.out.println("Memory Map");
		for(int i =0;i<modules.size();i++){
			ModuleObject temp = modules.get(i);
			//System.out.println("module"+i+": "+temp.getBaseAddress());
			ArrayList<Word> addresses = temp.getAddresses();
			for(int i6 = 0;i6<addresses.size();i6++){
				
				System.out.print(countLine+":\t"+addresses.get(i6).getInstruction());
				countLine++;
				if((addresses.get(i6).getUsed()==false)&&(addresses.get(i6).getType().compareTo("4")==0)){
					System.out.print(" Error: E type address not on use chain; treated as I type.");
				}
				if(addresses.get(i6).getTreatedAsExternal()){
					 System.out.print(" Error: Immediate address on use list; treated as External.");
				}
				if(addresses.get(i6).getUndefStatus()==true){
					System.out.print(" Error: "+addresses.get(i6).undefinedVariable()+" is not defined; zero used.");
				}
					System.out.println();
				
				
			}
			
		}
		System.out.println();
			ArrayList<VariableInfo> variables = symbolsTable;
			for(int i7 = 0;i7<variables.size();i7++){
				if(!variables.get(i7).getUsed()){
					System.out.println("Warning: "+variables.get(i7).getName()+" was defined in module "+variables.get(i7).getModule()+" but never used.");
				}
				//System.out.println(countLine+":\t"+addresses.get(i7).getInstruction());
			
			}


		/**
		 * Warning: X12 was defined in module 0 but never used.
			Warning: X21 was defined in module 1 but never used.
			Warning: X23 was defined in module 1 but never used.
			Warning: X61 was defined in module 5 but never used.
		 */
		
		
	}//end of main function
	
	//public static readFile

}
