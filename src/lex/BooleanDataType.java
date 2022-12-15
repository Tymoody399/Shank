package lex;

public class BooleanDataType extends InterpreterDataType{
private Boolean Boolean;
	
	public BooleanDataType()
	{this.Boolean = false;}
	
	public BooleanDataType(Boolean Bool)
	{Boolean = Bool;}
	
	public Boolean getBoolean()
	{return this.Boolean;}
	
	
	public String toString() {
		return Boolean.toString();}

	public void FromString(String input) {
		java.lang.Boolean.parseBoolean(input);}

}
