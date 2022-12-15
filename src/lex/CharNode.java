package lex;

public class CharNode extends Node{
private char Char;
		
	public CharNode(String String)
	{Char = String.charAt(0);}
	
	public char getCharNode()
	{return this.Char;}
	
	//Output for the token's string
		public String toString()
		{return "Char: " + getCharNode();}
}
