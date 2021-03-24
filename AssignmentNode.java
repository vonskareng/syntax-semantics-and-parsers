
package prop.assignment0;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


public class AssignmentNode implements INode  {
	private Lexeme ID;
	private Lexeme assignOperand;
	private ExpressionNode eNode;
	private Lexeme semicolon;
	
	public AssignmentNode(Tokenizer t, Map<Object, Object> variableValues) throws IOException, TokenizerException, ParserException {
		
		if (t.current().token() == Token.IDENT) {
			ID = t.current();
			t.match();	
		}else {
			throw new ParserException("wrong start symbol of assignment");
		}
		
		
		if (t.current().token() == Token.ASSIGN_OP) {
			assignOperand = t.current();
			t.match();	
		}
		else {
			throw new ParserException("Expected '=' but was "+  t.current().value());
		}
		
		eNode = new ExpressionNode(t);
		
		if (t.current().token() == Token.SEMICOLON) {
			semicolon = t.current();
			variableValues.put(ID.value(), eNode);
			t.match();
			
		}else {
			throw new ParserException("Expected ';' but was " + t.current().value());
		}
		

	}
	
	public Lexeme getID() {
		return ID;
	}
	
	@Override
	public Object evaluate(Object[] args) throws Exception {
		
		HashMap<Object, Object> idValues = (HashMap<Object, Object>) args[0];
		double result;
		if (idValues.get(ID.value()) instanceof ExpressionNode) { //är det en exp node i value så har den inte evaluerats tidigare
			result =(double) Math.round((double)eNode.evaluate(args) * 100000d) / 100000; // avrunda till 5 decimaler
			idValues.put(ID.value(), result); // spara över expnoden med värdet för en assignment i mapen
		}else {
			result = (double) idValues.get(ID.value()); //om den redan är evaluerad hämta värdet
		}

		return ""+ ID.value() + " = " + result + "\n";
	}

	@Override
	public void buildString(StringBuilder builder, int tabs) {
		String tab = ""; 
		for(int i = 0; i<tabs; i++) {
			tab+= "\t";
		}
		builder.append(tab +"AssignmentNode" + "\n");
		builder.append(tab +"\t" + ID + "\n"); 
		builder.append(tab+"\t" + assignOperand + "\n"); 
		eNode.buildString(builder, (tabs+1) );
		builder.append(tab+"\t" + semicolon + "\n");
		
	}



	


}
