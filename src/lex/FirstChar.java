package lex;



public class FirstChar extends BuiltInFunctionNode{
	private StringNode String;

	public FirstChar(StringNode Str)
	{this.String = Str;}
	
	public InterpreterDataType Execute() {
		var Char = new CharacterDataType(String.toString().charAt(0));
		return Char;}

	@Override
	public boolean isVariadic() {
		// TODO Auto-generated method stub
		return false;
	}
}
