package lex;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
public class Shank {
	/*
	 * MESSAGE: Hello User this is Shank. Using the data.txt file you could write based on the language definitions provided. Although is has
	 * a couple of small bugs you should still find the program usable.
	 */
	public static void main(String[] args) throws InvalidCharecter, IOException {

		ArrayList<ArrayList<String>> Lexer = new ArrayList<ArrayList<String>>();
		
		
		//ReadAllLines in data.txt file
		Path path = Paths.get("src/lex/data.txt");
		List<String> lines = Files.readAllLines(path, StandardCharsets.UTF_8);
		lines.forEach(line -> {
			try {Lexer L = new Lexer(line);
			Lexer.add(L.getOutput());} 
			catch (InvalidCharecter e) 
			{e.printStackTrace();}});
		
	
		
		Parser Parser = new Parser(Lexer);
		var SemanticAnalysis= new SemanticAnalysis(Parser);
		Interpreter Interpreter = new Interpreter(Parser);
		
		for(int i = 0; i < Parser.Parse().size(); i++)
			{if(!Interpreter.FunctionMap.containsKey(Parser.Parse().get(i).getName()))
				{Interpreter.FunctionMap.put(Parser.Parse().get(i).getName(), new FunctionNode(Parser.Parse().get(i)));}}
		
		
		//System.out.println(Lexer);
		//System.out.println(Parser.Parse());
		lex.Interpreter.InterpretFunction((FunctionNode) Interpreter.FunctionMap.get("Start")); 
		System.out.println(Interpreter.FunctionMap.get("Start"));

	}}


	
		
