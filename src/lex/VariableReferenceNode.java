package lex;

public class VariableReferenceNode extends Node{
	private String Name;
	
	VariableReferenceNode(String Name)
	{this.Name = Name;}
	
	private String getName()
	{return this.Name;}
	
	public String toString()
	{return getName();}
}
