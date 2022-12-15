package lex;

import java.util.Random;



public class GetRandom extends BuiltInFunctionNode{

	
	GetRandom()
	{}
	
	public InterpreterDataType Execute()
	{Random Random = new Random();
	return new IntDataType(Random.nextInt());}

	
	public boolean isVariadic() {
		return false;
	}

	
}
