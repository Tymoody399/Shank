package lex;

public class StringNode extends Node{
	private String String;
	
	public StringNode(String Str)
	{String = Str;}
	
	public String getStringNode()
	{return this.String;}
	
	//Output for the token's string
	public String toString()
		{return "String: " + getStringNode();}


}
