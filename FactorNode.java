
package prop.assignment0;

import java.io.IOException;
import java.util.HashMap;

public class FactorNode implements INode {
	private Lexeme intValue;
	private Lexeme ID;
	private Lexeme leftParen;
	private ExpressionNode eNode;
	private Lexeme rightParen;
	
	
	public FactorNode(Tokenizer t) throws IOException, TokenizerException, ParserException {
		if (t.current().token() == Token.INT_LIT) {
			intValue = t.current();
			t.match();
		}else if(t.current().token() == Token.IDENT) {
			ID = t.current();
			t.match();
			
		}else if(t.current().token() == Token.LEFT_PAREN) {
			leftParen = t.current();
			t.match();
			eNode = new ExpressionNode(t);
			if (t.current().token() == Token.RIGHT_PAREN) {
				rightParen = t.current();
				t.match();
			}else {
				throw new ParserException("expected ')' but was " + t.current().value());
			}
		}else {
			throw new ParserException("must be INT_LITERAL or IDENTIFIER");
		}
	}

	@Override
	public Object evaluate(Object[] args) throws Exception {
		if(intValue !=null) {
			return intValue.value();
		}else if (ID != null){
			HashMap<Object, Object> temp = ((HashMap<Object, Object>) args[0]);
			if (temp.get(ID.value()) instanceof ExpressionNode) {
				ExpressionNode e = (ExpressionNode) temp.get(ID.value());
				return (double)e.evaluate(args);
			}else if(temp.get(ID.value()) instanceof Double){
				return ((HashMap<Object, Object>) args[0]).get(ID.value());
			}else {
				throw new EvaluateException("variable "+ ID.value() +" is not initialized");
			}

		}else {
			return eNode.evaluate(args);
		}
		
		

	}
	
	

	@Override
	public void buildString(StringBuilder builder, int tabs) {
		String tab = ""; 
		for(int i = 0; i<tabs; i++) {
			tab+= "\t";
		}
		
		builder.append(tab + "FactorNode" + "\n");
		if(intValue!=null) {
			builder.append("\t" + tab + intValue + "\n");
			
		}else if(ID!=null){
			builder.append("\t" + tab + ID + "\n");
		}else {
			builder.append("\t" + tab + leftParen + "\n"); 
			eNode.buildString(builder, (tabs+1));
			builder.append("\t" + tab + rightParen + "\n");
		}
		

		
	}

}
