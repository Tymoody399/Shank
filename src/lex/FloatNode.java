package lex;

public class FloatNode extends Node{


	private float DataFloat;
	
	public FloatNode()
	{DataFloat = 0;}
	
	public FloatNode(String FloatNode)
	{DataFloat = Float.parseFloat(FloatNode);}
	
	public FloatNode(float Float) {
	this.DataFloat = Float;}

	public float getFloatNode()
	{return this.DataFloat;}
	
	//Output for the token's string
		public String toString()
		{return "Float: " + getFloatNode();}
	
}
