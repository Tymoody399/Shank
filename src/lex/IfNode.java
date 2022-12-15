package lex;

import java.util.ArrayList;
import java.util.LinkedList;

public class IfNode extends StatementNode{
	
	ArrayList<StatementNode> Statements;
	private Node Condition;
	private LinkedList<Node> List;
	
	
	//If Node
	public IfNode(Node Condition)
	{this.List = new LinkedList<Node>();
	this.Condition = Condition;
	Statements = new ArrayList<StatementNode>();}
	
	public Node getCondition()
	{return this.Condition;}
	
	public LinkedList<Node> getList()
	{return this.List;}
	
	public void AppendNode(Node ifNodes)
	{this.List.add(ifNodes);}
	
	public void addStatements(ArrayList<StatementNode> Statement)
	{this.Statements.addAll(Statement);}
	
	private ArrayList<StatementNode> getStatements()
	{return this.Statements;}
	
	public String toString()
	{return "IfNode Statements: " + getStatements();}

}
