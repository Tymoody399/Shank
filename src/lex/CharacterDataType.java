package lex;

public class CharacterDataType extends InterpreterDataType{
private char Character;

	
	public CharacterDataType(char Char)
	{this.Character = Char;}
	
	public String toString() {
		return String.valueOf(Character);}

	public void FromString(String input) {
		this.Character = input.charAt(0);}

}
