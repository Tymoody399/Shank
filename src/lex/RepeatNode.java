package lex;

import java.util.ArrayList;

public class RepeatNode extends StatementNode{
	
	ArrayList<StatementNode> Statements;
	Node Condition;
	
	public RepeatNode(Node Condition)
	{this.Condition = Condition;
	Statements = new ArrayList<StatementNode>();}
	
	public void addStatements(ArrayList<StatementNode> Statement)
	{this.Statements.addAll(Statement);}
	
	private ArrayList<StatementNode> getStatements()
	{return this.Statements;}
	
	public Node getCondition()
	{return this.Condition;}
	
	public String toString()
	{return "RepeatNode Statements: " + getStatements();}
}
