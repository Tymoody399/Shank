package lex;

public class RealToInteger extends BuiltInFunctionNode{
	private Float Float;

	
	RealToInteger(FloatNode Float)
	{this.Float = Float.getFloatNode();}
	
	
	public InterpreterDataType Execute() {
		return new IntDataType(this.Float.intValue());}


	public boolean isVariadic() {
		return false;
	}
}
