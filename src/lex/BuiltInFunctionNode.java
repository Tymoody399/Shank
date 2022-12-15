package lex;

public abstract class BuiltInFunctionNode extends CallableNode{
	
	
	
	public abstract boolean isVariadic();
	public abstract InterpreterDataType Execute();
}

