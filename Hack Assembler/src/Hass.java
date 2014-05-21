
public class Hass {
	public static void main(String[] args){
		String fileName = "Max.asm";
		Parser parser = new Parser(fileName);
		while(parser.hasMoreCommands()){
			System.out.println("Command: " + parser.lines.get(parser.currentCommand));
			System.out.println("TYPE: " + parser.commandType());
			System.out.println("SYMBOL: " + parser.symbol());
			System.out.println("DEST: " + parser.dest());
			System.out.println("COMP: " + parser.comp());
			System.out.println("JUMP: " + parser.jump());
			parser.advance();
		}
	}
}
