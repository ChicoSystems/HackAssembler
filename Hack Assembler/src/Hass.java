import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;


public class Hass {
	SymbolTable symbolTable = new SymbolTable();
	int currentMemoryAddress = 10;
	
	public static void main(String[] args){
		new Hass(args);
	}
	
	public Hass(String[] args){
		String fileName;
		if(args.length == 0){
			fileName = "Max.asm";
		}else{
			fileName = args[0];
		}
		
		String newFileName = fileName.replace(".asm", ".hack");
		String intermediateFilename = fileName.replace(".asm", ".hack0");
		ArrayList<String>preMachineCode = new ArrayList<String>();
		
		Parser parser = new Parser(fileName);
		//FIRST PASS
		while(parser.hasMoreCommands()){
			String command = processSymbols(parser.lines.get(parser.currentCommand));
			//if command starts with ( and ends with ) then it is a l_instruction and not added
			if(command.contains("(") && command.contains(")")){
				//do nothing
			}else{
				preMachineCode.add(command);
			}
			parser.advance();
		}
		writeToFile(intermediateFilename, preMachineCode);
		
		
		//SECOND PASS
		parser = new Parser(intermediateFilename);
		ArrayList<String>machineCode = new ArrayList<String>();
		while(parser.hasMoreCommands()){
			String command = parser.lines.get(parser.currentCommand);
			
			if(parser.commandType() == parser.A_COMMAND){
				//an a command is just a @number we translate to binary
				command = command.replace("@", "");
				short binaryCommand = Short.valueOf(command);
				String binaryString = Integer.toBinaryString(binaryCommand);
				while(binaryString.length() < 16){
					binaryString = "0"+binaryString;
				}
				machineCode.add(binaryString);
			}else if(parser.commandType() == parser.C_COMMAND){
				String preFix = "111";
				String compute = Code.comp(parser.comp());
				String dest = Code.dest(parser.dest());
				String jump = Code.jump(parser.jump());
				String binaryString = preFix+compute+dest+jump;
				machineCode.add(binaryString);
			}else{
				//the command is an L_COMMAND, do nothing.
			}
			parser.advance();
		}
		writeToFile(newFileName, machineCode);
	}
	
	 private String processSymbols(String command) {
		 int symbolAddress = currentMemoryAddress;
		if(containsSymbol(command)){
			ArrayList<String>symbolList = extractSymbols(command);
			for(int i = 0; i < symbolList.size(); i++){
				String symbol = symbolList.get(i);
				//strip @ if in symbol
				symbol = symbol.replace("@", "");
				symbol = symbol.replace("(", "");
				symbol = symbol.replace(")", "");
				if(symbolTable.contains(symbol)){
					symbolAddress = symbolTable.getAddress(symbol);
					command = command.replace(symbol, Integer.toString(symbolAddress));
					
				}else{
					//assign new address and add to symbol table
					symbolTable.addEntry(symbol, currentMemoryAddress);
					command = command.replace(symbol, Integer.toString(currentMemoryAddress));
					currentMemoryAddress++;
				}
			}	
		}
		return command;
	}
	 
	 private boolean containsSymbol(String command){
		 boolean contains = false;
		 if(command.startsWith("@") && command.contains("(") && command.contains("")){
			 //this is a label
			 contains = true;
		 }else{
			 String destMnu = dest(command);
			 String compMnu = comp(command);
			 String jumpMnu = jump(command);
			 if(isSymbol(destMnu) || isSymbol(compMnu) || isSymbol(jumpMnu)){
				 contains = true;
			 }
		 }
		 return contains;
	 }
	 
	 private ArrayList<String> extractSymbols(String command){
		 ArrayList<String>symbols = new ArrayList<String>();
		 if(command.contains("(") && command.contains(")")){
			// this is a label
			 command = command.replace("@", "");
			 command = command.replace("(", "");
			 command = command.replace(")", "");
			 symbols.add(command);
		 }else{
			 //must be a c command.
			 String destMnu = dest(command);
			 String compMnu = comp(command);
			 String jumpMnu = jump(command);
			 if(isSymbol(destMnu)){
				 symbols.add(destMnu);
			 }
			 
			 if(isSymbol(compMnu)){
				 symbols.add(compMnu);
			 }
			 
			 if(isSymbol(jumpMnu)){
				 symbols.add(jumpMnu);
			 }
		 }
		 return symbols;
	 }
	 
	

	public static void writeToFile(String fileName, ArrayList<String>machineCode) {
         try {
                 BufferedWriter bw = new BufferedWriter(new FileWriter(new File(
                		 fileName), true));
                 for(int i = 0; i < machineCode.size(); i++){
                	 bw.write(machineCode.get(i));
                     bw.newLine();
                 }
                
                 bw.close();
         } catch (Exception e) {
         }
	 }
		 
		 /**
		  * returns the
			dest
			mnemonic in
			the current
			C
			-command (8 possi-
			bilities). Should be called only
			when
			commandType()
			is
			C_COMMAND
			.
		  * @return
		  */
		 public String dest(String command){
			 String destMNU = "";
				 //if command doesn't have a = dest is null
				 if(!command.contains("=")){
					destMNU = null; 
				 }else{
					 //we have a destination, it should be the first item before =
					 destMNU = command.substring(0, command.indexOf("="));
				 }
			 return destMNU;
		 }
		 
		 /**
		  * turns the
			comp
			mnemonic in
			the current
			C
			-command (28 pos-
			sibilities). Should be called only
			when
			commandType()
			is
			C_COMMAND
			.
		  * @return
		  */
		 public String comp(String command){
			 //the compMNU should be between =,start  and ;,end
			 String compMNU = "";
				 boolean dstExists = command.contains("=");
				 boolean jmpExists = command.contains(";");
				 int start = 0;
				 int end = 0;
				 if(dstExists){
					 start = command.indexOf("=")+1;
				 }else{
					 start = 0;
				 }
				 
				 if(jmpExists){
					 end = command.indexOf(";");
				 }else{
					 end = command.length();
				 }
				 
				 compMNU = command.substring(start, end);
				 
			 return compMNU;
		 }
		 
		 /**
		  * turns the
			jump
			mnemonic in
			the current
			C
			-command (8 pos-
			sibilities). Should be called only
			when
			commandType()
			is
			C_COMMAND
			.
		  * @return
		  */
		 public String jump(String command){
			 String jmpMNU = "";
				 if(!command.contains(";")){
					 jmpMNU = null;
				 }else{
					 //jump command exists
					 jmpMNU = command.substring(command.indexOf(";")+1);
				 }
			 
			 return jmpMNU;
		 }
		 
		 boolean isSymbol(String mne){
			 if(mne == null)return false;
			 mne = mne.trim();
			 boolean returnVal = false;
			 if(mne.equals("-1")|| mne.equals("M")||
				mne.equals("!D")|| mne.equals("D")||
				mne.equals("!A")|| mne.equals("MD")||
				mne.equals("-D")|| mne.equals("A")||
				mne.equals("-A")|| mne.equals("AM")||
				mne.equals("D+1")|| mne.equals("AD")||
				mne.equals("A+1")|| mne.equals("AMD")||
				mne.equals("D-1")|| mne.equals("JGT")||
				mne.equals("A-1")|| mne.equals("JEQ")||
				mne.equals("D+A")|| mne.equals("JGE")||
				mne.equals("D-A")|| mne.equals("JLT")||
				mne.equals("A-D")|| mne.equals("JNE")||
				mne.equals("D&A")|| mne.equals("JLE")||
				mne.equals("D|A")|| mne.equals("JMP")||
				mne.equals("M")|| mne.equals("0")||
				mne.equals("!M")|| mne.equals("1")||
				mne.equals("-M")|| mne.equals("M-1")||
				mne.equals("M+1")|| mne.equals("D+M")||
				mne.equals("D-M")|| mne.equals("M-D")||
				mne.equals("D&M")|| mne.equals("D|M")){
				 returnVal = false;
			 }else if(mne.startsWith("0") || mne.startsWith("5")||
					 mne.startsWith("1") || mne.startsWith("6")||
					 mne.startsWith("2") || mne.startsWith("7")||
					 mne.startsWith("3") || mne.startsWith("8")||
					 mne.startsWith("4") || mne.startsWith("9")){
				 returnVal = false;
			 }else if(mne.startsWith("@") && mne.substring(1).startsWith("0")||
					 mne.startsWith("@") && mne.substring(1).startsWith("1")||
					 mne.startsWith("@") && mne.substring(1).startsWith("2")||
					 mne.startsWith("@") && mne.substring(1).startsWith("3")||
					 mne.startsWith("@") && mne.substring(1).startsWith("4")||
					 mne.startsWith("@") && mne.substring(1).startsWith("5")||
					 mne.startsWith("@") && mne.substring(1).startsWith("6")||
					 mne.startsWith("@") && mne.substring(1).startsWith("7")||
					 mne.startsWith("@") && mne.substring(1).startsWith("8")||
					 mne.startsWith("@") && mne.substring(1).startsWith("9")){
				 	returnVal=false;
			 }else{
			 
				 returnVal = true;
			 }
			 return returnVal;
		 }

}
