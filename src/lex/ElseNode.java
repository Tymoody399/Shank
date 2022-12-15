package lex;

import java.util.ArrayList;

public class ElseNode extends StatementNode{
	ArrayList<StatementNode> Statements;

	
	public ElseNode(Node IfChain)
	{Statements = new ArrayList<StatementNode>();}
	
	
	public void addStatements(ArrayList<StatementNode> Statement)
	{this.Statements.addAll(Statement);}
	
	private ArrayList<StatementNode> getStatements()
	{return this.Statements;}
	
	public String toString()
	{return "ElseNode Statements; " + getStatements();}
}
