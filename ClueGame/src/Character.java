
public class Character {

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
	
	public CharName getName(){
		return name;
	}
}
