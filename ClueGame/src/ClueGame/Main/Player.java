package ClueGame.Main;
import ClueGame.Data.Character;

public class Player {
	
	private int playerNo;
	private Character playerChar;
	
	public Player(int playerNo){
		this.playerNo = playerNo;
	}
	
	public int getNumber(){
		return playerNo;
	}
	
	public Character getCharacter(){
		return playerChar;
	}
}
