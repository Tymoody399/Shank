package lex;

public class SquareRoot extends BuiltInFunctionNode{
	private Float Float;
	
	
	public SquareRoot(FloatNode Float)
	{this.Float = Float.getFloatNode();}
	
	public InterpreterDataType Execute() {
		return new FloatDataType(Float * Float);
	}

	@Override
	public boolean isVariadic() {
		return false;
	}
	
}
