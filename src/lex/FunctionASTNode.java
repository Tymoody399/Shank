package lex;
import java.util.ArrayList;

public class FunctionASTNode extends Node{
	private String Name;
	ArrayList<VariableNode> Parameters, Local;
	ArrayList<StatementNode> Statements;
	
	
	public FunctionASTNode(String Name) 
	{this.Name = Name;
	this.Parameters = new ArrayList<VariableNode>();
	this.Local =  new ArrayList<VariableNode>();
	this.Statements =  new ArrayList<StatementNode>();}
	
	public String getName()
	{return this.Name;}
	
	public ArrayList<VariableNode> getParameters()
	{return this.Parameters;}
	
	public ArrayList<VariableNode> getLocal()
	{return this.Local;}
	
	public ArrayList<StatementNode> getStatements()
	{return this.Statements;}
	
	public void addParameters(VariableNode Parameters)
	{this.Parameters.add(Parameters);}
	
	public void addLocal(ArrayList<VariableNode> Local) {
		this.Local.addAll(Local);}
	
	public void addStatement(ArrayList<StatementNode> Statement) {
		this.Statements.addAll(Statement);}
	
	public String toString()
	{return "\nFunctionName: " + getName() + "\nParameters: " + getParameters() + "\nLocal variables: " + getLocal() + 
			"\nStatements: " +  getStatements() + "\n";}

	
		
	
}
