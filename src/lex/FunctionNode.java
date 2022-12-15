package lex;

import java.util.ArrayList;

public class FunctionNode extends CallableNode{
	ArrayList<VariableNode> Parameters, Local;
	ArrayList<StatementNode> Statement;
	private String Name;
	
	public FunctionNode(FunctionASTNode Function)
	{super(Function);
	this.Name = Function.getName();
	Parameters = Function.Parameters;
	Local = Function.Local;
	Statement = Function.getStatements();}
	
	public String getName()
	{return this.Name;}
}
