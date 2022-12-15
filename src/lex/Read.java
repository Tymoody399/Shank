package lex;

import java.util.ArrayList;

public class Read extends BuiltInFunctionNode{
	private ArrayList<InterpreterDataType> Values; 
	private String Expression = null;

	public Read(ArrayList<InterpreterDataType> Values)
	{this.Values = Values;}
	
	public String getExpression()
	{return this.Expression;}
	
	public InterpreterDataType Execute() {
		for(int i = 0; i < Values.size(); i++)
			{Expression.concat(Values.get(i).toString() + " ");}
		return null;}

	@Override
	public boolean isVariadic() {
		return true;
	}

}
