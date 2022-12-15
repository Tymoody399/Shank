package lex;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import lex.VariableNode.DataType;

public class Parser {
	private String ComparatorStr;
	private ArrayList<String> VariableNames;
	private ArrayList<ArrayList<String>> LexArrayExpress;
	private Node AST;
	private ArrayList<VariableNode> Constant, Variable;
	private FunctionASTNode Function;
	private int ArrayPosition;
	private IfNode HeadIf;
	
	//Empty Parse
	Parser()
	{this.LexArrayExpress = null;}
	
	//Parse with lexer
	Parser(ArrayList<ArrayList<String>> Lexer) throws InvalidCharecter
	{this.LexArrayExpress = Lexer;
	this.BuildBody();
	this.AST = null;}
	
	public ArrayList<FunctionASTNode> Parse() throws InvalidCharecter
	//Calls FunctionDefinition
	{return FunctionDefinition();}
	
	//Connects "begin" with its corresponding "end:
	private void BuildBody() throws InvalidCharecter
	{Stack<String> BodyConnect = new Stack<String>();
	int beginCount = 0;
	for(int i = 0; i < this.LexArrayExpress.size(); i++)
		{if(this.LexArrayExpress.get(i).contains("begin"))
			{if(this.LexArrayExpress.get(i).size() != 2)
				{throw new InvalidCharecter("Invalid begin: Extra token(s) on 'begin' line " + (i + 1));}
			this.LexArrayExpress.get(i).set(0, "begin (" + beginCount++ + ")");
			BodyConnect.push(this.LexArrayExpress.get(i).get(0));}
		else if(this.LexArrayExpress.get(i).contains("end"))
			{if(BodyConnect.isEmpty())
				{throw new InvalidCharecter("Invalid begin: missing a begin");}
			else if(this.LexArrayExpress.get(i).size() > 2)
				{throw new InvalidCharecter("Invalid end: Extra token(s) on 'end' line");}
			this.LexArrayExpress.get(i).set(0, "end (" + matchAndRemove(BodyConnect.pop()) + ")");}}
	}
	
	//Using the toString for the output of a number in the lexer, I can start at the 8th char position and go until Rperen.
	private String matchAndRemove(String Expression) 
	{if(Expression.contains("Number"))
		{int NumberEnd = Expression.length() - 1;
		String Number = Expression.substring(8, NumberEnd);
		return Number;}
	else if(Expression.contains("identifier"))
		{int identiferEnd = Expression.length() - 1;
		String identifer = Expression.substring(12, identiferEnd);
		return identifer;}
	else if(Expression.contains("begin"))
		{int BeginEnder = Expression.length() - 1;
		String Number = Expression.substring(7, BeginEnder);
		return Number;}
	else if(Expression.contains("String"))
		{int StringEnder = Expression.length() - 1;
		String Str = Expression.substring(8, StringEnder);
		return Str;}
	else if(Expression.contains("Boolean"))
		{
		int BooleanEnder = Expression.length() - 1;
		String Bool = Expression.substring(9, BooleanEnder);
		return Bool;}
	else
		{String Char = Expression.substring(11, 12);
		return Char;}}
	
	public Node Expression(List<String> list) throws InvalidCharecter
	{this.AST = null;
	Term(list);
	int Position = 0;
	if(list.contains("PLUS") || list.contains("MINUS"))
	{while(Position != list.size())
		{if(list.get(Position).equals("PLUS") || list.get(Position).equals("MINUS"))
			{if(isEmpty())
				{switch(list.get(Position))
				{case "PLUS": 
					this.AST = new MathOpNode("+", Factor(list,Position - 1), Factor(list,Position + 1));
					list.subList(Position - 1, Position + 2).clear();
					Position--;
					break;
				case "MINUS":
					this.AST = new MathOpNode("-", Factor(list,Position - 1), Factor(list,Position + 1));
					list.subList(Position - 1, Position + 2).clear();
					Position--;
					break;}}
			else
				{if(Position == list.size() - 1)
					{switch(list.get(Position))
					{case "PLUS": 
						this.AST = new MathOpNode("+", this.AST, Factor(list,Position - 1));
						list.subList(Position - 1, Position + 1).clear();
						Position--;
						break;
					case "MINUS":
						this.AST = new MathOpNode("-", this.AST, Factor(list,Position - 1));
						list.subList(Position - 1, Position + 1).clear();
						Position--;
						break;}}
				else 
				{switch(list.get(Position))
				{case "PLUS": 
					this.AST = new MathOpNode("+", this.AST, Factor(list,Position + 1));
					list.subList(Position, Position + 2).clear();
					break;
				case "MINUS":
					this.AST = new MathOpNode("-", this.AST, Factor(list,Position + 1));
					list.subList(Position, Position + 2).clear();
					break;}}}}
		else
			{Position++;}}}
	else if(this.AST != null)
		{return this.AST;}
	else
		{this.AST = Factor(list, 0);}
	return this.AST;}
	
	private List<String> Term(List<String> Term)
	{if(Term.contains("TIMES") || Term.contains("DIVIDED") || Term.contains("mod"))
		{int Position = 0;
		while(Position != Term.size())
			{if(Term.get(Position).equals("TIMES") || Term.get(Position).equals("DIVIDED") || Term.get(Position).equals("mod"))
				{if(isEmpty())
					{switch(Term.get(Position))
					{case "TIMES": 
						this.AST = new MathOpNode("*", Factor(Term,Position - 1), Factor(Term,Position + 1));
						Term.subList(Position - 1, Position + 2).clear();
						Position--;
						break;
					case "DIVIDED":
						this.AST = new MathOpNode("/", Factor(Term,Position - 1), Factor(Term,Position + 1));
						Term.subList(Position - 1, Position + 2).clear();
						Position--;
						break;
					case "mod":
						this.AST = new MathOpNode("%", Factor(Term,Position - 1), Factor(Term,Position + 1));
						Term.subList(Position - 1, Position + 2).clear();
						Position--;
						break;}}
				
				else
					{switch(Term.get(Position))
					{case "TIMES": 
						this.AST = new MathOpNode("*", this.AST, Factor(Term,Position + 1));
						Term.subList(Position, Position + 2).clear();
						break;
					case "DIVIDED":
						this.AST = new MathOpNode("/", this.AST, Factor(Term,Position + 1));
						Term.subList(Position , Position + 2).clear();
						break;
					case "mod":
						this.AST = new MathOpNode("%", this.AST, Factor(Term,Position + 1));
						Term.subList(Position , Position + 2).clear();
						break;}}}
			else
				{Position++;}}
		return Term;}
	else
		{return Term;}}
	
	public boolean isEmpty()
	{return this.AST == null;}
	
	//Creates a Factor node based on the given Array string position
	private Node Factor(List<String> Factor,int i) 
	{if(Factor.get(i).contains("True"))
		{return new BoolNode(true);}
	else if(Factor.get(i).contains("False"))
		{return new BoolNode(false);}
	else if(Factor.get(i).contains("identifier"))
		{return new VariableReferenceNode(Factor.get(i));}
	else if(Factor.get(i).contains("String"))
		{return new StringNode(matchAndRemove(Factor.get(i)));}
	else if(Factor.get(i).contains("Charecter"))
		{return new CharNode(matchAndRemove(Factor.get(i)));}
	else if(matchAndRemove(Factor.get(i)).contains("."))
		{return new FloatNode(matchAndRemove(Factor.get(i)));}
	else
		{return new IntegerNode(matchAndRemove(Factor.get(i)));}}
	
	private ArrayList<FunctionASTNode> FunctionDefinition() throws InvalidCharecter
	{ArrayList<String> ListofIdentifiers = new ArrayList<String>();
	ArrayList<FunctionASTNode> AstTreeFunctions = new ArrayList<FunctionASTNode>();
	//For loop for the entire Parser
	for(ArrayPosition = 0; ArrayPosition < this.LexArrayExpress.size(); ArrayPosition++)
	{if(this.LexArrayExpress.get(ArrayPosition).contains("define"))
		{if(!this.LexArrayExpress.get(ArrayPosition).get(0).equals("define") || !this.LexArrayExpress.get(ArrayPosition).get(1).contains("identifier") || !this.LexArrayExpress.get(ArrayPosition).get(2).equals("leftParen"))
			{throw new InvalidCharecter("Invalid Function declaration on line: " + (ArrayPosition + 1));}
		Function = new FunctionASTNode(matchAndRemove(this.LexArrayExpress.get(ArrayPosition).get(1)));
		AstTreeFunctions.add(Function);
		int Position = 3;
		//Loop for the function parameters
		while(!this.LexArrayExpress.get(ArrayPosition).get(Position).equals("rightParen"))
			{if(this.LexArrayExpress.get(ArrayPosition).get(Position).equals("EndofLine"))
				{throw new InvalidCharecter("Invalid Function declaration: Missing ')' after function name on line: " + (ArrayPosition + 1));}
			//The String is a identifier
			if(this.LexArrayExpress.get(ArrayPosition).get(Position).contains("identifier"))
				{if(!this.LexArrayExpress.get(ArrayPosition).get(Position + 1).equals("colon") && !this.LexArrayExpress.get(ArrayPosition).get(Position + 1).equals("comma"))
					{throw new InvalidCharecter("Invalid Function declaration: Missing ',' or ':' after identifier on line: " + (ArrayPosition + 1));}
				else if(!this.LexArrayExpress.get(ArrayPosition).get(Position - 1).equals("semicolon") && !Function.Parameters.isEmpty())
					{throw new InvalidCharecter("Invalid Function declaration: Missing ';' before identifier on line: " + (ArrayPosition + 1));}
				ListofIdentifiers.add(matchAndRemove(this.LexArrayExpress.get(ArrayPosition).get(Position)));}
			//The String is integer or real
			else if(this.LexArrayExpress.get(ArrayPosition).get(Position).equals("integer") || this.LexArrayExpress.get(ArrayPosition).get(Position).equals("real"))
				{if(ListofIdentifiers.isEmpty())
					{throw new InvalidCharecter("Invalid Function declaration: Missing identifier " + (this.ArrayPosition + 1));}
				for(int i = 0; i < ListofIdentifiers.size();i++)
					{if(this.LexArrayExpress.get(ArrayPosition).get(Position).equals("integer"))
						{Function.addParameters(new VariableNode(ListofIdentifiers.get(i), false, DataType.integer, new IntegerNode()));}
					else
						{Function.addParameters(new VariableNode(ListofIdentifiers.get(i), false, DataType.real, new FloatNode()));}}
				ListofIdentifiers.clear();}
			Position++;}
		LocalParameters();}}
	return AstTreeFunctions;}
	
	private void LocalParameters() throws InvalidCharecter
	{this.ArrayPosition++;
	if(this.LexArrayExpress.get(ArrayPosition).contains("constants") || this.LexArrayExpress.get(ArrayPosition).contains("variables"))
			{if(this.LexArrayExpress.get(ArrayPosition).size() != 2)
				{throw new InvalidCharecter("Invalid Function declaration: Extra token(s) on line: " + (this.ArrayPosition + 1));}
			while(this.LexArrayExpress.get(ArrayPosition).contains("constants") || this.LexArrayExpress.get(ArrayPosition).contains("variables"))	
				{if(this.LexArrayExpress.get(ArrayPosition).contains("variables"))
					{this.ArrayPosition++;
					Function.addLocal(Variables());}
				else
					{this.ArrayPosition++;
					Function.addLocal(Constants());}}}
	//checks for duplicate identifiers
	VariableNames = new ArrayList<String>();
	for(int i = 0; i < Function.Local.size(); i++)
		{VariableNames.add(Function.Local.get(i).getName());}
	for(int a = 0; a < Function.Parameters.size(); a++)
		{VariableNames.add(Function.Parameters.get(a).getName());}
	for(int x = 0; x < VariableNames.size(); x++)
		{for(int y = x + 1; y < VariableNames.size(); y++)
			{if(VariableNames.get(x).equals(VariableNames.get(y)))
				{throw new InvalidCharecter("Invalid Function declaration: Dubplicate Variable names");}}}
	ArrayList<StatementNode> FunctionStatements = new ArrayList<StatementNode>();
	Function.addStatement(BodyFuntion(FunctionStatements));}
	
	private ArrayList<VariableNode> Constants() throws InvalidCharecter
	{this.Constant = new ArrayList<VariableNode>();
	while(this.LexArrayExpress.get(this.ArrayPosition).get(0).contains("identifier"))
		{Constant.add(ProccessConstants());
		this.ArrayPosition++;}
		return Constant;}
	
	private VariableNode ProccessConstants() throws InvalidCharecter
	{if(this.LexArrayExpress.get(ArrayPosition).get(0).contains("identifier") && this.LexArrayExpress.get(ArrayPosition).get(1).equals("equal") && this.LexArrayExpress.get(ArrayPosition).get(2).contains("String") && this.LexArrayExpress.get(ArrayPosition).size() == 4)
		{return new VariableNode(matchAndRemove(this.LexArrayExpress.get(ArrayPosition).get(0)), true, DataType.String, new StringNode(matchAndRemove(this.LexArrayExpress.get(ArrayPosition).get(2))));}
	else if(this.LexArrayExpress.get(ArrayPosition).get(0).contains("identifier") && this.LexArrayExpress.get(ArrayPosition).get(1).equals("equal") && this.LexArrayExpress.get(ArrayPosition).get(2).contains("Char") && this.LexArrayExpress.get(ArrayPosition).size() == 4)
		{return new VariableNode(matchAndRemove(this.LexArrayExpress.get(ArrayPosition).get(0)), true, DataType.Char, new CharNode(matchAndRemove(this.LexArrayExpress.get(ArrayPosition).get(2))));}
	else if(this.LexArrayExpress.get(ArrayPosition).get(0).contains("identifier") && this.LexArrayExpress.get(ArrayPosition).get(1).equals("equal") && this.LexArrayExpress.get(ArrayPosition).get(2).contains("Boolean") && this.LexArrayExpress.get(ArrayPosition).size() == 4)
		{return new VariableNode(matchAndRemove(this.LexArrayExpress.get(ArrayPosition).get(0)), true, DataType.Boolean, new BoolNode(matchAndRemove(this.LexArrayExpress.get(ArrayPosition).get(2))));}
	else if(this.LexArrayExpress.get(ArrayPosition).get(0).contains("identifier") && this.LexArrayExpress.get(ArrayPosition).get(1).equals("equal") && this.LexArrayExpress.get(ArrayPosition).get(2).contains("Number") && this.LexArrayExpress.get(ArrayPosition).size() == 4)
		{if(this.LexArrayExpress.get(ArrayPosition).get(2).contains("."))
			{return new VariableNode(matchAndRemove(this.LexArrayExpress.get(ArrayPosition).get(0)), true, DataType.real, new FloatNode(matchAndRemove(this.LexArrayExpress.get(ArrayPosition).get(2))));}
		else
			{return new VariableNode(matchAndRemove(this.LexArrayExpress.get(ArrayPosition).get(0)), true, DataType.integer, new IntegerNode(matchAndRemove(this.LexArrayExpress.get(ArrayPosition).get(2))));}}
	else
		{throw new InvalidCharecter("Invalid Function declaration: Constant not in correct format on line: " + (ArrayPosition + 1));}}
	
	private ArrayList<VariableNode> Variables() throws InvalidCharecter
	{int Position = 0;
	ArrayList<String> ListofIdentifiers = new ArrayList<String>();
	this.Variable = new ArrayList<VariableNode>();
	while(!this.LexArrayExpress.get(ArrayPosition).get(Position).equals("EndofLine"))
		{if(this.LexArrayExpress.get(ArrayPosition).get(Position).contains("identifier"))
			{if(!this.LexArrayExpress.get(ArrayPosition).get(Position + 1).equals("colon") && !this.LexArrayExpress.get(ArrayPosition).get(Position + 1).equals("comma"))
				{throw new InvalidCharecter("Invalid Function declaration: Missing ',' or ':' after identifier on line: " + (ArrayPosition + 1));}
		ListofIdentifiers.add(matchAndRemove(this.LexArrayExpress.get(ArrayPosition).get(Position)));}
		if(this.LexArrayExpress.get(ArrayPosition).get(Position).equals("integer") || this.LexArrayExpress.get(ArrayPosition).get(Position).equals("real"))
		{for(int Identifier = 0; Identifier < ListofIdentifiers.size();Identifier++)
			{if(this.LexArrayExpress.get(ArrayPosition).get(Position).equals("integer"))
				{Variable.add(new VariableNode(ListofIdentifiers.get(Identifier), false, DataType.integer, new IntegerNode()));}
			else
				{Variable.add(new VariableNode(ListofIdentifiers.get(Identifier), false, DataType.real, new FloatNode()));}}}
		Position++;}
	this.ArrayPosition++;
	return Variable;}
	
	//Goes until end with matching number is reached. I.e "begin (0)" matches with "end (0)"
	private ArrayList<StatementNode> BodyFuntion(ArrayList<StatementNode> Statement) throws InvalidCharecter
	{if(!this.LexArrayExpress.get(ArrayPosition).get(0).contains("begin"))
		{throw new InvalidCharecter("Invalid Function declaration: Missing 'begin' on line: " + (ArrayPosition + 1));}
	String BeginNumb = matchAndRemove(this.LexArrayExpress.get(ArrayPosition).get(0));
	this.ArrayPosition++;
	while(!this.LexArrayExpress.get(ArrayPosition).get(0).equals("end (" + BeginNumb + ")"))
	{if(this.LexArrayExpress.get(ArrayPosition).contains("elseif") || this.LexArrayExpress.get(ArrayPosition).contains("ELSE"))
		{if(!Statement.get(Statement.size()-1).toString().contains("IfNode") && !Statement.get(Statement.size()-1).toString().contains("ElseIfNode"))
			{throw new InvalidCharecter("Invalid 'ElseIf' or 'Else' Statement on line: " + (ArrayPosition + 1));}}
	//Skips if its just an end of line Token
	if(this.LexArrayExpress.get(ArrayPosition).size() < 2)
		//If the next line is the matching end of line token, do nothing because the ArrayPosition++ at the end takes care of it
		{if(this.LexArrayExpress.get(ArrayPosition + 1).get(0).equals("end (" + BeginNumb + ")"))
			{}
		else
			{this.ArrayPosition++;}}
	else
		{Statement.add(Statements());}
	this.ArrayPosition++;}
	return Statement;}
	
	private StatementNode Statements() throws InvalidCharecter
		{if(this.LexArrayExpress.get(ArrayPosition).contains("assignment"))
			{return new StatementNode(Assignments());}
		else if(this.LexArrayExpress.get(ArrayPosition).contains("FOR"))
			{return new StatementNode(For(this.LexArrayExpress.get(ArrayPosition)));}
		else if(this.LexArrayExpress.get(ArrayPosition).contains("IF"))
			{return new StatementNode(If(this.LexArrayExpress.get(ArrayPosition)));}
		else if(this.LexArrayExpress.get(ArrayPosition).contains("elseif"))
			{return new StatementNode(ElseIfNode(this.LexArrayExpress.get(ArrayPosition)));}
		else if(this.LexArrayExpress.get(ArrayPosition).contains("else"))
			{return new StatementNode(ElseNode(this.LexArrayExpress.get(ArrayPosition)));}
		else if(this.LexArrayExpress.get(ArrayPosition).contains("WHILE"))
			{return new StatementNode(While(this.LexArrayExpress.get(ArrayPosition)));}
		else if(this.LexArrayExpress.get(ArrayPosition).contains("repeat"))
			{return new StatementNode(Repeat(this.LexArrayExpress.get(ArrayPosition)));}
		else
			{return new StatementNode(FunctionCall(this.LexArrayExpress.get(ArrayPosition)));}}
	
	
	public AssignmentNode Assignments() throws InvalidCharecter
	{if(!this.LexArrayExpress.get(ArrayPosition).get(0).contains("identifier") && !this.LexArrayExpress.get(ArrayPosition).get(1).contains("assignment") || this.LexArrayExpress.get(ArrayPosition).size() < 2)
		{throw new InvalidCharecter("Invalid Assignment: Incorrect format");}
	VariableReferenceNode Name = new VariableReferenceNode(matchAndRemove(this.LexArrayExpress.get(ArrayPosition).get(0)));
	int x = 0;
	while(!this.VariableNames.get(x).equals(Name.toString()))
		{if(x == this.VariableNames.size() - 1)
			{throw new InvalidCharecter("Invalid Assignment: Assignment Name doesn't exist");}
		
		x++;}

	List<String> Variables = new ArrayList<String>();
	for(int i = 2; i < this.LexArrayExpress.get(ArrayPosition).size() - 1; i++)
		{Variables.add(this.LexArrayExpress.get(ArrayPosition).get(i));}
	Node Expression = Expression(Variables);
	return new AssignmentNode(Name, Expression);}
	
	private Node BooleanExpressionNode(ArrayList<String> BoolExpress) throws InvalidCharecter
	{int LeftExpressEnd = 0;
	while(!isBooleanComparator(BoolExpress.get(LeftExpressEnd)))
		{if(LeftExpressEnd == BoolExpress.size() - 1)
			{throw new InvalidCharecter("Missing Boolean Comparator on line " + (ArrayPosition + 1));}
		LeftExpressEnd++;}
	LeftExpressEnd++;
	ArrayList<String> LeftExpression = new ArrayList<String>(BoolExpress.subList(0, LeftExpressEnd - 1));
	ArrayList<String> RightExpression = new ArrayList<String>(BoolExpress.subList(LeftExpressEnd, BoolExpress.size()));
	for (int i = 0; i < RightExpression.size(); i++)
		{if(isBooleanComparator(RightExpression.get(i)))
			{throw new InvalidCharecter("Multiple Comparators");}}
	switch(BoolExpress.get(LeftExpressEnd-1))
		{case "GreaterThan":
			ComparatorStr = ">";
			break;
		case "LessThan":
			ComparatorStr = "<";
			break;
		case "GreaterOrEqual":
			ComparatorStr = ">=";
			break;
		case "LessOrEqual":
			ComparatorStr = "<=";
			break;
		case "NotEqual":
			ComparatorStr = "<>";
			break;
		case "equal":
			ComparatorStr = "==";
			break;}
	this.AST = new MathOpNode(ComparatorStr, Expression(LeftExpression), Expression(RightExpression));
	return AST;}
	
	private WhileNode While(ArrayList<String> WhileString) throws InvalidCharecter
	{if(!WhileString.get(0).equals("WHILE") || WhileString.size() < 5)
		{throw new InvalidCharecter("Invalid 'While' Statement on line: " + (ArrayPosition + 1));}
	ArrayList<String> BoolExpress = new ArrayList<String>(WhileString.subList(1, WhileString.size() - 1));
	WhileNode While = new WhileNode(BooleanExpressionNode(BoolExpress));
	ArrayList<StatementNode> WhileStatements = new ArrayList<StatementNode>();
	this.ArrayPosition++;
	While.addStatements(BodyFuntion(WhileStatements));
	return While;}
	
	private RepeatNode Repeat(ArrayList<String> RepeatString) throws InvalidCharecter
	{if(!RepeatString.get(0).equals("repeat") || RepeatString.size() != 2)
			{throw new InvalidCharecter("Invalid 'Repeat' Statement on line: " + (ArrayPosition + 1));}
	ArrayList<StatementNode> RepeatStatements = new ArrayList<StatementNode>();
	this.ArrayPosition++;
	BodyFuntion(RepeatStatements);
	if(!this.LexArrayExpress.get(ArrayPosition + 1).get(0).equals("until"))
		{throw new InvalidCharecter("Invalid Repeat Statement: Invalid 'until' on line: " + (ArrayPosition + 1));}
	this.ArrayPosition++;
	ArrayList<String> BoolExpress = new ArrayList<String>(this.LexArrayExpress.get(ArrayPosition).subList(1, this.LexArrayExpress.get(ArrayPosition).size()));
	RepeatNode Repeat = new RepeatNode(BooleanExpressionNode(BoolExpress));
	Repeat.addStatements(RepeatStatements);
	return Repeat;}
	
	private ForNode For(ArrayList<String> ForString) throws InvalidCharecter
	{if(!ForString.get(0).equals("FOR") && !ForString.get(1).contains("identifier") && !ForString.get(2).equals("from") && !ForString.get(3).contains("Number") && !ForString.get(4).equals("to") && !ForString.get(5).contains("Number"))
		{throw new InvalidCharecter("Invalid 'For' Statement on line: " + (ArrayPosition + 1));}
	ArrayList<StatementNode> ForStatements = new ArrayList<StatementNode>();
	ForNode For = new ForNode(new VariableReferenceNode(matchAndRemove(ForString.get(1))), new IntegerNode (matchAndRemove(ForString.get(3))), new IntegerNode (matchAndRemove((ForString.get(5)))));
	this.ArrayPosition++;
	For.addStatements(BodyFuntion(ForStatements));
	return For;}
	
	private Node If(ArrayList<String> If) throws InvalidCharecter
	{if(!If.get(0).equals("IF") || If.size() < 5)
		{throw new InvalidCharecter("Invalid 'If' Statement on line: " + (ArrayPosition + 1));}
	ArrayList<StatementNode> IFStatements = new ArrayList<StatementNode>();
	ArrayList<String> BoolExpress = new ArrayList<String>(If.subList(1, If.size() - 1));
	IfNode IF = new IfNode(BooleanExpressionNode(BoolExpress));
	this.ArrayPosition++;
	IF.addStatements(BodyFuntion(IFStatements));
	this.HeadIf = IF;
	IF.AppendNode(IF);
	return IF;}
	
	private Node ElseIfNode(ArrayList<String> ElseIf) throws InvalidCharecter
	{if(!ElseIf.get(0).equals("elseif") || ElseIf.size() < 5)
		{throw new InvalidCharecter("Invalid 'ElseIf' Statement on line: " + (ArrayPosition + 1));}
	ArrayList<StatementNode> ElseIFStatements = new ArrayList<StatementNode>();
	ArrayList<String> BoolExpress = new ArrayList<String>(ElseIf.subList(1, ElseIf.size() - 1));
	ElseIfNode ElseIF = new ElseIfNode(BooleanExpressionNode(BoolExpress), this.HeadIf);
	this.ArrayPosition++;
	ElseIF.addStatements(BodyFuntion(ElseIFStatements));
	this.HeadIf.AppendNode(ElseIF);
	return ElseIF;}
	
	private Node ElseNode(ArrayList<String> Else) throws InvalidCharecter
	{if(!Else.get(0).equals("else") || Else.size() > 2)
		{throw new InvalidCharecter("Invalid 'Else' Statement on line: " + (ArrayPosition + 1));}
	ArrayList<StatementNode> ElseStatements = new ArrayList<StatementNode>();
	ElseNode ELSE = new ElseNode(this.HeadIf);
	this.ArrayPosition++;
	ELSE.addStatements(BodyFuntion(ElseStatements));
	this.HeadIf.AppendNode(ELSE);
	for(int i = 1; i < this.HeadIf.getList().size() - 1; i++)
		{if(this.HeadIf.getList().get(i).toString().contains("ElseNode"))
			{throw new InvalidCharecter("Invalid 'Else' Statement: Multiple Else Statements linked to one if statement" );}}
	return ELSE;}
	
	private Node FunctionCall(ArrayList<String> Function) throws InvalidCharecter
	{if(!Function.get(0).contains("identifier"))
		{throw new InvalidCharecter("Invalid Function call name on line: " + (ArrayPosition + 1));}
	FunctionCallNode FunctionCall = new FunctionCallNode(Function.get(0));
	int i = 1;
	while(!Function.get(i).equals("EndofLine"))
		{if(Function.get(i).contains("identifier") &&  !Function.get(i + 1).equals("comma") && !Function.get(i + 1).equals("EndofLine") || Function.get(i).contains("Number") &&  !Function.get(i + 1).equals("comma") && !Function.get(i + 1).equals("EndofLine"))
			{throw new InvalidCharecter("Invalid Function call name on line: " + (ArrayPosition + 1) + " Parameters aren't in the correct format");}
		if(Function.get(i).contains("identifier"))
			{FunctionCall.Parameters.add(new VariableReferenceNode(matchAndRemove(Function.get(i))));}
		else if(Function.get(i).contains("Number"))
			{if(Function.get(i).contains("."))
				{FunctionCall.Parameters.add(new FloatNode(matchAndRemove(Function.get(i))));}
				
			else
				{FunctionCall.Parameters.add(new IntegerNode(matchAndRemove(Function.get(i))));}}
		i++;}
	return FunctionCall;}

	private Boolean isBooleanComparator(String Comparator)
	{switch(Comparator)
		{case "GreaterThan": case "LessThan":case "GreaterOrEqual": case "LessOrEqual":case "NotEqual": case "equal":
			return true;
		default:
			return false;}}
	

	
	
	
}


	


