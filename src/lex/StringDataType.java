package lex;

public class StringDataType extends InterpreterDataType{
private String String;
	
	public StringDataType()
	{this.String = null;}
	
	public StringDataType(String Str)
	{this.String = Str;}
	
	public String toString() {
		return this.String;}

	public void FromString(String input) {
		this.String = input;}

	
	
}
