package lex;


public class LastChar extends BuiltInFunctionNode{
		private StringNode String;

		public LastChar(StringNode Str)
		{this.String = Str;}
		
		public InterpreterDataType Execute() {
			var Lenth = String.toString().length() - 1;
			var Char = new CharacterDataType(String.toString().charAt(Lenth));
			return Char;}

		@Override
		public boolean isVariadic() {
			// TODO Auto-generated method stub
			return false;
		}
}