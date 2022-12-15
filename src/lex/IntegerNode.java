package lex;

public class IntegerNode extends Node{


	private int DataInt;
	
	public IntegerNode()
	{DataInt = 0;}
	
	public IntegerNode(int Int)
	{this.DataInt = Int;}
	
	public IntegerNode(String IntegerNode)
	{DataInt = Integer.parseInt(IntegerNode);}
	
	public int getIntegerNode()
	{return this.DataInt;}

	
	//Output for the token's string
		public String toString()
		{return "Int: " + getIntegerNode();}

	
}
