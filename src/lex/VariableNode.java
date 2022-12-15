package lex;

public class VariableNode extends Node{
	private String Name, DataType;
	private Boolean Constant;
	private Node NodeAST;
	public enum DataType
	{integer, real, String, Char, Boolean;}
	
	public VariableNode() 
	{this.Name = null;
	this.Constant = false;
	this.NodeAST = null;}
	
	public VariableNode(String Name, Boolean Constant, DataType DataType, Node NodeAST) 
	{this.Name = Name;
	this.Constant = Constant;
	this.DataType = DataType.toString();
	this.NodeAST = NodeAST;}
	
	public String getName()
		{return this.Name;}
	
	public String getDataType()
	{return this.DataType;}
	
	public Boolean getConstant()
	{return this.Constant;}
	
	public Node getNodeAST()
	{return this.NodeAST;}
	
	public String toString()
	{return "{Name: " + getName() + ", InitualValue: " + getNodeAST() + "}";}
}
