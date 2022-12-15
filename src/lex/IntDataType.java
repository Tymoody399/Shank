package lex;

public class IntDataType extends InterpreterDataType{

private Integer Int;

	public IntDataType()
	{this.Int = 0;}
	
	public IntDataType(Integer Int)
	{this.Int = Int;}
	
	public Integer getInt()
	{return this.Int;}
	
	public String toString() {
		return this.Int.toString();}

	public void FromString(String input) {
		this.Int = java.lang.Integer.parseInt(input);}

}
