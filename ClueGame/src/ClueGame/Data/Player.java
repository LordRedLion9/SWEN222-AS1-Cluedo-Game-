package ClueGame.Data;
import java.util.ArrayList;

import ClueGame.Data.Character;
import ClueGame.Data.Location.LocName;

public class Player {
	
	private boolean active = true;
	private int playerNo;
	private Character playerChar; //Still needs to be assigned
	private Coordinate position;
	private LocName currentRoom;
	
	ArrayList<Clue> hand = new ArrayList<Clue>();
	
	public void addToHand(Clue clue){
		hand.add(clue);
	}
	
	public boolean getActive(){
		return active;
	}
	
	
	public void setActive(boolean val){
		active = val;
	}
	
	/**
	 * 
	 * print the players hand
	 *  
	 */
	public void printHand(){
		for (Clue c : hand){
			if (c instanceof Location){
				System.out.println(c.getType());
			}
		}
		for (Clue c : hand){
			if (c instanceof Character){
				System.out.println(c.getType());
			}
		}
		for (Clue c : hand){
			if (c instanceof Weapon){
				System.out.println(c.getType());
			}
		}
	}
	
	public ArrayList<Clue> getHand(){
		return hand;
	}
	
	/**
	 * 
	 * check if player has a card in thier hand
	 *  
	 * @param p clue to look for
	 * @return result
	 */
	public boolean contains(Clue p){
		for (Clue c : hand){
			if (c.getType().equals(p.getType())){return true;}
		}
		return false;
	}
	
	public Player(int playerNo){
		this.playerNo = playerNo;
		position = new Coordinate(0,0);
	}
	
	public int getNumber(){
		return playerNo;
	}
	
	public Character getCharacter(){ //Currently dangerous, will return unwritten field.
		return playerChar;
	}
	
	public void setCharacter(Character c){ //Currently dangerous, will return unwritten field.
		this.playerChar = c;
	}
	
	public Coordinate getPosition(){
		return position;
	}
	
	public void setPosition(Coordinate cord){
		position = cord;
	}
	
	/**
	 * 
	 * is the player in a room?
	 *  
	 * @return result
	 */
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
