package ClueGame.Data;
import ClueGame.Data.Character;
import ClueGame.Data.Location.LocName;

public class Player {
	
	private int playerNo;
	private Character playerChar;
	private Coordinate position;
	private LocName currentRoom;
	
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
	
	public boolean inRoom() {
		return (currentRoom != null);
	}

	public LocName getCurrentRoom() {
		return currentRoom;
	}

	public void setCurrentRoom(LocName currentRoom) {
		this.currentRoom = currentRoom;
	}
}
