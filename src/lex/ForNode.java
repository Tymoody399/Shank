package lex;

import java.util.ArrayList;

public class ForNode extends StatementNode{

	private VariableReferenceNode VarianceReference;
	private IntegerNode Start, End;
	ArrayList<StatementNode> Statements;
	
	public ForNode(VariableReferenceNode VarianceReference, IntegerNode Start, IntegerNode End)
	{this.VarianceReference = VarianceReference;
	this.Start = Start;
	this.End = End;
	this.Statements = new ArrayList<StatementNode>();}
	
	
	public int getStart() 
	{return this.Start.getIntegerNode();}
	
	public int getEnd() 
	{return this.End.getIntegerNode();}
	
	public VariableReferenceNode getName()
	{return this.VarianceReference;}
	
	public void addStatements(ArrayList<StatementNode> Statement)
	{this.Statements.addAll(Statement);}
	
	private ArrayList<StatementNode> getStatements()
	{return this.Statements;}
	
	public String toString()
	{return  "ForNode Statements" + getStatements();}
	
}
