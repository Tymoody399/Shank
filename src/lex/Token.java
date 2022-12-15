package lex;

public class Token{
	
	////Declare private variables
	private String str;
	
	//Empty token
	public Token()
	{this.str = null;}
	
	//Getter for the token's string
	public String getStr()
	{return this.str;}
	
	//Setter for the token's string
	public void setStr(String str)
	{this.str = str;}
	
	//Output for the token's string
	public String toString()
	{return "Number (" + this.getStr() + ")";}
}

