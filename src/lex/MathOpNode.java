package lex;

public class MathOpNode extends Node{

	private Node LeftChild, RightChild;
	private String Operator;

	
	public MathOpNode(String Operator,Node LeftChild, Node RightChild)
		{this.Operator = Operator;
		this.LeftChild = LeftChild;
		this.RightChild = RightChild;}
	

	public String getOperator()
		{return this.Operator;}
	
	public Node getLeftChild()
		{return this.LeftChild;}
	
	public void setLeftChild(Node LeftChild)
		{this.LeftChild = LeftChild;}
	
	public Node getRightChild()
		{return this.RightChild;}
	
	//Output for the token's string
	public String toString()
		{return "MathOp(" + getOperator() + "," + getLeftChild() + "," + getRightChild() + ")";}
}
