package lex;

public class IntegerToReal extends BuiltInFunctionNode{
	private Integer Int;

	IntegerToReal(IntegerNode Int)
	{this.Int = Int.getIntegerNode();}
	
	public InterpreterDataType Execute() {		
		return new FloatDataType(this.Int.floatValue());}


	public boolean isVariadic() {
		return false;
	}
}
