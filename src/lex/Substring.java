package lex;

import java.util.ArrayList;

public class Substring extends BuiltInFunctionNode{
	private ArrayList<InterpreterDataType> Values; 

	public Substring(ArrayList<InterpreterDataType> Values)
	{this.Values = Values;}
	
	public InterpreterDataType Execute() {
		var Str = (StringDataType)Values.get(0);
		var index = (IntDataType)Values.get(1);
		var Length = (IntDataType)Values.get(2);
		String NewString = Str.toString().substring(index.getInt(), Length.getInt());
		return new StringDataType(NewString);}

	@Override
	public boolean isVariadic() {
		// TODO Auto-generated method stub
		return false;
	}

}
