package lex;

import java.util.ArrayList;

public class FunctionCallNode extends StatementNode{
	private String Name;
	ArrayList<Node> Parameters;
	
	public FunctionCallNode(String Name)
	{this.Name = Name;
	this.Parameters = new ArrayList<Node>();}
	
	public String getName()
	{return this.Name;}
	
	public ArrayList<Node> getParameters()
	{return this.Parameters;}
	
	public String toString()
	{return "FunctionCallName: " + getName() + " ,Parameters: " + getParameters();}
}
