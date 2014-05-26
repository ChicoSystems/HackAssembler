
public class Code {
	
	/**
	 * takes the dest mnemonic and returns the binary
	 * @param mnem
	 * @return
	 */
	public static String dest(String mnem){
		String binary = "";
		if(mnem == null){
			binary = "000";
		}else if(mnem.equals("D")){
			binary = "010";
		}else if(mnem.equals("MD")){
			binary = "011";
		}else if(mnem.equals("A")){
			binary = "100";
		}else if(mnem.equals("AM")){
			binary = "101";
		}else if(mnem.equals("AD")){
			binary = "110";
		}else if(mnem.equals("AMD")){
			binary = "111";
		}else if(mnem.equals("M")){
			binary = "001";
		}
		return binary;
	}
	
	/**
	 * takes the comp mnemonic and returns the binary
	 * @param mnem
	 * @return
	 */
	public static String comp(String mnem){
		String binary = "";
		if(mnem.equals("0")){
			binary = "0101010";
		}else if(mnem.equals("1")){
			binary = "0111111";
		}else if(mnem.equals("-1")){
			binary = "0111010";
		}else if(mnem.equals("D")){
			binary = "0001100";
		}else if(mnem.equals("A")){
			binary = "0110000";
		}else if(mnem.equals("!D")){
			binary = "0001101";
		}else if(mnem.equals("!A")){
			binary = "0110001";
		}else if(mnem.equals("-D")){
			binary = "0001111";
		}else if(mnem.equals("-A")){
			binary = "0110011";
		}else if(mnem.equals("D+1")){
			binary = "0011111";
		}else if(mnem.equals("A+1")){
			binary = "0110111";
		}else if(mnem.equals("D-1")){
			binary = "0001110";
		}else if(mnem.equals("A-1")){
			binary = "0110010";
		}else if(mnem.equals("D+A")){
			binary = "0000010";
		}else if(mnem.equals("D-A")){
			binary = "0010011";
		}else if(mnem.equals("A-D")){
			binary = "0000111";
		}else if(mnem.equals("D&A")){
			binary = "0000000";
		}else if(mnem.equals("D|A")){
			binary = "0010101";
		}else if(mnem.equals("M")){
			binary = "1110000";
		}else if(mnem.equals("!M")){
			binary = "1110001";
		}else if(mnem.equals("-M")){
			binary = "1110011";
		}else if(mnem.equals("M+1")){
			binary = "1110111";
		}else if(mnem.equals("M-1")){
			binary = "1110010";
		}else if(mnem.equals("D+M")){
			binary = "1000010";
		}else if(mnem.equals("D-M")){
			binary = "1010011";
		}else if(mnem.equals("M-D")){
			binary = "1000111";
		}else if(mnem.equals("D&M")){
			binary = "1000000";
		}else if(mnem.equals("D|M")){
			binary = "1010101";
		}
		return binary;
	}
	
	public static String jump(String mnem){
		String binary = "";
		if(mnem == null){
			binary = "000";
		}else if(mnem.equals("JEQ")){
			binary = "010";
		}else if(mnem.equals("JGE")){
			binary = "011";
		}else if(mnem.equals("JLT")){
			binary = "100";
		}else if(mnem.equals("JNE")){
			binary = "101";
		}else if(mnem.equals("JLE")){
			binary = "110";
		}else if(mnem.equals("JMP")){
			binary = "111";
		}else if(mnem.equals("JGT")){
			binary = "001";
		}
		return binary;
	}
}
	
