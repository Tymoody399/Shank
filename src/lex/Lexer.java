package lex;

import java.util.ArrayList;

public class Lexer {
			
	private ArrayList<String> LexOuput = new ArrayList<String>();
	//Empty Lexer
	public Lexer()
	{}
	
	/*Lexer with an expression
	 * For this project, I've decided to just replace all spaces. However, I did reserved code in case we needed to use spaces for the future
	 */
	public Lexer(String Expression) throws InvalidCharecter 
	{this.LexOuput = new StateMachine(Expression).End();}
	
	//Enums for operators
		public enum TokenType
		{PLUS,MINUS,TIMES,DIVIDED, define, leftParen, rightParen, integer, real, begin, end, semicolon, colon, equal, comma, variables, constants, assignment, IF, then, ELSE, elseif, FOR, from, to, WHILE, repeat, until, mod,
		GreaterThan,LessThan, GreaterOrEqual,LessOrEqual, NotEqual, var, True, False}
	
		
		public ArrayList<String> getOutput()
			{return LexOuput;}

}


