import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Parser {
	static int A_COMMAND = 0;
	static int C_COMMAND = 1;
	static int L_COMMAND = 2;
	ArrayList <String>lines;
	int currentCommand;
	
	@SuppressWarnings("resource")
	Parser(String fileName){
		currentCommand = 0;
		lines = new ArrayList<String>();
		try {
			BufferedReader br;
			br = new BufferedReader(new FileReader(fileName));
			String line = br.readLine();
			while (line != null) {
				//ignore lines that start with //
				if(line.startsWith("//")){
					//don't add line if it starts with //
				}else{
					if(line.contains("//")){
						//remove everything in the line after, and including the //
						int commentLoc = line.indexOf("//");
						line = line.substring(0, commentLoc);
						
					}
					line = line.trim();
					if(!line.isEmpty()) lines.add(line);
					
				}
				line = br.readLine();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	 /**
	  *  are there more commands in the input?
	  *  if current command is bigger or equal to the size of lines(commands)
	  * @return
	  */
	public boolean hasMoreCommands(){
		boolean returnVal = true;
		if(currentCommand >= lines.size()){
			returnVal=false;
		}
		return returnVal;
	}
	
	/*reads the next command from
	the input and makes it the current
	command. Should be called only
	if
	hasMoreCommands()
	is true.
	Initially there is no current command.
	*/
	public void advance(){
		if(hasMoreCommands()){
			currentCommand++;
		}
	}
	
	/*
	 * Returns the type of the current command
		*/
	 public int commandType(){
		 String command = lines.get(currentCommand);
		 int type = -1;
		 if(command.startsWith("@")){
			 type = A_COMMAND;
		 }else if(command.startsWith("(") && command.endsWith(")")){
			 type = L_COMMAND;
		 }else{
			 type = C_COMMAND;
		 }
		 return type;
	 }
	 
	 //returns the xxx of a @xxx or (xxx)
	 public String symbol(){
		 String curSym = "";
		 if(commandType() == A_COMMAND || commandType() == L_COMMAND){
			 String command = lines.get(currentCommand);
			 curSym = command.replace("@", "");
			 curSym = curSym.replace("(", "");
			 curSym = curSym.replace(")", ""); 
		 }
		 return curSym;
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
	 public String dest(){
		 String destMNU = "";
		 if(commandType() == C_COMMAND){
			 String command = lines.get(currentCommand);
			 //if command doesn't have a = dest is null
			 if(!command.contains("=")){
				destMNU = null; 
			 }else{
				 //we have a destination, it should be the first item before =
				 destMNU = command.substring(0, command.indexOf("="));
			 }
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
	 public String comp(){
		 //the compMNU should be between =,start  and ;,end
		 String compMNU = "";
		 if(commandType() == C_COMMAND){
			 String command = lines.get(currentCommand);
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
			 
		 }
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
	 public String jump(){
		 String jmpMNU = "";
		 if(commandType() == C_COMMAND){
			 String command = lines.get(currentCommand);
			 if(!command.contains(";")){
				 jmpMNU = null;
			 }else{
				 //jump command exists
				 jmpMNU = command.substring(command.indexOf(";")+1);
			 }
		 }
		 return jmpMNU;
	 }
	
}