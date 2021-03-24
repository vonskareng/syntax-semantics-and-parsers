
package prop.assignment0;

import java.io.IOException;

public class TermNode implements INode {
	private FactorNode fNode;
	private Lexeme operand;
	private TermNode tNode;

	
	public TermNode(Tokenizer t) throws IOException, TokenizerException, ParserException {
		fNode = new FactorNode(t);
		while (t.current().token() == Token.MULT_OP || t.current().token() == Token.DIV_OP) {
			operand = t.current();
			t.match();
			tNode = new TermNode(t);
		}
	}

	@Override
	public Object evaluate(Object[] args) throws Exception {
		TermNode temp = tNode;
		double left; 
		
		
		left = (double) fNode.evaluate(args);
		
		if(operand!=null) {
			Lexeme tempOP = temp.getOP();
			if(operand.token()==Token.MULT_OP) {
				left = left * (double)temp.getFactor().evaluate(args);
			}else {
				left = left / (double)temp.getFactor().evaluate(args);
			}
			
			while(true) { //om nästa termnode har en termnode med operand utför operand och spara ner nya värdet
				if(tempOP==null) {
					
					return left;
				}
				
				temp = temp.tNode;
				
				
				if(tempOP.token() == Token.MULT_OP) {
					left = left * (double)temp.getFactor().evaluate(args);
				}else {
					left = left / (double)temp.getFactor().evaluate(args);
				}
				
				tempOP = temp.getOP();
				
			}
		
		}else {
			return left;
		}

		
		
	}
			
		
		
		
		
		
		
		
		
		
//		double leftOfOperand,rightOfOperand;
//		if(operand == null) {
//			return fNode.evaluate(args);
//		}else if(operand.token() == Token.MULT_OP) {
//			leftOfOperand = (double) fNode.evaluate(args);
//			rightOfOperand = (double) tNode.evaluate(args);
//			return leftOfOperand*rightOfOperand;
//		}else {
//			leftOfOperand = (double) fNode.evaluate(args);
//			rightOfOperand = (double) tNode.evaluate(args);
//			return leftOfOperand/rightOfOperand;
//		}
//		
	

	@Override
	public void buildString(StringBuilder builder, int tabs) {
		String tab = ""; 
		for(int i = 0; i<tabs; i++) {
			tab+= "\t";
		}
		
		builder.append(tab + "TermNode" + "\n");
		fNode.buildString(builder, (tabs+1));
		
		if(operand!=null) {
			builder.append("\t" + tab + operand + "\n"); 
			tNode.buildString(builder, (tabs+1));
			
		}
		
	}
	public Lexeme getOP() {
		return operand;
	}
	public FactorNode getFactor() {
		return fNode;
	}

}
