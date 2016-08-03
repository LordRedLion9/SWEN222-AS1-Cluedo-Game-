package ClueGame.Data;

public class Character implements Clue{

private CharName name;
	
	public enum CharName{
		Miss_Scarlet,
		Colonel_Mustard,
		Mrs_White,
		The_Reverend_Green,
		Mrs_Peacock,
		Professor_Plum
	}
	
	public Character(CharName name){
		this.name = name;
	}
	
	public String getType(){
		return name.name();
	}
}
