import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;


public class Hass2 {
	
	public static void main(String[] args){
		new Hass2(args);
	}
	
	public Hass2(String[] args) {
		// TODO Auto-generated constructor stub
		String fileName;
		if(args.length == 0){
			fileName = "Rect.asm";
		}else{
			fileName = args[0];
		}
		String newFileName = fileName.replace(".asm", ".hack");
		Parser parser = new Parser(fileName);
		SymbolTable symbolTable = new SymbolTable();
		ArrayList<String>machineCode = new ArrayList<String>();
		
		
		
		//first pass we go through and look for (labels) if we find one
		//we add that label to our symbol table. we need to deal with symbols.
		int currentRomAddress = 0;
		 while(parser.hasMoreCommands()){
			 String command = parser.lines.get(parser.currentCommand);
			 //String command = parser.lines.get(parser.currentCommand);
			 //deal with possibility of label first
			 if(parser.commandType() == parser.L_COMMAND){
				 //if this is a symbol add it to the symbol table,
				 //else do nothing for the first pass. it will point to the current rom address.
				 //or the next item that will added to the machine code on the next pass.
				 if(symbolTable.isSymbol(parser.symbol())){
					 symbolTable.addEntry(parser.symbol(), currentRomAddress);
				 }
			 }else{
				//we have an A_Command or a C_command, which means variables and not labels
				 //we will handle those the seconds pass.
				 //we do increment the current Rom address though, so that labels
				 //point to the correct command.
				 currentRomAddress++;
			 }
			 parser.advance();
		 }
		 
		 //the seconds pass we look up A_instructions symbols in the symbol table
		 //if we find a symbol we check the table for the correct ROM address.
		 //if we don't find it, we assign the next available ram address to it,
		 //and complete the command translation
		 //then we check C_instructions for symbols, translate and add to the table
		 //as needed
		 int currentRamAddress = 16;
		 parser = new Parser(fileName);
		 while(parser.hasMoreCommands()){
			 String command = parser.lines.get(parser.currentCommand);
			 String binary = "";
			 if(parser.commandType() == parser.A_COMMAND){
				 if(symbolTable.isSymbol(parser.symbol())){
					 //this A_Command is a symbol, lets look it up in the table
					 if(symbolTable.contains(parser.symbol())){
						 //table contains symbol, we can translate from it
						 int address = symbolTable.getAddress(parser.symbol());
						 binary = Integer.toBinaryString(address);
					 }else{
						//table does not contain symbol, we must assign a new
						//ram address to the symbol, and put it in the table
						 //then complete the translation of A_commands.
						 symbolTable.addEntry(parser.symbol(), currentRamAddress);
						 binary = Integer.toBinaryString(currentRamAddress);
						 currentRamAddress++;
					 }
				 }else{
					 //this A_Command is a number, not a symbol, translate it to binary
					 // translate string to short then short to binary string.
					 binary = Integer.toBinaryString(Short.valueOf(parser.symbol()));
				 }
			 }else if(parser.commandType() == parser.C_COMMAND){
				 // a C_command has the possibility of having a symbol in it's
				 // dest or jump fields, if either is true we have to rebuild
				 // the c_command, if it's not true we can convert the current
				 // c command to binary.
				 String dest = parser.dest();
				 String jump = parser.jump();
				 String comp = parser.comp();
				 
				 if(symbolTable.isSymbol(parser.dest()) || symbolTable.isSymbol(parser.jump())){
					 //we need to check dest and jump to see if they are in the symbol table,
					 //if they are not we need to add them then we need to translate our new binary command.
					 if(symbolTable.contains(dest)){
						 dest = Integer.toString(symbolTable.getAddress(dest));
					 }else{
						 //add dest to symbol table
						 symbolTable.addEntry(dest, currentRamAddress);
						 currentRamAddress++;
					 }
					 
					 if(symbolTable.contains(jump)){
						 jump = Integer.toString(symbolTable.getAddress(jump));
					 }else{
						 //add jump to symbol table
						 symbolTable.addEntry(jump, currentRamAddress);
						 currentRamAddress++;
					 }
					 
				 }else{
					 //we do not need to translate symbols into memory addresses
					 //we can tranlate from the code module directly.
					 jump = Code.jump(jump);
					 dest = Code.dest(dest);
					 comp = Code.comp(comp);
	
				 }
				 binary = "111"+comp+dest+jump;
			 }else{
				 //we ignore L_Commands in the second pass of the assembler
				 //as they generate no machine code.
			 }
			 binary.trim();
			 if(binary.length() != 0){
				 while(binary.length() < 16){
					 binary = "0"+binary;
				 }
				 machineCode.add(binary); 
			 }
			 parser.advance();
		 }
		 writeToFile(newFileName, machineCode);
	}
	
	
	public static void writeToFile(String fileName, ArrayList<String>machineCode) {
        try {
                BufferedWriter bw = new BufferedWriter(new FileWriter(new File(
               		 fileName), false));
                for(int i = 0; i < machineCode.size(); i++){
               	 bw.write(machineCode.get(i));
                    bw.newLine();
                }
               
                bw.close();
        } catch (Exception e) {
        }
	 }
	

}
