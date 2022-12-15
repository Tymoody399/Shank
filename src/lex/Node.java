package lex;

public abstract class Node {
	
	private Object Data;
	
	public Node()
	{this.Data = null;}
	
	public Node(Object Data)
	{this.Data = Data;}
	
	public Object getData()
	{return this.Data;}
	
	public void setData(Object Data)
	{this.Data = Data;}
	
	
	public abstract String toString();}
	
