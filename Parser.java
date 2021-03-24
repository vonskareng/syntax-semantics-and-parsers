
package prop.assignment0;

import java.io.IOException;


public class Parser implements IParser {

	private Tokenizer tokenizer;
	
	
	@Override
	public void open(String fileName) throws IOException, TokenizerException {
		
		tokenizer = new Tokenizer();
		tokenizer.open(fileName);
		tokenizer.match();
		
	}

	@Override
	public INode parse() throws IOException, TokenizerException, ParserException {
		
		 
	return new BlockNode(tokenizer);
		
	}
	


	@Override
	public void close() throws IOException {
		tokenizer.close();
		
	}
	


}
