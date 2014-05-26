import java.util.Hashtable;

public class SymbolTable {
	Hashtable<String, Integer> table;
	
	public SymbolTable(){
		initializeHashtable();
	}
	
	public void addEntry(String symbol, int address){
		table.put(symbol, address);
	}
	
	public boolean contains(String symbol){
		boolean returnVal = table.containsKey(symbol);
		return returnVal;
	}
	
	public int getAddress(String symbol){
		return table.get(symbol);
	}
	
	private void initializeHashtable(){
		table = new Hashtable<String, Integer>();
		table.put("SP", 0);
		table.put("LCL", 1);
		table.put("ARG", 2);
		table.put("THIS", 3);
		table.put("THAT", 4);
		table.put("R0", 0);
		table.put("R1", 1);
		table.put("R2", 2);
		table.put("R3", 3);
		table.put("R4", 4);
		table.put("R5", 5);
		table.put("R6", 6);
		table.put("R7", 7);
		table.put("R8", 8);
		table.put("R9", 9);
		table.put("R10", 10);
		table.put("R11", 11);
		table.put("R12", 12);
		table.put("R13", 13);
		table.put("R14", 14);
		table.put("R15", 15);
		table.put("SCREEN", 16384);
		table.put("KBD", 24576);
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
