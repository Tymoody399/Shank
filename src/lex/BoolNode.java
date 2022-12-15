package lex;

public class BoolNode extends Node{

private Boolean Bool;
	
	public BoolNode()
	{Bool = false;}
	
	public BoolNode(Boolean Boolean)
	{this.Bool = Boolean;}
	
	public BoolNode(String Boolean)
	{if(Boolean.equals("True"))
		{this.Bool = true;}
	else
		{this.Bool = false;}}
	
	public Boolean getBoolNode()
	{return this.Bool;}
	
	//Output for the token's string
		public String toString()
		{return "Boolean: " + getBoolNode();}
}
