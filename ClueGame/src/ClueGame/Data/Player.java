package ClueGame.Data;
import ClueGame.Data.Character;

public class Player {
	
	private int playerNo;
	private Character playerChar;
	private Coordinate position;
	
	public Player(int playerNo){
		this.playerNo = playerNo;
		position = new Coordinate(0,0);
	}
	
	public int getNumber(){
		return playerNo;
	}
	
	public Character getCharacter(){
		return playerChar;
	}
	
	public Coordinate getPosition(){
		return position;
	}
	
	public void setPosition(Coordinate cord){
		position = cord;
	}
}
