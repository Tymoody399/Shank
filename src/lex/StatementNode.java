package lex;

public class StatementNode extends Node{
	
	private Node Data;
	
	public StatementNode()
	{this.Data = null;}
	
	
	public StatementNode(Node Data)
		{this.Data = Data;}
	
	public Node getData()
	{return this.Data;}
	
	public String toString() {
		return getData().toString();
	}
}
