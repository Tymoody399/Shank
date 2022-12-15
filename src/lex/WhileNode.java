package lex;

import java.util.ArrayList;

public class WhileNode extends StatementNode{

	ArrayList<StatementNode> Statements;
	Node Condition;
	
	public WhileNode(Node Condition)
	{this.Condition = Condition;
	Statements = new ArrayList<StatementNode>();}
	
	public void addStatements(ArrayList<StatementNode> Statement)
	{this.Statements.addAll(Statement);}
	
	private ArrayList<StatementNode> getStatements()
	{return this.Statements;}
	
	public Node getCondition()
	{return this.Condition;}
	
	public String toString()
	{return "WhileNode Statement: " + getStatements();}
}
