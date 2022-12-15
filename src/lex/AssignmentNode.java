package lex;

public class AssignmentNode extends StatementNode{
	
	VariableReferenceNode Name;
	Node Expression;
	
	public AssignmentNode(VariableReferenceNode Name, Node Expression)
		{this.Name = Name;
		this.Expression = Expression;}
	
	public VariableReferenceNode getName()
	{return this.Name;}
	
	private Node getExpression()
	{return this.Expression;}
	
	public String toString()
		{return "AssignmentName: " + getName() + ", Expression: " + getExpression();}
}
