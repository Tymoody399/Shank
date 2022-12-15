package lex;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class StateMachine extends Lexer{
	private int Position = -1;
	private int State;
	private char CurrentChar, NextChar, PreviousChar, FirstChar, LastChar;
	public Token Token = new Token();
	private String Expression;
	public ArrayList<String> Output = new ArrayList<String>();
	private HashMap<String, TokenType> Words = new HashMap<>();

	public StateMachine(String Expression) throws InvalidCharecter
	{this.Expression = Expression;
	Words.put("integer", TokenType.integer);
	Words.put("real", TokenType.real);
	Words.put("begin", TokenType.begin);
	Words.put("end", TokenType.end);
	Words.put("variables", TokenType.variables);
	Words.put("constants", TokenType.constants);
	Words.put("define", TokenType.define);
	Words.put("if", TokenType.IF);
	Words.put("then", TokenType.then);
	Words.put("else", TokenType.ELSE);
	Words.put("elseif", TokenType.elseif);
	Words.put("for", TokenType.FOR);
	Words.put("from", TokenType.from);
	Words.put("to", TokenType.to);
	Words.put("while", TokenType.WHILE);
	Words.put("repeat", TokenType.repeat);
	Words.put("until", TokenType.until);
	Words.put("mod", TokenType.mod);
	Words.put("var", TokenType.var);
	Words.put("true", TokenType.True);
	Words.put("false", TokenType.False);
	Start();}
	
		//Start State
		public void Start() throws InvalidCharecter 
		{//If there's extra spaces in the beginning it will be deleted.
		while(this.Expression.length() != 0 && this.Expression.charAt(0) == ' ' || this.Expression.length() != 0 && this.Expression.charAt(0) == '\t')
			{Expression = Expression.substring(1);}
		if(this.Expression.length() == 0)
			{this.Output.add("EndofLine");}
		else {
			//If there's extra spaces in the end of the Expression it will be deleted.
			while(Expression.charAt(Expression.length() - 1) == ' ' || this.Expression.charAt(0) == '\t')
				{Expression = Expression.substring(0, Expression.length() - 1);}
			FirstChar = this.Expression.charAt(0);
			LastChar = this.Expression.charAt(getExpression().length() - 1);
			if(FirstChar == '*' || FirstChar == '/')
				{throw new InvalidCharecter("Invalid Charecter in the First postion");}
			else if(isOperator(LastChar))
				{throw new InvalidCharecter("Invalid Charecter in the LAST postion");}
			move();}}
	
	
	private String getExpression() {
			return this.Expression;}

	
	private void move() throws InvalidCharecter {
		if(this.Position == this.Expression.length() - 1)
			{Output.add("EndofLine");}
		else
		{this.Position++;
		this.CurrentChar = this.Expression.charAt(Position);
		if(isOperator(CurrentChar))
			{this.State = 1;}
		else if(isNumb(CurrentChar))
			{this.State = 2;}
		else if(isSpace(CurrentChar))
			{this.State = 3;}
		else if(this.CurrentChar == '(' && this.Expression.charAt(Position + 1) == '*')
			{this.State = 5;}
		else if(this.CurrentChar == '"')
			{this.State = 6;}
		else if(this.CurrentChar == '\'')
			{this.State = 7;}
		else
			{this.State = 4;}
		switch(this.State)
			{case 1:
				Operator();
				break;
			case 2:
				Number();
				break;
			case 3:
				Space();
				break;
			case 4:
				Word();
				break;
			case 5:
				Comment();
				break;
			case 6:
				Position++;
				String();
				break;
			case 7:
				Position++;
				Charecter();
				break;}}}
	
		//String State
		private void String() throws InvalidCharecter
		{Token.setStr("");
		while(this.Expression.charAt(Position) != '"')
			{CurrentChar = this.Expression.charAt(this.Position);
			Token.setStr(Token.getStr() + String.valueOf(CurrentChar));
			if(Position == this.Expression.length() - 1)
				{throw new InvalidCharecter(this.Expression + "-- Invalid Charecter in position: " + (Position + 1) + " :Missing double quote");}
			Position++;}
		Output.add("String (" +Token.getStr()+ ")");
		Token = new Token();
		move();}
		
		//CharecterState
		private void Charecter() throws InvalidCharecter
		{CurrentChar = this.Expression.charAt(this.Position);
		Token.setStr(String.valueOf(CurrentChar));
		if(this.Expression.charAt(this.Position + 1) != '\'')
			{throw new InvalidCharecter(this.Expression + "-- Invalid Charecter in position: " + (Position + 1) + " :Missing single quote");}
		Position++;
		Output.add("Charecter (" + Token.getStr()+ ")");
		Token = new Token();
		move();}
	
	
		//Comment State
		private void Comment() throws InvalidCharecter
		{while(CurrentChar != '*' && this.Expression.charAt(Position + 1) != ')')
			{if(this.Position == this.Expression.length() - 2)
				{throw new InvalidCharecter("Comment doesn't end with '*' or ')'");}
			this.Position++;}
		Position++;
		move();}
	
		//Number State
		private void Number() throws InvalidCharecter {
			CurrentChar = this.Expression.charAt(this.Position);
			if(Token.getStr() == null)
				{Token.setStr(String.valueOf(CurrentChar));}
			else
				{Token.setStr(Token.getStr() + String.valueOf(CurrentChar));}
			if(this.Position == this.Expression.length() - 1)
				{this.Output.add(Token.toString());
				this.Output.add("EndofLine");}
			else
				{move();}}
		
		//Operator State
		private void Operator() throws InvalidCharecter
		{this.CurrentChar = this.Expression.charAt(Position);
		this.NextChar = this.Expression.charAt(Position + 1);
		//First Character
		if(Position == 0)
			{Token.setStr(String.valueOf(CurrentChar));}
		//All other Characters
		else
			{this.PreviousChar = this.Expression.charAt(Position - 1);
			
			//Negative number or number with a plus in front
			if(isOperator(PreviousChar) && CurrentChar == '+' || isOperator(PreviousChar) && CurrentChar == '-')
				{Token.setStr(String.valueOf(CurrentChar));}
			else if(isOperator(PreviousChar) && isOperator(NextChar))
				{throw new InvalidCharecter("Invalid Charecter in position: " + (Position - 2) + "-" + Position + " -Consecutive Operators-");}
			else if(isOperator(PreviousChar))
				{throw new InvalidCharecter("Invalid Charecter in position: " + Position + " -Consecutive Operators-");}
			else
					{if(Token.getStr() != null)
						{if(isWord(Token.getStr())) 
							{Output.add("identifier (" + Token.getStr() + ")");}
						else
							{Output.add(this.Token.toString());}}
					switch(CurrentChar)
					{case '*':
						Output.add(TokenType.TIMES.toString());
						break;
					case '/':
						Output.add(TokenType.DIVIDED.toString());
						break;
					case '+':
						Output.add(TokenType.PLUS.toString());
						break;
					case '-':
						Output.add(TokenType.MINUS.toString());
						break;}
					this.Token = new Token();}}
		move();}
	
		
		//Space State
		private void Space() throws InvalidCharecter{
			this.PreviousChar = this.Expression.charAt(Position - 1);
			if(isNumb(PreviousChar))
				{Output.add(this.Token.toString());
				this.Token = new Token();}
			if(Token.getStr() != null && isWord(Token.getStr()) && !Token.getStr().equals("var"))
			{Output.add("identifier (" + Token.getStr() + ")");
			Token = new Token();}
			move();}

		//Word State
		private void Word() throws InvalidCharecter{
		CurrentChar = this.Expression.charAt(Position);
			switch(CurrentChar)
				{case ',':	
					if(Token.getStr() != null)
						{if(isWord(Token.getStr()))
							{Output.add("identifier (" + Token.getStr() + ")");}
						else
							{Output.add(Token.toString());}
						Token = new Token();}
					Output.add(TokenType.comma.toString());
					break;
				case ':': 
					if(Token.getStr() != null)
					{Output.add("identifier (" + Token.getStr() + ")");
					Token = new Token();}
					//Adds Assignment
					if(this.Expression.charAt(Position + 1) == '=')
						{Output.add(TokenType.assignment.toString());
						Position++;}
					//Adds Colon
					else
						{Output.add(TokenType.colon.toString());}
				break;
				case '=': 
					if(Token.getStr() != null)
						{if(isWord(Token.getStr()))
							{Output.add("identifier (" + Token.getStr() + ")");}
						else
							{Output.add(Token.toString());}
						Token = new Token();}
					Output.add(TokenType.equal.toString());
				break;
				case ';': 
				Output.add(TokenType.semicolon.toString());
				break;
				case '(': 
					if(Token.getStr() != null)
						{if(isWord(Token.getStr()))
							{Output.add("identifier (" + Token.getStr() + ")");}
						else
							{Output.add(Token.toString());}
						Token = new Token();}
				Output.add(TokenType.leftParen.toString());
				break;
				case ')':
					if(this.Token.getStr() != null)
						{if(isWord(Token.getStr()))
							{Output.add("identifier (" + Token.getStr() + ")");}
						else
							{Output.add(Token.toString());}
						Token = new Token();}
				Output.add(TokenType.rightParen.toString());
				break;
				case '>':
					if(Token.getStr() != null)
						{if(isWord(Token.getStr()))
							{Output.add("identifier (" + Token.getStr() + ")");}
						else
							{Output.add(Token.toString());}
						Token = new Token();}
					if(this.Expression.charAt(Position + 1) == '=')
						{Output.add(TokenType.GreaterOrEqual.toString());
						this.Position++;}
					else
						{Output.add(TokenType.GreaterThan.toString());}
					break;
				case '<':
					if(Token.getStr() != null)
						{if(isWord(Token.getStr()))
							{Output.add("identifier (" + Token.getStr() + ")");}
						else
							{Output.add(Token.toString());}
						Token = new Token();}
					if(this.Expression.charAt(Position + 1) == '=')
						{Output.add(TokenType.LessOrEqual.toString());
						this.Position++;}
					else if(this.Expression.charAt(Position + 1) == '>')
						{Output.add(TokenType.NotEqual.toString());
						this.Position++;}
					else
						{Output.add(TokenType.LessThan.toString());}
					break;
				case '%':
					if(Token.getStr() != null)
						{if(isWord(Token.getStr()))
							{Output.add("identifier (" + Token.getStr() + ")");}
						else
							{Output.add(Token.toString());}
						Token = new Token();}
					Output.add(TokenType.mod.toString());
				default:
					if(Token.getStr() == null)
						{Token.setStr(String.valueOf(CurrentChar));}
					else
						{Token.setStr(Token.getStr() + String.valueOf(CurrentChar));}
					if(Words.containsKey(Token.getStr()))
						//Separates 'var' from 'variable' and else from elseif
						{if(Token.getStr().equals("var") && !isSpace(this.Expression.charAt(Position + 1)) || Token.getStr().equals("else") && this.Position != this.Expression.length())
							{Token.setStr(Token.getStr());}
						else if(Token.getStr().equals("var"))
							{Token.setStr("var ");
							while(this.Expression.charAt(this.Position + 1) == ' ')
								{Position++;}}
						else if(Token.getStr().equals("true") || Token.getStr().equals("false"))
							{Output.add("Boolean (" + Words.get(Token.getStr()).toString()+	")");
							Token = new Token();}
						else {
							Output.add(Words.get(Token.getStr()).toString());
							Token = new Token();}}}
				if(Token.getStr() != null && Position == this.Expression.length() - 1)
					{if(isWord(Token.getStr()))
						{Output.add("identifier (" + Token.getStr() + ")");}
					else
						{Output.add(Token.toString());}
					Output.add("EndofLine");}
				else if(Position == this.Expression.length() - 1)
					{Output.add("EndofLine");}
				else
					{move();}}
		
		//End State
		public ArrayList<String> End()throws InvalidCharecter
		{int i = 0;
		while(i < this.Output.size() - 1)
			{if(this.Output.get(i).contains("Number") && this.Output.get(i + 1).contains("Number"))
				{throw new InvalidCharecter("Consecutive Numbers");}
			else if(isSymbol(this.Output.get(i)) && isSymbol(this.Output.get(i + 1)))
				{if(!this.Output.get(i).equals("leftParen") && !this.Output.get(i + 1).equals("rightParen"))
					{throw new InvalidCharecter("Consecutive Symbols" + " " + this.Output.get(0)+ " " + i);}}
			i++;}
		
		return this.Output;}
		
		//Distinguishes whether this char is a operator
		private Boolean isOperator(char i) 
			{switch(i)
				{case '*': case '+': case '-': case '/':
					return true;
				default:
					return false;}}
						
		//Distinguishes whether this char is a number
		private Boolean isNumb(char i)
			{switch(i)
				{case '0': case '1': case '2': case '3': case '4': case '5': case '6':case '7': case '8': case '9': case '.':
					return true;
				default:
					return false;}}
						
		//Distinguishes whether this char is a space
		private Boolean isSpace(char i)
			{switch(i)
				{case ' ': case '\t':
					return true;
				default:
					return false;}}
		
		private Boolean isSymbol(String Symbol)
		{switch(Symbol)
			{case "comma": case "assignment": case "colon": case "leftParen": case "semicolon": case "rightParen": case"GreaterOrEqual": case "GreaterThan": case "LessOrEqual": case "NotEqual": case "LessThan": case "mod":
				return true;
			default:
				return false;}}
		
		private Boolean isWord(String Str)
			{Pattern Pattern = java.util.regex.Pattern.compile(".*[a-zA-Z].*");
			Matcher matcherText = Pattern.matcher(Str);
			return matcherText.matches();}}


