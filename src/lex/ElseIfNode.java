package lex;
import java.util.ArrayList;

public class ElseIfNode extends StatementNode{
	ArrayList<StatementNode> Statements;
	private Node Condition;
	
	public ElseIfNode(Node Condition, Node IfChain)
	{this.Condition = Condition;
	Statements = new ArrayList<StatementNode>();}
	
	public Node getCondition()
	{return this.Condition;}
	
	public void addStatements(ArrayList<StatementNode> Statement)
	{this.Statements.addAll(Statement);}
	
	private ArrayList<StatementNode> getStatements()
	{return this.Statements;}
	
	public String toString()
	{return "ElseIfNode Statements " + getStatements();}
	
}