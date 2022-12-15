package lex;

import java.util.ArrayList;

public class Write extends BuiltInFunctionNode{
	private ArrayList<InterpreterDataType> Values;

	public Write(ArrayList<InterpreterDataType> Values)
	{this.Values = Values;}

	
	public InterpreterDataType Execute() {
		StringDataType String = new StringDataType(Values.get(0).toString());
		for(int i = 1; i < Values.size(); i++)
			{String.FromString(String.toString() + " " + Values.get(i).toString());;}
		return String;}


	@Override
	public boolean isVariadic() {
		return true;
	}}
