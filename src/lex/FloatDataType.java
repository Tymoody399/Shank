package lex;

public class FloatDataType extends InterpreterDataType{
	private Float Float;
	
	public FloatDataType()
	{this.Float = (float) 0.0;}
	
	public FloatDataType(Float Float)
	{this.Float = Float;}
	
	public Float getFloat()
	{return this.Float;}
	
	public String toString() {
		return this.Float.toString();}

	public void FromString(String input) {
		this.Float = java.lang.Float.parseFloat(input);}

	}
