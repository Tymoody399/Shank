package lex;

import java.util.ArrayList;

public abstract class CallableNode {
	private String Name;
	ArrayList<VariableNode> Parameters;
	
	public CallableNode()
	{this.Name = null;
	this.Parameters = null;}
	
	public CallableNode(FunctionASTNode Function)
	{this.Name = Function.getName();
	this.Parameters = Function.Parameters;}
}
