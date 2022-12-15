package lex;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;



public class Interpreter {
	
	private ArrayList<FunctionASTNode> AstTree;
	HashMap<String, CallableNode> FunctionMap = new HashMap<String, CallableNode>();
	private static InterpreterDataType LeftChildValue, RightChildValue;
	private static int LinkPosition;
	
	
	public Interpreter(Parser AstTree) throws InvalidCharecter
	{this.AstTree = AstTree.Parse();}
	
	//Function used to create interpreter data types based on the provided nodes
	public static void InterpretFunction(FunctionNode Function) throws InvalidCharecter
	{var Parameters = new ArrayList<InterpreterDataType>();
	var Local = new ArrayList<InterpreterDataType>();
	HashMap<String, InterpreterDataType> FunctionDataTypes = new HashMap<>();
	//Parameters
	for(int i = 0; i < Function.Parameters.size(); i++)
		{if(Function.Parameters.get(i).getConstant() == true)
			{if(Function.Parameters.get(i).getDataType().contains("String"))
				{StringNode String = (StringNode) Function.Parameters.get(i).getNodeAST();
				Parameters.add(new StringDataType(String.getStringNode()));}
			else if(Function.Parameters.get(i).getDataType().contains("Char"))	
				{CharNode Char = (CharNode) Function.Parameters.get(i).getNodeAST();
				Parameters.add(new CharacterDataType(Char.getCharNode()));}
			else if(Function.Parameters.get(i).getDataType().contains("Boolean"))
				{BoolNode Boolean = (BoolNode) Function.Parameters.get(i).getNodeAST();
				Parameters.add(new BooleanDataType(Boolean.getBoolNode()));}
			else if(Function.Parameters.get(i).getDataType().contains("int"))
				{IntegerNode Int = (IntegerNode) Function.Parameters.get(i).getNodeAST();
				Parameters.add(new IntDataType(Int.getIntegerNode()));}
			else
				{FloatNode Float = (FloatNode) Function.Parameters.get(i).getNodeAST();
				Parameters.add(new FloatDataType(Float.getFloatNode()));}}
		else
			{if(Function.Parameters.get(i).getDataType().contains("String"))
				{StringNode String = (StringNode) Function.Parameters.get(i).getNodeAST();
				Parameters.add(new StringDataType(String.getStringNode()));}
			else if(Function.Parameters.get(i).getDataType().contains("Boolean"))
				{BoolNode Boolean = (BoolNode) Function.Parameters.get(i).getNodeAST();
				Parameters.add(new BooleanDataType(Boolean.getBoolNode()));}
			else if(Function.Parameters.get(i).getDataType().contains("int"))
				{IntegerNode Int = (IntegerNode) Function.Parameters.get(i).getNodeAST();
				Parameters.add(new IntDataType(Int.getIntegerNode()));}
			else
				{FloatNode Float = (FloatNode) Function.Parameters.get(i).getNodeAST();
				Parameters.add(new FloatDataType(Float.getFloatNode()));}}
		FunctionDataTypes.put(Function.Parameters.get(i).getName(), Parameters.get(i));}
	//Local Variables
	for(int i = 0; i < Function.Local.size(); i++)
		{if(Function.Local.get(i).getConstant() == true)
			{if(Function.Local.get(i).getDataType().contains("String"))
				{StringNode String = (StringNode) Function.Local.get(i).getNodeAST();
				Local.add(new StringDataType(String.getStringNode()));}
			else if(Function.Local.get(i).getDataType().contains("Char"))	
				{CharNode Char = (CharNode) Function.Local.get(i).getNodeAST();
				Local.add(new CharacterDataType(Char.getCharNode()));}
			else if(Function.Local.get(i).getDataType().contains("Boolean"))
				{BoolNode Boolean = (BoolNode) Function.Local.get(i).getNodeAST();
				Local.add(new BooleanDataType(Boolean.getBoolNode()));}
			else if(Function.Local.get(i).getDataType().contains("int"))
				{IntegerNode Int = (IntegerNode) Function.Local.get(i).getNodeAST();
				Local.add(new IntDataType(Int.getIntegerNode()));}
			else
				{FloatNode Float = (FloatNode) Function.Local.get(i).getNodeAST();
				Local.add(new FloatDataType(Float.getFloatNode()));}}
		else
			{if(Function.Local.get(i).getDataType().contains("String"))
				{StringNode String = (StringNode) Function.Local.get(i).getNodeAST();
				Local.add(new StringDataType(String.getStringNode()));}
			else if(Function.Local.get(i).getDataType().contains("Boolean"))
				{BoolNode Boolean = (BoolNode) Function.Local.get(i).getNodeAST();
				Local.add(new BooleanDataType(Boolean.getBoolNode()));}
			else if(Function.Local.get(i).getDataType().contains("int"))
				{IntegerNode Int = (IntegerNode) Function.Local.get(i).getNodeAST();
				Local.add(new IntDataType(Int.getIntegerNode()));}
			else
				{FloatNode Float = (FloatNode) Function.Local.get(i).getNodeAST();
				Local.add(new FloatDataType(Float.getFloatNode()));}}
		FunctionDataTypes.put(Function.Local.get(i).getName(), Local.get(i));}
	InterpretBlock(Function.Statement, FunctionDataTypes);}
	
	
	//Solves the provided statement nodes
	private static void InterpretBlock(ArrayList<StatementNode> Statements, HashMap<String, InterpreterDataType> FunctionMap) throws InvalidCharecter {
		for(int i = 0; i < Statements.size(); i++)
			//FunctionCallNode
			{if(Statements.get(i).toString().startsWith("FunctionCallName"))
				{FunctionCallNode FunctionCall = (FunctionCallNode)Statements.get(i).getData();
				//RealToInteger
				if(FunctionCall.getName().equals("RealToInteger"))
					{if(FunctionCall.getParameters().size() != 2 )
						{throw new InvalidCharecter("Invalid StatementNode '" +  Statements.get(i).toString() + " :Provide two parameter");}
					else if(!FunctionCall.getParameters().get(0).toString().contains(".") || isWord(FunctionCall.getParameters().get(0).toString()))
						{throw new InvalidCharecter("Invalid StatementNode '" +  Statements.get(i).toString() + " :Provide a float data type");}
					else if(!FunctionCall.getParameters().get(1).toString().startsWith("var"))
						{throw new InvalidCharecter("Invalid StatementNode '" +  Statements.get(i).toString() + " :The second parameter should start with 'var'");}
					var IntValue = new RealToInteger((FloatNode)FunctionCall.getParameters().get(0));
					FunctionMap.put(FunctionCall.getParameters().get(1).toString(), IntValue.Execute());}
				//IntegerToReal
				else if(FunctionCall.getName().equals("IntegerToReal"))
					{if(FunctionCall.getParameters().size() != 2 )
						{throw new InvalidCharecter("Invalid StatementNode '" +  Statements.get(i).toString() + " :Provide two parameter");}
					else if(FunctionCall.getParameters().get(0).toString().contains(".") || isWord(FunctionCall.getParameters().get(0).toString()))
						{throw new InvalidCharecter("Invalid StatementNode '" +  Statements.get(i).toString() + " :Provide a float data type");}
					else if(!FunctionCall.getParameters().get(1).toString().startsWith("var"))
						{throw new InvalidCharecter("Invalid StatementNode '" +  Statements.get(i).toString() + " :The second parameter should start with 'var'");}
					var FloatValue = new IntegerToReal((IntegerNode)FunctionCall.getParameters().get(0));
					FunctionMap.put(FunctionCall.getParameters().get(1).toString(), FloatValue.Execute());}
				//Read
				else if(FunctionCall.getName().equals("Read"))
					{var Parameters = new ArrayList<InterpreterDataType>();
					if(FunctionCall.getParameters().size() == 0)
						{throw new InvalidCharecter("Invalid StatementNode '" +  Statements.get(i).toString() + " :No parameters to read from");}
					for(int Position = 0; Position < FunctionCall.getParameters().size(); Position++)
						{if(!FunctionMap.containsKey(FunctionCall.getParameters().get(Position).toString()))
							{throw new InvalidCharecter("Invalid StatementNode '" +  Statements.get(i).toString() + " :Invalid parameter");}
						Parameters.add(FunctionMap.get(FunctionCall.getParameters().get(Position).toString()));}
					var Read = new Read(Parameters);
					Read.Execute();
					FunctionMap.put("Read(" +Read.getExpression() + ")", null);}
							
				//Write
				else if(FunctionCall.getName().equals("Write"))
					{var Parameters = new ArrayList<InterpreterDataType>();
					if(FunctionCall.getParameters().size() == 0)
						{throw new InvalidCharecter("Invalid StatementNode '" +  Statements.get(i).toString() + " :No parameters to write from");}
					for(int Position = 0; Position < FunctionCall.getParameters().size(); Position++)
						{if(!FunctionMap.containsKey(FunctionCall.getParameters().get(Position).toString()))
							{throw new InvalidCharecter("Invalid StatementNode '" +  Statements.get(i).toString() + " :Invalid parameter");}
						Parameters.add(FunctionMap.get(FunctionCall.getParameters().get(Position).toString()));}
					var Write = new Write(Parameters);
					System.out.println(Write.Execute());}
				//SquareRoot
				else if(FunctionCall.getName().equals("SquareRoot"))
					{if(FunctionCall.getParameters().size() != 2 )
					{throw new InvalidCharecter("Invalid StatementNode '" +  Statements.get(i).toString() + " :Provide two parameter");}
				else if(!FunctionCall.getParameters().get(0).toString().contains(".") || isWord(FunctionCall.getParameters().get(0).toString()))
					{throw new InvalidCharecter("Invalid StatementNode '" +  Statements.get(i).toString() + " :Provide a float data type");}
				else if(!FunctionCall.getParameters().get(1).toString().startsWith("var"))
					{throw new InvalidCharecter("Invalid StatementNode '" +  Statements.get(i).toString() + " :The second parameter should start with 'var'");}
				var FloatValue = new SquareRoot((FloatNode)FunctionCall.getParameters().get(0));
				FunctionMap.put(FunctionCall.getParameters().get(1).toString(), FloatValue.Execute());}
				//GetRandom
				else if(FunctionCall.getName().equals("GetRandom"))
					{if(FunctionCall.getParameters().size() != 1 )
					{throw new InvalidCharecter("Invalid StatementNode '" +  Statements.get(i).toString() + " :Provide one parameter");}
				else if(FunctionCall.getParameters().get(0).toString().contains(".") || isWord(FunctionCall.getParameters().get(0).toString()))
					{throw new InvalidCharecter("Invalid StatementNode '" +  Statements.get(i).toString() + " :Provide a float data type");}
				else if(!FunctionCall.getParameters().get(1).toString().startsWith("var"))
					{throw new InvalidCharecter("Invalid StatementNode '" +  Statements.get(i).toString() + " :The second parameter should start with 'var'");}
				var IntValue = new GetRandom();
				FunctionMap.put(FunctionCall.getParameters().get(1).toString(), IntValue.Execute());}
				//FirstChar
				else if(FunctionCall.getName().equals("FirstChar"))
					{if(FunctionCall.getParameters().size() != 2 )
						{throw new InvalidCharecter("Invalid StatementNode '" +  Statements.get(i).toString() + " :Provide two parameter");}
					else if(FunctionCall.getParameters().get(0).toString().length() == 0)
						{throw new InvalidCharecter("Invalid StatementNode '" +  Statements.get(i).toString() + " :Provide a String data type that's not empty");}
					else if(!FunctionCall.getParameters().get(1).toString().startsWith("var"))
						{throw new InvalidCharecter("Invalid StatementNode '" +  Statements.get(i).toString() + " :The second parameter should start with 'var'");}
					var CharValue = new FirstChar((StringNode)FunctionCall.getParameters().get(0));
					FunctionMap.put(FunctionCall.getParameters().get(1).toString(), CharValue.Execute());}
				//LastChar
				else if(FunctionCall.getName().equals("LastChar"))
				{if(FunctionCall.getParameters().size() != 2 )
					{throw new InvalidCharecter("Invalid StatementNode '" +  Statements.get(i).toString() + " :Provide two parameter");}
				else if(FunctionCall.getParameters().get(0).toString().length() == 0)
					{throw new InvalidCharecter("Invalid StatementNode '" +  Statements.get(i).toString() + " :Provide a String data type that's not empty");}
				else if(!FunctionCall.getParameters().get(1).toString().startsWith("var"))
					{throw new InvalidCharecter("Invalid StatementNode '" +  Statements.get(i).toString() + " :The second parameter should start with 'var'");}
				var CharValue = new LastChar((StringNode)FunctionCall.getParameters().get(0));
				FunctionMap.put(FunctionCall.getParameters().get(1).toString(), CharValue.Execute());}
				//Substring
				else if(FunctionCall.getName().equals("Substring"))
				{if(FunctionCall.getParameters().size() != 4 )
					{throw new InvalidCharecter("Invalid StatementNode '" +  Statements.get(i).toString() + " :Provide two parameter");}
				else if(FunctionCall.getParameters().get(0).toString().length() == 0)
					{throw new InvalidCharecter("Invalid StatementNode '" +  Statements.get(i).toString() + " :Provide a String data type that's not empty");}
				else if(!FunctionCall.getParameters().get(3).toString().startsWith("var"))
					{throw new InvalidCharecter("Invalid StatementNode '" +  Statements.get(i).toString() + " :The second parameter should start with 'var'");}
				else if(FunctionCall.getParameters().get(1).toString().contains(".") || isWord(FunctionCall.getParameters().get(1).toString()) || FunctionCall.getParameters().get(2).toString().contains(".") || isWord(FunctionCall.getParameters().get(2).toString()))
					{throw new InvalidCharecter("Invalid StatementNode '" +  Statements.get(i).toString() + " :Index and length parameters should only be integers");}
				var index = (IntegerNode)FunctionCall.getParameters().get(1);
				var Length = (IntegerNode)FunctionCall.getParameters().get(2);
				if(index.getIntegerNode() >= Length.getIntegerNode() || index.getIntegerNode() < 0)
					{throw new InvalidCharecter("Invalid StatementNode '" +  Statements.get(i).toString() + " :Invalid index parameter");}
				else if(Length.getIntegerNode() > FunctionCall.getParameters().get(0).toString().length())
					{throw new InvalidCharecter("Invalid StatementNode '" +  Statements.get(i).toString() + " :Invalid Length parameter");}
				var SubStringParameters = new ArrayList<InterpreterDataType>();
				SubStringParameters.add(new StringDataType(FunctionCall.getParameters().get(0).toString()));
				SubStringParameters.add(new IntDataType(index.getIntegerNode()));
				SubStringParameters.add(new IntDataType(Length.getIntegerNode()));
				var StringValue = new Substring(SubStringParameters);
				FunctionMap.put(FunctionCall.getParameters().get(3).toString(), StringValue.Execute());}
				//User Created Function
				else
					{}
				
				}
			//IFNode, ElseIfNode, and ElseNode
			else if(Statements.get(i).toString().startsWith("IfNode"))
				{IfNode If = (IfNode)Statements.get(i).getData();
				var Link = If.getList();
				i = i + Link.size() - 1;
				if(If.getCondition().toString().contains("Boolean:"))
					{var ResolvedBoolean = ResolveBoolean(If.getCondition(), FunctionMap);
					if(ResolvedBoolean.getBoolean() == true)
						{InterpretBlock(If.Statements,FunctionMap);}}
				else if(EvaluateBooleanExpression(If.getCondition(), FunctionMap) == true)
					{InterpretBlock(If.Statements,FunctionMap);}
				else
					{if(Link.size() > 1)
						LinkPosition = 1;
						{while(LinkPosition < Link.size() - 1)
							{if(Link.get(LinkPosition).toString().contains("ElseNode"))
								{ElseNode Else = (ElseNode)Link.get(i);
								InterpretBlock(Else.Statements,FunctionMap);}
							else
								{ElseIfNode ElseIf = (ElseIfNode)Link.get(i);
								if(ElseIf.getCondition().toString().contains("Boolean:"))
									{var ResolvedBoolean = ResolveBoolean(ElseIf.getCondition(), FunctionMap);
									if(ResolvedBoolean.getBoolean() == true)
										{InterpretBlock(ElseIf.Statements,FunctionMap);}}
								else if(EvaluateBooleanExpression(ElseIf.getCondition(), FunctionMap) == true)
									{InterpretBlock(ElseIf.Statements,FunctionMap);
									break;}}
							LinkPosition++;}}}}
			//WhileNode
			else if(Statements.get(i).toString().startsWith("WhileNode"))
				{WhileNode While = (WhileNode)Statements.get(i).getData();
				if(While.getCondition().toString().contains("Boolean:"))
					{var ResolvedBoolean = ResolveBoolean(While.getCondition(), FunctionMap);
					while(ResolvedBoolean.getBoolean() != true)
						{InterpretBlock(While.Statements,FunctionMap);}}
				else if(EvaluateBooleanExpression(While.getCondition(), FunctionMap) == true)
					{while(EvaluateBooleanExpression(While.getCondition(), FunctionMap) != false)
						{InterpretBlock(While.Statements,FunctionMap);}}}
			//AssignmentNode
			else if(Statements.get(i).toString().startsWith("Assignment"))
				{AssignmentNode Assignment = (AssignmentNode)Statements.get(i).getData();
				if(!FunctionMap.containsKey(Assignment.getName().toString()))
					{throw new InvalidCharecter("Invalid Assignment " +  Statements.get(i).toString() + " Identifier hasn't been initialized");}
				if(Assignment.toString().contains("String:"))
					{FunctionMap.replace(Assignment.getName().toString(), ResolveString(Assignment.Expression));}
				else
					{Boolean isInt = true;
					if(FunctionMap.get(Assignment.getName().toString()).toString().contains("."))
						{isInt = false;}
					FunctionMap.replace(Assignment.getName().toString(), ResolveNumber(Assignment.Expression, isInt));}}
			//ForNode
			else if(Statements.get(i).toString().startsWith("ForNode"))
				{ForNode For = (ForNode)Statements.get(i).getData();
				if(For.getStart() < For.getEnd())
					{for(int ForStart = For.getStart(); ForStart <= For.getEnd(); ForStart++)
						{InterpretBlock(For.Statements,FunctionMap);}}
				else
					{for(int ForEnd = For.getEnd(); ForEnd >= For.getEnd(); ForEnd--)
						{InterpretBlock(For.Statements,FunctionMap);}}}
			//RepreatNode 
			else if(Statements.get(i).toString().startsWith("RepeatNode"))
				{RepeatNode Repeat = (RepeatNode)Statements.get(i).getData();
				if(Repeat.getCondition().toString().contains("Boolean:"))
					{var ResolvedBoolean = ResolveBoolean(Repeat.getCondition(), FunctionMap);
					while(ResolvedBoolean.getBoolean() != true)
						{InterpretBlock(Repeat.Statements,FunctionMap);}}
				if(EvaluateBooleanExpression(Repeat.getCondition(), FunctionMap) == true)
				{do
					{InterpretBlock(Repeat.Statements,FunctionMap);} 
				while(EvaluateBooleanExpression(Repeat.getCondition(), FunctionMap) != false);}}
			else
				{throw new InvalidCharecter("Invalid StatementNode '" +  Statements.get(i).toString());}}}

	//Resolves numeric boolean expressions
	private static Boolean EvaluateBooleanExpression(Node Condition,HashMap<String, InterpreterDataType> FunctionMap) throws InvalidCharecter
	{if(Condition == null)
		{return true;}
	MathOpNode BooleanExpression = (MathOpNode)Condition;
	 var Operator = BooleanExpression.getOperator();
	 var LeftChild = BooleanExpression.getLeftChild();
	 var RightChild = BooleanExpression.getRightChild();
	 if(LeftChild.toString().contains("MathOp"))
	 	{LeftChildValue = ResolveBooleanExpression(LeftChild, FunctionMap);}
	 else
		//left child is just a identifier
	 	{if(LeftChild.toString().contains("identifier"))
			{LeftChildValue = FunctionMap.get(matchAndRemove(LeftChild.toString()));}
	 	//left child is a number
	 	else
	 		//float
	 		{if(LeftChild.toString().contains("."))
	 			{FloatNode Number = (FloatNode)LeftChild; 
	 			LeftChildValue = new FloatDataType(Number.getFloatNode());}
	 		//Integer
	 		else
	 			{IntegerNode Number = (IntegerNode)LeftChild; 
	 			LeftChildValue = new IntDataType(Number.getIntegerNode());}}}
	 if(RightChild.toString().contains("MathOp"))
	 	{RightChildValue = ResolveBooleanExpression(RightChild, FunctionMap);}
	 else
		//Right child is just a identifier
	 	{if(RightChild.toString().contains("identifier"))
	 		{RightChildValue = FunctionMap.get(matchAndRemove(RightChild.toString()));}
	 	//Right child is a number
	 	else
	 		//float
	 		{if(RightChild.toString().contains("."))
	 			{FloatNode Number = (FloatNode)RightChild; 
	 			RightChildValue = new FloatDataType(Number.getFloatNode());}
	 		//Integer
	 		else
	 			{IntegerNode Number = (IntegerNode)RightChild; 
	 			RightChildValue = new IntDataType(Number.getIntegerNode());}}}
	if(LeftChildValue.toString().contains(".") && !RightChildValue.toString().contains(".") || !LeftChildValue.toString().contains(".") && RightChildValue.toString().contains("."))	
		{throw new InvalidCharecter("Invalid Boolean Expression: Opposite Data Types");}
	Boolean isInt = true;
	if(LeftChildValue.toString().contains("."))
		{isInt = false;}
	switch(Operator)
		{case "==":
			if(isInt == true)
				{if(Integer.parseInt(LeftChildValue.toString()) == Integer.parseInt(RightChildValue.toString()))
					{return true;}}
			else
				{if(Float.parseFloat(LeftChildValue.toString()) == Float.parseFloat(RightChildValue.toString()))
					{return true;}}
			break;
		case "<>":
			if(isInt == true)
				{if(Integer.parseInt(LeftChildValue.toString()) != Integer.parseInt(RightChildValue.toString()))
					{return true;}}
			else
				{if(Float.parseFloat(LeftChildValue.toString()) != Float.parseFloat(RightChildValue.toString()))
					{return true;}}
			break;
		case "<=":
			if(isInt == true)
				{if(Integer.parseInt(LeftChildValue.toString()) <= Integer.parseInt(RightChildValue.toString()))
					{return true;}}
			else
				{if(Float.parseFloat(LeftChildValue.toString()) <= Float.parseFloat(RightChildValue.toString()))
					{return true;}}
			break;
		case "<":
			if(isInt == true)
				{if(Integer.parseInt(LeftChildValue.toString()) < Integer.parseInt(RightChildValue.toString()))
					{return true;}}
			else
				{if(Float.parseFloat(LeftChildValue.toString()) < Float.parseFloat(RightChildValue.toString()))
					{return true;}}
			break;
		case ">=":
			if(isInt == true)
				{if(Integer.parseInt(LeftChildValue.toString()) >= Integer.parseInt(RightChildValue.toString()))
					{return true;}}
			else
				{if(Float.parseFloat(LeftChildValue.toString()) >= Float.parseFloat(RightChildValue.toString()))
					{return true;}}
				break;
		case ">":
			if(isInt == true)
				{if(Integer.parseInt(LeftChildValue.toString()) > Integer.parseInt(RightChildValue.toString()))
					{return true;}}
			else
				{if(Float.parseFloat(LeftChildValue.toString()) > Float.parseFloat(RightChildValue.toString()))
					{return true;}}
			break;}
	return false;}
	
	//resolves boolean expressions
	private static InterpreterDataType ResolveBooleanExpression(Node BooleanExpression,HashMap<String, InterpreterDataType> FunctionMap) throws InvalidCharecter
	{MathOpNode BooleanMathOp = (MathOpNode)BooleanExpression;
	Boolean isInt = true;
	if(BooleanMathOp.getRightChild().toString().contains("identifier"))
			{String Name = matchAndRemove(BooleanMathOp.getRightChild().toString());
			if(!FunctionMap.containsKey(Name))
				{throw new InvalidCharecter("Invalid Boolean Expression: " + Name + " is not initialized");}
			BooleanMathOp.setData(FunctionMap.get(Name));
			if(FunctionMap.get(Name).toString().contains("."))
				{isInt = false;}}
		if(BooleanMathOp.getLeftChild().toString().contains("MathOp"))
			{var NewLeftChild = ResolveBooleanExpression(BooleanMathOp.getLeftChild(), FunctionMap);
			if(NewLeftChild.toString().contains("String"))
				{BooleanMathOp.setLeftChild(new StringNode(NewLeftChild.toString()));}
			else if(NewLeftChild.toString().contains("."))
				{BooleanMathOp.setLeftChild(new FloatNode(NewLeftChild.toString()));}
			else
				{BooleanMathOp.setLeftChild(new IntegerNode(NewLeftChild.toString()));}}
		else
			{if(BooleanMathOp.getRightChild().toString().contains("identifier"))
				{String Name = matchAndRemove(BooleanMathOp.getLeftChild().toString());
				if(!FunctionMap.containsKey(Name))
					{throw new InvalidCharecter("Invalid Boolean Expression: " + Name + " is not initialized");}
				BooleanMathOp.setData(FunctionMap.get(Name));
				if(FunctionMap.get(Name).toString().contains("."))
					{isInt = false;}}}
	if(BooleanMathOp.getRightChild().toString().contains("String:"))
		{return ResolveString(BooleanMathOp);}
	else
		{return ResolveNumber(BooleanMathOp, isInt);}}

	public static InterpreterDataType ResolveNumber(Node Node, Boolean AssignmentType)
	{if(Node.toString().startsWith("Int:"))
		{IntegerNode IntValue = (IntegerNode)Node;
		return new IntDataType(IntValue.getIntegerNode());}
	else if(Node.toString().startsWith("Float:"))
		{FloatNode FloatValue = (FloatNode)Node;
		return new FloatDataType(FloatValue.getFloatNode());}
	else
		{MathOpNode MathOp = (MathOpNode)Node;
		if(AssignmentType == false)
			{float Total = 0;
			var LeftChild = (FloatNode)MathOp.getLeftChild();
			var RightChild = (FloatNode)MathOp.getRightChild();
			switch(MathOp.getOperator())
				{case "+":
					Total = LeftChild.getFloatNode() + RightChild.getFloatNode();
					break;
				case "*":
					Total = LeftChild.getFloatNode() * RightChild.getFloatNode();
					break;
				case "/":
					Total = LeftChild.getFloatNode() / RightChild.getFloatNode();
					break;
				case "-":
					Total = LeftChild.getFloatNode() - RightChild.getFloatNode();
					break;
				case "%":
					Total = LeftChild.getFloatNode() - RightChild.getFloatNode();
					break;}
		return new FloatDataType(Total);}
		else
			{int Total = 0;
			var LeftChild = (IntegerNode)MathOp.getLeftChild();
			var RightChild = (IntegerNode)MathOp.getRightChild();
			switch(MathOp.getOperator())
				{case "+":
					Total = LeftChild.getIntegerNode() + RightChild.getIntegerNode();
					break;
				case "*":
					Total =  LeftChild.getIntegerNode() * RightChild.getIntegerNode();
					break;
				case "/":
					Total =  LeftChild.getIntegerNode() / RightChild.getIntegerNode();
					break;
				case "-":
					Total =  LeftChild.getIntegerNode() - RightChild.getIntegerNode();
					break;
				case "%":
					Total =  LeftChild.getIntegerNode() - RightChild.getIntegerNode();
					break;}
			return new IntDataType(Total);}}}
	
	//Resolves Strings
	public static StringDataType ResolveString(Node Node)
	{if(Node.toString().startsWith("String:"))
		{StringNode String = (StringNode)Node;
		return new StringDataType(String.getStringNode());}
	else
		{MathOpNode MathOp = (MathOpNode)Node;
		if(MathOp.getLeftChild().toString().contains("Char:") && MathOp.getRightChild().toString().contains("Char:"))
			{var LeftChild = (CharNode)MathOp.getLeftChild();
			var RightChild = (CharNode)MathOp.getRightChild();
			return new StringDataType(String.valueOf(LeftChild.getCharNode()) + String.valueOf(RightChild.getCharNode()));}
		else if(MathOp.getRightChild().toString().contains("Char:"))
			{var LeftChild = (StringNode)MathOp.getLeftChild();
			var RightChild = (CharNode)MathOp.getRightChild();
			return new StringDataType(LeftChild.getStringNode() + String.valueOf(RightChild.getCharNode()));}
		else if(MathOp.getLeftChild().toString().contains("Char:"))
			{var LeftChild = (CharNode)MathOp.getLeftChild();
			var RightChild = (StringNode)MathOp.getRightChild();
			return new StringDataType(String.valueOf(LeftChild.getCharNode()) + RightChild.getStringNode());}
		else 
			{var LeftChild = (StringNode)MathOp.getLeftChild();
			var RightChild = (StringNode)MathOp.getRightChild();
			return new StringDataType(LeftChild.getStringNode() + RightChild.getStringNode());}}}
		
	public static BooleanDataType ResolveBoolean(Node Node, HashMap<String, InterpreterDataType> FunctionMap)
	{MathOpNode MathOp = (MathOpNode)Node;
	var LeftChild = (BooleanDataType)FunctionMap.get(matchAndRemove(MathOp.getLeftChild().toString()));
	var RightChild = (BoolNode)MathOp.getRightChild();
	switch(MathOp.getOperator())
		{case "==":
		if(LeftChild.getBoolean() == RightChild.getBoolNode())
			{return new BooleanDataType(true);}
		break;
		case "<>":
			if(LeftChild.getBoolean() != RightChild.getBoolNode())
				{return new BooleanDataType(true);}
		break;}
	return new BooleanDataType(false);
	}
	
	private static String matchAndRemove(String Expression) 
	{int identiferEnd = Expression.length() - 1;
	String identifer = Expression.substring(12, identiferEnd);
	return identifer;}
	
	private static Boolean isWord(String Str)
	{Pattern Pattern = java.util.regex.Pattern.compile(".*[a-zA-Z].*");
	Matcher matcherText = Pattern.matcher(Str);
	return matcherText.matches();}
	
}
