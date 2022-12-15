package lex;

import java.util.ArrayList;

public class SemanticAnalysis {
	private ArrayList<FunctionASTNode> AstTree;
	
	SemanticAnalysis(Parser AstTree) throws InvalidCharecter{
		this.AstTree = AstTree.Parse();
		for(int i = 0; i < this.AstTree.size(); i++)
			{CheckAssignments(this.AstTree.get(i).getStatements(), this.AstTree.get(i));}}
	
	public void CheckAssignments(ArrayList<StatementNode> Statements, FunctionASTNode Function) throws InvalidCharecter {
		for(int i = 0; i < Statements.size(); i++)
		{if(Statements.get(i).toString().contains("IfNode"))
			{var IfNode = (IfNode)Statements.get(i).getData();
			CheckAssignments(IfNode.Statements, Function);}
		else if(Statements.get(i).toString().contains("ElseIfNode"))
			{var ElseIfNode = (ElseIfNode)Statements.get(i).getData();
			CheckAssignments(ElseIfNode.Statements, Function);}
		else if(Statements.get(i).toString().contains("ElseNode"))
			{var ElseNode = (ElseNode)Statements.get(i).getData();
			CheckAssignments(ElseNode.Statements, Function);}
		else if(Statements.get(i).toString().contains("WhileNode"))
			{var WhileNode = (WhileNode)Statements.get(i).getData();
			CheckAssignments(WhileNode.Statements, Function);}
		else if(Statements.get(i).toString().contains("RepeatNode"))
			{var RepeatNode = (RepeatNode)Statements.get(i).getData();
			CheckAssignments(RepeatNode.Statements, Function);}
		else if(Statements.get(i).toString().contains("ForNode"))
			{var ForNode = (ForNode)Statements.get(i).getData();
			CheckAssignments(ForNode.Statements, Function);}
		else if(Statements.get(i).toString().contains("Assignment"))
			{var Assignment = (AssignmentNode)Statements.get(i).getData();
			String Name  = Assignment.getName().toString();
			var Variable = SearchAssignmentName(Name, Function);
			var Value = Variable.getNodeAST();
			if(Assignment.Expression.toString().contains("MathOp"))
				{if(Value.toString().contains("String") && !Assignment.Expression.toString().contains("String"))
					{throw new InvalidCharecter("Invalid Assignment Statment in Function: " + Function.getName() +" -- " + "Variable " + Name + " must contain atleast one String data type");}
				else if(Value.toString().contains("Char"))
					{throw new InvalidCharecter("Invalid Assignment Statment in Function: " + Function.getName() +" -- " + "Variable " + Name + " instantiated char values cannot have math operators");}
				TraverseMathOp((MathOpNode) Assignment.Expression, Variable, Function);}
			else if(Value.toString().contains("Float"))
				{if(!Assignment.Expression.toString().contains("Float"))
						{throw new InvalidCharecter("Invalid Assignment Statment in Function: " + Function.getName() +" -- " + "Variable " + Name + " must contain Float data type");}}
			else if(Value.toString().contains("Int"))
				{if(!Assignment.Expression.toString().contains("Int"))
						{throw new InvalidCharecter("Invalid Assignment Statment in Function: " + Function.getName() +" -- " + "Variable " + Name + " must contain Int data type");}}
			else if(Value.toString().contains("String"))
					{if(!Assignment.Expression.toString().contains("String"))
						{throw new InvalidCharecter("Invalid Assignment Statment in Function: " + Function.getName() +" -- " + "Variable " + Name + " must contain String data type");}}
			else
				{if(!Assignment.Expression.toString().contains("Char"))
					{throw new InvalidCharecter("Invalid Assignment Statment in Function: " + Function.getName() +" -- " + "Variable " + Name + " must contain Char data type");}}}}}
	
	private void TraverseMathOp(MathOpNode MathOp, VariableNode Variable,  FunctionASTNode Function) throws InvalidCharecter
	{var Name = Variable.getName();
	var DataType = Variable.getNodeAST();
	//Float
	if(DataType.toString().contains("Float"))
		{if(!MathOp.getRightChild().toString().contains("Float"))
			{throw new InvalidCharecter("Invalid Assignment Statment in Function: " + Function.getName() +" -- " + "Variable " + Name + " -- Expression should only be Float data type");}
		if(MathOp.getLeftChild().toString().contains("MathOp"))
			{TraverseMathOp((MathOpNode) MathOp.getLeftChild(), Variable, Function);}
		else
			{if(!MathOp.getLeftChild().toString().contains("Float"))
				{throw new InvalidCharecter("Invalid Assignment Statment in Function: " + Function.getName() +" -- " + "Variable " + Name + " -- Expression should only be Float data type");}}}
	//Int
	else if(DataType.toString().contains("Int"))
		{if(!MathOp.getRightChild().toString().contains("Int"))
			{throw new InvalidCharecter("Invalid Assignment Statment in Function: " + Function.getName() +" -- " + "Variable " + Name + " -- Expression should only be Int data type");}
		if(MathOp.getLeftChild().toString().contains("MathOp"))
			{TraverseMathOp((MathOpNode) MathOp.getLeftChild(), Variable, Function);}
		else
			{if(!MathOp.getLeftChild().toString().contains("Int"))
				{throw new InvalidCharecter("Invalid Assignment Statment in Function: " + Function.getName() +" -- " + "Variable " + Name + " -- Expression should only be Int data type");}}}
	//String
	else if(DataType.toString().contains("String"))
		{if(!MathOp.getOperator().equals("+"))
			{throw new InvalidCharecter("Invalid Assignment Statment in Function: " + Function.getName() +" -- " + "Variable " + Name + " -- Expression should only contain '+' operator");}
		if(!MathOp.getRightChild().toString().contains("String") && !MathOp.getRightChild().toString().contains("Char"))
			{throw new InvalidCharecter("Invalid Assignment Statment in Function: " + Function.getName() +" -- " + "Variable " + Name + " -- Expression should only contain a String or Charecter data Type");}
		if(MathOp.getLeftChild().toString().contains("MathOp"))
			{TraverseMathOp((MathOpNode) MathOp.getLeftChild(), Variable, Function);}
		else
			{if(!MathOp.getLeftChild().toString().contains("String") && !MathOp.getLeftChild().toString().contains("Char"))
				{throw new InvalidCharecter("Invalid Assignment Statment in Function: " + Function.getName() +" -- " + "Variable " + Name + " -- Expression should only contain a String or Charecter data Type");}}}}
	
	
	
	private VariableNode SearchAssignmentName(String Name, FunctionASTNode Function)
		{int Position = 0;
		while(Position < Function.Parameters.size())
			{if(Function.Parameters.get(Position).getName().equals(Name))
				{return Function.Parameters.get(Position);}
			Position++;}
		Position = 0;
		while(Position < Function.Local.size())
		{if(Function.Local.get(Position).getName().equals(Name))
			{return Function.Local.get(Position);}
		Position++;}
		return null;}
	
	
}
